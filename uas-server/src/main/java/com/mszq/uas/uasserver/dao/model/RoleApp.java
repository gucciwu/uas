package com.mszq.uas.uasserver.dao.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table uas_role_app
 *
 * @mbg.generated do_not_delete_during_merge Thu Nov 22 17:14:59 CST 2018
 */
public class RoleApp {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_role_app.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_role_app.ROLE_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Long roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_role_app.APP_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    private Long appId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_role_app.ID
     *
     * @return the value of uas_role_app.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_role_app.ID
     *
     * @param id the value for uas_role_app.ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_role_app.ROLE_ID
     *
     * @return the value of uas_role_app.ROLE_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_role_app.ROLE_ID
     *
     * @param roleId the value for uas_role_app.ROLE_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_role_app.APP_ID
     *
     * @return the value of uas_role_app.APP_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_role_app.APP_ID
     *
     * @param appId the value for uas_role_app.APP_ID
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }
}