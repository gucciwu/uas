package com.mszq.platform.app.permission.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.permission.dto.PermissionWebDTO;
import com.mszq.platform.app.permission.service.IPermissionService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.MenuTree;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.Permission;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPermissionService permissionService;

	@RequestMapping(value = "/list")
	@ResponseBody//将内容或对象作为 HTTP 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	public EUDataGridResult list() throws Exception {
		EUDataGridResult res = permissionService.queryAll();
		return res;
	}
	
	@PostMapping(value = "/tree")
    @ResponseBody
    public List<Tree> tree() {
        return permissionService.getPermissionTree();
    }
	
//	@RequestMapping(value = "/menuTree")
//    @ResponseBody
//    public List<MenuTree> menuTree() {
//        return permissionService.getMenuTree();
//    }
	
	@RequestMapping(value = "/query")
    @ResponseBody
    public PermissionWebDTO queryPermissions() {
        return permissionService.queryPermissions();
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult addPermission(Permission record) {
		CustomResult result = null;
		int count = permissionService.insert(record);
		if (count == 1) {
			result = CustomResult.ok("添加成功！");
		} else {
			result = CustomResult.error("添加失败！");
		}
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult updatePermission(Permission record) {
		int emp = permissionService.updateByPrimaryKey(record);
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
	public CustomResult deletePermissions(String[] ids) {
		int children = permissionService.getChildrenBykey(ids);
		if(children > 0){
			return CustomResult.error("删除的数据中包含子节点，请先删除子节点！");
		}
		int emp = permissionService.deleteBatch(ids);
		if (emp > 0) {
			return CustomResult.ok("删除成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}

}
