package com.we.dao;

import com.we.bean.RequestForm;

public interface RequestFormMapper {
    int deleteByPrimaryKey(Integer requestformid);

    int insert(RequestForm record);

    int insertSelective(RequestForm record);

    RequestForm selectByPrimaryKey(Integer requestformid);

    int updateByPrimaryKeySelective(RequestForm record);

    int updateByPrimaryKey(RequestForm record);
}