package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;

public class DelRoleToUserExRequest extends ExRequest {

    private long roleId;
    private long userId;
    private boolean autoDelAccount;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isAutoDelAccount() {
        return autoDelAccount;
    }

    public void setAutoDelAccount(boolean autoDelAccount) {
        this.autoDelAccount = autoDelAccount;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
