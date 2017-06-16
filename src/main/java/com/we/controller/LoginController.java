package com.we.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.Role;
import com.we.service.ILoginService;
import com.we.service.IUserService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "WEData")
public class LoginController implements SAMLUserDetailsService {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private ILoginService rolesService;
	@Resource
	private IUserService userService;
	
	/**
	 * Login with ADFS
	 * 	the class need implements SAMLUserDetailsService 
	 * 	so that we can get the username after login ADFS successfully.
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/init.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto getRecentRequestForm(HttpServletRequest request,
			HttpServletResponse reponse) {
		logger.info("Call init.json");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = auth.getName(); 
		Role rolesByUserName = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			rolesByUserName = rolesService.getRoleInfo(username);
			if(rolesByUserName == null){
				reponse.setStatus(Constant.VALIDATION_ERROR);
				return new ResultDto(Constant.NACK, "Please contact your administrator to obtain permissions.", null);
			}else{
				Map<String, Object> userMap = this.userService.userLogin(username, "");
				String permission = String.valueOf(userMap.get("permission"));
				Map<String, Object> permissionMap = mapper.readValue(permission, new TypeReference<Map<String, Object>>() { });
				userMap.put("permission", permissionMap);
				reponse.setStatus(Constant.HTTP_OK);
				return new ResultDto(Constant.ACK, Constant.SUCCEED, userMap);
			}
			
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			logger.info("Call init.json met error because database error");
			reponse.setStatus(Constant.VALIDATION_ERROR);
			return new ResultDto(Constant.NACK, "Login Error", null);
		}
	}
	
	@Override
	public Object loadUserBySAML(SAMLCredential arg0)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}