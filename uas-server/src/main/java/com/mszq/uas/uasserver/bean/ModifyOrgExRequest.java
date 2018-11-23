package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.Org;

public class ModifyOrgExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    private Org org;
}
