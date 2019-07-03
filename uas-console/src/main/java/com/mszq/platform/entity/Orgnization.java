package com.mszq.platform.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Orgnization {
	private Long id;
	private String name;
	private String telephone;
	private String address;
	private String parentOrgnizationName;
	private Long parentOrgnizationId;
	private Date createTime;
	private Date updateTime;
	private Long creatorId;
	private Long _parentId;
	private Long pid;
	private String registerCode;
	private String usci;
	private String legalRepresentative;
	private String contacts;
	private String mobile;
	private String zipCode;
	private String businessLicense;
	private String orgType;
	private MultipartFile licenseFile;
	
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
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getParentOrgnizationName() {
		return parentOrgnizationName;
	}
	public void setParentOrgnizationName(String parentOrgnizationName) {
		this.parentOrgnizationName = parentOrgnizationName;
	}
	public Long getParentOrgnizationId() {
		return parentOrgnizationId;
	}
	public void setParentOrgnizationId(Long parentOrgnizationId) {
		this.parentOrgnizationId = parentOrgnizationId;
		this._parentId = parentOrgnizationId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long get_parentId() {
		return _parentId;
	}
//	public void set_parentId(Long _parentId) {
//		this._parentId = _parentId;
//	}
//	public Long getPid() {
//		return pid;
//	}
	public void setPid(Long pid) {
		this.pid = pid;
		this.parentOrgnizationId = pid;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public String getUsci() {
		return usci;
	}
	public void setUsci(String usci) {
		this.usci = usci;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
    public MultipartFile getLicenseFile() {
        return licenseFile;
    }
    public void setLicenseFile(MultipartFile licenseFile) {
        this.licenseFile = licenseFile;
        if (licenseFile != null) {
            this.businessLicense = ((CommonsMultipartFile)licenseFile).getFileItem().getName();
        }
    }
	
	

}
