/**
 * 
 */
package com.we.brief.service;

import java.sql.SQLException;
import java.util.List;

import com.we.bean.ProjectBriefDto;
import com.we.bean.ProjectParameter;

/**
 * @author liliu
 *
 */
public interface IProjectBriefService {
	
	/**
	 * @param biref
	 * @return
	 * @throws SQLException
	 */
	int save(ProjectBriefDto biref) throws SQLException;

	/**
	 * @param biref
	 * @return
	 * @throws SQLException
	 */
	int submit(ProjectBriefDto biref) throws SQLException;
	
	/**
	 * @param biref
	 * @return
	 * @throws SQLException
	 */
	int edit(ProjectBriefDto biref) throws SQLException;
	
	
	
	
	/**
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	List<List<?>> listDetailInfo(ProjectParameter param) throws SQLException;

	
	/**
	 * @param briefID
	 * @return
	 * @throws SQLException
	 */
	int deleteReceivedReports(int briefID) throws SQLException;


	/**
	 * @param briefID
	 * @return
	 * @throws SQLException
	 */
	ProjectBriefDto getBriefDetail(int briefID) throws SQLException;
	
	
}
