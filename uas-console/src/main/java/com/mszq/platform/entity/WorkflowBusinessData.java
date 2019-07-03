package com.mszq.platform.entity;

import java.util.Date;

/**
 * 工作流与业务数据的关联关系表
 * @author michel
 *
 */
public class WorkflowBusinessData {
	private String id;
	private String processInstanceId;
	private String businessDataId;
	private String businessTableName;
	private String isCurrentAssignee="0";//判断当前登录人是否是当前task的候选人,默认是0，代表不是当前候选人
	private Date createTime;
	
	public WorkflowBusinessData(){
		
	}
	public WorkflowBusinessData(String id,String processInstanceId,String businessDataId,String businessTableName,Date createTime){
		this.id=id;
		this.processInstanceId=processInstanceId;
		this.businessDataId=businessDataId;
		this.businessTableName=businessTableName;
		this.createTime=createTime;
	}
	
	
	public String getIsCurrentAssignee() {
		return isCurrentAssignee;
	}
	public void setIsCurrentAssignee(String isCurrentAssignee) {
		this.isCurrentAssignee = isCurrentAssignee;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstaceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getBusinessDataId() {
		return businessDataId;
	}
	public void setBusinessDataId(String businessDataId) {
		this.businessDataId = businessDataId;
	}
	public String getBusinessTableName() {
		return businessTableName;
	}
	public void setBusinessTableName(String businessTableName) {
		this.businessTableName = businessTableName;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
