package com.mszq.platform.app.permission.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.entity.Permission;

public interface IPermissionDAO {
	public Set<Permission> getPermissionsByIDs(@Param("IDs") String IDs);
	
	public List<Permission> queryAll();
	
	public List<Permission> queryPermissionsByUserID(Long userID);

	public int insert(Permission record);

	public int deleteBatch(String[] ids);

	public int updateByPrimaryKey(Permission record);
	
	public int getChildrenBykey(String[] ids);
	
	public List<Permission> queryPermissionByParams(Map<String,Object> params);

}
