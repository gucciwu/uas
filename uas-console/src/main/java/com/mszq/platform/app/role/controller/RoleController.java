package com.mszq.platform.app.role.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.role.service.IRoleService;
import com.mszq.platform.base.BaseController;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.Role;


@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired(required=true)
	private IRoleService roleService;

	@RequestMapping(value = "/list")
	@ResponseBody//将内容或对象作为 HTTP 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	private EUDataGridResult list(@RequestParam(value="page") Integer page, @RequestParam(value="rows")Integer rows) throws Exception {
		EUDataGridResult res = roleService.queryAll(page, rows);
		return res;
	}
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> tree() {
        return roleService.getRoleTree();
    }
	
	/**
     * 授权页面根据角色查询资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public CustomResult findResourceByRoleId(Long id) {
    	CustomResult result = null;
    	List<Long> resources = roleService.getPermissionIdListByRoleId(id);
    	if (resources != null) {
			result = CustomResult.ok("成功",resources);
		} else {
			result = CustomResult.error("失败");
		}
		return result;
    }
    
    /**
     * 授权
     *
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public CustomResult grant(Long id, String resourceIds) {
        CustomResult result = null;
		int count = roleService.updateRolePermission(id, resourceIds);
		if (count >= 0) {
			result = CustomResult.ok("授权成功！");
		} else {
			result = CustomResult.error("授权失败！");
		}
		return result;
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult addRole(Role record) {
		CustomResult result = null;
		int count = roleService.insert(record);
		if (count == 1) {
			result = CustomResult.ok("添加成功！");
		} else {
			result = CustomResult.error("添加失败！");
		}
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult updateRole(Role record) {
		int emp = roleService.updateByPrimaryKey(record);
		if (emp > 0) {
			return CustomResult.ok("修改成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("修改异常！");
		}
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult deleteRoles(String[] ids) {
		int emp = roleService.deleteBatch(ids);
		if (emp > 0) {
			return CustomResult.ok("删除成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	
}
