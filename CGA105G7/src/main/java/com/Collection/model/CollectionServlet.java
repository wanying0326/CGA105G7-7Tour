package com.Collection.model;



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ColArt.model.ColArtService;
import com.ColIti.model.ColItiService;
import com.ColVen.model.ColVenService;
import com.Users.model.UsersVO;



@MultipartConfig
@WebServlet("/collectionServlet")
public class CollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		if ("deleteVen".equals(action)) { // 來自collection.jsp

				/***************************1.接收請求參數***************************************/
				Integer venId = Integer.valueOf(req.getParameter("venId"));
				Integer userId = Integer.valueOf(req.getParameter("userId"));
	
				/***************************2.開始刪除資料***************************************/
				ColVenService colVenSvc = new ColVenService();
				colVenSvc.deleteColVen(venId,userId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/collection/collection.jsp");// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
		if ("deleteArt".equals(action)) { // 來自myArt.jsp

			/***************************1.接收請求參數***************************************/
			Integer artId = Integer.valueOf(req.getParameter("artId"));
			Integer userId = Integer.valueOf(req.getParameter("userId"));

			/***************************2.開始刪除資料***************************************/
			ColArtService colArtSvc = new ColArtService();
			colArtSvc.deleteColArt(artId,userId);
			
			/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/collection/myArt.jsp");// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
	}
		if ("deleteTrip".equals(action)) { // 來自myTrip.jsp

			/***************************1.接收請求參數***************************************/
			Integer tripShareId = Integer.valueOf(req.getParameter("tripShareId"));
			Integer userId = Integer.valueOf(req.getParameter("userId"));

			/***************************2.開始刪除資料***************************************/
			ColItiService colItiSvc = new ColItiService();
			colItiSvc.deleteColIti(tripShareId,userId);
			
			/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/collection/myTrip.jsp");// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
	}
		if ("loginout".equals(action)) {// 來自loginout.jsp的請求
			req.getSession().invalidate(); 
 res.sendRedirect(req.getContextPath()+"/front-end/member/login.jsp");
		
		}
		if ("collection".equals(action)) {// 來自loginout.jsp的請求
			UsersVO usersVO=(UsersVO) req.getSession().getAttribute("usersVO");
			try {
				if(usersVO==null) {
				res.sendRedirect(req.getContextPath()+"/front-end/member/login.jsp");
					return;
				}
			} catch (NullPointerException e) {
					e.printStackTrace();
			}
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/collection/collection.jsp");
			successView.forward(req, res);
		}
	}

}
