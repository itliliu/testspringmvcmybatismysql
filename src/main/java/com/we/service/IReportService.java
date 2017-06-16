package com.we.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.Report;
import com.we.bean.ReportResult;

public interface IReportService {
	
	/**
	 * Get report list by project brief id
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	List<Map<String,Object>> getReportByProjectID(int projectID) throws SQLException;
	
	/**
	 * Insert a report
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	int insertReport(ReportResult record) throws SQLException;
	
	/**
	 * Update report
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	int updateReport(ReportResult record) throws SQLException;
	
	/**
	 * Update project brief's status = 4
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	int updateProjectBriefStatus(Integer projectID) throws SQLException;
	
	/**
	 * Update project proposal's status = 4
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	int updateProjectProposalStatus(Integer projectID) throws SQLException;
	
	/**
	 * Reject reports, set status = 3
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	int rejectReport(Map<String, Object> paramMap) throws SQLException;
	
	/**
	 * Complete a project, set status = 5
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	int completeReport(int projectID) throws SQLException;
	
	/**
	 * Delete a report by project brief id or project brief parentid
	 * @param reportID
	 * 		project brief id or project brief parentid
	 * @return
	 * @throws SQLException
	 */
	int deleteReport(int reportID) throws SQLException;
	int insertReportWhenApproveProposal(Report record) throws SQLException;
	
}
