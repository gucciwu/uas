package com.mszq.platform.app.role.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.app.role.dto.RolePermissionWebDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.EmployeeRole;
import com.mszq.platform.entity.Role;

public interface IRoleService {
//	public List<Long> getPermissionsByRoleID(Long roleID);
	
	public EUDataGridResult queryAll(int page, int rows);
	public Role queryRoleById(long id);

	public int insert(Role record);

	public int deleteBatch(String[] ids);

	public int updateByPrimaryKey(Role record);
	
	public List<Long> getPermissionIdListByRoleId(Long id);
	
	public int updateRolePermission(Long id, String resourceIds);
	
	public List<Tree> getRoleTree();
	public List<RolePermissionWebDTO> queryAdminRoleAndPermissionByEmployeeId(long employeeId);

}
