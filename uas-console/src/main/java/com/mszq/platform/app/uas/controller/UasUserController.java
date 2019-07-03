package com.mszq.platform.app.uas.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.uas.service.IUasUserService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasPassword;
import com.mszq.platform.entity.UasUser;
import com.mszq.platform.util.MD5Function;

@Controller
@RequestMapping("/uasUser")
public class UasUserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private IUasUserService uasUserService;
	
	@RequestMapping(value = "/list")
	@ResponseBody // 将内容或对象作为 HTTP
	// 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	public EUDataGridResult list(@RequestParam Map<String, Object> condition, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) throws Exception {
		EUDataGridResult res = null;
		try {
			res = uasUserService.selectList(condition, page, rows);
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult update(UasUser record) {
		int ret = -1;
		String msg = "";
		
		try {
			ret = uasUserService.update(record);
		} catch(Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException) {
				if (e.getMessage().indexOf("JOB_NUMBER") > 0) {
					msg = "员工编号信息已存在";
				} else if (e.getMessage().indexOf("ID_NUMBER") > 0) {
					msg = "员工证件号信息已存在";
				} else {
					msg = "该员工信息已存在";
				}
			} else {
				msg = "保存出错";
			}
			logger.error(msg, e);
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
	public CustomResult deleteRoles(String[] ids) {
		int ret =  -1;
		try {
			ret = uasUserService.deleteBatch(ids);
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
	public CustomResult insert(UasUser record) {
		int ret =  -1;
		String msg = "";
		try {
		    ret =  uasUserService.insert(record);
		} catch(Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException) {
				if (e.getMessage().indexOf("for key 'JOB_NUMBER'") > 0) {
					msg = "员工编号信息已存在";
				} else if (e.getMessage().indexOf("for key 'ID_NUMBER'") > 0) {
					msg = "员工证件号信息已存在";
				} else {
					msg = "该员工信息已存在";
				}
			} else {
				msg = "保存出错";
			}
			logger.error(msg, e);
		}
		
		if (ret > 0) {
			return CustomResult.ok("保存成功！");
		} else if (ret == 0) {
			return CustomResult.error(msg);
		} else {
			return CustomResult.error(msg);
		}
	}
	
	
	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult savePassword(UasPassword record) {
		int ret =  -1;
		String msg = "";
		try {
			//record.setPassword(record.getPassword());
		    ret =  uasUserService.savePassword(record);
		} catch(Exception e) {
			logger.error(msg, e);
		}
		
		if (ret > 0) {
			return CustomResult.ok("保存成功！");
		} else if (ret == 0) {
			return CustomResult.error("保存不成功！");
		} else {
			return CustomResult.error("保存异常！");
		}
	}
	
	
	@RequestMapping(value = "/getPassword")
	@ResponseBody
	public UasPassword getPassword(Long userId) {
		UasPassword p =  uasUserService.getPassword(userId);
		if (p==null) {
			p = new UasPassword();
			p.setUserId(userId);
		}
		return p;
	}
}
