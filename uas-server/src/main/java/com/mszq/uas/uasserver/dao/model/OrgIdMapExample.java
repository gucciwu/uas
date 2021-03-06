package com.mszq.uas.uasserver.dao.model;

import java.util.ArrayList;
import java.util.List;

public class OrgIdMapExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public OrgIdMapExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdIsNull() {
            addCriterion("SROUCE_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdIsNotNull() {
            addCriterion("SROUCE_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdEqualTo(Long value) {
            addCriterion("SROUCE_ORG_ID =", value, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdNotEqualTo(Long value) {
            addCriterion("SROUCE_ORG_ID <>", value, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdGreaterThan(Long value) {
            addCriterion("SROUCE_ORG_ID >", value, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SROUCE_ORG_ID >=", value, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdLessThan(Long value) {
            addCriterion("SROUCE_ORG_ID <", value, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdLessThanOrEqualTo(Long value) {
            addCriterion("SROUCE_ORG_ID <=", value, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdIn(List<Long> values) {
            addCriterion("SROUCE_ORG_ID in", values, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdNotIn(List<Long> values) {
            addCriterion("SROUCE_ORG_ID not in", values, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdBetween(Long value1, Long value2) {
            addCriterion("SROUCE_ORG_ID between", value1, value2, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgIdNotBetween(Long value1, Long value2) {
            addCriterion("SROUCE_ORG_ID not between", value1, value2, "srouceOrgId");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeIsNull() {
            addCriterion("SROUCE_ORG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeIsNotNull() {
            addCriterion("SROUCE_ORG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeEqualTo(Short value) {
            addCriterion("SROUCE_ORG_TYPE =", value, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeNotEqualTo(Short value) {
            addCriterion("SROUCE_ORG_TYPE <>", value, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeGreaterThan(Short value) {
            addCriterion("SROUCE_ORG_TYPE >", value, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("SROUCE_ORG_TYPE >=", value, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeLessThan(Short value) {
            addCriterion("SROUCE_ORG_TYPE <", value, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeLessThanOrEqualTo(Short value) {
            addCriterion("SROUCE_ORG_TYPE <=", value, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeIn(List<Short> values) {
            addCriterion("SROUCE_ORG_TYPE in", values, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeNotIn(List<Short> values) {
            addCriterion("SROUCE_ORG_TYPE not in", values, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeBetween(Short value1, Short value2) {
            addCriterion("SROUCE_ORG_TYPE between", value1, value2, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andSrouceOrgTypeNotBetween(Short value1, Short value2) {
            addCriterion("SROUCE_ORG_TYPE not between", value1, value2, "srouceOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdIsNull() {
            addCriterion("TARGET_ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdIsNotNull() {
            addCriterion("TARGET_ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdEqualTo(Long value) {
            addCriterion("TARGET_ORG_ID =", value, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdNotEqualTo(Long value) {
            addCriterion("TARGET_ORG_ID <>", value, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdGreaterThan(Long value) {
            addCriterion("TARGET_ORG_ID >", value, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TARGET_ORG_ID >=", value, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdLessThan(Long value) {
            addCriterion("TARGET_ORG_ID <", value, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdLessThanOrEqualTo(Long value) {
            addCriterion("TARGET_ORG_ID <=", value, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdIn(List<Long> values) {
            addCriterion("TARGET_ORG_ID in", values, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdNotIn(List<Long> values) {
            addCriterion("TARGET_ORG_ID not in", values, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdBetween(Long value1, Long value2) {
            addCriterion("TARGET_ORG_ID between", value1, value2, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgIdNotBetween(Long value1, Long value2) {
            addCriterion("TARGET_ORG_ID not between", value1, value2, "targetOrgId");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeIsNull() {
            addCriterion("TARGET_ORG_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeIsNotNull() {
            addCriterion("TARGET_ORG_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeEqualTo(Short value) {
            addCriterion("TARGET_ORG_TYPE =", value, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeNotEqualTo(Short value) {
            addCriterion("TARGET_ORG_TYPE <>", value, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeGreaterThan(Short value) {
            addCriterion("TARGET_ORG_TYPE >", value, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("TARGET_ORG_TYPE >=", value, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeLessThan(Short value) {
            addCriterion("TARGET_ORG_TYPE <", value, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeLessThanOrEqualTo(Short value) {
            addCriterion("TARGET_ORG_TYPE <=", value, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeIn(List<Short> values) {
            addCriterion("TARGET_ORG_TYPE in", values, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeNotIn(List<Short> values) {
            addCriterion("TARGET_ORG_TYPE not in", values, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeBetween(Short value1, Short value2) {
            addCriterion("TARGET_ORG_TYPE between", value1, value2, "targetOrgType");
            return (Criteria) this;
        }

        public Criteria andTargetOrgTypeNotBetween(Short value1, Short value2) {
            addCriterion("TARGET_ORG_TYPE not between", value1, value2, "targetOrgType");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated do_not_delete_during_merge Wed Nov 28 15:48:47 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}