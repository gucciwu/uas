package com.mszq.uas.sso.model;

/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table UAS_ORG
 *
 * @mbg.generated do_not_delete_during_merge Wed Nov 28 15:48:47 CST 2018
 */
public class Org {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.GRADE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Integer grade;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.ORG_TYPE
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short orgType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.ORG_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.PARENT_ORG_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long parentOrgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.ID
     *
     * @return the value of UAS_ORG.ID
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.ID
     *
     * @param id the value for UAS_ORG.ID
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.GRADE
     *
     * @return the value of UAS_ORG.GRADE
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.GRADE
     *
     * @param grade the value for UAS_ORG.GRADE
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.NAME
     *
     * @return the value of UAS_ORG.NAME
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.NAME
     *
     * @param name the value for UAS_ORG.NAME
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.ORG_TYPE
     *
     * @return the value of UAS_ORG.ORG_TYPE
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getOrgType() {
        return orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.ORG_TYPE
     *
     * @param orgType the value for UAS_ORG.ORG_TYPE
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setOrgType(Short orgType) {
        this.orgType = orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.ORG_ID
     *
     * @return the value of UAS_ORG.ORG_ID
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.ORG_ID
     *
     * @param orgId the value for UAS_ORG.ORG_ID
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.STATUS
     *
     * @return the value of UAS_ORG.STATUS
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.STATUS
     *
     * @param status the value for UAS_ORG.STATUS
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.PARENT_ORG_ID
     *
     * @return the value of UAS_ORG.PARENT_ORG_ID
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getParentOrgId() {
        return parentOrgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.PARENT_ORG_ID
     *
     * @param parentOrgId the value for UAS_ORG.PARENT_ORG_ID
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG.COMMENT
     *
     * @return the value of UAS_ORG.COMMENT
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG.COMMENT
     *
     * @param comment the value for UAS_ORG.COMMENT
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}