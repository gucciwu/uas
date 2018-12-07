package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.Org;

public class UpdateOrgRequest extends ExRequest {
    private Org org;

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
