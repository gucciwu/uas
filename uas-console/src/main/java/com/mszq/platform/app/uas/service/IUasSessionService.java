package com.mszq.platform.app.uas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasSession;

public interface IUasSessionService {
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows);
	int insert(UasSession record);
	public int update(UasSession record);
	public int deleteBatch(String[] ids);
}
