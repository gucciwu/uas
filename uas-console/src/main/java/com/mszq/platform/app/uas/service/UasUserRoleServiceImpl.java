package com.mszq.platform.app.uas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.uas.dao.IUasUserRoleDAO;
import com.mszq.platform.entity.UasUserRole;

@Service
public class UasUserRoleServiceImpl implements IUasUserRoleService {
	@Autowired
	IUasUserRoleDAO uasUserRoleDao;

	@Override
	public List<UasUserRole> getRoleListByUser(Long userId) {
		return uasUserRoleDao.queryRoleListByUser(userId);
	}

	@Override
	public int saveRolePermission(Long userId, String roleIds) {
		// 给角色授权时，先删除旧的mapping，再插入新的
		int count = 0;
		List<UasUserRole> raList = new ArrayList<UasUserRole>();

		uasUserRoleDao.deleteUserRoleMappingBatch(userId);

		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			if (!"".equals(roleId.trim())) {
				UasUserRole ra = new UasUserRole();

				ra.setRoleId(Long.parseLong(roleId));
				ra.setUserId(userId);

				raList.add(ra);
			}
		}
		
		if (raList.size() > 0) {
			count = uasUserRoleDao.insertUserRoleMappingBatch(raList);
		}
		return count;
	}

}
