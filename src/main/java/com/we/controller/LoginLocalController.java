package com.we.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.User;
import com.we.service.IUserService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "login")
public class LoginLocalController {
	
	private static Logger logger = Logger.getLogger(LoginLocalController.class);
	
	@Resource
	private IUserService userService;
	
	/**
	 * In order to avoid the complexity of development, we disable ADFS/ Azure AD,
	 * And use this interface to login to our web site.
	 * The password is hard code, value is hengtian@123
	 * @param request
	 * @param reponse
	 * @param message: login information
	 * @return
	 */
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto getRecentRequestForm(HttpServletRequest request,
			HttpServletResponse reponse,@RequestBody String message) {
		logger.info("call login.json");
		logger.info("parameter: " + message);
		ObjectMapper mapper = new ObjectMapper();
		ResultDto returnDto = null;
		try {
			User user = mapper.readValue(message, User.class);
			if("hengtian@123".equals(user.getPassword())){
				Map<String, Object> userMap = this.userService.userLogin(user.getUsername(), user.getPassword());
				String permission = String.valueOf(userMap.get("permission"));
				Map<String, Object> permissionMap = mapper.readValue(permission, new TypeReference<Map<String, Object>>() { });
				userMap.put("permission", permissionMap);
				reponse.setStatus(Constant.HTTP_OK);
				returnDto = new ResultDto(Constant.ACK, Constant.SUCCEED, userMap);
			}else{
				reponse.setStatus(Constant.HTTP_OK);
				returnDto = new ResultDto(Constant.NACK, "Username or Password error, Please check your Username and Password", null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Call Login.json met error because of parameters error", e);
			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.NACK, "Parameter error", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info("Call Login.json met error because of database error", e);
			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.NACK, "Login error", null);
		} catch (Exception e){
			logger.info("Call Login.json met error because of database error", e);
			reponse.setStatus(Constant.HTTP_OK);
			returnDto = new ResultDto(Constant.NACK, "Username or Password error, Please check your Username and Password", null);
		}
		return returnDto;
	}
	
}
