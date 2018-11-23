package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.Role;

public class ModifyRoleExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
