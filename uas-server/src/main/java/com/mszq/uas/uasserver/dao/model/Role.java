package com.mszq.uas.uasserver.dao.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table UAS_ROLE
 *
 * @mbg.generated do_not_delete_during_merge Wed Nov 28 15:48:47 CST 2018
 */
public class Role {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ROLE.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ROLE.ROLE_NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ROLE.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ROLE.PARENT_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Long parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ROLE.ROLE_TYPE_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private Integer roleTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UAS_ROLE.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ROLE.ID
     *
     * @return the value of UAS_ROLE.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ROLE.ID
     *
     * @param id the value for UAS_ROLE.ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ROLE.ROLE_NAME
     *
     * @return the value of UAS_ROLE.ROLE_NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ROLE.ROLE_NAME
     *
     * @param roleName the value for UAS_ROLE.ROLE_NAME
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ROLE.STATUS
     *
     * @return the value of UAS_ROLE.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ROLE.STATUS
     *
     * @param status the value for UAS_ROLE.STATUS
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ROLE.PARENT_ID
     *
     * @return the value of UAS_ROLE.PARENT_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ROLE.PARENT_ID
     *
     * @param parentId the value for UAS_ROLE.PARENT_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ROLE.ROLE_TYPE_ID
     *
     * @return the value of UAS_ROLE.ROLE_TYPE_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Integer getRoleTypeId() {
        return roleTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ROLE.ROLE_TYPE_ID
     *
     * @param roleTypeId the value for UAS_ROLE.ROLE_TYPE_ID
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setRoleTypeId(Integer roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UAS_ROLE.COMMENT
     *
     * @return the value of UAS_ROLE.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UAS_ROLE.COMMENT
     *
     * @param comment the value for UAS_ROLE.COMMENT
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}