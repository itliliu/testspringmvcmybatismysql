package com.we.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProjectService {
	 List<Map<String,Object>> recentProjectByUidAndClientId(Integer userid,Integer clientid)throws SQLException;
	 List<Map<String,Object>> getRecentProject(Integer userid,Integer clientid)throws SQLException;
}
