package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.Role;

public class AddRoleExRequest extends com.mszq.uas.uasserver.bean.ExRequest {

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Role role;
}
