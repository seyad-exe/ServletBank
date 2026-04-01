package com.banking.api.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req , HttpServletResponse resp) throws ServletException , IOException {
		//only the session we created is used, new session is not created because of "false"
		HttpSession session = req.getSession(false);
		
		if(session == null|| session.getAttribute("role") == null) {
			resp.setStatus(401); //unauthorized
			resp.getWriter().println("log in required");
			return;
		}
		
		String userRole = (String) session.getAttribute("role");
		
		if("USER".equals(userRole)){
			resp.setStatus(403); //forbidden
			resp.getWriter().println("user not allowed access");
		}
		if("ADMIN".equals(userRole)){
			resp.setStatus(200); //ok
			resp.getWriter().println("welcome to admin dashboard");
		}
	}

}
