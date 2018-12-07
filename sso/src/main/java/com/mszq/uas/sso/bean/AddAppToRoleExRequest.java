package com.mszq.uas.sso.bean;

public class AddAppToRoleExRequest extends ExRequest {

    private long roleId;
    private long appId;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
