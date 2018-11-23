package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;
import com.mszq.uas.uasserver.dao.model.App;

public class AddAppRequest extends ExRequest {
    private App app;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
