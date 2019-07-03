package com.mszq.platform.app.uas.service;

import java.util.List;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasOrgType;

public interface IUasOrgTypeService {
	public EUDataGridResult queryAll(int page, int rows);
	public List<UasOrgType> query();
	int insert(UasOrgType record);
	public int update(UasOrgType record);
	public int deleteBatch(String[] ids);
}
