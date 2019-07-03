package com.mszq.platform.app.institution.service;

import java.util.List;

import com.mszq.platform.entity.Institution;

public interface IInstitutionService {

	int insert(Institution record);

	List<Institution> selectByName(String name);

	List<Institution> selectByCode(String code);

}
