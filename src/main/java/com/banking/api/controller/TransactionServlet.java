package com.banking.api.controller;

import com.banking.api.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/transaction")
public class TransactionServlet extends HttpServlet {

	private AccountService accService;
	
	@Override
	public void init() throws ServletException {
		this.accService = new AccountService();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("username")==null) {
			resp.setStatus(401); //unauthorized
			resp.getWriter().println("login required to do transaction");
			return;
		}
		
		String username = (String) session.getAttribute("username");
		String type = req.getParameter("type");
		double amount;
		try {
			amount = Double.parseDouble(req.getParameter("amount"));
		}catch(NumberFormatException e){
			resp.setStatus(400); //bad request
			resp.getWriter().println("invalid amount format");
			return;
		}
		String resultMsg = accService.processTransaction(username, type.toUpperCase(), amount);
		
		if(resultMsg.equals("transaction completed successfully")) {
			resp.setStatus(200);
			resp.getWriter().println("Successfully processed " + type + " of $" + amount);
		}else {
			resp.setStatus(400);
			resp.getWriter().println("transaction failed: " + resultMsg);
		}
	}
}
