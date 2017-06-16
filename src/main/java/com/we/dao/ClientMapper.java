package com.we.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.we.bean.Client;

public interface ClientMapper {
	int deleteByPrimaryKey(Integer clientid);

	int insert(Client record);

	int insertSelective(Client record);

	Client selectByPrimaryKey(Integer clientid);

	/**
	 * 
	 * selectParentByUid @param uid @return @return
	 * List<Map<String,Object>> @throws
	 */
	List<Map<String, Object>> selectParentByUid(int uid);

	List<Client> selectByParentId(int parentid);

	/** is parent child */
	int isParentByPrimaryKey(Integer clientid);

	int updateClient(Map<String, Object> clientMap) throws SQLException;;

	int updateByPrimaryKey(Client record);

	List<Client> selectAll();

	/** Find all client corresponding to the user */
	List<Client> getClientByUserid(Map<String, Object> map);

	/** find client where ParentID=0 */
	List<Map<String, Object>> getParents();

	/** find client where ParentID!=0 */
	List<Map<String, Object>> getChildren();

	int insertClient(Client record) throws SQLException;

	Client selectByClientName(String clientName);

	/**
	 * SELECT sql: select client name by client id
	 * 
	 * @param clientID
	 * @return
	 * @throws SQLException
	 */
	List<Map<String, Object>> getClientNameByClientID(Integer clientID) throws SQLException;

	/**
	 * selectChildByUid find child by uid when userclientmap userid = #{uiid}
	 * and userclientmap.clientid = Client.clientid. @param uid @return
	 * List<Map<String,Object>> @throws
	 */
	List<Map<String, Object>> selectChildByUid(int uid);

	/**
	 * Find the parent class according to the subclass id is parentid
	 */
	List<Map<String, Object>> selectParentByChild(List<Map<String, Object>> childList);

	List<Client> selectByUidAndParentId(@Param("uid") int uid, @Param("parentid") int parentid);

	boolean selectStatusByClientID(Integer clientID) throws SQLException;

	boolean selectClientStatusByTemplateID(Integer templateid) throws SQLException;

}