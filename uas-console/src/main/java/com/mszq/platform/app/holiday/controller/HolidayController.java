package com.mszq.platform.app.holiday.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.app.holiday.dto.HolidayDto;
import com.mszq.platform.app.holiday.service.IHolidayService;
import com.mszq.platform.app.orgnization.service.IOrgnizationService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.util.ComboOption;
import com.mszq.platform.util.GlobalConfig;
import com.mszq.platform.util.MD5Function;

import net.sf.ehcache.search.expression.And;

import org.springframework.util.StringUtils;


@Controller
@RequestMapping("/holiday") 
public class HolidayController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IHolidayService service;
	@RequestMapping(value = "/getNextTradeDate")
	@ResponseBody
	public Date  getNextTradeDate(HolidayDto dto) {
		return service.getTradingDate(dto.getStartDate(),dto.getDays());
	}
	
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   
        dateFormat.setLenient(true);   //Calendar宽松解析字符串
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }
}
