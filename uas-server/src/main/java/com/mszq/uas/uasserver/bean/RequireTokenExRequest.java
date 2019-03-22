package com.mszq.uas.uasserver.bean;

public class RequireTokenExRequest extends ExRequest {
    private String sessionId;
    private long appId;

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
}
