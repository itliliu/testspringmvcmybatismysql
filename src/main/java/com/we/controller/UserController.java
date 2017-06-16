package com.we.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.User;
import com.we.service.IUserService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;
@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	/**
	 * Add a user
	 *  If the user is accountteam, you must have ClientIDS
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/add.json", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto add(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(message, User.class);
			if(user.getRoleid()==null){
				return new ResultDto(Constant.NACK, "The role is null ", null);
			}
			if(user.getRoleid()==1 && user.getClientids().size()<=0){
				return new ResultDto(Constant.NACK, "The clientids is null ", null);
			}
			if(!userService.validateUserName(user)){
				return new ResultDto(Constant.NACK, "The username has exists ", null);
			}
			int userid = userService.add(user);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, userid);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "add parameter error ", null);
		}  catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "add sql error ", null);
		}
	}
	
	/**
	 * Delete a user.
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public ResultDto deleteById(HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(message, User.class);
			userService.deleteById(user.getUserid());
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "delete parameter error ", null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "delete sql error", null);
		}
	}
	
	/**
	 * Edit a user
	 *  If the user is accountteam, you must have ClientIDS
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/edit.json", method = RequestMethod.PUT)
	@ResponseBody
	public ResultDto edit( HttpServletRequest request,
			HttpServletResponse reponse, @RequestBody String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(message, User.class);
			if(user.getRoleid()==null){
				return new ResultDto(Constant.NACK, "The role is null ", null);
			}
			if(user.getRoleid()==1 && user.getClientids().size()<=0){
				return new ResultDto(Constant.NACK, "The clientids is null ", null);
			}
			userService.editById(user);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, null);
		} catch (IOException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit parameter error ", null);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "edit sql error ", null);
		}
	}
	

	/**
	 * Search user by username or email or  id
	 * @param keyword
	 * @param pageIndex
	 * @param pageSize
	 * @param clientId
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/searchuser.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto searchUser(String keyword,Integer pageIndex ,Integer pageSize,Integer clientId,
			HttpServletRequest request, HttpServletResponse reponse) {
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("keyword", keyword);
			map.put("rows", pageSize);
			map.put("clientid", clientId);
			map.put("page", (pageIndex-1)*pageSize);
			Map<String, Object> templates = this.userService.getUserForPage(map);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, templates);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "get detailed fail", null);
		}
	}
	
	
	
	
}
