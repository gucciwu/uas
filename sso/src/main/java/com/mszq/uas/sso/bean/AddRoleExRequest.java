package com.mszq.uas.sso.bean;


import com.mszq.uas.sso.model.Role;

public class AddRoleExRequest extends ExRequest {

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Role role;
}
