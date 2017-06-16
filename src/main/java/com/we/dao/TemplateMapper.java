package com.we.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.we.bean.Template;

public interface TemplateMapper {
	int deleteByPrimaryKey(Integer templateid);

	int insert(Template record);

	int insertSelective(Template record);

	/**
	 * Get template by templat id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	Template selectByPrimaryKey(Integer templateid) throws SQLException;

	int updateByPrimaryKeySelective(Template record);

	int updateByPrimaryKey(Template record);

	/**
	 * 
	 * @Title: selectPublicByUidAndClient
	 * @Description: TODO
	 * @param @param
	 *            record
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:32:13 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> selectPublicByUidAndClient(Template record);

	/**
	 * 
	 * @Title: selectOwnerByUidAndClient
	 * @param @param
	 *            record
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:42:17 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> selectOwnerByUidAndClient(Template record);

	/**
	 * 
	 * @Title: selectPublicByUidAndClientForPage
	 * @Description: TODO
	 * @param @param
	 *            map
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:44:38 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> selectPublicByUidAndClientForPage(Map<String, Object> map);

	/**
	 * 
	 * @Title: selectOwnerByUidAndClientForPage
	 * @Description: TODO
	 * @param @param
	 *            map
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:44:51 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> selectOwnerByUidAndClientForPage(Map<String, Object> map);

	/**
	 * 
	 * @Title: selectByMaps
	 * @Description: TODO
	 * @param @param
	 *            map
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:48:16 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> selectByMaps(Map<String, Object> map);

	/**
	 * 
	 * @Title: getCountPublicByUidAndClientForPage
	 * @Description: TODO
	 * @param @param
	 *            map
	 * @param @return
	 * @return int
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:44:25 PM
	 * @version V1.0
	 */
	int getCountPublicByUidAndClientForPage(Map<String, Object> map);

	/**
	 * 
	 * @Title: getCountOwnerByUidAndClientForPage
	 * @Description: TODO
	 * @param @param
	 *            map
	 * @param @return
	 * @return int
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:44:15 PM
	 * @version V1.0
	 */
	int getCountOwnerByUidAndClientForPage(Map<String, Object> map);

	/**
	 * 
	 * @Title: getRecentTemplate
	 * @Description: TODO
	 * @param @param
	 *            paramMap
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:43:55 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> getRecentTemplate(Map<String, Object> paramMap);

	/**
	 * 
	 * @Title: getTemplateListForReport
	 * @Description: TODO
	 * @param @param
	 *            paramMap
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:43:40 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> getTemplateListForReport(Map<String, Object> paramMap);

	List<Template> getAll();

	int getCountByMaps(Map<String, Object> map);

	/**
	 * 
	 * @Title: getPowerBITemplateByClientid
	 * @Description: TODO
	 * @param @param
	 *            clientid
	 * @param @return
	 * @return List<Map<String,Object>>
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:43:26 PM
	 * @version V1.0
	 */
	List<Map<String, Object>> getPowerBITemplateByClientid(Integer clientid);

	/**
	 * 
	 * @Title: updatePower
	 * @Description: TODO Batch modification
	 * @param @param
	 *            listPowerBIid
	 * @param @param
	 *            templateid
	 * @return void
	 * @author mingqiangyao
	 * @throws @date
	 *             May 19, 2017 2:42:42 PM
	 * @version V1.0
	 */
	void updatePower(@Param("list") List<Integer> listPowerBIid, @Param("powerId") Integer templateid);

	Map<String, Object> selectByPrimaryKeyToMap(int templateId);

	/**
	 * Get clientID and client ParentID by clientID.
	 * 
	 * @param clientID
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> getClientIDAndParentID(int clientID) throws SQLException;
    
	/**
	 * Update template. If PowerBITemplateID of report template is modified, update Report's PowerBITemplateID 
	 * 	and update ReportFile's PowerBITemplateUrl
	 * @param template
	 * @return
	 * @throws SQLException
	 */
	int updateTemplate(Template template) throws SQLException;

}