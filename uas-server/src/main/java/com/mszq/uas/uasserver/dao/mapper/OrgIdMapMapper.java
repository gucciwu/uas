package com.mszq.uas.uasserver.dao.mapper;

import com.mszq.uas.uasserver.dao.model.OrgIdMap;
import com.mszq.uas.uasserver.dao.model.OrgIdMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgIdMapMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    long countByExample(OrgIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int deleteByExample(OrgIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int insert(OrgIdMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int insertSelective(OrgIdMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    List<OrgIdMap> selectByExample(OrgIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    OrgIdMap selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByExampleSelective(@Param("record") OrgIdMap record, @Param("example") OrgIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByExample(@Param("record") OrgIdMap record, @Param("example") OrgIdMapExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByPrimaryKeySelective(OrgIdMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG_ID_MAP
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByPrimaryKey(OrgIdMap record);
}