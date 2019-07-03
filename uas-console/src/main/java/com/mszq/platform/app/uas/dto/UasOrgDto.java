package com.mszq.platform.app.uas.dto;

public class UasOrgDto {
	private Long id;

    private Integer grade;

    private String name;

    private Short orgType;
    
    private String orgTypeName;

    private Long orgId;

    private Short status;
    
    private String statusName;

    private Long parentOrgId;
    
    private Long _parentId;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

	public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
    
    public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Long getParentOrgId() {
        return parentOrgId;
    }
    
    public Long get_parentId() {
		return _parentId;
	}

	public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
        this._parentId = parentOrgId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}
