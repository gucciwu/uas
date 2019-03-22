package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.Role;

public class AddRoleExRequest extends ExRequest {

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Role role;
}
