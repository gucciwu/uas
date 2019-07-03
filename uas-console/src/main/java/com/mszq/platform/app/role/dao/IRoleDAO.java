package com.mszq.platform.app.role.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.role.dto.RolePermissionWebDTO;
import com.mszq.platform.entity.PermissionRole;
import com.mszq.platform.entity.Role;

@MapperScan
public interface IRoleDAO {
	
//	public List<Long> getPermissionsByRoleID(@Param("roleID") Long roleID);

	public List<Role> queryAll();
	public Role queryRoleById(long id);

	public int insert(Role record);

	public int deleteBatch(String[] ids);
	
	public int deletePermissionMappingBatch(String[] ids);
	
	public int deleteEmployeeMappingBatch(String[] ids);

	public int updateByPrimaryKey(Role record);
	
	public List<Long> getPermissionIdListByRoleId(Long id);
	
	public int insertRolePermissionBatch(List<PermissionRole> prList);
	
	/**
	 * 查询某个人员的“管理员”角色以及这些角色对应的权限
	 * @param employeeId
	 * @return
	 */
	public List<RolePermissionWebDTO> queryAdminRoleAndPermissionByEmployeeId(@Param("employeeId") long employeeId);
	
	
}
