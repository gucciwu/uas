package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.RoleType;
import com.mszq.uas.sso.model.RoleType;

public class AddRoleTypeExRequest extends ExRequest {

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    private RoleType roleType;
}
