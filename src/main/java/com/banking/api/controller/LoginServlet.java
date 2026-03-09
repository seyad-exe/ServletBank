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
	
	
	
}
