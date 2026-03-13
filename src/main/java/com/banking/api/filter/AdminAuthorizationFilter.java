package com.banking.api.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthorizationFilter implements Filter {
	@Override
	public void init(FilterConfig filterconfig) throws ServletException{
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp , FilterChain chain) throws IOException, ServletException{
		//type casted generic servlet object ---> specific http req/resp object
		HttpServletRequest httpreq = (HttpServletRequest) req;
		HttpServletResponse httpresp = (HttpServletResponse) resp;
		
		HttpSession session = httpreq.getSession(false);
	}
}
