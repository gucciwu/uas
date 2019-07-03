package com.mszq.platform.app.workflow.dto;

public class ApproveDTO {
	
	public static String APPROVE_TYPE_APPROVE="approve";
	public static String APPROVE_TYPE_REJECT="reject";
	public static String APPROVE_TYPE_TRANS="trans";
	
	private String taskId;
	private String id;
	private String approveType;
	private String opinion;
	private String rejectOpinion;
	private String activityId;
	private String userId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getRejectOpinion() {
		return rejectOpinion;
	}
	public void setRejectOpinion(String rejectOpinion) {
		this.rejectOpinion = rejectOpinion;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
