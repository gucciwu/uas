package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.ExRequest;

public class DelAppRequest extends ExRequest {
    private long appId;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
