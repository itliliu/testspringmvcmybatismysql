package com.we.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.Report;
import com.we.bean.ReportResult;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer reportid);

    int insert(ReportResult record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer reportid);
    
    /**
     * Update report
     * @param record
     * @return 0 update failed, 1 update succeed
     */
    int updateByPrimaryKeySelective(ReportResult record);

    int updateByPrimaryKey(Report record);
    
    /**
     * SELECT sql: select report info by project brief id or project brief parentid
     *      we need to left join Template twice because we need to get 
     * 		report template's id and name, 
     *      powerBI template's id and name. 
     *      
     * @param projectID: project brief id or project brief parentID
     * @return
     * @throws SQLException
     */
    List<Map<String,Object>> getReportByProjectID(int projectID) throws SQLException;
    
    int insertCommit(Report record) throws SQLException;
    
    int updateCommitByPrimaryKeySelective(Report record) throws SQLException;
    
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
	 * Call procedure RejectReport to reject reports, set status = 3
	 * 	Need to add comment to all the report by project brief id.
	 * 	And set status = 3 in table ProjectBrief and table ProjectProposal.
	 * @param request
	 * @param reponse
	 * @param message
	 * @return
	 */
    int rejectReport(Map<String, Object> paramerMap) throws SQLException;
    
    /**
	 * Call procedure CompleteReport to complete a project, set status = 5
	 * 	need to set status = 5 in table ProjectBrief and ProjectProposal
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
    int completeReport(int reportID) throws SQLException;
    
    /**
	 * Delete a report by project brief id or project brief parent id
	 * @param reportID
	 * 		project brief id or project brief parent id
	 * @return
	 * @throws SQLException
	 */
    int deleteReport(int reportID) throws SQLException;
}