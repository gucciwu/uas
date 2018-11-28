package com.mszq.uas.uasserver.dao.mapper;

import com.mszq.uas.uasserver.dao.model.Org;
import com.mszq.uas.uasserver.dao.model.OrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    long countByExample(OrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int deleteByExample(OrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int insert(Org record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int insertSelective(Org record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    List<Org> selectByExample(OrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    Org selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByExampleSelective(@Param("record") Org record, @Param("example") OrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByExample(@Param("record") Org record, @Param("example") OrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByPrimaryKeySelective(Org record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_ORG
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByPrimaryKey(Org record);
}