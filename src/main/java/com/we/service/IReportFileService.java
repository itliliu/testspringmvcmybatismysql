package com.we.service;

import java.sql.SQLException;
import java.util.Map;

import com.we.bean.ReportFile;

public interface IReportFileService {
	
	/**
	 * Insert report file info to table ReportFile.
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	int createReportFile(Map<String,Object> record) throws SQLException;
	
	/**
	 * Update report file's url to table ReportFile.
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	int updateReportFile(ReportFile record) throws SQLException; 
	
	/**
	 * Get report file info.
	 * @param reportID
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> getReportFilePath(int reportID) throws SQLException;
}
