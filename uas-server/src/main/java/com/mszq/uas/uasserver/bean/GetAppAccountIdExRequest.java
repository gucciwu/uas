package com.mszq.uas.uasserver.bean;

import java.util.List;

public class GetAppAccountIdExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    private long appId;
    private String secret;

    private List<Long> userIds;
    private List<String> jobNumbers;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<String> getJobNumbers() {
        return jobNumbers;
    }

    public void setJobNumbers(List<String> jobNumbers) {
        this.jobNumbers = jobNumbers;
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
