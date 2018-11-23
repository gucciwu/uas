package com.mszq.uas.uasserver.dao.model;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table uas_user
 *
 * @mbg.generated do_not_delete_during_merge Thu Nov 22 17:14:59 CST 2018
 */
public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.JOB_NUMBER
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String jobNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.USER_NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.STATUS
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.CREATE_TIME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.MODIFY_TIME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Date modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.ID_NUMBER
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String idNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.ID_TYPE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Short idType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.ORG_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Long orgId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.ORG_TYPE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Short orgType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.TEL
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String tel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.MOBILE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.EMAIL
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.LOGIN_COUNT
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Integer loginCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.LAST_LOGIN_IP
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String lastLoginIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_user.LAST_LOGIN_INFO
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String lastLoginInfo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.ID
     *
     * @return the value of uas_user.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.ID
     *
     * @param id the value for uas_user.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.JOB_NUMBER
     *
     * @return the value of uas_user.JOB_NUMBER
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getJobNumber() {
        return jobNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.JOB_NUMBER
     *
     * @param jobNumber the value for uas_user.JOB_NUMBER
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.USER_NAME
     *
     * @return the value of uas_user.USER_NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.USER_NAME
     *
     * @param userName the value for uas_user.USER_NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.STATUS
     *
     * @return the value of uas_user.STATUS
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.STATUS
     *
     * @param status the value for uas_user.STATUS
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.CREATE_TIME
     *
     * @return the value of uas_user.CREATE_TIME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.CREATE_TIME
     *
     * @param createTime the value for uas_user.CREATE_TIME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.MODIFY_TIME
     *
     * @return the value of uas_user.MODIFY_TIME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.MODIFY_TIME
     *
     * @param modifyTime the value for uas_user.MODIFY_TIME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.ID_NUMBER
     *
     * @return the value of uas_user.ID_NUMBER
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.ID_NUMBER
     *
     * @param idNumber the value for uas_user.ID_NUMBER
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.ID_TYPE
     *
     * @return the value of uas_user.ID_TYPE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Short getIdType() {
        return idType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.ID_TYPE
     *
     * @param idType the value for uas_user.ID_TYPE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setIdType(Short idType) {
        this.idType = idType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.NAME
     *
     * @return the value of uas_user.NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.NAME
     *
     * @param name the value for uas_user.NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.ORG_ID
     *
     * @return the value of uas_user.ORG_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.ORG_ID
     *
     * @param orgId the value for uas_user.ORG_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.ORG_TYPE
     *
     * @return the value of uas_user.ORG_TYPE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Short getOrgType() {
        return orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.ORG_TYPE
     *
     * @param orgType the value for uas_user.ORG_TYPE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setOrgType(Short orgType) {
        this.orgType = orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.TEL
     *
     * @return the value of uas_user.TEL
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.TEL
     *
     * @param tel the value for uas_user.TEL
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.MOBILE
     *
     * @return the value of uas_user.MOBILE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.MOBILE
     *
     * @param mobile the value for uas_user.MOBILE
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.EMAIL
     *
     * @return the value of uas_user.EMAIL
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.EMAIL
     *
     * @param email the value for uas_user.EMAIL
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.LOGIN_COUNT
     *
     * @return the value of uas_user.LOGIN_COUNT
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.LOGIN_COUNT
     *
     * @param loginCount the value for uas_user.LOGIN_COUNT
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.LAST_LOGIN_IP
     *
     * @return the value of uas_user.LAST_LOGIN_IP
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.LAST_LOGIN_IP
     *
     * @param lastLoginIp the value for uas_user.LAST_LOGIN_IP
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_user.LAST_LOGIN_INFO
     *
     * @return the value of uas_user.LAST_LOGIN_INFO
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getLastLoginInfo() {
        return lastLoginInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_user.LAST_LOGIN_INFO
     *
     * @param lastLoginInfo the value for uas_user.LAST_LOGIN_INFO
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setLastLoginInfo(String lastLoginInfo) {
        this.lastLoginInfo = lastLoginInfo == null ? null : lastLoginInfo.trim();
    }
}