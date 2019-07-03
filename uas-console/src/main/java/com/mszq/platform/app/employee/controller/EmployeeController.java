package com.mszq.platform.app.employee.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.app.orgnization.service.IOrgnizationService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.util.ComboOption;
import com.mszq.platform.util.GlobalConfig;
import com.mszq.platform.util.MD5Function;

import net.sf.ehcache.search.expression.And;

import org.springframework.util.StringUtils;


@Controller
@RequestMapping("/employee") // url:/模块/资源/{id}/细分 /seckill/list
public class EmployeeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IOrgnizationService orgnizationService;

	@RequestMapping(value = "/list")
	@ResponseBody//将内容或对象作为 HTTP 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	public EUDataGridResult list(Employee employee, Integer page, Integer rows) throws Exception {
		Map<String, Object> condition = new HashMap<String, Object>();
		if (StringUtils.hasText(employee.getName())) {
            condition.put("name", employee.getName());
        }
        if (StringUtils.hasText(employee.getAccount())) {
            condition.put("account", employee.getAccount());
        }
        if (employee.getOrgnizationId() != null) {
        	List<Long> ids = orgnizationService.getAllChildren(employee.getOrgnizationId());
//        	String org = StringUtils.collectionToDelimitedString(ids, ",");
            condition.put("orgnizationId", ids);
        }
        if (StringUtils.hasText(employee.getRoleIds())) {
            condition.put("roleIds", employee.getRoleIds());
        }
		EUDataGridResult result = employeeService.queryAll(page, rows, condition);
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult addUser(Employee employee) {
		CustomResult result = null;
		Employee emp = employeeService.getEmployeeByAccount(employee.getAccount());
		if (emp != null) {
			return CustomResult.error("注册名已存在!");
		} else {
			if(!StringUtils.hasText(employee.getPassword())){
				return CustomResult.error("密码不能为空!");
			}
			int count = employeeService.insert(employee);
			if (count == 1) {
				result = CustomResult.ok("添加成功");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult updateUser(Employee employee) {
		Employee temp = employeeService.getEmployeeByAccount(employee.getAccount());
		if (temp != null && !temp.getId().equals(employee.getId())) {
			return CustomResult.error("注册名已存在!");
		} else {
			int emp = employeeService.updateByPrimaryKey(employee);
			if (emp > 0) {
				return CustomResult.ok("修改成功！");
			} else if (emp == 0) {
				return CustomResult.error("数据不存在！");
			} else {
				return CustomResult.error("修改异常！");
			}
		}
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult deleteUsers(String[] ids) {
		int emp = employeeService.deleteBatch(ids);
		if (emp > 0) {
			return CustomResult.ok("删除成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	
	@RequestMapping(value = "/update/password", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult updatePassword(String oldPassword,String newPassword) {
		Map<String,Object> params=new HashMap<String,Object>();
		int emp=0;
		oldPassword = MD5Function.getMD5Digest(oldPassword);
		Employee temp = employeeService.getEmployeeByAccount((String)UserSecurityManager.getAttribute("account"));
		if(!temp.getPassword().equalsIgnoreCase(oldPassword))  return CustomResult.build(0,"旧密码不正确");
		newPassword = MD5Function.getMD5Digest(newPassword);
		
		params.put("ids",new Long[]{(Long)UserSecurityManager.getAttribute("ID")});
		params.put("password",newPassword);
		params.put("updateTime",new Date());
		emp=employeeService.updateByParams(params);
		
		if (emp > 0) {
			return CustomResult.build(1,"操作成功");
		} else if (emp == 0) {
			return CustomResult.build(0,"操作失败，数据不存在");
		} else {
			return CustomResult.build(0,"操作失败，数据不存在");
		}
	}
	@RequestMapping(value = "/update/resetPasswordBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult resetPasswordBatch(String[] ids) {
		Map<String,Object> params=new HashMap<String,Object>();
		List<Long> idsList=new ArrayList();
		for(String id:ids){
			idsList.add(Long.parseLong(id));
		}
		int emp=0;
		params.put("ids",idsList);
		params.put("password",MD5Function.getMD5Digest(MD5Function.getMD5Digest((String)GlobalConfig.getConfgiValue("initPassword"))));
		params.put("updateTime",new Date());
		emp=employeeService.updateByParams(params);
		
		if (emp > 0) {
			return CustomResult.build(1,"操作成功");
		} else if (emp == 0) {
			return CustomResult.build(0,"操作失败，数据不存在");
		} else {
			return CustomResult.build(0,"操作失败，数据不存在");
		}
	}
	@RequestMapping(value = "/combo")
	@ResponseBody
	public List<ComboOption>  queryForCombo(Integer id, String q) {
		if(q==null){
			q=new String();
		}
		return employeeService.queryForCombo(q.trim());
	}
}
