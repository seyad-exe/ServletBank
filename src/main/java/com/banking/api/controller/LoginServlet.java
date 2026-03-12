package com.banking.api.controller;
import com.banking.api.model.User;
import com.banking.api.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private AuthService authservice;
	
	@Override
	public void init() throws  ServletException{
		this.authservice = new AuthService();
	}
	@Override
	protected void doPost(HttpServletRequest req,HttpServletResponse resp )
		throws ServletException,IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User loggedInUser = authservice.authenticate(username, password);
		
		if(loggedInUser != null) {
			HttpSession session = req.getSession(true);
			//storing username and role for this session
			session.setAttribute("username", loggedInUser.getusername());
			session.setAttribute("role", loggedInUser.getrole());
			
			resp.setStatus(200); //OK
			resp.getWriter().println("login success for" + loggedInUser.getrole() + loggedInUser.getusername());
		}
		else {
			resp.setStatus(401); // unauthorized
			resp.getWriter().println("invalid credentials");
		}
		
	}
	
	
}
