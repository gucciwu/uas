package com.mszq.uas.sso.bean;

public class AddRoleToUserExRequest extends ExRequest {

    private boolean autoAddAccount;
    private long userId;
    private long roleId;

    public boolean isAutoAddAccount() {
        return autoAddAccount;
    }

    public void setAutoAddAccount(boolean autoAddAccount) {
        this.autoAddAccount = autoAddAccount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
