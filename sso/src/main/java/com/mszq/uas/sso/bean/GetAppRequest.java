package com.mszq.uas.sso.bean;

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
