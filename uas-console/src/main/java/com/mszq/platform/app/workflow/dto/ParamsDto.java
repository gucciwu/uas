package com.mszq.platform.app.workflow.dto;

public class ParamsDto {
	private String pdName;
	
	private String assignee;

	/**
	 * @return the pdName
	 */
	public String getPdName() {
		return pdName;
	}

	/**
	 * @param pdName the pdName to set
	 */
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

}
