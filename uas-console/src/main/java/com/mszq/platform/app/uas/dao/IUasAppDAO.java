package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasAppDto;
import com.mszq.platform.entity.UasApp;

@MapperScan
public interface IUasAppDAO {
	List<UasAppDto> selectList( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasApp record);

    int insertSelective(UasApp record);

    UasAppDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasApp record);

    int updateByPrimaryKey(UasApp record);
}