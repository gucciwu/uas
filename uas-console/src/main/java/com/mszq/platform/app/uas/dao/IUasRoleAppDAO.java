package com.mszq.platform.app.uas.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasRoleAppDto;
import com.mszq.platform.entity.UasRoleApp;

@MapperScan
public interface IUasRoleAppDAO {
	List<UasRoleAppDto> queryAppListByRole(Long roleId);
	
	UasRoleAppDto getRoleApp(UasRoleAppDto roleApp);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasRoleApp record);

    int insertSelective(UasRoleApp record);

    UasRoleApp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasRoleApp record);

    int updateByPrimaryKey(UasRoleApp record);
    
    int insertRoleAppMappingBatch(List<UasRoleApp> roleAppList);
    
    int deleteRoleAppMappingBatch(Long roleId);
}