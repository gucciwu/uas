package com.mszq.uas.uasserver.bean;

import io.swagger.annotations.ApiModelProperty;

public class ExRequest extends Request {
	@ApiModelProperty(value = "服务调用者的Secret", dataType="String", notes="请求报文保留字段", required = true)
	private String _secret;

	public String get_secret() {
		return _secret;
	}

	public void set_secret(String _secret) {
		this._secret = _secret;
	}

}
