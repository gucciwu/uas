package com.mszq.platform.app.workflow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.workflow.dto.UserDto;
import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.WorkflowBusinessData;

@MapperScan
public interface IWorkflowDAO {	
	public int addWorkflowBusinessData(WorkflowBusinessData workflowBusiness);
	public String queryProcessInstanceId(@Param("businessDataId") String businessDataId,@Param("businessTableName") String businessTableName);
	public List<WorkflowBusinessData> queryWorkflowBusinessData(Map<String,Object> params);
	public int deleteWorkflowBusinessDataBatch(String[] ids);
	public List<Map> queryUsers(UserDto condition);
}
