package com.mszq.platform.app.uas.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import com.mszq.platform.entity.UasOrgType;

@MapperScan
public interface IUasOrgTypeDAO {
	List<UasOrgType> queryAll();
	
    int deleteByPrimaryKey(Short id);
    
    int deleteBatch(String[] ids);

    int insert(UasOrgType record);

    int insertSelective(UasOrgType record);

    UasOrgType selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(UasOrgType record);

    int updateByPrimaryKey(UasOrgType record);
}