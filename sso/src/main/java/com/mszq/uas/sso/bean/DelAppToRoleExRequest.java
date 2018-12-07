package com.mszq.uas.sso.bean;

public class DelAppToRoleExRequest extends ExRequest {

    private long roleAppId;

    public long getRoleAppId() {
        return roleAppId;
    }

    public void setRoleAppId(long roleAppId) {
        this.roleAppId = roleAppId;
    }

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

    private long roleId;
    private long appId;
}
