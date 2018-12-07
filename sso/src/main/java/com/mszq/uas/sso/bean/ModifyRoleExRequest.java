package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.Role;

public class ModifyRoleExRequest extends ExRequest {
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
