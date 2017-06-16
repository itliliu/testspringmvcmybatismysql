package com.we.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.ProjectBriefResult;
import com.we.dao.ProjectBriefMapper;
import com.we.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService{
	@Resource
	private ProjectBriefMapper projectBriefMapper;
	
	@Override
	public List<Map<String, Object>> recentProjectByUidAndClientId(Integer uid, Integer clientId) throws SQLException {
		ProjectBriefResult projectBrief = new ProjectBriefResult();
		projectBrief.setUserid(uid);
		projectBrief.setClientID(clientId);
		return projectBriefMapper.selectRecentProjectByUidAndClient(projectBrief);
	}

	@Override
	public List<Map<String, Object>> getRecentProject(Integer userid,
			Integer clientId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("clientid", clientId);
		return this.projectBriefMapper.getRecentProject(paramMap);
	}

}
