/**
 * 
 */
package com.we.brief.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.we.bean.ProjectBrief;
import com.we.brief.service.IBriefService;
import com.we.dao.ProjectBriefMapper;
import com.we.dao.ReportMapper;

/**
 * @author liliu
 *
 */
@Service("briefService")
public class BriefServiceImpl implements IBriefService {

	@Resource
	private ProjectBriefMapper projectbriefmapper;
	/* (non-Javadoc)
	 * @see com.we.brief.service.IBriefService#Save(com.we.bean.ProjectBrief)
	 */
	@Override
	public int save(ProjectBrief biref) throws SQLException {
		return this.projectbriefmapper.save(biref);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IBriefService#Submit(com.we.bean.ProjectBrief)
	 */
	@Override
	public int submit(ProjectBrief biref) throws SQLException {
		return this.projectbriefmapper.submit(biref);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IBriefService#listDetailInfo(com.we.bean.ProjectBrief)
	 */
	@Override
	public List<ProjectBrief> listDetailInfo(ProjectBrief biref)
			throws SQLException {
		return this.projectbriefmapper.listDetailInfo(biref);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IBriefService#deleteReceivedReports(com.we.bean.ProjectBrief)
	 */
	@Override
	public int deleteReceivedReports(int projectBriefID) throws SQLException {
		return this.projectbriefmapper.deleteReceivedReports(projectBriefID);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IBriefService#modifyBrief(int)
	 */
	/*@Override
	public int modifyBrief(ProjectBrief biref) throws SQLException {
		return this.projectbriefmapper.modifyBrief(biref);
	}
*/
	/* (non-Javadoc)
	 * @see com.we.brief.service.IBriefService#getBriefDetail(int)
	 */
	@Override
	public ProjectBrief getBriefDetail(int projectBriefID) throws SQLException {
		return this.projectbriefmapper.getBriefDetail(projectBriefID);
	}

}
