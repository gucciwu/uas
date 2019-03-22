package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.Org;

public class UpdateOrgRequest extends ExRequest {
    private Org org;

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
