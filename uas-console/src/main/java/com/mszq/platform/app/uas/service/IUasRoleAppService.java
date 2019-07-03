package com.mszq.platform.app.uas.service;

import java.util.List;

import com.mszq.platform.app.uas.dto.UasRoleAppDto;

public interface IUasRoleAppService {
	public List<UasRoleAppDto> getAppListByRole(Long RoleId);
	public int saveRolePermission(Long roleId, String appIds);
}
