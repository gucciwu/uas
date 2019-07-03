package com.mszq.platform.app.agenda.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sound.sampled.Port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.app.login.controller.LoginController;
import com.mszq.platform.app.agenda.dao.IAgendaDAO;
import com.mszq.platform.app.agenda.dto.AgendaDTO;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Agenda;



@Service
public class AgendaServiceImpl implements IAgendaService{
	private final Logger logger=LoggerFactory.getLogger(AgendaServiceImpl.class);
	@Resource
    IAgendaDAO AgendaDAO;
	@Autowired
	IEmployeeService employeeService;
   // <option value="1" selected="selected">分钟</option>
   // <option value="2">小时</option>
   // <option value="3">天</option>
   // <option value="4">周</option>
	private String getRemindTime(AgendaDTO AgendaDTO) throws ParseException {
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
         Date date=sdf.parse(AgendaDTO.getStart());
         Calendar calendar=Calendar.getInstance();
         calendar.setTime(date);
         Date resultDate = null;
         Integer advanceTime=AgendaDTO.getAdvanceTime();
		switch (AgendaDTO.getTimeType()) {
			case 1:
				  calendar.add(Calendar.MINUTE,0-advanceTime);
				  resultDate=calendar.getTime();
				  break;
	      case 2:
	      	  calendar.add(Calendar.HOUR_OF_DAY, 0-advanceTime);
	      	  resultDate=calendar.getTime();
	      	  break;
	      case 3:
	           calendar.add(Calendar.DATE, 0-advanceTime);
	           resultDate=calendar.getTime();
	      	  break;
	      case 4:
	      	 calendar.add(Calendar.WEEK_OF_YEAR, 0-advanceTime);
	      	 resultDate=calendar.getTime();
      	  break;
			default:
				break;
			}
		return sdf.format(resultDate);
	}
    @Override
	public Map<String,Integer> getAdvanceByRemindTime(String remindTime,String start) throws ParseException{
		 Map map=new HashMap();
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 long time=sdf.parse(start).getTime()-sdf.parse(remindTime).getTime();
		  long ss=time/(1000); //共计秒数
		  int  mm = (int)ss/60;   //共计分钟数	
		  int  HH=(int)mm/60;  //共计小时数
		  int dd=(int)HH/24;   //共计天数
		  if(mm < 60){
			  map.put("timeType", 1);
			  map.put("advanceTime", mm);
		  }else{
			  if(HH<24){
				  map.put("timeType", 2);
				  map.put("advanceTime", HH);
			  }else{
	             if(dd<7){
	            	  map.put("timeType", 3);
					  map.put("advanceTime", dd);
	             }else{
	            	 map.put("timeType", 4);
					  map.put("advanceTime",(int)dd/7);
	             }
			  }
		  }
		  
		  return map;
	}
    @Override
	public int create(AgendaDTO AgendaDTO) throws ParseException {
    	Agenda Agenda=new Agenda();	
    	//获取当前登录者id
    	String color=AgendaDTO.getStatus().substring(1);
    	logger.info("method[create]"+AgendaDTO.getEnd() + ","+AgendaDTO.getTitle() + ","+AgendaDTO.getProductId());
		Long userID = (Long)UserSecurityManager.getAttribute("ID");
    	Agenda.setCreatorId(userID.intValue());
    	Agenda.setCreateTime(new Date());
    	Agenda.setUpadteTime(new Date());
    	Agenda.setEnd(AgendaDTO.getEnd());
    	Agenda.setStart(AgendaDTO.getStart()); 
    	Agenda.setTitle(AgendaDTO.getTitle());   
    	Agenda.setType(AgendaDTO.getType());
    	Agenda.setStatus("#"+color);//在数据库中保存显示的颜色
    	logger.info("method[create]"+AgendaDTO.getStatus() + ","+AgendaDTO.getType() + ","+userID.intValue());
		if(null!=AgendaDTO.getAdvanceTime()&&0!=AgendaDTO.getAdvanceTime()){
			Agenda.setIsRemind((byte) 1);
			Agenda.setRemindTime(getRemindTime(AgendaDTO));
		}else{
			Agenda.setIsRemind((byte) 2);
		}
		logger.info("method[create]"+Agenda.getIsRemind());
		logger.info("before[insert]");
		return AgendaDAO.insert(Agenda);
	}


	@Override
	public int update(AgendaDTO AgendaDTO) throws Exception{
		//获取当前登录者id
		Long userID = (Long)UserSecurityManager.getAttribute("ID");
		Agenda Agenda2=AgendaDAO.selectByPrimaryKey(AgendaDTO.getId());
		if((long)Agenda2.getCreatorId()!=userID){
			return -1;
		}
		Agenda Agenda=new Agenda();
		Agenda.setCreatorId(Agenda2.getCreatorId());;
		Agenda.setUpadteTime(new Date());
		Agenda.setEnd(AgendaDTO.getEnd());
		Agenda.setStart(AgendaDTO.getStart());
		Agenda.setIsRemind(AgendaDTO.getIsRemind());
		Agenda.setType(AgendaDTO.getType());
		Agenda.setTitle(AgendaDTO.getTitle());
		Date createTime=Agenda2.getCreateTime();
		Agenda.setId(Agenda2.getId());
		Agenda.setCreateTime(createTime);
		Agenda.setStatus("#"+AgendaDTO.getStatus().substring(1));
		if(AgendaDTO.getIsRemind().equals((byte) 1)){
		   Agenda.setRemindTime(getRemindTime(AgendaDTO));
		}
		return AgendaDAO.updateByPrimaryKey(Agenda);
	}
	@Override
	public int delete(long id){
		Long userID = (Long)UserSecurityManager.getAttribute("ID");
		int createrid=AgendaDAO.selectByPrimaryKey(id).getCreatorId();
		if(createrid==userID.intValue()){
			return AgendaDAO.deleteByPrimaryKey(id);
		}else{
			return -1;
		}	
	}

	@Override
	public AgendaDTO getAgendaDTOByKey(long id) throws ParseException {
		AgendaDTO AgendaDTO=new AgendaDTO();
		Agenda Agenda= AgendaDAO.selectByPrimaryKey(id);
		AgendaDTO.setId(id);

		//如果登陆者和创建者相等，则ajax请求数据保存创建者id;如果登陆者和创建者不一样，则ajax请求数据creatorid=0
	    Long userID = (Long)UserSecurityManager.getAttribute("ID");	
	    if(Agenda.getCreatorId()==userID.intValue()){
	    	AgendaDTO.setCreatorId(Agenda.getCreatorId());
	    }else{
	    	AgendaDTO.setCreatorId(0);
	    }
	    AgendaDTO.setCreatorName(employeeService.getEmployeeByID(Agenda.getCreatorId()).getName());
        AgendaDTO.setTitle(Agenda.getTitle());
        AgendaDTO.setStart(Agenda.getStart());
        AgendaDTO.setEnd(Agenda.getEnd());
        AgendaDTO.setIsRemind(Agenda.getIsRemind());
        AgendaDTO.setType(Agenda.getType());
        AgendaDTO.setStatus(Agenda.getStatus());
        if(Agenda.getIsRemind().equals(Byte.valueOf((byte) 1))){
        	 Map<String,Integer> map=getAdvanceByRemindTime(Agenda.getRemindTime(), Agenda.getStart());
        	 for(String key:map.keySet()){
        		 if(key.equals("timeType")){
        			 AgendaDTO.setTimeType(map.get(key));
        		 }else{
        			 AgendaDTO.setAdvanceTime(map.get(key));
        		 }
   		   }
        }
        return AgendaDTO;
	}
	@Override
	public List<Agenda> getAllList(Map<String, Object> params) {
		List<Agenda> list=AgendaDAO.queryAll(params);
		return list;
	}

	@Override
	public List<Agenda> getList(Map<String, Object> params) {
		List<Agenda> list=AgendaDAO.queryUserAll(params);
		return list;
	}
}
