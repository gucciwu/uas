package com.mszq.platform.app.institution.dao;

import java.util.List;

import com.mszq.platform.entity.Institution;

public interface IInstitutionDAO {
    int deleteByPrimaryKey(Long id);

    int insert(Institution record);

    int insertSelective(Institution record);

    Institution selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Institution record);

    int updateByPrimaryKey(Institution record);

	List<Institution> selectByName(String name);

	List<Institution> selectByCode(String code);

}
