package com.mszq.uas.uasserver.bean;

public class GetOrgsExRequest extends ExRequest {

    private int grade;
    private String name;
    private int orgType;
    private long orgId;
    private int status;
    private long parentOrgId;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
}
