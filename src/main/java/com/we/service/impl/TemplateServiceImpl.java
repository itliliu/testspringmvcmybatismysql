package com.we.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.Template;
import com.we.bean.User;
import com.we.dao.FormatMapper;
import com.we.dao.ReportTypeMapper;
import com.we.dao.TemplateMapper;
import com.we.dao.UserClientMapMapper;
import com.we.dao.UserMapper;
import com.we.service.ITemplateService;
import com.we.tool.Constant;

@Service("templateService")
public class TemplateServiceImpl implements ITemplateService {
	@Resource
	private TemplateMapper templateMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private UserClientMapMapper userClientMapMapper;
	@Resource
	private FormatMapper formatMapper;
	@Resource
	private ReportTypeMapper reportTypeMapper;

	private static final String FORMAT = "Format";
	private static final String REPORT_TYPE = "ReportType";

	/**
	 * Get the last 5 data
	 */
	@Override
	public List<Map<String, Object>> recentTemplate(int userid, int clientid, String type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("clientid", clientid);
		paramMap.put("type", type);
		return this.templateMapper.getRecentTemplate(paramMap);
	}

	/**
	 * select template by map if user is AccountTeam only see what belongs to it
	 * if type, the value is 'public' ( Not including myself) or 'owner' ( only
	 * myself)
	 */
	@Override
	public Map<String, Object> selectType(Map<String, Object> map, String type) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> listMaps;
		int count;
		// is accountteam
		User user = userMapper.selectByPrimaryKey((Integer) (map.get("userid")));
		if (user != null && user.getRoleid() == 1) {
			// find of user clientid
			List<Integer> userClientids = userClientMapMapper.getClientidByUserid((Integer) (map.get("userid")));
			if (userClientids.size() < 1) {
				return null;
			}
			map.put("clientids", userClientids);
		}

		if (Constant.OWNER.equals(type)) {
			listMaps = templateMapper.selectOwnerByUidAndClientForPage(map);
			listMaps = transformation(listMaps);
			returnMap.put("template", listMaps);
			count = templateMapper.getCountOwnerByUidAndClientForPage(map);
			returnMap.put("pageNumber", count);
			return returnMap;
		}
		if (Constant.PUBLIC.equals(type)) {
			listMaps = templateMapper.selectPublicByUidAndClientForPage(map);
			listMaps = transformation(listMaps);
			returnMap.put("template", listMaps);
			count = templateMapper.getCountPublicByUidAndClientForPage(map);
			returnMap.put("pageNumber", count);
			return returnMap;
		}
		return null;
	}

	@Override
	public Map<String, Object> getById(int templateId) {
		return templateMapper.selectByPrimaryKeyToMap(templateId);
	}

	@Override
	public int deleteById(int templateId) {
		return templateMapper.deleteByPrimaryKey(templateId);
	}

	@Override
	public int editById(Template template) throws SQLException {
		return templateMapper.updateTemplate(template);
	}

	@Override
	public List<Map<String, Object>> getTemplateListForReport(Map<String, Object> paramMap) throws SQLException {
		// TODO Auto-generated method stub
		return this.templateMapper.getTemplateListForReport(paramMap);
	}

	@Override
	public int add(Template template) {
		templateMapper.insertSelective(template);
		return template.getTemplateid();
	}

	@Override
	public List<Template> getAll() throws SQLException {
		return templateMapper.getAll();
	}

	@Override
	public List<Map<String, Object>> getPowerBITemplateByClientid(Integer clientid) {

		return this.templateMapper.getPowerBITemplateByClientid(clientid);
	}

	@Override
	public Map<String, Object> getFormatReportType() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.FORMAT, formatMapper.getAll());
		map.put(Constant.REPORT_TYPE, reportTypeMapper.getAll());
		return map;
	}

	/**
	 * 
	 * transformation Convert ID to corresponding fields
	 * 
	 * @@param list @return List<Map<String,Object>> @throws
	 */
	private List<Map<String, Object>> transformation(List<Map<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put(FORMAT,
					formatMapper.selectByPrimaryKey((Integer) (list.get(i).get(FORMAT))).getFormattype());
			list.get(i).put(REPORT_TYPE,
					reportTypeMapper.selectByPrimaryKey((Integer) (list.get(i).get(REPORT_TYPE))).getReporttype());
		}
		return list;
	}

	/**
	 * Get template by templat id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Template selectByPrimaryKey(int id) throws SQLException {
		// TODO Auto-generated method stub
		return this.templateMapper.selectByPrimaryKey(id);
	}

	/**
	 * SELECT sql: select clientID and client ParentID by clientID.
	 */
	@Override
	public Map<String, Object> getClientIDAndParentID(int clientID) throws SQLException {
		return this.templateMapper.getClientIDAndParentID(clientID);
	}

}
