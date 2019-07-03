package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import com.mszq.platform.app.uas.dto.UasLogDto;
import com.mszq.platform.entity.UasLog;

public interface IUasLogDAO {
	List<UasLogDto> selectList( Map<String, Object> condition); 
	
    int deleteByPrimaryKey(Long id);

    int insert(UasLog record);

    int insertSelective(UasLog record);

    UasLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasLog record);

    int updateByPrimaryKey(UasLog record);
}