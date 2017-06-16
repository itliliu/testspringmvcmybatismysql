package com.we.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.User;
import com.we.bean.UserClientMap;
import com.we.dao.UserClientMapMapper;
import com.we.dao.UserMapper;
import com.we.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserClientMapMapper userClientMapMapper;
	
	/**
	 * To get the user info. Including role, permission, default client and 
	 * default clientid and so on.
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> userLogin(String username, String password) throws SQLException {
		return this.userMapper.vaildateUser(username);
	}
	
	/**
	 * add table user and add userclientmap table
	 */
	@Override
	public int add(User user) throws SQLException {
		this.userMapper.insert(user);
		if(user.getRoleid()!=1){
			UserClientMap userClientMap = new UserClientMap();
			userClientMap.setClientid(-1);
			userClientMap.setUserid(user.getUserid());
			userClientMap.setIsdefault(0);
			userClientMapMapper.insert(userClientMap);
		}
		else if(!user.getClientids().isEmpty()){
			userClientMapMapper.insertClientid(user.getClientids(), user.getUserid());
		}
		return user.getUserid();
	}

	@Override
	public int deleteById(Integer userid) throws SQLException {
		return this.userMapper.deleteByPrimaryKey(userid);
	}
	
	/**
	 * If the user is accountteam, first remove the relevant 
	 * data in the UCM table, and then increase it again
	 */
	@Override
	public int editById(User user) throws SQLException {
		if(user.getRoleid()!=null){
			
			if(user.getRoleid()==1){
				userClientMapMapper.deleteByUserId(user.getUserid());
				userClientMapMapper.insertClientid(user.getClientids(), user.getUserid());
			}
		}
		return this.userMapper.updateByPrimaryKeySelective(user);
	}
	
	/**Get current page data*/
	@Override
	public Map<String, Object> getUserForPage(Map<String, Object> map) throws SQLException {
		Map<String, Object> returnmap = new HashMap<String,Object>();
		returnmap.put("data", userMapper.getUserForPage(map));
		returnmap.put("pageNumber", this.userMapper.getCountUser(map));
		return returnmap;
	}
	
	/**
	 * Find users by name
	 */
	@Override
	public boolean validateUserName(User user) throws SQLException {
		User oldUser = userMapper.selectByName(user.getUsername());
		if(oldUser!=null) return false;
		return true;
	}


}
