package com.we.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.ProjectBriefResult;
import com.we.bean.ProjectParameter;
import com.we.bean.ProjectProposal;
import com.we.bean.ProjectProposalResult;
import com.we.dao.ProjectBriefMapper;
import com.we.dao.ProjectProposalMapper;
import com.we.service.IRequestService;

@Service("requestService")
public class RequestServiceImpl implements IRequestService {

	@Resource
	private ProjectBriefMapper projectBriefMapper;
	@Resource
	private ProjectProposalMapper projectProposalMapper;

	/**
	 * Save a project brief, the status = 0
	 * 
	 * @param record
	 *            project brief data
	 */
	@Override
	public int saveProjectBrief(ProjectBriefResult record) throws SQLException {
		return this.projectBriefMapper.saveProjectBrief(record);
	}

	/**
	 * submit a project brief, the status = 1
	 * 
	 * @param record
	 *            project brief data
	 */
	@Override
	public int submitProjectBrief(ProjectBriefResult record) throws SQLException {
		return this.projectBriefMapper.submitProjectBrief(record);
	}

	@Override
	public int deleteProjectBrief(ProjectBriefResult record) throws SQLException {
		return this.projectBriefMapper.deleteByPrimaryKey(record.getProjectBriefID());
	}

	@Override
	public int updateProjectBriefToProposal(Integer birefID) throws SQLException {
		return this.projectBriefMapper.updateProjectBriefToProposal(birefID);
	}

	/**
	 * Get project brief by projectBriefID.
	 * 
	 */
	@Override
	public List<ProjectBriefResult> getProjectBriefByPrimaryKeyUIDAndClient(Map<String, Object> briefMap)
			throws SQLException {
		return this.projectBriefMapper.getProjectBriefByPrimaryKeyUIDAndClient(briefMap);
	}

	/**
	 * Save project proposal, set status = 1(brief and proposal)
	 */
	@Override
	public int saveProjectProposal(ProjectProposalResult proposal) throws SQLException {
		return this.projectProposalMapper.saveProjectProposal(proposal);
	}

	/**
	 * Submit a project proposal, set status = 2(proposal, brief).
	 */
	@Override
	public int submitProjectProposal(ProjectProposalResult proposal) throws SQLException {
		return this.projectProposalMapper.submitProjectProposal(proposal);
	}

	/**
	 * Account team reject a project proposal, set proposal status = 1
	 */
	@Override
	public int rejectProjectProposal(ProjectProposalResult proposal) throws SQLException {
		return this.projectProposalMapper.rejectProjectProposal(proposal);
	}

	/**
	 * Approve a project proposal, Set proposal status = 3
	 */
	@Override
	public int approveProjectProposal(Integer projectBriefID) throws SQLException {
		return this.projectProposalMapper.approveProjectProposal(projectBriefID);
	}

	/**
	 * Account team reject a project proposal, set brief status = 1
	 */
	@Override
	public int rejectProjectProposalBrief(Integer projectBriefID) throws SQLException {
		return this.projectProposalMapper.rejectProjectProposalBrief(projectBriefID);
	}

	/**
	 * Approve a project proposal, Set brief status = 3
	 */
	@Override
	public int approveProjectProposalBrief(Integer projectBriefID) throws SQLException {
		return this.projectProposalMapper.approveProjectProposalBrief(projectBriefID);
	}

	/**
	 * Get project proposal data by projectBriefID
	 */
	@Override
	public List<ProjectProposalResult> getProjectProposalByPrimaryKeyUidAndClient(Integer projectBriefID)
			throws SQLException {
		return this.projectProposalMapper.getProjectProposalByPrimaryKeyUidAndClient(projectBriefID);
	}

	/**
	 * Get project list or search project list by key word, return total number
	 * and project list
	 */
	@Override
	public List<List<?>> getProjectListByStatusOrKeyword(ProjectParameter param) throws SQLException {
		return this.projectBriefMapper.getProjectListByStatusOrKeyword(param);
	}

	/**
	 * update project brief and project proposal last operator date
	 */
	@Override
	public int updateLastOperatorDate(Map<String, Object> paramMap) throws SQLException {
		return this.projectBriefMapper.updateLastOperatorDate(paramMap);
	}

	/**
	 * Update project brief last operator date and last modify date.
	 */
	@Override
	public int updateProjectBriefDate(int projectBriefID) throws SQLException {
		return this.projectBriefMapper.updateProjectBriefDate(projectBriefID);
	}

	/**
	 * Update project proposal last operator date and last modify date.
	 */
	@Override
	public int updateProjectProposalDate(int projectBriefID) throws SQLException {
		return this.projectProposalMapper.updateProjectProposalDate(projectBriefID);
	}

	/**
	 * get project proposal data by project brief id or project parent id.
	 */
	@Override
	public ProjectProposal getProjectPoroposalByBrirfID(int briefID) throws SQLException {
		return this.projectProposalMapper.getProjectPoroposalByBrirfID(briefID);
	}

	/**
	 * 
	 * Get ProjectBrief by projectBriefID
	 * 
	 * @param briefID
	 * @return
	 * @throws SQLException
	 */
	@Override
	public ProjectBriefResult getProjectBriefByBrirfID(int briefID) throws SQLException {
		return this.projectBriefMapper.selectByPrimaryKey(briefID);
	}
}
