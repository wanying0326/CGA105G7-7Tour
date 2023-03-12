package com.ecpayPayment.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.shoppingCart.RoomItem;
import com.stock.model.StockService;
import com.Users.model.UsersVO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;

@WebServlet("/FonPay")
public class FonPayServlet extends HttpServlet {
	static String FONPAY_API_KEY = "588418532082";
	static String FONPAY_API_SECRET = "9Zc4DSfuzk5F4yrpGFfI";
	static String FONPAY_API_MERCHANT_CODE = "ME18315275";
	static String PAYMENT_CREATE_ORDER = "PaymentCreateOrder";

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
		Vector<RoomItem> paylist = (Vector<RoomItem>) req.getSession().getAttribute("paymentItems");
		StockService stockService = new StockService();
		int total = 0;////總金額
////////// 確認庫存
		for (RoomItem roomItem : paylist) {
			Stream<LocalDate> stream = roomItem.getCheckinDate().datesUntil(roomItem.getCheckoutDate(),
					Period.ofDays(1));
			List<LocalDate> dateList = stream.collect(Collectors.toList());
			for (LocalDate day : dateList) {
				if (stockService.getOneStock(roomItem.getRoomId(), day).getRoomRest() < roomItem.getAmount()) {
					req.setAttribute("lossStock", "error");
					req.getRequestDispatcher("/ShoppingCart?action=showCart").forward(req, res);
					return; // 如果其中有一天庫存小於需求，中斷回傳
				}
			}
			total += roomItem.getPrice() * roomItem.getAmount();
		}
		UsersVO usersVO = (UsersVO) req.getSession().getAttribute("usersVO");
		Integer couponNo = (req.getParameter("couponNo") == null || req.getParameter("couponNo").equals("")) ? null : Integer.parseInt(req.getParameter("couponNo"));
		int couponDiscount = 0; ////優惠券折抵金額
		CouponService couponService = new CouponService();
		if(!(couponNo == null)) {
			CouponVO couponVO = couponService.getOneCoupon(couponNo);
			if(couponVO.getCouponType().equals(0)) {
				couponDiscount = couponVO.getCouponDiscount().intValue();
			}else {
				BigDecimal discount = new BigDecimal(couponVO.getCouponDiscount());
				BigDecimal totalPrice = new BigDecimal(total);
				discount = totalPrice.subtract(totalPrice.multiply(discount)).setScale(0, RoundingMode.HALF_UP);
				couponDiscount = discount.intValue();
			}
		}
		Boolean bonusPointsUse = (req.getParameterValues("bonus") != null ) ? true : false;
		Integer bonusPoint = 0; ////紅利點數
		if(bonusPointsUse) {
			if(total > usersVO.getBonusPoints()) {
				bonusPoint = usersVO.getBonusPoints();
			}else {
				bonusPoint = total;
			}
		}
		int payTotal = total - couponDiscount - bonusPoint;//付款金額
		
		String customerName = req.getParameter("customerName");
		String phone = req.getParameter("customerPhone");
		String email = req.getParameter("customerEmail");
		
		HashMap<String, Object> payInfo = new HashMap<String, Object>();
		payInfo.put("couponNo", couponNo);
		payInfo.put("customerName", customerName);
		payInfo.put("customerPhone", phone);
		payInfo.put("customerEmail", email);
		payInfo.put("orgPrice", total);
		payInfo.put("couponDiscount", couponDiscount);
		payInfo.put("bonusPoint", bonusPoint);
		req.getSession().setAttribute("payInfo", payInfo);
		
		if(payTotal < 1) {
			req.getRequestDispatcher("/ShoppingCart?action=newOrder").forward(req, res);
			return;
		}
        
		
		
		
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
	    
	    String paymentNo = new String("7Tour訂房" + usersVO.getUserId() + System.currentTimeMillis());
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	    String paymentDueDate = LocalDateTime.now().plusMinutes(30).format(dateTimeFormatter);
	    
	    String jsonInputString = "{ " +
	               "'request':{" +
	               "'paymentNo':'" + paymentNo + "'," +
	               "'totalPrice':" + payTotal + "," +
	               "'paymentDueDate':'" + paymentDueDate + "'," +
	               "'itemName':'7Tour訂房'," +
//	               "'callbackUrl':'https://test-platform.wecometw.com/fonpay/payment/1'," +
	               "'redirectUrl':'http://" + req.getServerName() + ":8081" + req.getContextPath() + "/ToCreateOrder'," +
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
		res.sendRedirect(paymentUrl);
		return;
    }
}
