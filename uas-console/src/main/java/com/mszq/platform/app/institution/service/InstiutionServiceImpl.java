package com.mszq.platform.app.institution.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.institution.dao.IInstitutionDAO;
import com.mszq.platform.entity.Institution;

@Service
public class InstiutionServiceImpl implements IInstitutionService{
	@Autowired
	private IInstitutionDAO institutionDAO;
	@Override
	public int insert(Institution record){
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		return institutionDAO.insert(record);
	}
	@Override
	public List<Institution> selectByName(String name){
		return institutionDAO.selectByName(name);
	}
	@Override
	public List<Institution> selectByCode(String code){
		return institutionDAO.selectByCode(code);
	}
}
