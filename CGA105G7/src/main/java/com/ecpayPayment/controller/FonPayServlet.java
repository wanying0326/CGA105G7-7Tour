package com.ecpayPayment.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.orderDetail.model.OrderDetailService;
import com.orderDetail.model.OrderDetailVO;
import com.roomOrder.controller.OrderPayManagement;
import com.roomOrder.model.RoomOrderService;
import com.roomOrder.model.RoomOrderVO;
import com.stock.model.StockService;
import com.stock.model.StockVO;
import com.Mes.model.MesService;

@WebServlet("/FonPay")
public class FonPayServlet extends HttpServlet {
	static String FONPAY_API_KEY = "588418532082";
	static String FONPAY_API_SECRET = "9Zc4DSfuzk5F4yrpGFfI";
	static String FONPAY_API_MERCHANT_CODE = "ME18315275";
	static String PAYMENT_CREATE_ORDER = "PaymentCreateOrder";
	
	private ScheduledExecutorService scheduler;

	public void init() throws ServletException {
        super.init();
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }
    
    public void destroy() {
        scheduler.shutdownNow();
        super.destroy();
    }

    public void canacelOrder(String paymentTransactionId){
        scheduler.schedule(() -> {
        	OrderPayManagement management = new OrderPayManagement();
            try {
				if (!management.paymentStatus(paymentTransactionId).equals("SUCCESS")) {
					RoomOrderService orderService = new RoomOrderService();
					List<RoomOrderVO> roomOrderVOs = orderService.getByPaymentTransactionId(paymentTransactionId);
					for(RoomOrderVO vo : roomOrderVOs) {
						vo.setOrderStatus(3);
						orderService.updateRoomOrder(vo);
		///////////////////通知會員訂單已取消
						byte[] buf = null;
						try (InputStream in = Files.newInputStream(Path.of(getServletContext().getRealPath("/front-end/room/images") + "/cancelled.png"))){
							buf = new byte[in.available()];
							in.read(buf);
						} catch (IOException e) {
							e.printStackTrace();
						}
						MesService mesService = new MesService();
						mesService.addMesVO(vo.getUserId(), "訂單已取消",
								"您的住宿訂單編號：" + vo.getOrderId() + "已取消",
								buf, new Timestamp(System.currentTimeMillis()), (byte) 1);
		///////////////歸還庫存量
						OrderDetailService orderDetailService = new OrderDetailService();
						List<OrderDetailVO> orderDetailList = orderDetailService.getByOrderID(vo.getOrderId());
						StockService stockService = new StockService();
						LocalDate lStartDay = vo.getCheckinDate();
						LocalDate lEndDay = vo.getCheckoutDate();
						Stream<LocalDate> stream = lStartDay.datesUntil(lEndDay, Period.ofDays(1));
						List<LocalDate> dateList = stream.collect(Collectors.toList());
						List<OrderDetailVO> orderDetailLists = orderDetailService.getByOrderID(vo.getOrderId());
						for(OrderDetailVO detailVO : orderDetailLists) {
							for(LocalDate day : dateList) {
								StockVO stockVO = stockService.getOneStock(detailVO.getRoomId(), day);
								if(stockVO != null) {
									stockVO.setRoomRest(stockVO.getRoomRest() + detailVO.getRoomAmount());
									stockService.updateStock(stockVO);
								}
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }, 30, TimeUnit.MINUTES);
    }
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
        
		int payTotal = (int) req.getAttribute("payTotal");
		int userId = (int) req.getAttribute("userId");
		List<Integer> orderIdList = (List<Integer>) req.getAttribute("orderIdList");
		
		// 測試卡號: 一般信用卡測試卡號 : 4311-9522-2222-2222 安全碼 : 222
	    URL url = new URL ("https://test-api.fonpay.tw/api/payment/"+PAYMENT_CREATE_ORDER);
	    HttpURLConnection con = (HttpURLConnection)url.openConnection();
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Accept", "application/json");
	    con.setRequestProperty("key",FONPAY_API_KEY);
	    con.setRequestProperty("secret", FONPAY_API_SECRET);
	    con.setRequestProperty("merchantCode", FONPAY_API_MERCHANT_CODE);
	    con.setRequestProperty("Content-Type", "application/json");
	    con.setRequestProperty("User-Agent", "Tibame_Student");
	    con.setRequestProperty("X-ignore", "true");
	    con.setDoOutput(true);
	    
	    String paymentNo = new String("7Tour訂房" + userId + System.currentTimeMillis());
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	    String paymentDueDate = LocalDateTime.now().plusMinutes(30).format(dateTimeFormatter);
	    
	    String jsonInputString = "{ " +
	               "'request':{" +
	               "'paymentNo':'" + paymentNo + "'," +
	               "'totalPrice':" + payTotal + "," +
	               "'paymentDueDate':'" + paymentDueDate + "'," +
	               "'itemName':'7Tour訂房'," +
	               "'redirectUrl':'http://" + req.getServerName() + ":8081" + req.getContextPath() + "/PayFinish'," +
	               "}," +
	               "'basic':{" +
	               "'appVersion':'0.9'," +
	               "'os':'IOS'," +
	               "'appName':'POSTMAN'," +
	               "'latitude':24.25," +
	               "'clientIp':'61.216.102.83'," +
	               "'lang':'zh_TW'," +
	               "'deviceId':'123456789'," +
	               "'longitude':124.25" +
	               "}}";
	    
	    try(OutputStream os = con.getOutputStream()) {
           byte[] input = jsonInputString.getBytes("utf-8");
           os.write(input, 0, input.length);
	    }
	    StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
           String responseLine = null;
           while ((responseLine = br.readLine()) != null) {
               response.append(responseLine.trim());
           }
        }
        
        
        //導向刷卡頁面
        HashMap<String, Object> map = new Gson().fromJson(response.toString(), HashMap.class);
		LinkedTreeMap result = (LinkedTreeMap) map.get("result");
		LinkedTreeMap payment = (LinkedTreeMap) result.get("payment");
		String paymentUrl = (String) payment.get("paymentUrl");
		String paymentTransactionId = (String) payment.get("paymentTransactionId");
		RoomOrderService orderService = new RoomOrderService();
		for(Integer orderId : orderIdList) {
			RoomOrderVO orderVO = orderService.getOneRoomOrder(orderId);
			orderVO.setPaymentTransactionId(paymentTransactionId);
			orderService.updateRoomOrder(orderVO);
		}
		//30分鐘未付款取消訂單
        canacelOrder(paymentTransactionId);
		res.sendRedirect(paymentUrl);
		return;
    }
}
