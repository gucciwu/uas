package com.mszq.platform.app.agenda.controller;

import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.activiti.engine.impl.util.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.agenda.dto.AgendaDTO;
import com.mszq.platform.app.agenda.service.IAgendaService;
import com.mszq.platform.app.employee.service.IEmployeeService;

import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Agenda;


@Controller
@RequestMapping("/agenda")
public class AgendaController {
	@Autowired
	IAgendaService agendaService;
	@Autowired
	IEmployeeService employeeService;
	//添加日程
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult create(AgendaDTO AgendaDTO) throws ParseException {
		CustomResult result = null;
		int m=AgendaDTO.getEnd().compareTo(AgendaDTO.getStart());
		if(m<0){
			return CustomResult.error("开始时间不能小于结束时间！");
		}
		if(m==0){
			return CustomResult.error("开始时间不能等于结束时间！");
		}
		int count=agendaService.create(AgendaDTO);
		if (count == 1) {
			result = CustomResult.ok("操作成功！");
		}
		return result;				
	}
	//根据id获取日程
	@RequestMapping(value="/keylist")
	@ResponseBody
	public AgendaDTO KeyListener(long id) throws ParseException{
		AgendaDTO keyAgenda=agendaService.getAgendaDTOByKey(id);
		return keyAgenda;
	}
	
	//获取所有日程
	@RequestMapping(value="/list")
	@ResponseBody
	public  JSON list() throws ParseException{
		List<Agenda> list;
		Map<String, Object> params=new HashMap<String,Object>();
		 //判断是否为管理员登录
		 boolean isAdmin = (boolean)UserSecurityManager.getAttribute("isAdmin");
		 Long userID = (Long)UserSecurityManager.getAttribute("ID");	
		 if(isAdmin){
			 list=agendaService.getAllList(params);	
		 }else{
			//查看登录者的权限				
			 params.put("userID", userID);
		     list=agendaService.getList(params);	
		 }
		 
	     JSONArray ja =new JSONArray();	     
	     for (Agenda schedule : list) {
	    	 JSONObject jo =new JSONObject();
	    	 jo.put("id", schedule.getId());
	    	 jo.put("title", schedule.getTitle());
	    	 jo.put("start", schedule.getStart());
	    	 jo.put("end", schedule.getEnd()); 
	    	 jo.put("type",schedule.getType());
	    	 jo.put("status",schedule.getStatus());//颜色
	    	 jo.put("creatorName",employeeService.getEmployeeByID(schedule.getCreatorId()).getName());
	    	 ////如果登陆者和创建者相等，则ajax请求数据保存创建者id;如果登陆者和创建者不一样，则ajax请求数据creatorid=0
	    	 if(schedule.getCreatorId()==userID.intValue()){
	    		 jo.put("creatorId", schedule.getCreatorId()); 
	    	 }else{
	    		 jo.put("creatorId", -768);//特殊处理
	    	 }	    	 
	    	 jo.put("isRemind",schedule.getIsRemind());
		    	
	    	 if(schedule.getIsRemind().equals(Byte.valueOf((byte) 1))){
	    		 Map<String,Integer> map=agendaService.getAdvanceByRemindTime(schedule.getRemindTime(), schedule.getStart());
	    		 for(String key:map.keySet()){
                      jo.put(key, map.get(key));
	    		 }
	    	 }
	    	 ja.put(jo);
		}
	     return JSONSerializer.toJSON(ja.toString());
	}
	//获取所有日程时间
	@RequestMapping(value="/datelist")
	@ResponseBody
	public Map getDateList() throws ParseException{
		Map<Long,List<String>> dateList=new LinkedHashMap();//返回值
		List<Agenda> list;
		Map<String, Object> params=new HashMap<String,Object>();
		 //判断是否为管理员登录
		 boolean isAdmin = (boolean)UserSecurityManager.getAttribute("isAdmin");
		 if(isAdmin){
			 list=agendaService.getAllList(params);
		 }else{
			//查看登录者的权限
			 Long userID = (Long)UserSecurityManager.getAttribute("ID");		
			 params.put("userID", userID);
			 list=agendaService.getList(params);	
		 }	
		//判空
		if(list==null || list.size()==0){
			return null;
		}
		for(Agenda Agenda : list){
			List<String> timeList=new ArrayList<>();
			String start=Agenda.getStart().substring(0,10);
			String end=Agenda.getEnd().substring(0, 10);
			if(start.equals(end)){
				timeList.add(start);
				dateList.put(Agenda.getId(),timeList);
			}else{
				timeList.add(start);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				long time=sdf.parse(end).getTime()-sdf.parse(start).getTime();
		        int dd=(int)time/(1000*60*60*24);
		        Calendar cal = Calendar.getInstance();
		        for(int i=1;i<dd;i++){
	        	   cal.setTime(sdf.parse(start));
	               cal.add(Calendar.DATE, i);
	               String miString=sdf.format(cal.getTime());
	           	   timeList.add(miString);
		        }
		    	timeList.add(end);
			}
			dateList.put(Agenda.getId(),timeList);
		}
		 return dateList;
	}
	//获取所有日程title列表
	@RequestMapping(value="/dateCalendarList")
	@ResponseBody
	public Map getDateCalendarList() throws ParseException {
	    Map<String,List<Agenda>> dateCalendarList=new HashMap<>();//返回值
		List<Agenda> list;
		Map<String, Object> params=new HashMap<String,Object>();
		 //判断是否为管理员登录
		 boolean isAdmin = (boolean)UserSecurityManager.getAttribute("isAdmin");
		 if(isAdmin){
			 list=agendaService.getAllList(params);
		 }else{
			//查看登录者的权限
			 Long userID = (Long)UserSecurityManager.getAttribute("ID");		
			 params.put("userID", userID);
			 list=agendaService.getList(params);	
		 }		
		//判空
		if(list==null || list.size()==0){
			return null;
		}
		for(Agenda Agenda : list){
			List<Agenda> Agendas=new ArrayList<>();
			String start=Agenda.getStart().substring(0,10);
			String end=Agenda.getEnd().substring(0, 10);
			if(dateCalendarList.containsKey(start)){
				for(String dateStr : dateCalendarList.keySet()){
					if(dateStr.equals(start)){
						List<Agenda> listItem=dateCalendarList.get(start);
						listItem.add(Agenda);
						//dateCalendarList.remove(start);
						dateCalendarList.put(start, listItem);
						break;
					}
				}
			}else{
				List<Agenda> newList=new ArrayList<>();
				newList.add(Agenda);
				dateCalendarList.put(start,newList);
			}
			
			if(!start.equals(end)){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				long time=sdf.parse(end).getTime()-sdf.parse(start).getTime();
		        int dd=(int)time/(1000*60*60*24);
		        Calendar cal = Calendar.getInstance();
		        for(int i=1;i<=dd;i++){
	        	   cal.setTime(sdf.parse(start));
	               cal.add(Calendar.DATE, i);
	               String miString=sdf.format(cal.getTime());
		       		if(dateCalendarList.containsKey(miString)){
						for(String dateStr : dateCalendarList.keySet()){
							if(dateStr.equals(miString)){
								List<Agenda> listItem=dateCalendarList.get(miString);
								listItem.add(Agenda);
								//dateCalendarList.remove(start);
								dateCalendarList.put(miString, listItem);
								break;
							}
						}
					}else{
						List<Agenda> newList=new ArrayList<>();
						newList.add(Agenda);
						dateCalendarList.put(miString,newList);
					}
		        }
			}
		} 
		return dateCalendarList;
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public CustomResult updateCalendare(AgendaDTO AgendaDTO) throws Exception{
		int m=AgendaDTO.getEnd().compareTo(AgendaDTO.getStart());
		if(m<0){
			return CustomResult.error("开始时间不能小于结束时间！");
		}
		if(m==0){
			return CustomResult.error("开始时间不能等于结束时间！");
		}
	  int emp=agendaService.update(AgendaDTO);
		if (emp > 0) {
			return CustomResult.ok("修改成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else if(emp == -1){
			return CustomResult.error("只能修改自己创建的日历！");
		}else {
			return CustomResult.error("修改异常！");
		}
	}
	@RequestMapping(value = "/delete")
	@ResponseBody
	public CustomResult deleteCalendar(long id){
		int emp=agendaService.delete(id);
		if (emp > 0) {
			return CustomResult.ok("删除成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		}else if(emp == -1){
			return CustomResult.error("只能删除自己创建的日历！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
}
