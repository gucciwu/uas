package com.mszq.platform.app.uas.service;

import java.util.List;

import com.mszq.platform.entity.UasUserRole;

public interface IUasUserRoleService {
	public List<UasUserRole> getRoleListByUser(Long userId);
	public int saveRolePermission(Long userId, String roleIds);
}
