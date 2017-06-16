package com.we.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.Client;
import com.we.bean.UserClientMap;

public interface IClientService {
	List<Map<String, Object>> selectParentByUid(int uid) throws SQLException;

	Map<String, Object> selectChildByUidAndParentId(int uid, int parentid) throws SQLException;

	int setDefault(UserClientMap userClientMap) throws SQLException;

	List<Client> getAll() throws SQLException;

	int add(Client client) throws SQLException;

	int editClient(Map<String, Object> clientMap) throws SQLException;

	List<Client> getClientByUserid(Integer userid) throws SQLException;

	Map<String, Object> getTree() throws SQLException;

	Client selectByClientName(String clientName) throws SQLException;

	Map<String, Object> getClientNameByClientID(Integer clientID) throws SQLException;

	boolean selectStatusByClientID(Integer clientID) throws SQLException;

	boolean selectClientStatusByTemplateID(Integer templateid) throws SQLException;

}
