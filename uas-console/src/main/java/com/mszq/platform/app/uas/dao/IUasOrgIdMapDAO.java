package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasOrgIdMapDto;
import com.mszq.platform.entity.UasOrgIdMap;

@MapperScan
public interface IUasOrgIdMapDAO {
	List<UasOrgIdMapDto> selectList( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasOrgIdMap record);

    int insertSelective(UasOrgIdMap record);

    UasOrgIdMap selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasOrgIdMap record);

    int updateByPrimaryKey(UasOrgIdMap record);
}