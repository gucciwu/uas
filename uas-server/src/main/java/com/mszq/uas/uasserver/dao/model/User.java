package com.mszq.uas.uasserver.dao.model;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table UAS_USER
 *
 * @mbg.generated do_not_delete_during_merge Wed Nov 28 15:48:47 CST 2018
 */
public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.JOB_NUMBER
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String jobNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.USER_NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.CREATE_TIME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.MODIFY_TIME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Date modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.ID_NUMBER
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String idNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.ID_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short idType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.ORG_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long orgId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.ORG_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short orgType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.TEL
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String tel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.MOBILE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.EMAIL
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.LOGIN_COUNT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Integer loginCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.LAST_LOGIN_IP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String lastLoginIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_USER.LAST_LOGIN_INFO
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String lastLoginInfo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.ID
     *
     * @return the value of UAS_USER.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.ID
     *
     * @param id the value for UAS_USER.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.JOB_NUMBER
     *
     * @return the value of UAS_USER.JOB_NUMBER
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getJobNumber() {
        return jobNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.JOB_NUMBER
     *
     * @param jobNumber the value for UAS_USER.JOB_NUMBER
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.USER_NAME
     *
     * @return the value of UAS_USER.USER_NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.USER_NAME
     *
     * @param userName the value for UAS_USER.USER_NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.STATUS
     *
     * @return the value of UAS_USER.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.STATUS
     *
     * @param status the value for UAS_USER.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.CREATE_TIME
     *
     * @return the value of UAS_USER.CREATE_TIME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.CREATE_TIME
     *
     * @param createTime the value for UAS_USER.CREATE_TIME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.MODIFY_TIME
     *
     * @return the value of UAS_USER.MODIFY_TIME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.MODIFY_TIME
     *
     * @param modifyTime the value for UAS_USER.MODIFY_TIME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.ID_NUMBER
     *
     * @return the value of UAS_USER.ID_NUMBER
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.ID_NUMBER
     *
     * @param idNumber the value for UAS_USER.ID_NUMBER
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.ID_TYPE
     *
     * @return the value of UAS_USER.ID_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getIdType() {
        return idType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.ID_TYPE
     *
     * @param idType the value for UAS_USER.ID_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setIdType(Short idType) {
        this.idType = idType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.NAME
     *
     * @return the value of UAS_USER.NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.NAME
     *
     * @param name the value for UAS_USER.NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.ORG_ID
     *
     * @return the value of UAS_USER.ORG_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.ORG_ID
     *
     * @param orgId the value for UAS_USER.ORG_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.ORG_TYPE
     *
     * @return the value of UAS_USER.ORG_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getOrgType() {
        return orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.ORG_TYPE
     *
     * @param orgType the value for UAS_USER.ORG_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setOrgType(Short orgType) {
        this.orgType = orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.TEL
     *
     * @return the value of UAS_USER.TEL
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.TEL
     *
     * @param tel the value for UAS_USER.TEL
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.MOBILE
     *
     * @return the value of UAS_USER.MOBILE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.MOBILE
     *
     * @param mobile the value for UAS_USER.MOBILE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.EMAIL
     *
     * @return the value of UAS_USER.EMAIL
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.EMAIL
     *
     * @param email the value for UAS_USER.EMAIL
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.LOGIN_COUNT
     *
     * @return the value of UAS_USER.LOGIN_COUNT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.LOGIN_COUNT
     *
     * @param loginCount the value for UAS_USER.LOGIN_COUNT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.LAST_LOGIN_IP
     *
     * @return the value of UAS_USER.LAST_LOGIN_IP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.LAST_LOGIN_IP
     *
     * @param lastLoginIp the value for UAS_USER.LAST_LOGIN_IP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_USER.LAST_LOGIN_INFO
     *
     * @return the value of UAS_USER.LAST_LOGIN_INFO
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getLastLoginInfo() {
        return lastLoginInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_USER.LAST_LOGIN_INFO
     *
     * @param lastLoginInfo the value for UAS_USER.LAST_LOGIN_INFO
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setLastLoginInfo(String lastLoginInfo) {
        this.lastLoginInfo = lastLoginInfo == null ? null : lastLoginInfo.trim();
    }
}