package com.mszq.platform.app.uas.dto;

public class UasAppDto {
	private Long id;

	private String name;

	private String secret;

	private Short orgType;

	private String orgTypeName;

	private String path;

	private String comment;

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
		this.name = name == null ? null : name.trim();
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret == null ? null : secret.trim();
	}

	public Short getOrgType() {
		return orgType;
	}

	public void setOrgType(Short orgType) {
		this.orgType = orgType;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? null : comment.trim();
	}
}
