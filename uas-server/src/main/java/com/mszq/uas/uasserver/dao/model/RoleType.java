package com.mszq.uas.uasserver.dao.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table uas_role_type
 *
 * @mbg.generated do_not_delete_during_merge Thu Nov 22 17:14:59 CST 2018
 */
public class RoleType {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_role_type.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_role_type.NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_role_type.COMMENT
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_role_type.ID
     *
     * @return the value of uas_role_type.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_role_type.ID
     *
     * @param id the value for uas_role_type.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_role_type.NAME
     *
     * @return the value of uas_role_type.NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_role_type.NAME
     *
     * @param name the value for uas_role_type.NAME
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_role_type.COMMENT
     *
     * @return the value of uas_role_type.COMMENT
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_role_type.COMMENT
     *
     * @param comment the value for uas_role_type.COMMENT
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}