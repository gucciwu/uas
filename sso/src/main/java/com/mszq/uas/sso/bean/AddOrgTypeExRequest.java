package com.mszq.uas.sso.bean;

import com.mszq.uas.sso.model.OrgType;

public class AddOrgTypeExRequest extends ExRequest {
    private OrgType orgType;

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }
}
