package com.we.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.Client;
import com.we.bean.User;
import com.we.bean.UserClientMap;
import com.we.dao.ClientMapper;
import com.we.dao.UserClientMapMapper;
import com.we.dao.UserMapper;
import com.we.service.IClientService;
import com.we.tool.Constant;

@Service("ClientService")
public class ClientServiceImpl implements IClientService {
	@Resource
	private ClientMapper clientMapper;
	@Resource
	private UserClientMapMapper userClientMapMapper;
	@Resource
	private UserMapper userMapper;

	/**
	 * Get all parent clients by userid. if user is AccountTeam only see what
	 * belongs to it
	 * 
	 * @param userid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectParentByUid(int uid) {
		User user = userMapper.selectByPrimaryKey(uid);
		List<Map<String, Object>> parentList;
		List<Map<String, Object>> list = clientMapper.selectParentByUid(uid);
		// user is AccountTeam
		if (user.getRoleid() == 1) {

			List<Map<String, Object>> childList = clientMapper.selectChildByUid(uid);
			if (childList.size() > 0) {
				parentList = clientMapper.selectParentByChild(childList);
				// find all child beacuse list no have parentList
				list.addAll(parentList);
				// Duplicate removal
				for (int i = 0; i < list.size() - 1; i++) {
					for (int j = list.size() - 1; j > i; j--) {
						if (list.get(j).get("id").equals(list.get(i).get("id"))) {
							list.remove(j);
						}
					}
				}
				// Sort by name
				Collections.sort(list, new Comparator<Map<String, Object>>() {
					public int compare(Map<String, Object> o1, Map<String, Object> o2) {
						String name1 = (String) o1.get("name");
						String name2 = (String) o2.get("name");
						return name1.compareTo(name2);
					}

				});
			}
		}
		List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			int clientid = (int) map.get("id");
			if (clientMapper.isParentByPrimaryKey(clientid) > 0) {
				map.put(Constant.STATUS, "parent");
			} else {
				map.put(Constant.STATUS, "");
			}
			returnMap.add(map);
		}
		return returnMap;
	}

	/**
	 * find child by uid and parentid if user is AccountTeam only see what
	 * belongs to it
	 * 
	 * @param uid
	 * @param parentid
	 * @return
	 */
	@Override
	public Map<String, Object> selectChildByUidAndParentId(int uid, int parentid) {
		User user = userMapper.selectByPrimaryKey(uid);
		Client parentClient = clientMapper.selectByPrimaryKey(parentid);
		if (parentClient == null)
			return null;
		List<Client> list;
		if (user.getRoleid() == 1) {
			list = clientMapper.selectByUidAndParentId(uid, parentid);
		} else {
			list = clientMapper.selectByParentId(parentid);
		}

		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> childMap = new HashMap<String, Object>();
			childMap.put("id", list.get(i).getClientid());
			childMap.put("name", list.get(i).getClientname());
			childMap.put("isDeactivated", list.get(i).getStatus());
			childMap.put("default", 0);
			if (clientMapper.isParentByPrimaryKey(list.get(i).getClientid()) > 0) {
				childMap.put(Constant.STATUS, "parent");
			} else {
				childMap.put(Constant.STATUS, "");
			}
			childList.add(childMap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentClient.getClientid());
		map.put("parentName", parentClient.getClientname());
		map.put("childClient", childList);
		return map;
	}

	/**
	 * set UserClientMap default,set isdefault = 1
	 * 
	 * @param userClientMap
	 * @return
	 */
	@Override
	public int setDefault(UserClientMap userClientMap) throws SQLException {
		return this.userClientMapMapper.setDefault(userClientMap);
	}

	@Override
	public List<Client> getAll() throws SQLException {
		return this.clientMapper.selectAll();
	}

	/**
	 * 
	 */
	@Override
	public int add(Client client) throws SQLException {
		int i = this.clientMapper.insertClient(client);
		if (client.getParentid() != 0) {
			client = clientMapper.selectByClientName(client.getClientname());
			List<Map<String, Object>> listMap = userClientMapMapper.selectByClientidAndRole(client.getParentid());
			if (listMap.size() > 0) {
				userClientMapMapper.updateClientByUserid(client.getClientid(), listMap);
			}
		}
		return i;
	}

	@Override
	public int editClient(Map<String, Object> clientMap) throws SQLException {
		return this.clientMapper.updateClient(clientMap);
	}

	/**
	 * Get client by userid if user is AccountTeam only see what belongs to it
	 * 
	 * @param userid
	 * @return
	 */
	@Override
	public List<Client> getClientByUserid(Integer userid) {
		User user = userMapper.selectByPrimaryKey(userid);
		Map<String, Object> map = new HashMap<String, Object>();
		// if user is AccountTeam
		if (user.getRoleid() == 1) {
			List<Integer> userClientids = userClientMapMapper.getClientidByUserid(userid);
			if (userClientids.size() < 1) {
				return null;
			}
			map.put("userClientids", userClientids);
		}
		map.put("userid", userid);
		return clientMapper.getClientByUserid(map);
	}

	/**
	 * get all client
	 */
	@Override
	public Map<String, Object> getTree() throws SQLException {
		List<Map<String, Object>> listParents = clientMapper.getParents(); // parentid
																			// 0
		List<Map<String, Object>> listChilds = clientMapper.getChildren(); // parentid
																			// !=0
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent", listParents);
		map.put("child", listChilds);
		return map;
	}

	@Override
	public Client selectByClientName(String clientName) throws SQLException {
		return clientMapper.selectByClientName(clientName);
	}

	/**
	 * Get client name by ClientID.
	 * 
	 * @param clientID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Map<String, Object> getClientNameByClientID(Integer clientID) throws SQLException {
		List<Map<String, Object>> clientList = this.clientMapper.getClientNameByClientID(clientID);
		if (clientList.size() < 1) {
			return null;
		}
		return clientList.get(0);
	}

	@Override
	public boolean selectStatusByClientID(Integer clientID) throws SQLException {
		return this.clientMapper.selectStatusByClientID(clientID);
	}

	@Override
	public boolean selectClientStatusByTemplateID(Integer templateid) throws SQLException {
		return this.clientMapper.selectClientStatusByTemplateID(templateid);
	}

}
