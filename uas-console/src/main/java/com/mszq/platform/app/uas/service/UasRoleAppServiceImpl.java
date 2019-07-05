package com.mszq.platform.app.uas.service;

import java.util.List;

import com.mszq.platform.app.config.dao.IConfigDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.uas.dao.IUasAppDAO;
import com.mszq.platform.app.uas.dao.IUasRoleAppDAO;
import com.mszq.platform.app.uas.dto.UasAppDto;
import com.mszq.platform.app.uas.dto.UasRoleAppDto;

import net.sf.json.JSONObject;

@Service
public class UasRoleAppServiceImpl implements IUasRoleAppService {
	@Autowired
	IUasRoleAppDAO uasRoleAppDao;

	@Autowired
	IUasAppDAO uasAppDAO;

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
	public List<UasRoleAppDto> getAppListByRole(Long RoleId) {
		return uasRoleAppDao.queryAppListByRole(RoleId);
	}

	@Override
	public int saveRolePermission(Long roleId, String appIds) {
//		Log4j2Util
		// 给角色授权时，先删除旧的mapping，再插入新的
		int count = 0;
		// List<UasRoleApp> raList = new ArrayList<UasRoleApp>();
		List<UasRoleAppDto> roleAppList = uasRoleAppDao.queryAppListByRole(roleId);

		String[] appIdArray = appIds.split(",");
		for (String appId : appIdArray) {
			if (!"".equals(appId.trim())) {
				if(!isExistApp(appId, roleAppList)) {
					JSONObject obj = new JSONObject();
					obj.put("_appId", this.getAppId());
					obj.put("_devInfo", "");
					obj.put("_secret", this.getSecret());
					obj.put("appId", Long.parseLong(appId));
					obj.put("roleId", roleId);

					apiSender.setMethod("/permission/add_app_to_role");
					apiSender.setObj(obj);
					apiSender.send();
				}
			}
		}
		
		for (UasRoleAppDto roleApp : roleAppList) { 
			if(isDeleteApp(roleApp.getAppId().toString(), appIds)) {
				JSONObject obj = new JSONObject();
				obj.put("_appId", this.getAppId());
				obj.put("_devInfo", "");
				obj.put("_secret", this.getSecret());
		        obj.put("appId", roleApp.getAppId());
		        obj.put("roleAppId", roleApp.getId()); 
		        obj.put("roleId", roleApp.getRoleId());
		  
		        apiSender.setMethod("/permission/del_app_to_role"); apiSender.setObj(obj);
		        apiSender.send(); 
			}
	    }

		return count;
	}

	private boolean isExistApp(String newAppId, List<UasRoleAppDto> roleAppList) {
		for (UasRoleAppDto roleApp : roleAppList) {
			if (newAppId.equals(roleApp.getAppId().toString())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isDeleteApp(String oldAppId, String appIds) {
		String[] newAppIdArray = appIds.split(","); 
		
		for (String newAppId : newAppIdArray) {
			if (newAppId.equals(oldAppId)) {
				return false;
			}
		}
		return true;
	}
	

}
