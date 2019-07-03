package com.mszq.platform.app.remind.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.app.holiday.service.IHolidayService;
import com.mszq.platform.app.remind.service.IRemindService;
import com.mszq.platform.base.SqlMapper;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.Remind;
import com.mszq.platform.entity.RemindRule;
import com.mszq.platform.util.DateConverter;
import com.mszq.platform.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class RemindJob implements Job {

	@Autowired
	private IRemindService remindService;
	@Autowired
	private IHolidayService holidayService;
	@Autowired
	private SqlMapper sqlMapper;
	@Autowired
	private IEmployeeService employeeService;
	
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<RemindRule> ruleList;
		String tableName;
		String column;
		String triggerRule;
		String contentRule;
		String targetRule;
		String content="";
		String[] tmp;
		String paramValue;
		Date triggerDate;
		String querySql = "";
		String queryColumn = "";
		
		List<Map<String, Object>> dataList;
		Map conditions=new HashMap();
		List<Employee> employees=new ArrayList();
		List<Remind> remindList=new ArrayList();
		List<String> contentList=new ArrayList();

		String targetType;
		String targetId;
		JSONObject target;
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);  

		ruleList = remindService.queryRemindRule(null);
		for (RemindRule rule : ruleList) {
			tableName = rule.getTableName();
			triggerRule = rule.getTriggerRule();
			contentRule = rule.getContentRule();
			targetRule = rule.getTargetRule();

			// 1、分析triggerrule,目前仅支持"expiration_date=current_date-3"这种格式的。
			column = triggerRule.substring(0, (triggerRule.indexOf("=") == -1) ? 0 : triggerRule.indexOf("="));
			paramValue = triggerRule.substring(
					(triggerRule.indexOf("+") != -1) ? triggerRule.indexOf("+") : triggerRule.indexOf("-"),
					triggerRule.length());

			triggerDate = holidayService.getTradingDate(Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).getTime(),
					Integer.parseInt(paramValue));

			// 2、提取contentRule中的字段,逗号分隔
			queryColumn = StringUtil.extractBracketInnerStr(contentRule);

			/// 3.将contentRule中需要替换的位置用#来代替
			for (String s : queryColumn.split(",")) {
				contentRule = contentRule.replaceAll("\\{" + s + "\\}", "#");
			}

			// 3、构造查询sql，查询业务数据
			querySql = "select " + queryColumn + " from " + tableName + " where " + column + "='"
					+ format.format(triggerDate) + "'";
			dataList = sqlMapper.selectList(querySql);

			
			
			
			
			
			// 3.生成content
			
			for (Map map : dataList) {
				content=contentRule;
				for (Object key : map.keySet()) {
					content= content.replaceFirst("#", map.get(key).toString());
				}
				contentList.add(content);
			}

			// 4、解析targetRule，生成接收者的id
			JSONArray jsonArray = JSONArray.fromObject(targetRule);
			for (int i = 0; i < jsonArray.size(); i++) {
				target=jsonArray.getJSONObject(i);
				targetType=target.getString("type");
				targetId=target.getString("id");
				switch(targetType){
				case "1" :{//机构
					break;
				}
				case "2":{//角色
					conditions.put("roleId",targetId);
					employees=employeeService.queryAll(conditions);
					targetId="";
					for(Employee e:employees){//取出人员id，生成提醒内容
						targetId+=e.getId().toString()+",";
					}
					break;
				}
				case "3":{//个人，已经保存到targetId中
					break;
				}
				}
				
				for(String id:targetId.split(",")){
					for(String c:contentList) remindList.add(new Remind(c,c,new Date(),null,null,null,Long.parseLong(id),"01","0"));
				}
				
				//插入提醒
				if(remindList.size()>0) remindService.insertRemindBatch(remindList);
	
				conditions.clear();
				remindList.clear();
			}

		}

	}


}
