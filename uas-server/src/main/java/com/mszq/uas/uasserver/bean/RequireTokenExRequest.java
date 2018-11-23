package com.mszq.uas.uasserver.bean;

public class RequireTokenExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    private String sessionId;
    private long appId;
    private String secret;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
