package com.mszq.uas.sso.bean;

public class UpdateOrgResponse extends Response {
    private long innerOrgId;

    public long getInnerOrgId() {
        return innerOrgId;
    }

    public void setInnerOrgId(long innerOrgId) {
        this.innerOrgId = innerOrgId;
    }
}
