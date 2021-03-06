package com.mszq.platform.app.uas.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.uas.service.IUasAppAccountService;
import com.mszq.platform.base.BaseController;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasAppAccount;

@Controller
@RequestMapping("/uasAppAccount")
public class UasAppAccountController  extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private IUasAppAccountService uasAppAccountService;
	@RequestMapping(value = "/list")
	@ResponseBody // 将内容或对象作为 HTTP
					// 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	private EUDataGridResult list(@RequestParam Map<String, Object> condition, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) throws Exception {
		EUDataGridResult res = null;
		try {
			res = uasAppAccountService.selectList(condition, page, rows);
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult update(UasAppAccount record) {
		int ret = -1;
		String msg = "";
		try {
			ret = uasAppAccountService.update(record);
		} catch(Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException) {
				msg = "该系统的人员账户已存在";
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
			ret = uasAppAccountService.deleteBatch(ids);
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
	public CustomResult insert(UasAppAccount record) {
		int ret =  -1;
		String msg = "";
		try {
		    ret =  uasAppAccountService.insert(record);
		} catch(Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException) {
				msg = "该系统的人员账户已存在";
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
}
