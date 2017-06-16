/**
 * 
 */
package com.we.brief.service;

import java.sql.SQLException;
import java.util.List;

import com.we.bean.ProjectBrief;

/**
 * @author liliu
 *
 */
public interface IBriefService {
	
	int save(ProjectBrief biref) throws SQLException;

	int submit(ProjectBrief biref) throws SQLException;
	
	List<ProjectBrief> listDetailInfo(ProjectBrief biref) throws SQLException;
	
	int deleteReceivedReports(int projectBriefID) throws SQLException;

//	int modifyBrief(ProjectBrief biref) throws SQLException;

	ProjectBrief getBriefDetail(int projectBriefID) throws SQLException;
	
	
	
}
