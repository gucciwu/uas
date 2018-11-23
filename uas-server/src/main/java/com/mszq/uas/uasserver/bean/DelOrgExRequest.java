package com.mszq.uas.uasserver.bean;

public class DelOrgExRequest extends com.mszq.uas.uasserver.bean.ExRequest {

    private long orgId;
    private short orgType;

    public short getOrgType() {
        return orgType;
    }

    public void setOrgType(short orgType) {
        this.orgType = orgType;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
