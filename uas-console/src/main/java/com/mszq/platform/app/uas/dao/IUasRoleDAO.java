package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasRoleDto;
import com.mszq.platform.entity.UasRole;

@MapperScan
public interface IUasRoleDAO {
	List<UasRoleDto> selectList( Map<String, Object> condition);
	
	List<UasRoleDto> queryAll( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasRole record);

    int insertSelective(UasRole record);

    UasRoleDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasRole record);

    int updateByPrimaryKey(UasRole record);
}
