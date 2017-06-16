/**
 * 
 */
package com.we.brief.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.we.bean.ProjectBriefDto;
import com.we.bean.ProjectParameter;
import com.we.brief.service.IProjectBriefService;
import com.we.dao.ProjectBriefDtoMapper;
import com.we.enums.BriefStatusEnum;

/**
 * @author liliu
 *
 */
@Transactional(value = "mysql")
@Service("briefDtoService")
public class ProjectBriefServiceImpl implements IProjectBriefService {
	@Resource
	private ProjectBriefDtoMapper projectbriefmapper;
	
//	private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	/* (non-Javadoc)
	 * @see com.we.brief.service.IProjectBriefService#save(com.we.bean.ProjectBrief)
	 */
	@Transactional( propagation = Propagation.REQUIRES_NEW)
	@Override
	public int save(ProjectBriefDto biref) throws SQLException {
//		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		biref.setCreateDate(new Date());
		biref.setModifyDate(new Date());
		if( "".equals(biref.getBriefId()) || biref.getBriefId() == null)
		{
			biref.setStatus(BriefStatusEnum.UNSUBMIT.getKey());
		}
		return this.projectbriefmapper.insertSelective(biref);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IProjectBriefService#submit(com.we.bean.ProjectBrief)
	 */
	@Transactional( propagation = Propagation.REQUIRES_NEW)
	@Override
	public int submit(ProjectBriefDto biref) throws SQLException {
		biref.setModifyDate(new Date());
		biref.setStatus(BriefStatusEnum.SUBMIT.getKey());
		return this.projectbriefmapper.updateByPrimaryKeySelective(biref);
	}
	
	/* (non-Javadoc)
	 * @see com.we.brief.service.IProjectBriefService#submit(com.we.bean.ProjectBrief)
	 */
	@Transactional( propagation = Propagation.REQUIRES_NEW)
	@Override
	public int edit(ProjectBriefDto biref) throws SQLException {
		biref.setModifyDate(new Date());
		return this.projectbriefmapper.updateByPrimaryKeySelective(biref);
	}

	/**
	 * Get project list or search project list by key word, return total number
	 * and project list
	 */
	@Override
	public List<List<?>> listDetailInfo(ProjectParameter param) throws SQLException {
		return this.projectbriefmapper.listDetailInfo(param);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IProjectBriefService#deleteReceivedReports(int)
	 */
	@Transactional( propagation = Propagation.REQUIRES_NEW)
	@Override
	public int deleteReceivedReports(int briefId) throws SQLException {
		return this.projectbriefmapper.deleteByPrimaryKey(briefId);
	}

	/* (non-Javadoc)
	 * @see com.we.brief.service.IProjectBriefService#getBriefDetail(int)
	 */
	@Override
	public ProjectBriefDto getBriefDetail(int briefId) throws SQLException {
		return this.projectbriefmapper.selectByPrimaryKey(briefId);
	}

}
