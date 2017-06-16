package com.we.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * Call procedure UserLogin to 
     * get the user's info by username. Result includs role, permission, default clientname and clientid and so on.
     * If the user's doesn't specify a default client, the clientname is "all" and the defaultclientid is -1
     * @param username
     * @return
     * @throws SQLException
     */
    Map<String, Object> vaildateUser(String username) throws SQLException;
   
    /**
     * 
    * @Title: getUserForPage 
    * @Description: TODO Create a temporary table containing ClientID 
    * and find the corresponding data through the temporary table
    * @param @param map
    * @param @return    
    * @return List<Map<String,Object>>   
    * @author mingqiangyao 
    * @throws
    * @date May 19, 2017 1:54:31 PM 
    * @version V1.0
     */
	List<Map<String, Object>> getUserForPage(Map<String, Object> map);

	int getCountUser(Map<String, Object> map);

	User selectByName(String username);

}