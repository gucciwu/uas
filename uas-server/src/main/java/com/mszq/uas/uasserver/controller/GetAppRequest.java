package com.mszq.uas.uasserver.controller;

import com.mszq.uas.uasserver.bean.ExRequest;

public class GetAppRequest extends ExRequest {
    private long appId;
    private String name;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}