package com.mszq.uas.syncdata.mode;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table uas_org
 *
 * @mbg.generated do_not_delete_during_merge Mon Mar 18 18:03:54 CST 2019
 */
public class Org {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.GRADE
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Integer grade;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.NAME
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.ORG_TYPE
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Short orgType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.ORG_ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Long orgId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.STATUS
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.PARENT_ORG_ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Long parentOrgId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.COMMENT
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_org.MODIFY_TIME
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    private Date modifyTime;

    private int hrId;
    private int adminId;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.ID
     *
     * @return the value of uas_org.ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.ID
     *
     * @param id the value for uas_org.ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.GRADE
     *
     * @return the value of uas_org.GRADE
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.GRADE
     *
     * @param grade the value for uas_org.GRADE
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.NAME
     *
     * @return the value of uas_org.NAME
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.NAME
     *
     * @param name the value for uas_org.NAME
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.ORG_TYPE
     *
     * @return the value of uas_org.ORG_TYPE
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Short getOrgType() {
        return orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.ORG_TYPE
     *
     * @param orgType the value for uas_org.ORG_TYPE
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setOrgType(Short orgType) {
        this.orgType = orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.ORG_ID
     *
     * @return the value of uas_org.ORG_ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.ORG_ID
     *
     * @param orgId the value for uas_org.ORG_ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.STATUS
     *
     * @return the value of uas_org.STATUS
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.STATUS
     *
     * @param status the value for uas_org.STATUS
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.PARENT_ORG_ID
     *
     * @return the value of uas_org.PARENT_ORG_ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Long getParentOrgId() {
        return parentOrgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.PARENT_ORG_ID
     *
     * @param parentOrgId the value for uas_org.PARENT_ORG_ID
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.COMMENT
     *
     * @return the value of uas_org.COMMENT
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.COMMENT
     *
     * @param comment the value for uas_org.COMMENT
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_org.MODIFY_TIME
     *
     * @return the value of uas_org.MODIFY_TIME
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_org.MODIFY_TIME
     *
     * @param modifyTime the value for uas_org.MODIFY_TIME
     *
     * @mbg.generated Mon Mar 18 18:03:54 CST 2019
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}