package com.mszq.uas.uasserver.bean;

public class AddAppResponse extends Response {
    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    private long appId;
}
