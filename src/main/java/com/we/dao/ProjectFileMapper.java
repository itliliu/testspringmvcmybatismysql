package com.we.dao;

import com.we.bean.ProjectFile;

public interface ProjectFileMapper {
    int deleteByPrimaryKey(Integer projectfileid);

    int insert(ProjectFile record);

    int insertSelective(ProjectFile record);

    ProjectFile selectByPrimaryKey(Integer projectfileid);

    int updateByPrimaryKeySelective(ProjectFile record);

    int updateByPrimaryKey(ProjectFile record);
}