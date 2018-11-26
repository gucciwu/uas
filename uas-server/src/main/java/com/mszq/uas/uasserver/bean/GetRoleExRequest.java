package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;

public class GetRoleExRequest extends ExRequest {
    private String roleName;
    private short status;
    private long parentId;
    private int roleTypeId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(int roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
}
