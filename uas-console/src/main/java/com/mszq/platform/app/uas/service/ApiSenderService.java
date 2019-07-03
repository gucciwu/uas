package com.mszq.platform.app.uas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.config.dao.IConfigDAO;
import com.mszq.platform.app.uas.common.SslUtils;

import net.sf.json.JSONObject;

@Service
public class ApiSenderService {
	@Autowired
	private IConfigDAO configDAO;
	
	private JSONObject obj;
	private String method;
	
	public JSONObject getObj() {
		return obj;
	}
	public void setObj(JSONObject obj) {
		this.obj = obj;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public void send() throws RuntimeException {
		String restfulUrl = configDAO.getConfigValueByCode("restfulUrl");
		String respStr = SslUtils.postRequest(restfulUrl + method, obj.toString(), 5000);
		
		JSONObject response = JSONObject.fromObject(respStr);
		String code = response.getString("code");
		if (!"0".equals(code)) {
			throw new RuntimeException("LDAP API 服务"+method+" 调用错误：" + code + "," +response.getString("msg") );
		}
	}

}
