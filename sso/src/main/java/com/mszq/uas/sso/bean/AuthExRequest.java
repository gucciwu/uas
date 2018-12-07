package com.mszq.uas.sso.bean;

public class AuthExRequest extends Request {
    private String jobNumber;
    private String password;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
