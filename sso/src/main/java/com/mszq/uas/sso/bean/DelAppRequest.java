package com.mszq.uas.sso.bean;

public class DelAppRequest extends ExRequest {
    private long appId;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
