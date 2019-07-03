package com.mszq.platform.entity;

import java.util.Date;

public class Institution {
	 private Long id;

	    private String name;

	    private String registerCode;

	    private String type;

	    private Long parentId;

	    private String hvpsAccount;

	    private Date createTime;

	    private Date updateTime;

	    private Long creatorId;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getRegisterCode() {
	        return registerCode;
	    }

	    public void setRegisterCode(String registerCode) {
	        this.registerCode = registerCode;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public Long getParentId() {
	        return parentId;
	    }

	    public void setParentId(Long parentId) {
	        this.parentId = parentId;
	    }

	    public String getHvpsAccount() {
	        return hvpsAccount;
	    }

	    public void setHvpsAccount(String hvpsAccount) {
	        this.hvpsAccount = hvpsAccount;
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

	    public Long getCreatorId() {
	        return creatorId;
	    }

	    public void setCreatorId(Long creatorId) {
	        this.creatorId = creatorId;
	    }
}
