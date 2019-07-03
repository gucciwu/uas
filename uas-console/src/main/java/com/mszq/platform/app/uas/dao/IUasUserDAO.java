package com.mszq.platform.app.uas.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.uas.dto.UasUserDto;
import com.mszq.platform.entity.UasApp;
import com.mszq.platform.entity.UasUser;

@MapperScan
public interface IUasUserDAO {
    List<UasUserDto> selectList( Map<String, Object> condition);
	
    int deleteByPrimaryKey(Long id);

    int insert(UasUser record);

    int insertSelective(UasUser record);

    UasUserDto selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UasUser record);

    int updateByPrimaryKey(UasUser record);
}
