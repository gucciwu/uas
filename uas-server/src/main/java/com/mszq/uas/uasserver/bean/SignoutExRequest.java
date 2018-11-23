package com.mszq.uas.uasserver.bean;

public class SignoutExRequest extends com.mszq.uas.uasserver.bean.Request {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
