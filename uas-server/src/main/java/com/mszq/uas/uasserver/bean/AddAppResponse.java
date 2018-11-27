package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.Response;

public class AddAppResponse extends Response {
    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    private long appId;
}
