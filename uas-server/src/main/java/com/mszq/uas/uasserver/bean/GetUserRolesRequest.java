package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;

public class GetUserRolesRequest extends ExRequest {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
