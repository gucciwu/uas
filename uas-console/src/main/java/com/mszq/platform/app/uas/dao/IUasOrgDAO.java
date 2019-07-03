package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasOrgDto;
import com.mszq.platform.entity.UasOrg;

@MapperScan
public interface IUasOrgDAO {
	List<UasOrgDto> selectList( Map<String, Object> condition);
	
	List<UasOrgDto> queryAll( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasOrg record);

    int insertSelective(UasOrg record);

    UasOrgDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasOrg record);

    int updateByPrimaryKey(UasOrg record);
}