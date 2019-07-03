package com.mszq.platform.app.workflow.dto;

import java.util.Date;

public class TodoDTO {
	private String processDefinationKey;
	private String processDefinationName;
	private String processInstanceId;
	private String title;
	private String taskId;
	private String taskName;
	private String businessDataId;//业务数据主键
	private String businessDataTable;//业务数据主表，businessKey就是businessTable表的主键值
	private Date createTime;
	
	private Date completeTime;
	
	private String type;
	private String status;//业务状态，用于在首页中设置显示哪些操作按钮
	
	private String approveUrl;
	
	private String sendUserName;
	
	
	public TodoDTO(){
		
	}
	public TodoDTO(String title,String taskId,String taskName,String processInstanceId,String processDefinationKey,String processDefinationName,Date createTime){
		this.title=title;
		this.taskId=taskId;
		this.taskName=taskName;
		this.processInstanceId=processInstanceId;
		this.processDefinationKey=processDefinationKey;
		this.processDefinationName=processDefinationName;
		this.createTime=createTime;
	}
	
	public TodoDTO(String title,String taskId,String taskName,String businessDataId,String businessDataTable,String processInstanceId,String processDefinationKey,String processDefinationName,Date createTime,String status){
		this.title=title;
		this.taskId=taskId;
		this.taskName=taskName;
		this.businessDataId=businessDataId;
		this.businessDataTable=businessDataTable;
		this.processInstanceId=processInstanceId;
		this.processDefinationKey=processDefinationKey;
		this.processDefinationName=processDefinationName;
		this.createTime=createTime;
		this.status=status;
	}

	
	public TodoDTO(String title,String taskId,String taskName,String businessDataId,
			String businessDataTable,String processInstanceId,String processDefinationKey,
			String processDefinationName,Date createTime,String status,String approveUrl){
		this.title=title;
		this.taskId=taskId;
		this.taskName=taskName;
		this.businessDataId=businessDataId;
		this.businessDataTable=businessDataTable;
		this.processInstanceId=processInstanceId;
		this.processDefinationKey=processDefinationKey;
		this.processDefinationName=processDefinationName;
		this.createTime=createTime;
		this.status=status;
		this.approveUrl=approveUrl;
	}
	
	
	
	public String getBusinessDataId() {
		return businessDataId;
	}
	public void setBusinessDataId(String businessDataId) {
		this.businessDataId = businessDataId;
	}
	public String getBusinessDataTable() {
		return businessDataTable;
	}
	public void setBusinessDataTable(String businessDataTable) {
		this.businessDataTable = businessDataTable;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProcessDefinationName() {
		return processDefinationName;
	}

	public void setProcessDefinationName(String processDefinationName) {
		this.processDefinationName = processDefinationName;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinationKey() {
		return processDefinationKey;
	}
	public void setProcessDefinationKey(String processDefinationKey) {
		this.processDefinationKey = processDefinationKey;
	}
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApproveUrl() {
		return approveUrl;
	}
	public void setApproveUrl(String approveUrl) {
		this.approveUrl = approveUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	
	

}
