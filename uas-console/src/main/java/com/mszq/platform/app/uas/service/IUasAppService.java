package com.mszq.platform.app.uas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasApp;

public interface IUasAppService {
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows);
	public List<Tree> getAppTree();
	int insert(UasApp record);
	public int update(UasApp record);
	public int deleteBatch(String[] ids);
}
