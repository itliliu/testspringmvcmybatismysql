package com.we.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.we.bean.UserClientMap;

public interface UserClientMapMapper {
    int deleteByPrimaryKey(Integer mapid);

    int insert(UserClientMap record);

    int insertSelective(UserClientMap record);

    UserClientMap selectByPrimaryKey(Integer mapid);

    int updateByPrimaryKeySelective(UserClientMap record);
    
    List<UserClientMap> selectByUserClientMap(UserClientMap record);
    
    int updateIsDefaultByUid(UserClientMap record) throws SQLException;
    
    int updateByPrimaryKey(UserClientMap record);
    
    /**set isdefault = 1*/
    int setDefault(UserClientMap userClientMap) throws SQLException;
    
    /**Batch additions*/
    int insertClientid(@Param("list")List<Integer> list ,@Param("userid")int userid);

	List<Integer> getClientidByUserid(Integer integer);

	int deleteByClientid(Integer clientid);

	int deleteByUserId(Integer userid);

	List<Map<String, Object>> selectByClientidAndRole(Integer parentid);

	int updateClientByUserid(@Param("clientid") Integer clientid,@Param("list") List<Map<String, Object>> listMap);

	
    
}