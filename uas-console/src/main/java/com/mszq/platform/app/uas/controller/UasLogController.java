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

import com.mszq.platform.app.uas.service.IUasLogService;
import com.mszq.platform.base.BaseController;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasLog;

@Controller
@RequestMapping("/uasLog")
public class UasLogController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private IUasLogService uasLogService;
	
	@RequestMapping(value = "/list")
	@ResponseBody // 将内容或对象作为 HTTP
	// 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	private EUDataGridResult list(@RequestParam Map<String, Object> condition, @RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows) throws Exception {
		EUDataGridResult res = null;
		try {
			res = uasLogService.selectList(condition, page, rows);
		} catch(Exception e) {
			logger.error("查询出错", e);
		}
		return res;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult update(UasLog record) {
		int ret = -1;
		try {
			ret = uasLogService.update(record);
		} catch(Exception e) {
			logger.error("更新出错", e);
		}
		
		if (ret > 0) {
			return CustomResult.ok("修改成功！");
		} else if (ret == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("修改异常！");
		}
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult deleteRoles(String[] ids) {
		int ret =  -1;
		try {
			ret = uasLogService.deleteBatch(ids);
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
	public CustomResult insert(UasLog record) {
		int ret =  -1;
		try {
		    ret =  uasLogService.insert(record);
		} catch (Exception e) {
			logger.error("保存出错", e);
		}

		if (ret > 0) {
			return CustomResult.ok("保存成功！");
		} else if (ret == 0) {
			return CustomResult.error("保存不成功！");
		} else {
			return CustomResult.error("保存异常！");
		}
	}
	
}
