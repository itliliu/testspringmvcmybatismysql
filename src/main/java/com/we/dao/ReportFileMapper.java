package com.we.dao;

import java.sql.SQLException;
import java.util.Map;

import com.we.bean.ReportFile;

public interface ReportFileMapper {
	int deleteByPrimaryKey(Integer reportfileid);

	int insert(ReportFile record);

	int insertSelective(ReportFile record);

	ReportFile selectByPrimaryKey(Integer reportfileid);

	int updateByPrimaryKeySelective(ReportFile record);

	int updateByPrimaryKey(ReportFile record);
	
	/**
	 * Call procedure CreateReportFile to insert report file.
	 * 	we need store real template name to ReportFile rather than just a template id.
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	int createReportFile(Map<String, Object> record) throws SQLException;
	
	/**
	 * Update report file's url to table ReportFile.
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	int updateReportFile(ReportFile record) throws SQLException;
	
	/**
	 * Get Get report file info by reportid.
	 * 	The result includes ProjectBriefID, ProjectBriefName, 
	 *		ClientName, ClientID and ReportFileUrl
	 * @param reportID
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> getReportFilePath(int reportID) throws SQLException;
}