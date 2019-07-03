package com.mszq.platform.app.log.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mszq.platform.util.Log4j2Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.log.dao.ILogDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.*;

@Aspect
@Service
public class LogServiceImpl implements ILogService{
	private final Logger logger=LoggerFactory.getLogger(LogServiceImpl.class);
	private String[] stra;
	private Object[] args;
	private String logStr;
	@Resource
	private ILogDAO logDAO;
	
	
	
	public EUDataGridResult getList(Map<String,String> param,int page, int pageSize) {
		// 分页处理
		PageHelper.startPage(page, pageSize);
		List<Log> list = logDAO.queryAll(param);
		// 取记录总条数
		PageInfo<Log> pageInfo = new PageInfo<Log>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
				
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	
	@Override
	public void log() {
		System.out.println("目标参数列表：");
		Log4j2Util.LogIntoDB(logger, Log4j2Util.LogLevel.INFO, "");
		
	}

	/**
	 * 这里应该仅记录关键数据的操作，不需要什么都记录下来
	 */
	@After("execution(* com.mszq.*.app.login.controller.*.log*(..)) "
			+ "or execution(* com.mszq.*.app.config.controller.*.create*(..)) "
			+ "or execution(* com.mszq.*.*.dictionary.controller.*.create*(..)) " 
			+ "or execution(* com.mszq.*.*.employee.controller.*.addUser(..)) " 
			+ "or execution(* com.mszq.*.*.permission.controller.*.add*(..)) " 
			+ "or execution(* com.mszq.platform.app.orgnization.controller.OrgnizationController.addOrgnization(..))")  
	@Override
	public void logArg(JoinPoint point) {
		//TODO  未做完，以下仅为示例,需要根据实际情况将日志添加到数据库  
		args = point.getArgs();
		stra=phaseFuctionCall(point.toShortString());
		if(stra.length==2){
			switch(stra[0]){
			case "Login":{
				switch(stra[1]){
				case "login":logStr="登录";break;
				case "logout":logStr="登出";break;
				}
			}break;
			case "Config":{
				switch(stra[1]){
				case "create":{
					logStr="新增参数："+((Config)args[0]).getName();
					break;
				}
				case "update":break;
				}
				
				break;
			}
			}			
	        Log4j2Util.LogIntoDB(logger, Log4j2Util.LogLevel.INFO, logStr);
		}
		
	}

	@Override
	public void logArgAndReturn(JoinPoint point, Object returnObj) {
		//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        Object[] args = point.getArgs();
        System.out.println("目标参数列表：");
        if (args != null) {
            for (Object obj : args) {
                System.out.println(obj + ",");
            }
            System.out.println();
            System.out.println("执行结果是：" + returnObj);
        }
        Log4j2Util.LogIntoDB(logger, Log4j2Util.LogLevel.INFO, "");
		
	}
	
	private String[] phaseFuctionCall(String str){//"execution(ConfigController.create(..))" to [ConfigController,create]  execution(LoginController.logout())
		int index= str.indexOf("(..)");
		int index2=str.indexOf("()");
		if(index!=-1){
			str=str.substring(str.indexOf("(")+1,index);
		}else if(str.indexOf("()")!=-1){
			str=str.substring(str.indexOf("(")+1,index2);
		}
		String[] stra=str.split("\\.");
		
		if(stra.length==2) stra[0]=stra[0].substring(0,stra[0].indexOf("Controller"));
		return stra;
	}

}
