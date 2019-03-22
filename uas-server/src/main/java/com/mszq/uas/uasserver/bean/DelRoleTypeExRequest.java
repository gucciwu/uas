package com.mszq.uas.uasserver.bean;

public class DelRoleTypeExRequest extends ExRequest {
    private long roleType;

    public long getRoleType() {
        return roleType;
    }

    public void setRoleType(long roleType) {
        this.roleType = roleType;
    }
}
