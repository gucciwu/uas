package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.RoleType;

public class AddRoleTypeExRequest extends ExRequest {

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    private RoleType roleType;
}
