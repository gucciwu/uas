package com.mszq.uas.sso.bean;

public class SignoutExRequest extends Request {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
