package com.banking.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/api/*")
public class ApiAuthenticationFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp , FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpreq = (HttpServletRequest) req;
		HttpServletResponse httpresp = (HttpServletResponse) resp;
		HttpSession session = httpreq.getSession(false);
		
		if(session != null && session.getAttribute("username") != null) {
			chain.doFilter(req, resp);
		} else {
			httpresp.setStatus(401); //unauthorized 
			httpresp.getWriter().println("you must be logged in to access this API.");
		}
	}
}
