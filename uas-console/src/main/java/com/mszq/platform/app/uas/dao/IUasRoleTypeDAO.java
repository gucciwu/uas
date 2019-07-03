package com.mszq.platform.app.uas.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.UasRoleType;

@MapperScan
public interface IUasRoleTypeDAO {
	List<UasRoleType> queryAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(UasRoleType record);

    int insertSelective(UasRoleType record);

    UasRoleType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasRoleType record);

    int updateByPrimaryKey(UasRoleType record);
}