package com.mszq.platform.entity;

import java.util.Date;

public class Job {
	private Integer id;
    private String name;
    private String jobClass;
    private String descrption;
    private Date creatTime;
    private Date updataTime;
    private Integer creatorId;
    private String quartzTime;
    private String status;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJobClass() {
        return jobClass;
    }
    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }
    public String getDescrption() {
        return descrption;
    }
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
    public Date getCreatTime() {
        return creatTime;
    }
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
    public Date getUpdataTime() {
        return updataTime;
    }
    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }
    public Integer getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
    public String getQuartzTime() {
        return quartzTime;
    }
    public void setQuartzTime(String quartzTime) {
        this.quartzTime = quartzTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
