package com.mszq.platform.app.uas.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasLog;

public interface IUasLogService {
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows);
	int insert(UasLog record);
	public int update(UasLog record);
	public int deleteBatch(String[] ids);
}
