package com.roomOrder.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class OrderPayManagement {
	static String FONPAY_API_KEY = "588418532082";
	static String FONPAY_API_SECRET = "9Zc4DSfuzk5F4yrpGFfI";
	static String FONPAY_API_MERCHANT_CODE = "ME18315275";
	
	 public void cancelOrder(String paymentTransactionId) throws IOException {
 		
		    URL url = new URL ("https://test-api.fonpay.tw/api/payment/paymentCancelOrder");
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
		    return;
	    }
	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
		public boolean refundable(String paymentTransactionId) throws IOException {
	    		
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
	        
	        
	        HashMap<String, Object> map = new Gson().fromJson(response.toString(), HashMap.class);
			LinkedTreeMap result = (LinkedTreeMap) map.get("result");
			LinkedTreeMap payment = (LinkedTreeMap) result.get("payment");
			return (boolean) payment.get("refundable");
	        
	    }
	 
	 public void refundOrder(Integer price, String paymentTransactionId, Integer orderId) throws IOException {
 		
		    URL url = new URL ("https://test-api.fonpay.tw/api/payment/paymentRefundOrder");
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
		    
		    
		    String jsonInputString = "{ " +
		               "'request':{" +
		               "'refundPrice':" + price + "," +
		               "'paymentTransactionId':'" + paymentTransactionId + "'," +
		               "'refundNo':'" + orderId + "'" +
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
		    
		    return;
	    }
}
