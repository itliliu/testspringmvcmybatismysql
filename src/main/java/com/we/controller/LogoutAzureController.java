package com.we.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
public class LogoutAzureController {
	
	private Logger logger = Logger.getLogger(LogoutAzureController.class);
	
	@RequestMapping(value = "/logout.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto removeAllCookie(HttpServletRequest httpRequest, HttpServletResponse reponse){
		
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // Create httpget.    
            HttpGet httpget = new HttpGet("https://login.windows.net/zhonghongcloudbridgepro.onmicrosoft.com/oauth2/logout");  
            System.out.println("executing request " + httpget.getURI());  
            // execute get request    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // Get response entity
                HttpEntity entity = response.getEntity();
                if (entity != null) {  
                    logger.info("Response content length: " + entity.getContentLength()); 
                    logger.info("Response content: " + EntityUtils.toString(entity));  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            logger.info(e.getMessage());  
        } catch (ParseException e) {  
        	logger.info(e.getMessage());  
        } catch (IOException e) {  
        	logger.info(e.getMessage());
        } finally {  
            // close connect
            try {  
                httpclient.close();  
            } catch (IOException e) {  
            	logger.info(e.getMessage());
            }  
        }  
		
		Enumeration<String> e = httpRequest.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String sessionName = (String) e.nextElement();
			logger.info(sessionName);
			httpRequest.getSession().removeAttribute(sessionName);
		}
		
       
        //httpRequest.getSession().invalidate();
        Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				reponse.addCookie(cookie);
			}
		}
		return new ResultDto(Constant.ACK, "Succeed", 1);
	}
	
}
