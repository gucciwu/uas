package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.dao.model.OrgType;

public class AddOrgTypeExRequest extends ExRequest {
    private OrgType orgType;

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }
}
