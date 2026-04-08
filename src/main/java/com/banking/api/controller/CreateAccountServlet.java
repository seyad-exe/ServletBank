package com.banking.api.controller;

import com.banking.api.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/accounts/create")
public class CreateAccountServlet extends HttpServlet {
	
	private AccountService accService;
	
	@Override
	public void init() {
		this.accService = new AccountService();
	}
	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		 
		 HttpSession session = req.getSession(false);
		 String username = (String) session.getAttribute("username");
		 
		 String newAccountNumber = accService.createNewAccount(username);
		 
		 if(newAccountNumber != null) {
			 resp.setStatus(201); //created
			 resp.getWriter().println("Successfully created new account: " + newAccountNumber + " with balance $0.00");
		 } else {
			 resp.setStatus(500); // server error
			 resp.getWriter().println("account creation failed");
		 }
	 }

}
