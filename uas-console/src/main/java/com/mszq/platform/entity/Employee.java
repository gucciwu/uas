package com.mszq.platform.entity;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -321537172730361017L;
	private Long id;
	 private String account;
	 private String password;
	 private String name;	
	 private String pinyin;
	 private String photo;
	 private String mobile;
	 private String telphone;
	 private String email;
	 private Long orgnizationId;
	 private String orgnizationName;
	 private String roleIds;
	 private String roleNames;
	 private Integer status;
	 private Integer type;
	 private Integer sort;
	 private Long creatorId;
	 private Date createTime;
	 private Date updateTime;
	 
	 
	 public Employee(){}
	 public Employee(String name,String mobile,String orgnizationName,Date createTime,long creatorId){
		 this.name=name;
		 this.mobile=mobile;
		 this.orgnizationName=orgnizationName;
		 this.createTime=createTime;
		 this.creatorId=creatorId;
	 }
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getOrgnizationId() {
		return orgnizationId;
	}
	public void setOrgnizationId(Long orgnizationId) {
		this.orgnizationId = orgnizationId;
	}
	public String getOrgnizationName() {
		return orgnizationName;
	}
	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
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
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	 
}
