package com.mszq.platform.app.workflow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.WorkflowTrace;
@MapperScan
public interface IWorkflowTraceDAO {
	public int addWorkflowTrace(WorkflowTrace trace);
	public List<WorkflowTrace> queryWorkflowTrace(Map<String,Object> params);
	public List<Map> getAllTraceInfo(@Param("bid")String id,@Param("procDefKey")String pdid) ;

}
