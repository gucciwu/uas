package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.AppAccount;

public class ChangeIdToAppExRequest extends ExRequest {

    private AppAccount appAccount;

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
