package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;
import com.mszq.uas.uasserver.dao.model.UserRole;

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
