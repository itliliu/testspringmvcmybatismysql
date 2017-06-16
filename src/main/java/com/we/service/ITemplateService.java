package com.we.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.Template;

public interface ITemplateService {

	List<Map<String, Object>> recentTemplate(int userid, int clientId, String type) throws SQLException;

	Map<String, Object> getById(int templateId) throws SQLException;

	int deleteById(int templateId) throws SQLException;

	int editById(Template template) throws SQLException;

	List<Map<String, Object>> getTemplateListForReport(Map<String, Object> paramMap) throws SQLException;

	int add(Template template) throws SQLException;

	List<Template> getAll() throws SQLException;

	Map<String, Object> selectType(Map<String, Object> map, String type) throws SQLException;

	List<Map<String, Object>> getPowerBITemplateByClientid(Integer clientid) throws SQLException;

	Map<String, Object> getFormatReportType() throws SQLException;

	/**
	 * Get template by templat id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Template selectByPrimaryKey(int id) throws SQLException;

	/**
	 * Get clientID and client parentID by clientID.
	 * 
	 * @param clientID
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> getClientIDAndParentID(int clientID) throws SQLException;
}
