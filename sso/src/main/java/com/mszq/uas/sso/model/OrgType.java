package com.mszq.uas.sso.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table UAS_ORG_TYPE
 *
 * @mbg.generated do_not_delete_during_merge Wed Nov 28 15:48:47 CST 2018
 */
public class OrgType {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG_TYPE.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ORG_TYPE.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG_TYPE.ID
     *
     * @return the value of UAS_ORG_TYPE.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG_TYPE.ID
     *
     * @param id the value for UAS_ORG_TYPE.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ORG_TYPE.COMMENT
     *
     * @return the value of UAS_ORG_TYPE.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ORG_TYPE.COMMENT
     *
     * @param comment the value for UAS_ORG_TYPE.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}