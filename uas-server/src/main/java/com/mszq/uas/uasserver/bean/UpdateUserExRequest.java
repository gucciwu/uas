package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.User;

public class UpdateUserExRequest extends com.mszq.uas.uasserver.bean.ExRequest {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
