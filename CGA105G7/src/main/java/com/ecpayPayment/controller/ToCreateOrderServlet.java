package com.ecpayPayment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ToCreateOrder")
public class ToCreateOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	String paymentTransactionId = req.getParameter("paymentTransactionId");
    	req.setAttribute("paymentTransactionId", paymentTransactionId);
		req.getRequestDispatcher("/ShoppingCart?action=newOrder").forward(req, res);
		return;
    }
}
