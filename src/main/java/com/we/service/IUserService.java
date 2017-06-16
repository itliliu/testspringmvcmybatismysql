package com.we.service;

import java.sql.SQLException;
import java.util.Map;

import com.we.bean.User;

public interface IUserService {
	
	/**
	 * To get the user info. Including role, permission, default client and 
	 * default clientid and so on.
	 * The password doesn't store in our mysql database. we use ADFS to 
	 * check whether the password is currently. so, the password's value is ""
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> userLogin(String username, String password) throws SQLException;
	
	int add(User user) throws SQLException;

	int deleteById(Integer userid) throws SQLException;

	int editById(User user) throws SQLException;
	
	
	Map<String, Object> getUserForPage(Map<String, Object> map) throws SQLException;
	
	boolean validateUserName(User user) throws SQLException;
	
	
}
