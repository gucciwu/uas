package com.mszq.platform.app.uas.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.UasUserRole;

@MapperScan
public interface IUasUserRoleDAO {
	List<UasUserRole> queryRoleListByUser(Long userId);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasUserRole record);

    int insertSelective(UasUserRole record);

    UasUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasUserRole record);

    int updateByPrimaryKey(UasUserRole record);
    
    int insertUserRoleMappingBatch(List<UasUserRole> userRoleList);
    
    int deleteUserRoleMappingBatch(Long userId);
}