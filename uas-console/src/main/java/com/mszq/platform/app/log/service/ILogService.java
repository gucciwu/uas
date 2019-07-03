package com.mszq.platform.app.log.service;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import com.mszq.platform.base.EUDataGridResult;


@Service
public interface ILogService {
	//无参的日志方法
    public void log();
    //有参的日志方法
    public void logArg(JoinPoint point);
    //有参有返回值的方法
    public void logArgAndReturn(JoinPoint point,Object returnObj);
    public EUDataGridResult getList(Map<String,String> param,int page, int pageSize) ;

}
