package com.mszq.platform.app.employee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.EmployeeRole;
import com.mszq.platform.util.ComboOption;


@MapperScan
public interface IEmployeeDAO {
	public String getRolesByEmployeeID(@Param("employeeID") String employeeID);
	public Employee getEmployeeByAccount(@Param("account") String account);
	public List<Employee> queryAll(@Param("condition") Map<String, Object> condition);
	public List<Employee> queryByPrimaryKey(@Param("id") Integer id);
    public int insert(Employee record);
    public int insertEmployeeRoleBatch(List<EmployeeRole> erList);
    public int deleteBatch(String[] ids);
    public int deleteRoleMappingBatch(String[] ids);
    public int updateByPrimaryKey(Employee record);
    public int updateByParams(Map<String, Object> params);
    public List<Employee> queryByOrgnizationIds(String[] orgnizationIds);
    public List<Employee> queryEmployeeHasAdminRole(Map<String,Object> params);
	public Employee selectEmployeeByKey(@Param("id") long id);
	
	public List<ComboOption> queryForCombo(@Param("text") String text);
}
