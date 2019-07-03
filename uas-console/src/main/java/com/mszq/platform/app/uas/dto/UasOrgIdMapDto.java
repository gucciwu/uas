package com.mszq.platform.app.uas.dto;

public class UasOrgIdMapDto {
	private Long id;

    private Long srouceOrgId;
    
    private String srouceOrgName;

    private Short srouceOrgType;
    
    private String srouceOrgTypeName;

    private Long targetOrgId;
    
    private String targetOrgName;

    private Short targetOrgType;
    
    private String targetOrgTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSrouceOrgId() {
        return srouceOrgId;
    }

    public void setSrouceOrgId(Long srouceOrgId) {
        this.srouceOrgId = srouceOrgId;
    }

    public Short getSrouceOrgType() {
        return srouceOrgType;
    }

    public void setSrouceOrgType(Short srouceOrgType) {
        this.srouceOrgType = srouceOrgType;
    }

    public Long getTargetOrgId() {
        return targetOrgId;
    }

    public void setTargetOrgId(Long targetOrgId) {
        this.targetOrgId = targetOrgId;
    }

    public Short getTargetOrgType() {
        return targetOrgType;
    }

    public void setTargetOrgType(Short targetOrgType) {
        this.targetOrgType = targetOrgType;
    }

	public String getSrouceOrgName() {
		return srouceOrgName;
	}

	public void setSrouceOrgName(String srouceOrgName) {
		this.srouceOrgName = srouceOrgName;
	}

	public String getSrouceOrgTypeName() {
		return srouceOrgTypeName;
	}

	public void setSrouceOrgTypeName(String srouceOrgTypeName) {
		this.srouceOrgTypeName = srouceOrgTypeName;
	}

	public String getTargetOrgName() {
		return targetOrgName;
	}

	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	public String getTargetOrgTypeName() {
		return targetOrgTypeName;
	}

	public void setTargetOrgTypeName(String targetOrgTypeName) {
		this.targetOrgTypeName = targetOrgTypeName;
	}
}
