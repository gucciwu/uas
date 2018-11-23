package com.mszq.uas.uasserver.bean;

public class DelIdToAppExRequest extends com.mszq.uas.uasserver.bean.ExRequest {

    private long userId;
    private long appId;
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
