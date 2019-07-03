package com.mszq.platform.entity;

import java.io.Serializable;
import java.util.Date;

public class Config implements Serializable {
	/**
     * 序列化
     */
    private static final long serialVersionUID = 8332005704402289862L;

    private int id;
	private String name;
	private String code;
	private String value;
	private int status;//1 为可用，0为不可用，可能还会有其他状态
	private Date createTime;
	private Date updateTime;
	private int creatorID;
	
	public Config(){
		
	}
	public Config(int id,String name,String code,String value,int status,Date createTime,Date updateTime,int creatorId){
		this.id=id;
		this.name=name;
		this.code=code;
		this.value=value;
		this.status=status;
		this.createTime=createTime;
		this.updateTime=updateTime;
		this.creatorID=creatorId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
