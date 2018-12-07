package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.User;

public class UpdateUserExRequest extends ExRequest {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
