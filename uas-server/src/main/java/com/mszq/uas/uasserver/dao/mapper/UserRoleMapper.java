package com.mszq.uas.uasserver.dao.mapper;

import com.mszq.uas.uasserver.dao.model.UserRole;
import com.mszq.uas.uasserver.dao.model.UserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    long countByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int deleteByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int insert(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int insertSelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    List<UserRole> selectByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    UserRole selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int updateByPrimaryKeySelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uas_user_role
     *
     * @mbg.generated Thu Nov 22 17:14:59 CST 2018
     */
    int updateByPrimaryKey(UserRole record);
}