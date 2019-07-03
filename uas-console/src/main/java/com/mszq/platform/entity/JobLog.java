package com.mszq.platform.entity;

import java.util.Date;

public class JobLog {
    private Long id;
    private Integer jobId;
    private Date runTime;
    private String runResult;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getJobId() {
        return jobId;
    }
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
    public Date getRunTime() {
        return runTime;
    }
    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }
    public String getRunResult() {
        return runResult;
    }

    public void setRunResult(String runResult) {
        this.runResult = runResult;
    }
}