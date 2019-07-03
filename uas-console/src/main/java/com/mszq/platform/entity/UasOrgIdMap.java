package com.mszq.platform.entity;

public class UasOrgIdMap {
    private Long id;

    private Long srouceOrgId;

    private Short srouceOrgType;

    private Long targetOrgId;

    private Short targetOrgType;

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
}