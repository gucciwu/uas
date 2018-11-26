package com.mszq.uas.uasserver.bean;

import java.util.List;

public class GetAppAccountIdExRequest extends com.mszq.uas.uasserver.bean.ExRequest {
    private long userId;
    private String jobNumber;

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
