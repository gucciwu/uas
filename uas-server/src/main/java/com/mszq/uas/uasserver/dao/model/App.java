package com.mszq.uas.uasserver.dao.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table uas_app
 *
 * @mbg.generated do_not_delete_during_merge Mon Nov 26 17:03:41 CST 2018
 */
public class App {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_app.ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_app.NAME
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_app.SECRET
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String secret;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_app.ORG_TYPE
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private Short orgType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_app.PATH
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String path;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_app.COMMENT
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_app.ID
     *
     * @return the value of uas_app.ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_app.ID
     *
     * @param id the value for uas_app.ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_app.NAME
     *
     * @return the value of uas_app.NAME
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_app.NAME
     *
     * @param name the value for uas_app.NAME
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_app.SECRET
     *
     * @return the value of uas_app.SECRET
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getSecret() {
        return secret;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_app.SECRET
     *
     * @param secret the value for uas_app.SECRET
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_app.ORG_TYPE
     *
     * @return the value of uas_app.ORG_TYPE
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public Short getOrgType() {
        return orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_app.ORG_TYPE
     *
     * @param orgType the value for uas_app.ORG_TYPE
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setOrgType(Short orgType) {
        this.orgType = orgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_app.PATH
     *
     * @return the value of uas_app.PATH
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_app.PATH
     *
     * @param path the value for uas_app.PATH
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_app.COMMENT
     *
     * @return the value of uas_app.COMMENT
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_app.COMMENT
     *
     * @param comment the value for uas_app.COMMENT
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}