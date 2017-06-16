package com.we.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.we.service.ProjectService;
import com.we.tool.Constant;
import com.we.tool.ResultDto;

@Controller
@Transactional(value = "mysql")
@RequestMapping(value = "project")
public class ProjectController {
	private static Logger logger = Logger.getLogger(ClientController.class);
		
	@Resource
	private ProjectService projectService;
	
	/**
	 * Get recent project from menu.
	 * @param userid
	 * @param clientId
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/recent.json", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto recentProject(Integer userid,Integer clientId,HttpServletRequest request, 
			HttpServletResponse reponse){
		try {
			List<Map<String, Object>> listMaps = projectService.getRecentProject(userid, clientId);
			reponse.setStatus(Constant.HTTP_OK);
			return new ResultDto(Constant.ACK, Constant.SUCCEED, listMaps);
		} catch (SQLException e) {
			reponse.setStatus(Constant.SERVER_ERROR);
			logger.error(e.toString());
			return new ResultDto(Constant.NACK, "recent Error", null);
		}
	}
}
