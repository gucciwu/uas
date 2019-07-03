package com.mszq.platform.app.permission.service;

import java.util.List;
import java.util.Set;

import com.mszq.platform.app.permission.dto.PermissionWebDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.MenuTree;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.Permission;

public interface IPermissionService {
	public List<Permission> queryPermissionsByUserID(Long userID);
	public EUDataGridResult queryAll();
	public List<Tree> getPermissionTree();
//	public List<MenuTree> getMenuTree();
	public int insert(Permission record);
	public int deleteBatch(String[] ids);
    public int updateByPrimaryKey(Permission record);
    public int getChildrenBykey(String[] ids);
    
    public PermissionWebDTO queryPermissions();

}
