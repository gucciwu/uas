package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.Response;

public class AddRoleToUserResponse extends Response{
    private long userRoleId;

    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }
}
