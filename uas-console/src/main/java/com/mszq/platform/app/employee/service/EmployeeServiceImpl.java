package com.mszq.platform.app.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.employee.dao.IEmployeeDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.EmployeeRole;
import com.mszq.platform.util.ComboOption;
import com.mszq.platform.util.MD5Function;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	@Autowired
	private IEmployeeDAO employeeDAO;
	
//	private Map DAOParams=new HashMap();

	@Override
	public Employee getEmployeeByAccount(String account) {
		return employeeDAO.getEmployeeByAccount(account);
	}
	@Override
	public String getRolesByEmployeeID(String employeeID) {
		return employeeDAO.getRolesByEmployeeID(employeeID);
	}

	@Override
	public EUDataGridResult queryAll(int page, int rows, Map<String, Object> conditions) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<Employee> list = employeeDAO.queryAll(conditions);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<Employee> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public List<Employee> queryAll(Map<String, Object> conditions) {
		return employeeDAO.queryAll(conditions);
	}
 
	
	
	
	
	
	@Override
	@Transactional
	public int insert(Employee record) {
		String password = MD5Function.getMD5Digest(record.getPassword());
		record.setPassword(password);
		record.setUpdateTime(new Date());
		record.setCreateTime(new Date());
		//插入主表后能从对象中获得新插入的自增主键id
		int count = employeeDAO.insert(record);
		//这一步应该可以忽略，因为是新插入的数据，mapping表中应该没有对应的记录
		employeeDAO.deleteRoleMappingBatch(new String[]{record.getId().toString()});
		
		List<EmployeeRole> erList = new ArrayList<EmployeeRole>();
		if(record.getRoleIds()!= null){
			String[] roleIdArray = record.getRoleIds().split(",");
	        for (String roleId : roleIdArray) {
	        	EmployeeRole er = new EmployeeRole();
	        	er.setEmployeeId(record.getId());
	        	er.setRoleId(Long.parseLong(roleId));
	        	erList.add(er);
	        }
		}
        if (erList.size() > 0) {
        	employeeDAO.insertEmployeeRoleBatch(erList);
		}
		return count;
	}
	
	@Override
	@Transactional
	public int deleteBatch(String[] ids) {
		employeeDAO.deleteRoleMappingBatch(ids);
		return employeeDAO.deleteBatch(ids);
	}
	
	@Override
	@Transactional
	public int updateByPrimaryKey(Employee record) {//此接口不提供修改密码的功能
		if(StringUtils.hasText(record.getPassword())){
			String password = MD5Function.getMD5Digest(record.getPassword());
			record.setPassword(password);
		}
		//给用户设置角色时，先删除旧的mapping，再插入新的
		employeeDAO.deleteRoleMappingBatch(new String[]{record.getId().toString()});
		
		List<EmployeeRole> erList = new ArrayList<EmployeeRole>();
		if(StringUtils.hasText(record.getRoleIds())){
			String[] roleIdArray = record.getRoleIds().split(",");
	        for (String roleId : roleIdArray) {
	        	EmployeeRole er = new EmployeeRole();
	        	er.setEmployeeId(record.getId());
	        	er.setRoleId(Long.parseLong(roleId));
	        	erList.add(er);
	        }
		}
        if (erList.size() > 0) {
        	employeeDAO.insertEmployeeRoleBatch(erList);
		}
		
		record.setUpdateTime(new Date());
		return employeeDAO.updateByPrimaryKey(record);
	}
	@Override
	public List<Employee> queryByOrgnizationIds(String[] orgnizationIds) {
		return employeeDAO.queryByOrgnizationIds(orgnizationIds);
	}
	@Override
	public List<Employee> queryEmployeeHasAdminRole(String[] employeeIds,String adminRole) {
		//构造DAO的查询条件，如果是过于一个以上的参数且包含数组，则需要将参数设置为Map类型
//		DAOParams.clear();
		Map DAOParams=new HashMap();
		DAOParams.put("adminRole", adminRole);
		DAOParams.put("employeeIds",employeeIds );
		return employeeDAO.queryEmployeeHasAdminRole(DAOParams);
	}
	@Override
	public int updateByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return employeeDAO.updateByParams(params);
	}
	@Override
	public Employee getEmployeeByID(long id) {
		return employeeDAO.selectEmployeeByKey(id);
	}
	@Override
	public List<ComboOption> queryForCombo(String text) {
		return employeeDAO.queryForCombo(text);
	}

}
