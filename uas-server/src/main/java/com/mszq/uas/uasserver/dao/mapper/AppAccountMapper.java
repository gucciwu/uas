package com.mszq.uas.uasserver.dao.mapper;

import com.mszq.uas.uasserver.dao.model.AppAccount;
import com.mszq.uas.uasserver.dao.model.AppAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    long countByExample(AppAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int deleteByExample(AppAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int insert(AppAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int insertSelective(AppAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    List<AppAccount> selectByExample(AppAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    AppAccount selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int updateByExampleSelective(@Param("record") AppAccount record, @Param("example") AppAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int updateByExample(@Param("record") AppAccount record, @Param("example") AppAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int updateByPrimaryKeySelective(AppAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_app_account
     *
     * @mbg.generated Mon Nov 26 17:03:41 CST 2018
     */
    int updateByPrimaryKey(AppAccount record);
}