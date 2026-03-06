package com.banking.api.controller;

import com.banking.api.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
//this is com=ntroller layer for  registering a new user
public class RegistrationServlet  extends HttpServlet{
	
	//userservice is required for the controller to wprk
	private UserService userService;
	
	//init is initializing userservice, runs once
	@Override
	public void init() throws ServletException{
		this.userService = new UserService();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		String username= req.getParameter("username"); 
		String password= req.getParameter("pasword");
		//making sure both fields are not empty
		if(username == null||username.trim().isEmpty() || password==null|| password.trim().isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //first i used just 400 by default, but this is better practice
			resp.getWriter().println("enter both username and password");
			return;
		}
		
		boolean isRegistered = userService.registerUser(username,password); //going to write this method now
	}
}
