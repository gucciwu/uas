package com.mszq.platform.app.uas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasOrg;

public interface IUasOrgService {
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows);
	public EUDataGridResult queryAll(@Param("condition") Map<String, Object> condition);
	public List<Tree> getOrgnizationTree(Map<String, Object> condition);
	int insert(UasOrg record);
	public int update(UasOrg record);
	public int deleteBatch(String[] ids);
}
