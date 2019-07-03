package com.mszq.platform.app.uas.service;

import java.util.List;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasRoleType;

public interface IUasRoleTypeService {
	public EUDataGridResult queryAll(int page, int rows);
	public List<UasRoleType> query();
	int insert(UasRoleType record);
	public int update(UasRoleType record);
	public int deleteBatch(String[] ids);
}
