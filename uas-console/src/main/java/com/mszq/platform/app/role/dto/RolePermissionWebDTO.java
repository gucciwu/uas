package com.mszq.platform.app.role.dto;

public class RolePermissionWebDTO {
	private long roleId;
	private String roleName;
	private String permissionName;//权限的名称，用逗号分隔，直接从数据库中查询出来
	
	public RolePermissionWebDTO(){}
	
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	

}
