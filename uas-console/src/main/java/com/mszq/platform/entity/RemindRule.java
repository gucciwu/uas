package com.mszq.platform.entity;

public class RemindRule {
	private Long id;
	private String tableName;
	private String triggerRule;
	private String contentRule;
	private String targetRule;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTriggerRule() {
		return triggerRule;
	}
	public void setTriggerRule(String triggerRule) {
		this.triggerRule = triggerRule;
	}
	public String getContentRule() {
		return contentRule;
	}
	public void setContentRule(String contentRule) {
		this.contentRule = contentRule;
	}
	public String getTargetRule() {
		return targetRule;
	}
	public void setTargetRule(String targetRule) {
		this.targetRule = targetRule;
	}
	
	

}
