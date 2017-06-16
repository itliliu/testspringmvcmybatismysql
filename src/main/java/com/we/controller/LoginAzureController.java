package com.we.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.we.service.IUserService;
import com.we.tool.AuthHelper;
import com.we.tool.Constant;

@Controller
@Transactional(value = "mysql")
public class LoginAzureController {

	private static Logger logger = Logger.getLogger(LoginLocalController.class);

	@Resource
	private IUserService userService;

	@RequestMapping(value = "/login.json", method = { RequestMethod.GET, RequestMethod.POST })
	public String getDirectoryObjects(HttpServletRequest httpRequest, HttpServletResponse reponse) {
		HttpSession session = httpRequest.getSession();
		AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);

		logger.info("call login.json");
		ObjectMapper mapper = new ObjectMapper();
		//TODO ResultDto retrnDto = null;
		//TODO ModelAndView modelAndView = null;
		try {

			if (result == null) {
				reponse.setStatus(Constant.HTTP_OK);
				//TODO returnDto = new ResultDto(Constant.NACK,
						//"Username or Password error, Please check your Username and Password", null);
			} else {

				String userLoginName = result.getUserInfo().getDisplayableId();
				String userName = userLoginName.split("@")[0];

				Map<String, Object> userMap = this.userService.userLogin(userName, null);
				String permission = String.valueOf(userMap.get("permission"));
				Map<String, Object> permissionMap = mapper.readValue(permission,
						new TypeReference<Map<String, Object>>() {
						});
				userMap.put("permission", permissionMap);
				reponse.setStatus(Constant.HTTP_OK);
				//TODO returnDto = new ResultDto(Constant.ACK, Constant.SUCCEED, userMap);
				String jsonfromMap = mapper.writeValueAsString(userMap);
				logger.info(jsonfromMap);
				String urlStr = URLEncoder.encode(jsonfromMap, "UTF-8");
				logger.info(urlStr);
				Cookie cookie = new Cookie("permission", urlStr);
				cookie.setMaxAge(Constant.COOKIETVALIDITYTIME);//
				cookie.setPath("/");
				//TODO cookie.setDomain("172.16.129.246");
				reponse.setContentType("text/html");
				reponse.addCookie(cookie);

			}

		} catch (IOException e) {
			logger.info("Call Login.json met error because of parameters error", e);
			reponse.setStatus(Constant.HTTP_OK);
			//TODO returnDto = new ResultDto(Constant.NACK, "Parameter error", null);
		} catch (SQLException e) {
			logger.info("Call Login.json met error because of database error", e);
			reponse.setStatus(Constant.HTTP_OK);
			//TODO returnDto = new ResultDto(Constant.NACK, "Login error", null);
		} catch (Exception e) {
			logger.info("Call Login.json met error because of database error", e);
			reponse.setStatus(Constant.HTTP_OK);
			//TODO returnDto = new ResultDto(Constant.NACK,
					//"Username or Password error, Please check your Username and Password", null);
		}
		
		//TODO return returnDto;
		return "index";
	}
}
