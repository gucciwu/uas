package com.mszq.uas.sso.bean;

public class UpdateUserResponse extends Response {
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
