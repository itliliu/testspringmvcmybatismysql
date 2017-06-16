package com.we.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.Role;
import com.we.dao.RoleMapper;
import com.we.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	
	@Resource
	private RoleMapper roleMapper;
	
	/**
	 * Get Role information by username
	 * 	the value of username is user's name or user's email.
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Role getRoleInfo(String username) throws SQLException {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByNameOrEmail(username);
	}

}
