package com.mszq.platform.app.workflow.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mszq.platform.app.workflow.dto.ParamsDto;
import com.mszq.platform.app.workflow.dto.UserDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.WorkflowBusinessData;
import com.mszq.platform.entity.WorkflowTrace;

@Service
public interface IWorkflowService {
	/**
	 * 
	 * @param processDefinitionKey:流程定义的key，对应ACT_RE_PROCDEF表中的KEY字段
	 * @param businessKey:启动流程的账号id，这个在业务系统中是账号，在activiti中是账号的id
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey,String businessKey);
	
	public ProcessInstance startProcessInstanceById(String processDefinitionKey);
	
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey,String businessKey, Map<String, Object> params);
	
	
	public void startProcessAndCompleteSubmitActivity(String processDefinitionKey,String businessKey, Map<String, Object> params);
	
	//public JsonNode getModelJSON(String processInstanceId);
	public EUDataGridResult queryTodo(ParamsDto dto,int page, int rows);
	public List<WorkflowBusinessData> queryWorkflowBusinessData( Map<String, Object> params);
	public List<WorkflowBusinessData> queryWorkflowBusinessDataIncluingAssignee(Map<String, Object> params);
	
	public String queryProcessInstanceId(String businessDataId,String businessTableName);
	public InputStream loadByProcessInstance(String resourceType,String processInstanceId);
	
	public int addWorkflowBusinessData(String processInstaceId,String businessDataId,String businessTableName);
	public int addWorkflowBusinessData(String[] processInstaceIds,String[] businessDataIds );

	//签收、完成一并做
	public void claimAndComplete(String processInstanceId, String userId,String actionName);
	//签收
	public void claim(String taskId, String userId);
	
	//完成task
    public boolean  complete(String taskId, String userId,String type,String opinion);
    
	//完成task
    public boolean  complete(String taskId, String userId,Map<String, Object> variables,String type,String opinion);
	
	//完成task
	public void complete(String taskId, Map<String, Object> variables);
	
	public List<Map<String, Object>> traceImage(String processInstanceId) throws Exception;
	public EUDataGridResult traceInfo(String businessKey,String pocDefKey,int page, int pageSize) throws Exception;
	public List<Map> getAllTraceInfo(String id,String pdid) ;
	
	public int addWorkflowTrack(WorkflowTrace track);
	public boolean isCurrentAssignee(String businessDataId,String businessTableName,String userId);
	
	//流程跳转方法
	 public void jumpToEnd(String processDefinationKey,String businessDataId);
	 public void jump(final String processInstanceId,final String targetAct);

	void claimAndComplete(String processInstanceId, String userId, Map<String, Object> variables, String actionName);

	void suspendProcess(String taskId, String userId);

	String reject(String taskId, String targetActId,String opinion);
	

	void trans(String taskId, String userId,String opinion);
	

	List<ActivityImpl> getBeforeActivities(String taskId);

	Employee getEmployeeByID(String id);

	Boolean isNextActivityEnd(String taskId);


	public EUDataGridResult queryWorkflowUsers(Integer page, Integer rows, UserDto userDto);

	public Map getWorkflowInfo(Long id,String status, String processId);

	public String getProDefKey(String tid, String type);

	public EUDataGridResult queryHisTask(String string, Integer page, Integer rows);
	
	
	EUDataGridResult querySingleHiTask(Map map,Integer page,Integer rows);

	EUDataGridResult queryUserTask(ParamsDto dto, Integer page, Integer rows);

}
