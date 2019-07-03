package com.mszq.platform.app.uas.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.UasPassword;
@MapperScan
public interface IUasPasswordDAO {
    int deleteByPrimaryKey(Long id);

    int insert(UasPassword record);

    int insertSelective(UasPassword record);

    UasPassword selectByPrimaryKey(Long id);
    
    UasPassword selectByUserId(Long userId);

    int updateByPrimaryKeySelective(UasPassword record);

    int updateByPrimaryKey(UasPassword record);
}