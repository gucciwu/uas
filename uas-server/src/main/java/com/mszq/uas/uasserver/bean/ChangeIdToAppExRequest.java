package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.AppAccount;

public class ChangeIdToAppExRequest extends com.mszq.uas.uasserver.bean.ExRequest {

    private AppAccount appAccount;
    private long appId;
    private String secret;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
