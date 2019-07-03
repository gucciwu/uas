package com.mszq.platform.app.employee.service;


import com.mszq.platform.entity.Employee;
import com.mszq.platform.util.ComboOption;

import java.util.List;
import java.util.Map;

import com.mszq.platform.base.EUDataGridResult;

public interface IEmployeeService {
	public Employee getEmployeeByAccount(String account);
	public EUDataGridResult queryAll(int page, int rows, Map<String, Object> conditions);
	public List<Employee> queryAll(Map<String, Object> conditions);
	public String getRolesByEmployeeID(String employeeID);
	public int insert(Employee record);
	public int deleteBatch(String[] ids);
    public int updateByPrimaryKey(Employee record);
    public int updateByParams(Map<String, Object> params);
    public List<Employee> queryByOrgnizationIds(String[] orgnizationIds);
    public List<Employee> queryEmployeeHasAdminRole(String[] employeeIds,String adminRole);
    public Employee getEmployeeByID(long id);
    
    public List<ComboOption> queryForCombo(String text);
    
}
