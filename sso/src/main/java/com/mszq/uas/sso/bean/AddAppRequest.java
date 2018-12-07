package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.App;

public class AddAppRequest extends ExRequest {
    private App app;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
