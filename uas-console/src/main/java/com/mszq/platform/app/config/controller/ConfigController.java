package com.mszq.platform.app.config.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.config.service.IConfigService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.Employee;

@Controller
@RequestMapping(value="/config")
public class ConfigController {
	private final Logger logger=LoggerFactory.getLogger(ConfigController.class);
	@Resource
	IConfigService configService;
	
	/**
	 * 从左侧菜单访问本方法
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public EUDataGridResult list(String name,String code,Integer page, Integer rows, HttpServletResponse response){
		Map<String,String> params=new HashMap<String,String>();
		if(null!=name&&!"".equalsIgnoreCase(name)) params.put("name",name);
		if(null!=code&&!"".equalsIgnoreCase(code)) params.put("code",code);

		return configService.getList(params,page, rows);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult create(Config config) {
		CustomResult result = null;
		Config temp = configService.getConfigByCode(config.getCode());
		if (temp != null) {
			result = CustomResult.error("code已存在!,请输入一个不重复的code");
		} else {
			int count =  configService.create(config);
			if (count == 1) {
				result = CustomResult.ok("操作成功！");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult update(Config config) {
		int result = configService.update(config);
		if (result > 0) {
			return CustomResult.ok("修改成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("修改异常！");
		}
	}
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult delete(String[] ids) {
		int result = configService.deleteBatch(ids);
		if (result > 0) {
			return CustomResult.ok("删除成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
}

