package com.roomOrder.controller;

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
import com.roomOrder.model.RoomOrderService;
import com.roomOrder.model.RoomOrderVO;
import com.shoppingCart.RoomItem;
import com.stock.model.StockService;
import com.Users.model.UsersVO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;

@WebServlet("/Pay")
public class PayServlet extends HttpServlet {
	static String FONPAY_API_KEY = "588418532082";
	static String FONPAY_API_SECRET = "9Zc4DSfuzk5F4yrpGFfI";
	static String FONPAY_API_MERCHANT_CODE = "ME18315275";
	static String PAYMENT_CREATE_ORDER = "PaymentCreateOrder";

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	Integer orderId = Integer.valueOf(req.getParameter("orderId"));
    	RoomOrderService roomOrderService = new RoomOrderService();
    	RoomOrderVO roomOrderVO = roomOrderService.getOneRoomOrder(orderId);
    	String paymentTransactionId = roomOrderVO.getPaymentTransactionId();
    	
    	URL url = new URL ("https://test-api.fonpay.tw/api/payment/paymentQueryOrder");
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
		
	    System.out.println(paymentTransactionId);
		    
	    String jsonInputString = "{ " +
	               "'request':{" +
	               "'paymentTransactionId':'" + paymentTransactionId + "'" +
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
        System.out.println(response);
        
        //導向刷卡頁面
        HashMap<String, Object> map = new Gson().fromJson(response.toString(), HashMap.class);
        System.out.println(map.get("result"));
		LinkedTreeMap result = (LinkedTreeMap) map.get("result");
		LinkedTreeMap payment = (LinkedTreeMap) result.get("payment");
		String paymentUrl = (String) payment.get("paymentUrl");
		res.sendRedirect(paymentUrl);
    }
}
