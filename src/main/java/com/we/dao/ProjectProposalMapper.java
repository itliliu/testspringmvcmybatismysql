package com.we.dao;

import java.sql.SQLException;
import java.util.List;

import com.we.bean.ProjectProposal;
import com.we.bean.ProjectProposalResult;

public interface ProjectProposalMapper {
	int deleteByPrimaryKey(Integer projectproposalid) throws SQLException;

	int insert(ProjectProposal record) throws SQLException;

	int insertSelective(ProjectProposal record) throws SQLException;

	ProjectProposal selectByPrimaryKey(Integer projectproposalid) throws SQLException;

	int updateByPrimaryKeySelective(ProjectProposal record) throws SQLException;

	int updateByPrimaryKey(ProjectProposal record) throws SQLException;

	/**
	 * call procedure SavesaveProjectProposal:
	 * 		if projectProsalID is null, then insert new proposal data(status = 1)
	 * 		else if projectProposal is not null, update previous proposal status = -1,
	 * 			and  insert new proposal data(status=1), update new version, 
	 * 			update last operator date and last modify date. 
	 * 
	 * @param proposal
	 * @return
	 * @throws SQLException
	 */
	int saveProjectProposal(ProjectProposalResult proposal) throws SQLException;

	/**
	 * call procedure SubmitsaveProjectProposal:
	 * 		if projectProsalID is null, then insert new proposal data(status = 2)
	 * 		else if projectProposal is not null, update previous proposal status = -1,
	 * 			and  insert new proposal data(status=2), update new version, 
	 * 			update last operator date and last modify date and update brief status=2. 
	 * 
	 * @param proposal
	 * @return
	 * @throws SQLException
	 */
	int submitProjectProposal(ProjectProposalResult proposal) throws SQLException;

	/**
	 * UPDATE sql: 
	 * 	Reject project proposal.
	 * 		update proposal status = 1, update reject comment, 
	 * 		update last operator date and last modify date
	 * @param proposal
	 * @return
	 * @throws SQLException
	 */
	int rejectProjectProposal(ProjectProposalResult proposal) throws SQLException;
	
	/**
	 *UPDATE sql: 
	 * 	Approve project proposal.
	 * 		update project proposal status = 2,
	 *      update last operator date and last modify date
	 * @param projectBriefID
	 * @return
	 * @throws SQLException
	 */
	int approveProjectProposal(Integer projectBriefID) throws SQLException;

	/**
	 * UPDATE sql: 
	 * 	Reject project proposal.
	 * 		update brief status = 1, 
	 * 		update last operator date and last modify date
	 * @param proposal
	 * @return
	 * @throws SQLException
	 */
	int rejectProjectProposalBrief(Integer projectBriefID) throws SQLException;

	/**
	 *UPDATE sql: 
	 * 	Approve project proposal.
	 * 		update project brief status = 2,
	 *      update last operator date and last modify date
	 * @param projectBriefID
	 * @return
	 * @throws SQLException
	 */
	int approveProjectProposalBrief(Integer projectBriefID) throws SQLException;

	/**
	 * SELECT sql: select project proposal data by projectBriefID
	 * @param projectBriefID
	 * @return
	 * @throws SQLException
	 */
	List<ProjectProposalResult> getProjectProposalByPrimaryKeyUidAndClient(Integer projectBriefID) throws SQLException;

	/**
	 * UPDATE sql: update project proposal last operator date and last modify date.
	 * @param projectBriefID: project brief id or project brief parent id.
	 * 
	 * @return
	 * @throws SQLException
	 */
	int updateProjectProposalDate(int projectBriefID) throws SQLException;

	/**
	 * SELECT sql: select project proposal data by project brief id or project brief parent id.
	 * @param briefID: project brief id or project brief parent id.
	 * @return
	 * @throws SQLException
	 */
	ProjectProposal getProjectPoroposalByBrirfID(int briefID) throws SQLException;
}