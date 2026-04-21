package com.banking.api.controller;

import com.banking.api.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/accounts")
public class UserAccountsServlet extends HttpServlet {
	
	private AccountService accService;
	
	@Override
	public void init() {
		this.accService = new AccountService();
	}
	
	@Override
	public void doGet(HttpServletRequest req , HttpServletResponse resp) throws ServletException , IOException {
		
		HttpSession session = req.getSession(false);
		String username = (String) session.getAttribute("username");
		
		List<String> accounts = accService.getUserAccounts(username);
		
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		out.println("Your Account details:");
		
		for(String acc : accounts) {
			out.println(acc);
		}
	}

}
