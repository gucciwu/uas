package com.mszq.uas.sso.bean;

public class AddRoleToUserResponse extends Response {
    private long userRoleId;

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }
}
