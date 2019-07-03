package com.mszq.platform.app.log.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.log.service.ILogService;
import com.mszq.platform.base.EUDataGridResult;

@Controller
@RequestMapping(value="/log")
public class LogController {
	@Resource
	private ILogService logService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public EUDataGridResult list(String account,String name,String startTime,String endTime,Integer page, Integer rows ){
		Map<String,String> params=new HashMap<String,String>();
		if(null!=account&&!"".equalsIgnoreCase(account)) params.put("account",account);
		if(null!=name&&!"".equalsIgnoreCase(name)) params.put("name",name);
		if(null!=startTime&&!"".equalsIgnoreCase(startTime)) params.put("startTime",startTime);
		if(null!=endTime&&!"".equalsIgnoreCase(endTime)) params.put("endTime",endTime);
		return logService.getList(params,page, rows);
	}

}
