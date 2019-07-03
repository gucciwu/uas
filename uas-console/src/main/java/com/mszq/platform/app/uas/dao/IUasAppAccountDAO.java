package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasAppAccountDto;
import com.mszq.platform.entity.UasAppAccount;

@MapperScan
public interface IUasAppAccountDAO {
	List<UasAppAccountDto> selectList( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasAppAccount record);

    int insertSelective(UasAppAccount record);

    UasAppAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasAppAccount record);

    int updateByPrimaryKey(UasAppAccount record);
}