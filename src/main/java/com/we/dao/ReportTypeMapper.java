package com.we.dao;

import java.util.List;

import com.we.bean.ReportType;

public interface ReportTypeMapper {
    int deleteByPrimaryKey(Integer reporttypeid);

    int insert(ReportType record);

    int insertSelective(ReportType record);

    ReportType selectByPrimaryKey(Integer reporttypeid);
    
    List<ReportType> getAll();

    int updateByPrimaryKeySelective(ReportType record);

    int updateByPrimaryKey(ReportType record);
}