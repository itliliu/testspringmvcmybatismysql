package com.we.dao;

import java.util.List;
import java.util.Map;
import java.sql.SQLException;

import com.we.bean.ProjectBrief;
import com.we.bean.ProjectBriefResult;
import com.we.bean.ProjectParameter;

public interface ProjectBriefMapper {

	/**
	 * update project brief status = -2, update last operator date and last
	 * modify date
	 * 
	 * @param projectbriefid
	 * @return
	 * @throws SQLException
	 */
	int deleteByPrimaryKey(Integer projectbriefid) throws SQLException;

	/**
	 * call procedure SaveProjectBrief: if parameter projectBriefID is null,
	 * then insert new data(status=0) else if parameter projectBriefID is not
	 * null, then update previous status(>=0) to -1, insert new data(status=0),
	 * update new version, update last operator date and last modify date
	 * 
	 * @param record:
	 *            project brief data
	 * @return
	 * @throws SQLException
	 */
	int saveProjectBrief(ProjectBriefResult record) throws SQLException;

	/**
	 * call procedure submitProjectBrief: if parameter projectBriefID is null,
	 * then insert new data(status=1) else if parameter projectBriefID is not
	 * null, then update previous status(>=0) to -1, insert new data(status=1),
	 * update new version, update last operator date and last modify date
	 * 
	 * @param record:
	 *            project brief data
	 * @return
	 * @throws SQLException
	 */
	int submitProjectBrief(ProjectBriefResult record) throws SQLException;

	int insert(ProjectBriefResult record) throws SQLException;

	int insertSelective(ProjectBriefResult record) throws SQLException;

	List<Map<String, Object>> getRecentProject(Map<String, Object> paramMap);

	int updateSatusAndVersionByPrimaryKey(Integer projectbriefid) throws SQLException;

	/**
	 * SELECT sql: select project brief data by project brief id;
	 * 
	 * @param projectbriefid
	 * @return
	 * @throws SQLException
	 */
	ProjectBriefResult selectByPrimaryKey(Integer projectbriefid) throws SQLException;

	int updateByPrimaryKeySelective(ProjectBriefResult record) throws SQLException;

	int updateByPrimaryKey(ProjectBriefResult record) throws SQLException;

	int updateProjectBriefToProposal(Integer projectbriefid) throws SQLException;

	/**
	 * call procedure GetProjectBriefByUserIDAndPrimaryKey: select project brief
	 * data.
	 * 
	 * @param briefMap
	 * @return
	 * @throws SQLException
	 */
	List<ProjectBriefResult> getProjectBriefByPrimaryKeyUIDAndClient(Map<String, Object> briefMap) throws SQLException;

	List<Map<String, Object>> selectRecentProjectByUidAndClient(ProjectBriefResult record) throws SQLException;

	List<List<?>> getAllProjectListAndPageNumber(ProjectParameter param) throws SQLException;

	/**
	 * Get project list by status and search project list by keyword call
	 * procedure GetProjectListByStatusOrKeyword: if role is account team,
	 * return project list belong to account team client, others return all
	 * project list if status=-1,return all project list, others return project
	 * list belong to current status if keyword is not null, then return project
	 * list by projectBriefID or projectBrief ParentID or project name or client
	 * name(project name and client name is fuzziness query)
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	List<List<?>> getProjectListByStatusOrKeyword(ProjectParameter param) throws SQLException;

	/**
	 * update project brief and project proposal last operator date call
	 * procedure UpdateLastOperatorDate: update project brief last operator
	 * date, update project proposal last operator date
	 * 
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	int updateLastOperatorDate(Map<String, Object> paramMap) throws SQLException;

	/**
	 * UPDATE sql: update project brief last operator date and last modify date.
	 * 
	 * @param projectBriefID
	 * @return
	 * @throws SQLException
	 */
	int updateProjectBriefDate(int projectBriefID) throws SQLException;

	/**
	 * SELECT sql: select project brief data by project brief id or project
	 * brief parent id.
	 * 
	 * @param briefID
	 * @return
	 * @throws SQLException
	 */
	ProjectBriefResult getProjectBriefByBrirfID(int briefID) throws SQLException;

	/**
	 * SELECT sql: select project brief count. 
	 * @param clientid
	 * @return
	 * @throws SQLException
	 */
	int getProjectBriefCountByClientID(Integer clientid) throws SQLException;
	
	
	
	int save(ProjectBrief biref) throws SQLException;

	int submit(ProjectBrief biref) throws SQLException;
	
	List<ProjectBrief> listDetailInfo(Map<String, Object> map) throws SQLException;
	
	int deleteReceivedReports(int projectBriefID) throws SQLException;

//	int modifyBrief(ProjectBrief biref) throws SQLException;

	ProjectBrief getBriefDetail(int projectBriefID) throws SQLException;

}