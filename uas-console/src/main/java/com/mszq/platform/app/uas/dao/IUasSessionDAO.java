package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasSessionDto;
import com.mszq.platform.entity.UasSession;
@MapperScan
public interface IUasSessionDAO {
	List<UasSessionDto> selectList( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasSession record);

    int insertSelective(UasSession record);

    UasSession selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasSession record);

    int updateByPrimaryKey(UasSession record);
}