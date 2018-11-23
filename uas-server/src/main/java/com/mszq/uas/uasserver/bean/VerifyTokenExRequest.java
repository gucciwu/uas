package com.mszq.uas.uasserver.bean;

public class VerifyTokenExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
