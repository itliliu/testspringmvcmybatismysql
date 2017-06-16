package com.we.dao;

import com.we.bean.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    /**
     * Get role info by username.
     * 	The value of username is user's name or user's email
     * @param username
     * @return
     */
    Role getRoleByNameOrEmail(String username);
}