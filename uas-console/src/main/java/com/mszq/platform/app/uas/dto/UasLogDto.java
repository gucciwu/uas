package com.mszq.platform.app.uas.dto;

import java.util.Date;

public class UasLogDto {
	private Long id;

    private Date operTime;

    private String operComment;

    private String loginIp;

    private Integer userId;
    
    private String userName;

    private String accountId;

    private Short accountType;

    private Long appId;
    
    private String appName;

    private String lastLoginMac;

    private String lastLoginInfo;

    private String lastLoginIp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getOperComment() {
		return operComment;
	}

	public void setOperComment(String operComment) {
		this.operComment = operComment;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Short getAccountType() {
		return accountType;
	}

	public void setAccountType(Short accountType) {
		this.accountType = accountType;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getLastLoginMac() {
		return lastLoginMac;
	}

	public void setLastLoginMac(String lastLoginMac) {
		this.lastLoginMac = lastLoginMac;
	}

	public String getLastLoginInfo() {
		return lastLoginInfo;
	}

	public void setLastLoginInfo(String lastLoginInfo) {
		this.lastLoginInfo = lastLoginInfo;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
}
