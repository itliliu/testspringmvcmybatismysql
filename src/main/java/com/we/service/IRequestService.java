package com.we.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.ProjectBriefResult;
import com.we.bean.ProjectParameter;
import com.we.bean.ProjectProposal;
import com.we.bean.ProjectProposalResult;

public interface IRequestService {
	int saveProjectBrief(ProjectBriefResult record) throws SQLException;

	int submitProjectBrief(ProjectBriefResult biref) throws SQLException;
	
	int deleteProjectBrief(ProjectBriefResult record) throws SQLException;

	int updateProjectBriefToProposal(Integer biref) throws SQLException;

	List<ProjectBriefResult> getProjectBriefByPrimaryKeyUIDAndClient(Map<String,Object> briefMap) throws SQLException;

	int saveProjectProposal(ProjectProposalResult proposal) throws SQLException;

	int submitProjectProposal(ProjectProposalResult proposal) throws SQLException;

	int rejectProjectProposal(ProjectProposalResult proposal) throws SQLException;

	int approveProjectProposal(Integer projectBriefID) throws SQLException;
	
	int rejectProjectProposalBrief(Integer projectBriefID) throws SQLException;

	int approveProjectProposalBrief(Integer projectBriefID) throws SQLException;

	List<ProjectProposalResult> getProjectProposalByPrimaryKeyUidAndClient(Integer projecBriefID)  throws SQLException;

	List<List<?>> getProjectListByStatusOrKeyword(ProjectParameter param) throws SQLException;

	int updateLastOperatorDate(Map<String, Object> paramMap) throws SQLException;
	
	/**
	 * Update the lastmodifydate and lastoperatordate for Project Brief by project brief id
	 * @param projectBriefID
	 * @return
	 * @throws SQLException
	 */
	int updateProjectBriefDate(int projectBriefID) throws SQLException;
	
	/**
	 * Update the lastmodifydate and lastoperatordate for Project Proposal by project brief id
	 * @param projectBriefID
	 * @return
	 * @throws SQLException
	 */
	int updateProjectProposalDate(int projectBriefID) throws SQLException;

	ProjectProposal getProjectPoroposalByBrirfID(int briefID) throws SQLException;
	
	ProjectBriefResult getProjectBriefByBrirfID(int briefID) throws SQLException;
}
