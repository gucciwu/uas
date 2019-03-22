package com.mszq.uas.uasserver.bean;

public class DelRoleExRequest extends ExRequest {
    private long roleId;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
