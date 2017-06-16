package com.we.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.we.bean.ProjectBriefDto;
import com.we.bean.ProjectParameter;

public interface ProjectBriefDtoMapper {
    /**
     * @param briefId
     * @return
     */
    int deleteByPrimaryKey(Integer briefId);

    /**
     * @param record
     * @return
     */
    int insert(ProjectBriefDto record);

    /**
     * @param record
     * @return
     */
    int insertSelective(ProjectBriefDto record);
    
	/**
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	List<List<?>> listDetailInfo(ProjectParameter param) throws SQLException;
    /**
     * @param briefId
     * @return
     */
    ProjectBriefDto selectByPrimaryKey(Integer briefId);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ProjectBriefDto record);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKeyWithBLOBs(ProjectBriefDto record);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKey(ProjectBriefDto record);
}