package com.mszq.uas.sso.bean;

public class VerifyTokenResponse extends com.mszq.uas.sso.bean.Response {

    private String sessionId;
    private long userId;
    private String jobNumber;
    private String appAccount;
    private long orgId;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(String appAccount) {
        this.appAccount = appAccount;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
}
