package com.mszq.platform.app.uas.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasRole;

public interface IUasRoleService {
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows);
	public EUDataGridResult queryAll();
	int insert(UasRole record);
	public int update(UasRole record);
	public int deleteBatch(String[] ids);
	public List<Tree> getRoleTree(Map<String, Object> condition);
}
