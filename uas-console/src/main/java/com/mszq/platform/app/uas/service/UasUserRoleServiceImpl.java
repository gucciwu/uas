package com.mszq.platform.app.uas.service;

import java.util.ArrayList;
import java.util.List;

import com.mszq.platform.app.config.dao.IConfigDAO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.uas.dao.IUasUserRoleDAO;
import com.mszq.platform.entity.UasUserRole;

@Service
public class UasUserRoleServiceImpl implements IUasUserRoleService {
	@Autowired
	IUasUserRoleDAO uasUserRoleDao;

	@Autowired
	IConfigDAO configDAO;

	@Autowired
	ApiSenderService apiSender;

	private String appId = null;
	private String secret = null;

	private String getAppId(){
		if(appId == null){
			this.appId = configDAO.getConfigValueByCode("uasAppId");
		}
		return this.appId;
	}

	private String getSecret(){
		if(secret == null){
			this.secret =configDAO.getConfigValueByCode("uasSecret");
		}

		return this.secret;
	}


	@Override
	public List<UasUserRole> getRoleListByUser(Long userId) {
		return uasUserRoleDao.queryRoleListByUser(userId);
	}

	@Override
	public int saveRolePermission(Long userId, String roleIds) {
		// 给角色授权时，先删除旧的mapping，再插入新的
		List<UasUserRole> uasUserRoles = uasUserRoleDao.queryRoleListByUser(userId);
		for(UasUserRole uur:uasUserRoles){
			JSONObject obj = new JSONObject();
			obj.put("_appId", this.getAppId());
			obj.put("_devInfo", "");
			obj.put("_secret", this.getSecret());
			obj.put("autoDelAccount", true);
			obj.put("roleId",uur.getRoleId());
			obj.put("userId",uur.getUserId());

			apiSender.setMethod("/permission/del_role_to_user");
			apiSender.setObj(obj);
			apiSender.send();
		}

		String[] roleIdArray = roleIds.split(",");
		int count = 0;
		for (String roleId : roleIdArray) {
			if (!"".equals(roleId.trim())) {
				JSONObject obj = new JSONObject();
				obj.put("_appId", this.getAppId());
				obj.put("_devInfo", "");
				obj.put("_secret", this.getSecret());
				obj.put("autoDelAccount", true);
				obj.put("roleId",Long.parseLong(roleId));
				obj.put("userId",userId);

				apiSender.setMethod("/permission/add_role_to_user");
				apiSender.setObj(obj);
				apiSender.send();
				count++;
			}
		}

		return count;
	}

}
