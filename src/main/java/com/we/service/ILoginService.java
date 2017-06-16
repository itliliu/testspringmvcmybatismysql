package com.we.service;

import java.sql.SQLException;

import com.we.bean.Role;


public interface ILoginService {
	
	/**
	 * Get Role information by username
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	Role getRoleInfo(String username) throws SQLException;
	
}
