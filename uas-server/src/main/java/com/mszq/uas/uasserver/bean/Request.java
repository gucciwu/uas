package com.mszq.uas.uasserver.bean;

import io.swagger.annotations.ApiModelProperty;

public class Request {

    @ApiModelProperty(value = "服务调用者的ID", dataType="String", notes="请求报文保留字段", required = true)
    private long _appId;
    @ApiModelProperty(value = "服务调用者的设备信息", dataType="String", notes="请求报文保留字段", required = false)
    private String _devInfo;

    public long get_appId() {
        return _appId;
    }
    public void set_appId(long _appId) {
        this._appId = _appId;
    }
    public String get_devInfo() {
        return _devInfo;
    }
    public void set_devInfo(String _devInfo) {
        this._devInfo = _devInfo;
    }
}
