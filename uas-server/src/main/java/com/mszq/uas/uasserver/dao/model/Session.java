package com.mszq.uas.uasserver.dao.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table uas_session
 *
 * @mbg.generated do_not_delete_during_merge Mon Nov 26 17:03:41 CST 2018
 */
public class Session {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_session.ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_session.SESSION_ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String sessionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_session.TICKET
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String ticket;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_session.USER_ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column uas_session.JOB_NUMBER
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    private String jobNumber;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_session.ID
     *
     * @return the value of uas_session.ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_session.ID
     *
     * @param id the value for uas_session.ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_session.SESSION_ID
     *
     * @return the value of uas_session.SESSION_ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_session.SESSION_ID
     *
     * @param sessionId the value for uas_session.SESSION_ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_session.TICKET
     *
     * @return the value of uas_session.TICKET
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_session.TICKET
     *
     * @param ticket the value for uas_session.TICKET
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_session.USER_ID
     *
     * @return the value of uas_session.USER_ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_session.USER_ID
     *
     * @param userId the value for uas_session.USER_ID
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column uas_session.JOB_NUMBER
     *
     * @return the value of uas_session.JOB_NUMBER
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public String getJobNumber() {
        return jobNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column uas_session.JOB_NUMBER
     *
     * @param jobNumber the value for uas_session.JOB_NUMBER
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? null : jobNumber.trim();
    }
}