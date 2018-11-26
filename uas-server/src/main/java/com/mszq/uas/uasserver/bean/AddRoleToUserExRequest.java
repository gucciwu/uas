package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;
import com.mszq.uas.uasserver.dao.model.UserRole;

public class AddRoleToUserExRequest extends ExRequest {

    private boolean autoAddAccount;
    private UserRole userRole;

    public boolean isAutoAddAccount() {
        return autoAddAccount;
    }

    public void setAutoAddAccount(boolean autoAddAccount) {
        this.autoAddAccount = autoAddAccount;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
