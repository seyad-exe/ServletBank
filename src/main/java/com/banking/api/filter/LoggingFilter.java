package com.banking.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("/*")
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp , FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpreq = (HttpServletRequest) req;
		
		String method = httpreq.getMethod();
		String uri = httpreq.getRequestURI();
		String clientIP = httpreq.getRemoteAddr();
		
		System.out.println("[" + LocalDateTime.now() + "] " + method + " request from " + clientIP + " to " + uri);
		
		chain.doFilter(req, resp);
	}
}
