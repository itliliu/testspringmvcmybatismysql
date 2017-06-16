package com.we.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.Report;
import com.we.bean.ReportResult;
import com.we.dao.ReportMapper;
import com.we.service.IReportService;

@Service("reportService")
public class ReportServiceImpl implements IReportService {
	
	@Resource
	private ReportMapper reportmapper;
	
	/**
	 * Get request form list by project brief id or project brief parentID
	 */
	@Override
	public List<Map<String,Object>> getReportByProjectID(int projectID) throws SQLException {
		return this.reportmapper.getReportByProjectID(projectID);
	}

	/**
	 * Insert a report
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int insertReport(ReportResult record) throws SQLException {
		return this.reportmapper.insert(record);
	}
	
	/**
	 * Update report
	 * @param record
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int updateReport(ReportResult record) throws SQLException {
		return this.reportmapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * Update project brief's status = 4
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int updateProjectBriefStatus(Integer projectID) throws SQLException {
		return this.reportmapper.updateProjectBriefStatus(projectID);
	}
	
	/**
	 * Update project proposal's status = 4
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int updateProjectProposalStatus(Integer projectID) throws SQLException {
		return this.reportmapper.updateProjectProposalStatus(projectID);
	}
	
	/**
	 * Reject reports, set status = 3
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
	@Override
	public int rejectReport(Map<String, Object> paramMap) throws SQLException{
		return this.reportmapper.rejectReport(paramMap);
	}
	
	/**
	 * Complete a project, set status = 5
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int completeReport(int projectID) throws SQLException{
		return this.reportmapper.completeReport(projectID);
	}
	
	/**
	 * Delete a report by project brief id or project brief parentid
	 * @param reportID
	 * 		project brief id or project brief parentid
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int deleteReport(int reportID) throws SQLException {
		return this.reportmapper.deleteReport(reportID);
	}

	@Override
	public int insertReportWhenApproveProposal(Report record)
			throws SQLException {
		return this.reportmapper.insertSelective(record);
	}

}
