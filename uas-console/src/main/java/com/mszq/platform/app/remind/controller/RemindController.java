package com.mszq.platform.app.remind.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.remind.service.IRemindService;
import com.mszq.platform.app.remind.service.RemindServiceImpl;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Remind;

/**
 * 首页查询提醒、设置提醒规则
 * @author michel
 *
 */
@Controller
@RequestMapping("/remind")
public class RemindController {
	@Autowired
	IRemindService remindService;
	
	@RequestMapping(value = "/titleList")
	@ResponseBody
	public EUDataGridResult getRemindTitleList(String title,Integer page,Integer rows){
		Map<String, Object> params = new HashMap<String, Object>();
	    if(null!=title&&!"".equalsIgnoreCase(title)){
	    	params.put("title", "%" + title + "%");
	    }
	    if(page==null){
	    	page=1;
	    }
	    if(rows==null){
	    	rows=5;
	    }
		params.put("type", "01");
	   return remindService.getTitleList(params, page, rows);
	}
	
	
	@RequestMapping(value = "/titleWorkflowList")
	@ResponseBody
	public EUDataGridResult getRemindWorkflowTitleList(String title,Integer page,Integer rows){
		Map<String, Object> params = new HashMap<String, Object>();
	    if(null!=title&&!"".equalsIgnoreCase(title)){
	    	params.put("title", "%" + title + "%");
	    }
		params.put("type", "09");
	    if(page==null){
	    	page=1;
	    }
	    if(rows==null){
	    	rows=5;
	    }
	   return remindService.getTitleList(params, page, rows);
	}
	
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EUDataGridResult getRemindList(String title,Integer page,Integer rows){
		Map<String, Object> params = new HashMap<String, Object>();
	    if(null!=title&&!"".equalsIgnoreCase(title)){
	    	params.put("title", "%" + title + "%");
	    }
	   return remindService.getRemindList(params, page, rows);
	}
	
	
	
	/**
	 * 查询可以提醒规则
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/rule/list")
	@ResponseBody
	public EUDataGridResult queryRemindRule(Integer page,Integer rows){
		Map<String, Object> params = new HashMap<String, Object>();
	    
	   return remindService.queryRemindRule(params, page, rows);
	}
}
