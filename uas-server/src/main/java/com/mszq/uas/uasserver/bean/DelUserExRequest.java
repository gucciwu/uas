package com.mszq.uas.uasserver.bean;

public class DelUserExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    private String jobNumber;
    private long appId;
    private String secret;

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

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
