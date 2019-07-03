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

import com.mszq.platform.app.uas.service.IUasOrgService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasOrg;

@Controller
@RequestMapping("/uasOrg")
public class UasOrgController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private IUasOrgService uasOrgService;
	/*
	@RequestMapping(value = "/list")
	@ResponseBody // 将内容或对象作为 HTTP
					// 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	private EUDataGridResult list(@RequestParam Map<String, Object> condition, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) throws Exception {
		EUDataGridResult res = null;
		try {
			res = uasOrgService.selectList(condition, page, rows);
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}
	*/
	@RequestMapping(value = "/list")
	@ResponseBody // 将内容或对象作为 HTTP
	private EUDataGridResult list(@RequestParam Map<String, Object> condition) {
		EUDataGridResult res = null;
		try {
			String orgType = (String)condition.get("orgType");
			if (orgType != null && !("").equals(orgType)) {
				res = uasOrgService.queryAll(condition);
			} else {
				res = new EUDataGridResult();
			}
			
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}
	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult update(UasOrg record) {
		int ret = -1;
		String msg = "";
		try {
			ret = uasOrgService.update(record);
		} catch(Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException) {
				msg = "该部门信息已存在";
			} else {
				msg = "保存出错";
			}
		}
		
		if (ret > 0) {
			return CustomResult.ok("保存成功！");
		} else if (ret == 0) {
			return CustomResult.error(msg);
		} else {
			return CustomResult.error(msg);
		}
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult deleteBatch(String[] ids) {
		int ret =  0;
		try {
			ret = uasOrgService.deleteBatch(ids);
		} catch(Exception e) {
			logger.error("删除出错", e);
		}
		
		if (ret > 0) {
			return CustomResult.ok("删除成功！");
		} else if (ret == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult insert(UasOrg record) {
		int ret =  -1;
		String msg = "";
		try {
		    ret =  uasOrgService.insert(record);
		} catch(Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException) {
				msg = "该部门信息已存在";
			} else {
				msg = "保存出错";
			}
		}
		
		if (ret > 0) {
			return CustomResult.ok("保存成功！");
		} else if (ret == 0) {
			return CustomResult.error(msg);
		} else {
			return CustomResult.error(msg);
		}
	}
	
	
	@RequestMapping(value = "/tree")
    @ResponseBody
	private  List<Tree> getOrgnizationTree(@RequestParam Map<String, Object> condition) {
		List<Tree> res = null;
		try {
			res = uasOrgService.getOrgnizationTree(condition);
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}
	
}
