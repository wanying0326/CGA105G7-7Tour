package com.ecpayPayment.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Mes.model.MesService;
import com.Users.model.UsersService;
import com.Users.model.UsersVO;
import com.orderDetail.model.OrderDetailService;
import com.orderDetail.model.OrderDetailVO;
import com.room.model.RoomService;
import com.roomOrder.controller.OrderEmail;
import com.roomOrder.model.RoomOrderService;
import com.roomOrder.model.RoomOrderVO;
import com.vendor.model.VendorService;


@WebServlet("/PayFinish")
public class PayFinishServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	req.setCharacterEncoding("UTF-8");
    	String paymentTransactionId = req.getParameter("paymentTransactionId");
    	
    	RoomOrderService orderService = new RoomOrderService();
    	VendorService vendorService = new VendorService();
    	UsersService usersService = new UsersService();
    	OrderDetailService orderDetailService = new OrderDetailService();
    	RoomService roomService = new RoomService();
    	
    	List<RoomOrderVO> roomOrderVOList = orderService.getByPaymentTransactionId(paymentTransactionId);
    	UsersVO usersVO = usersService.getOneUser(roomOrderVOList.get(0).getUserId());
    	StringBuilder messageText = new StringBuilder("親愛的顧客" + usersVO.getUserName() + "您好<br>"
    			+ "    您" + LocalDate.now() + "於本網站訂購以下住宿，訂單已成立"
    			+ "    <br>");
		List<OrderDetailVO> detailList = new ArrayList<OrderDetailVO>();
    	for(RoomOrderVO vo : roomOrderVOList) {
 //////////訂單狀態更新已付款   		
    		vo.setOrderStatus(0);
    		orderService.updateRoomOrder(vo);
    		messageText.append("    <table style=\"border-collapse:collapse; border: 1px solid black; text-align: center; margin: 5px;\">"
	      		+ "        <tr>"
	      		+ "            <th style=\"background-color:#75c6e0; padding: 10px;\">" + vendorService.getOneVendor(vo.getVenId()).getVenName() 
	      		+ "<br>" + vo.getCheckinDate() + " 至 " + vo.getCheckoutDate() + "</th>"
	      		+ "        </tr>"
	      		+ "        <tr style=\"border-collapse:collapse; border: 1px solid black;\"><td>");
    		List<OrderDetailVO> byOrderID = orderDetailService.getByOrderID(vo.getOrderId());
    		detailList.addAll(byOrderID);
    	}
    	
    	//郵件內容
		int listcount = 0;
		for(OrderDetailVO vo : detailList) {
			if(listcount != 0) {
				messageText.append("<br>" + roomService.getOneRoom(vo.getRoomId()).getRoomName() + vo.getRoomAmount() + "間");
			}else {
				messageText.append(roomService.getOneRoom(vo.getRoomId()).getRoomName() + vo.getRoomAmount() + "間");
			}
		}
		messageText.append("</td></tr></table>");
		
////////寄出通知訂單成立郵件
		String subject = "【7Tour】親愛的顧客您好，您的訂單已成立";
		messageText.append("    訂單明細可至會員中心【我的訂單】查詢<br>"
	      		+ "    <br>"
	      		+ "    此為系統通知郵件，請勿回覆此郵件。<br>"
	      		+ "    <br>"
	      		+ "    感謝您使用7Tour旅遊網！<br>"
	      		+ "    祝您有個美好的旅程~<br>"
	      		+ "    7Tour訂房中心");	 
		OrderEmail orderEmail = new OrderEmail();
	    orderEmail.sendMail(usersVO.getUserAccount(), subject, messageText.toString());	
 /////// 站內通知
		byte[] buf = null;
		try (InputStream in = Files.newInputStream(Path.of(getServletContext().getRealPath("/front-end/room/images") + "/shopping-bag.png"))){
			buf = new byte[in.available()];
			in.read(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MesService mesService = new MesService();
		mesService.addMesVO(usersVO.getUserId(), "訂單已成立",
				"您的住宿訂單已成立，詳情可至我的訂單中查看",
				buf, new Timestamp(System.currentTimeMillis()), (byte) 1);
    	
		req.getRequestDispatcher("/RoomOrder?action=toThisUserOrder").forward(req, res);
		return;
    }
}
