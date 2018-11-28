package com.mszq.uas.uasserver.dao.mapper;

import com.mszq.uas.uasserver.dao.model.IpList;
import com.mszq.uas.uasserver.dao.model.IpListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IpListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    long countByExample(IpListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int deleteByExample(IpListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int insert(IpList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int insertSelective(IpList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    List<IpList> selectByExample(IpListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    IpList selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByExampleSelective(@Param("record") IpList record, @Param("example") IpListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByExample(@Param("record") IpList record, @Param("example") IpListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByPrimaryKeySelective(IpList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UAS_IP_LIST
     *
     * @mbg.generated Wed Nov 28 15:48:47 CST 2018
     */
    int updateByPrimaryKey(IpList record);

    @Select("SELECT * FROM UAS_IP_LIST")
    List<IpList> selectAll();
}