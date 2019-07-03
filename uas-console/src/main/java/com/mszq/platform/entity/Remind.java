package com.mszq.platform.entity;


import java.util.Date;

public class Remind {

    private Long id;

    private String title;

    private String content;

    private Date sendTime;

    private Date expireTime;

    private Long senderId;

    private String senderName;
    private Long receiverId;

    private String type;

    private String status;
    
    public Remind(){}
    
    public Remind(String title,String content,Date sendTime,Date expireTime,Long senderId,String senderName,Long receiverId,String type,String status){
    	this.title=title;
    	this.content=content;
    	this.sendTime=sendTime;
    	this.expireTime=expireTime;
    	this.senderId=senderId;
    	this.senderName=senderName;
    	this.setReceiverId(receiverId);
    	this.type=type;
    	this.status=status;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
}