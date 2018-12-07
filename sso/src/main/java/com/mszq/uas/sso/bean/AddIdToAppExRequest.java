package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.AppAccount;

public class AddIdToAppExRequest extends ExRequest {
    private AppAccount appAccount;

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
