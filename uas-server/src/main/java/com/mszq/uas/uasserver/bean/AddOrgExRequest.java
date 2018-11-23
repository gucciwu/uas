package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.Org;

public class AddOrgExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    private Org org;

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }
}
