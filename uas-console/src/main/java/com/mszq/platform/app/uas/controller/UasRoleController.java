package com.mszq.platform.app.uas.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.uas.service.IUasRoleService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasRole;

@Controller
@RequestMapping("/uasRole")
public class UasRoleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private IUasRoleService uasRoleService;

	public EUDataGridResult list(@RequestParam Map<String, Object> condition, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) throws Exception {
		EUDataGridResult res = null;
		try {
			res = uasRoleService.selectList(condition, page, rows);
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody // 将内容或对象作为 HTTP
	public EUDataGridResult query() throws Exception {
		EUDataGridResult res = null;
		try {
			res = uasRoleService.queryAll();
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult updateRoleType(UasRole record) {
		int ret = -1;
		CustomResult result = null;
		try {
			ret = uasRoleService.update(record);
			
			if (ret > 0) {
				result = CustomResult.ok("修改成功！");
			} else if (ret == 0) {
				result = CustomResult.error("数据不存在！");
			} else {
				result = CustomResult.error("修改异常！");
			}
		} catch (Exception e) {
			logger.error("更新出错", e);
			result = CustomResult.error(e.getMessage());
		}
		
		return result;
	}

	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult deleteBatch(String[] ids) {
		int ret = 0;
		String error = "";
		CustomResult result = null;
		try {
			ret = uasRoleService.deleteBatch(ids);
			
			if (ret > 0) {
				result = CustomResult.ok("删除成功！");
			} else if (ret == 0) {
				result = CustomResult.error("删除失败！:"+error);
			} else {
				result = CustomResult.error("删除异常！");
			}
		} catch (Exception e) {
			logger.error("删除出错", e);
			result = CustomResult.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult insert(UasRole record) {
		int ret = -1;
		String error = "";
		CustomResult result = null;
		try {
			ret = uasRoleService.insert(record);
			
			if (ret > 0) {
				result =  CustomResult.ok("保存成功！");
			} else if (ret == 0) {
				result =  CustomResult.error("保存失败！:"+error);
			} else {
				result =  CustomResult.error("保存异常！");
			}
		} catch (Exception e) {
			logger.error("保存出错", e);
			result = CustomResult.error(e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/tree")
    @ResponseBody
	private  List<Tree> getRoleTree(@RequestParam Map<String, Object> condition) {
		List<Tree> res = null;
		try {
			res = uasRoleService.getRoleTree(condition);
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}
	
}
