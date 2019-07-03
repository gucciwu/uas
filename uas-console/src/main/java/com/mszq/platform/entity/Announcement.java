package com.mszq.platform.entity;

import java.util.Date;

public class Announcement {
    private Integer id;
    private String title;
    private String content;
    private Byte isTop;
    private Byte status;
    private String validDate;
    private String type;
    private Integer creatorId;
    private Date creatorTime;
    private Date updateTime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Byte getIsTop() {
        return isTop;
    }
    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getValidDate() {
        return validDate;
    }
    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}