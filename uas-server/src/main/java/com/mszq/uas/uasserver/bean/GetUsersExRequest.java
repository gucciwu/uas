package com.mszq.uas.uasserver.bean;

public class GetUsersExRequest extends com.mszq.uas.uasserver.bean.ExRequest {

    private String startUpdateTime;
    private String endUpdateTime;
    private short orgType;
    private long orgId;
    private long userId;
    private String jobNumber;
    private long appId;
    private String secret;
    private String idNumber;
    private Short idType;

    public void setOrgType(short orgType) {
        this.orgType = orgType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }

    public String getStartUpdateTime() {
        return startUpdateTime;
    }

    public void setStartUpdateTime(String startUpdateTime) {
        this.startUpdateTime = startUpdateTime;
    }

    public String getEndUpdateTime() {
        return endUpdateTime;
    }

    public void setEndUpdateTime(String endUpdateTime) {
        this.endUpdateTime = endUpdateTime;
    }
    public short getOrgType() {
        return orgType;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
