package com.mszq.platform.app.uas.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasPassword;
import com.mszq.platform.entity.UasUser;

public interface IUasUserService {
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows);
	int insert(UasUser record) throws RuntimeException;
	public int update(UasUser record) throws RuntimeException;
	public int deleteBatch(String[] ids);
	public UasPassword getPassword(Long userId);
	public int savePassword(UasPassword password) throws RuntimeException;
}
