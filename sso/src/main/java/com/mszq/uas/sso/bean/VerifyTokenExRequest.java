package com.mszq.uas.sso.bean;

public class VerifyTokenExRequest extends ExRequest {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
