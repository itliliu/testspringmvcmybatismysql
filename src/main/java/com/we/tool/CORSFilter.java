package com.we.tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;



public class CORSFilter implements Filter {

	@Override
	public void destroy() {



	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
		httpResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("X-Powered-By", "3.2.1");
		httpResponse.setHeader("Content-Type", "application/json;charset=utf-8");
		
		chain.doFilter(request, response);
		
		
	}

	

   
	@Override
    public void init(FilterConfig config) throws ServletException {
		
		
    }

}
