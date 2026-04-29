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
		String password= req.getParameter("password");
		//making sure both fields are not empty
		if(username == null||username.trim().isEmpty() || password==null|| password.trim().isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //first i used just 400 by default, but this is better practice
			resp.getWriter().println("enter both username and password");
			return;
		}
		//going to add regex logic here soon
		String usernameRegex = "^[a-zA-Z0-9_]{5,20}$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
		
        if (!username.matches(usernameRegex)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid username: Must be 5-20 characters and contain only letters, numbers, or underscores.");
            return;
        }
        
        if (!password.matches(passwordRegex)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Weak password: Must be at least 8 characters, include an uppercase, a lowercase, a number, and a special character.");
            return;
        }
		
		boolean isRegistered = userService.registerUser(username,password); //going to write this method now
		
		if (isRegistered) {
			resp.setStatus(HttpServletResponse.SC_CREATED); // value is 201, meaning its is created
			resp.getWriter().println("user registered");
		}
		else {
			resp.setStatus(HttpServletResponse.SC_CONFLICT); //409 , so there could be a conflict and the user already exists
			resp.getWriter().println("registration failed, user may already exist");
		}
	}
}
