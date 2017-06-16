package com.we.dao;

import java.util.List;

import com.we.bean.Format;

public interface FormatMapper {
    int deleteByPrimaryKey(Integer formatid);

    int insert(Format record);

    int insertSelective(Format record);

    Format selectByPrimaryKey(Integer formatid);
    
    List<Format> getAll();
    
    int updateByPrimaryKeySelective(Format record);

    int updateByPrimaryKey(Format record);
}