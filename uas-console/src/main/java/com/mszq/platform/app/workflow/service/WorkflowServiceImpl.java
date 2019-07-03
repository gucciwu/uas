package com.mszq.platform.app.workflow.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.employee.dao.IEmployeeDAO;
import com.mszq.platform.app.remind.dao.IRemindDAO;
import com.mszq.platform.app.workflow.command.NodeJumpTaskCmd;
import com.mszq.platform.app.workflow.constant.WorkflowConstant;
import com.mszq.platform.app.workflow.dao.IActivitiExtendDao;
import com.mszq.platform.app.workflow.dao.IWorkflowDAO;
import com.mszq.platform.app.workflow.dao.IWorkflowTraceDAO;
import com.mszq.platform.app.workflow.dto.ParamsDto;
import com.mszq.platform.app.workflow.dto.TodoDTO;
import com.mszq.platform.app.workflow.dto.UserDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.IConstants;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.Remind;
import com.mszq.platform.entity.TestWF;
import com.mszq.platform.entity.WorkflowBusinessData;
import com.mszq.platform.entity.WorkflowTrace;
import com.mszq.platform.util.AmountUtil;
import com.mszq.platform.util.StringUtil;
import com.mszq.platform.util.WorkflowUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements IWorkflowService {
	@Resource
	ProcessEngine processEngine;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	@Resource
	private IdentityService identityService;
	@Resource
	protected ManagementService managementService;
	@Resource
	private IWorkflowDAO workflowDAO;

	@Resource
	private IWorkflowTraceDAO workflowTraceDAO;

	@Autowired
	private IEmployeeDAO employeeDAO;
	
	@Autowired
	private IActivitiExtendDao activitiExtendDao;
	
	@Resource
	IRemindDAO remindDAO;

	private final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

	public ProcessInstance startProcessByKey(String processDefinitionKey) {
		return processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey); // 使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	}

	public ProcessInstance startProcessById(String processDefinitionId) {
		return processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId); // 使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	}

	/**
	 * 查询待办，将待签收与待处理一起合并返回
	 * 
	 * @param userId:act_id_user中的id
	 * @param page
	 * @param pageParams
	 * @return
	 */
	@Override
	public EUDataGridResult queryTodo(ParamsDto dto, int page, int rows) {

		if(dto==null){
			dto=new ParamsDto();
		}
		if(dto.getAssignee()==null){
			dto.setAssignee(UserSecurityManager.getAttribute("account").toString());
		}
		
		String userId=dto.getAssignee();
		
		List<TodoDTO> todoDTOList = new ArrayList();
		String processDefinationKey = null;
		String businessKey = null;
		TodoDTO todoDTO = null;
		EUDataGridResult result = new EUDataGridResult();
		List<Task> tasks = null;

		// 根据当前人的ID查询
		
		if (page == -1 || rows == -1) {
			page = 1;
			rows = 10;
		}
		
		EUDataGridResult re=this.queryUserTask(dto, page, rows);
		
//		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
//		tasks = taskQuery.listPage((page - 1) * rows, rows);
		
		tasks=(List<Task>) re.getRows();
		
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(processInstanceId).active().includeProcessVariables().singleResult();

			if (processInstance == null) {
				continue;
			}
			String processDefinationId = processInstance.getProcessDefinitionId();
			processDefinationKey = processInstance.getProcessDefinitionKey();
			businessKey = processInstance.getBusinessKey();

			BigDecimal amount = (BigDecimal) processInstance.getProcessVariables()
					.get(WorkflowConstant.WORKFLOW_APPROVE_AMOUNT_KEY);

			String approveUrl = task.getFormKey();
			String processDefinationName = processEngine.getRepositoryService()
					.getProcessDefinition(processDefinationId).getName();

			String title = "【"+processDefinationName+"】："+processInstance.getProcessVariables().get(WorkflowConstant.WORKFLOW_INSTANCE_TITLE_KEY);

						
			
			todoDTO = new TodoDTO();
			todoDTO.setTitle(title);
			todoDTO.setApproveUrl(approveUrl);
			todoDTO.setBusinessDataId(businessKey);
			todoDTO.setCreateTime(task.getCreateTime());
			todoDTO.setTaskId(task.getId());
			todoDTO.setProcessInstanceId(processInstanceId);
			todoDTO.setProcessDefinationKey(processDefinationId);
			todoDTO.setProcessDefinationName(processDefinationName);
			todoDTO.setTaskName(task.getName());
			todoDTO.setType(task.getCategory());
			todoDTO.setSendUserName(processInstance.getProcessVariables()
					.get(WorkflowConstant.WORKFLOW_INSTANCE_PREVIOUS_USERNAME).toString());
			todoDTOList.add(todoDTO);
		}
		result.setTotal(re.getTotal());
		result.setRows(todoDTOList);
		return result;
	}

	public List<WorkflowBusinessData> queryWorkflowBusinessData(Map<String, Object> params) {

		return workflowDAO.queryWorkflowBusinessData(params);
	}

	/**
	 * 查询待办列表,待签收与待处理的分别查询。 暂时不用这个方法
	 */

	public EUDataGridResult getTodoListSeprate(String accountId, int page, int rows) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		EUDataGridResult euresult = new EUDataGridResult();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 已经签收的任务
		List<Task> todoList = taskService.createTaskQuery().taskAssignee(accountId).active().list();
		for (Task task : todoList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

			Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
			singleTask.put("status", "todo");
			result.add(singleTask);
		}
		// TODO 查找待办事项
		// 等待签收的任务
		List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(accountId).active().list();
		for (Task task : toClaimList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);

			Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
			singleTask.put("status", "claim");
			result.add(singleTask);
		}
		euresult.setRows(result);
		euresult.setTotal(result.size());
		return euresult;
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey) {
		ProcessInstance processInstance = null;
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId((String) UserSecurityManager.getAttribute("account"));
			processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,
					businessKey, new HashMap<String, Object>());
			// 存工作流与业务数据之间的关系
			addWorkflowBusinessData(processInstance.getId(), businessKey, IConstants.TABLE_NAME_REPORT_PERIOD);
			// TODO 记录操作日志
			addWorkflowTrack(new WorkflowTrace(processInstance.getId(), null, IConstants.WORKFLOW_ACT_START_NAME,
					new Date(), (String) UserSecurityManager.getAttribute("account"),
					IConstants.WORKFLOW_ACT_START_TYPE, null, businessKey, processInstance.getProcessDefinitionKey()));
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;

	}

	@Override
	public ProcessInstance startProcessInstanceById(String processDefinitionKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void complete(String processInstanceId, Map<String, Object> variables) {
		// TODO 根据流程实例ID来查询当前正在执行的task，然后完成这个task,list的结果会有多个吗？？？？
		List<Task> ts = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		if (ts.size() == 1)
			taskService.complete(ts.get(0).getId(), variables);
	}

	@Override
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	@Override
	public void claimAndComplete(String processInstanceId, String userId, Map<String, Object> variables,
			String actionName) {
		String businessKey = "";
		// TODO 根据流程实例ID来查询当前正在执行的task，然后完成这个task,list的结果会有多个吗？？？？
		businessKey = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(processInstanceId).active().singleResult().getBusinessKey();
		List<Task> ts = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		if (ts.size() == 1) {
			taskService.claim(ts.get(0).getId(), userId);
			taskService.complete(ts.get(0).getId(), variables);
			ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(ts.get(0).getProcessInstanceId()).singleResult();
			addWorkflowTrack(new WorkflowTrace(processInstanceId, ts.get(0).getId(), ts.get(0).getName(), new Date(),
					userId, actionName, (String) variables.get("comment"), businessKey,
					instance.getProcessDefinitionKey()));
		}

	}

	@Override
	public int addWorkflowTrack(WorkflowTrace trace) {
		trace.setId(StringUtil.gengerteUUID());
		return workflowTraceDAO.addWorkflowTrace(trace);
	}

	/**
	 * 查询流程定义对象
	 *
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
		Map<String, Object> singleTask = new HashMap<String, Object>();
		singleTask.put("id", task.getId());
		singleTask.put("name", task.getName());
		singleTask.put("createTime", sdf.format(task.getCreateTime()));
		singleTask.put("pdname", processDefinition.getName());
		singleTask.put("pdversion", processDefinition.getVersion());
		singleTask.put("pid", task.getProcessInstanceId());
		return singleTask;
	}

	@Override
	public int addWorkflowBusinessData(String processInstaceId, String businessDataId, String businessTableName) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<WorkflowBusinessData> workflowBusinessDataList = new ArrayList<WorkflowBusinessData>();
		String workflowBusinessDataIds = "";

		// 先检查是否有未完成的工作流，如果有，则删掉，这是个保险措施，一般不会出现
		params.put("businessDataIds", new String[] { businessDataId });
		params.put("businessTableName", businessTableName);
		params.put("processInstanceId", processInstaceId);
		params.put("isOver", "0");
		workflowBusinessDataList = workflowDAO.queryWorkflowBusinessData(params);
		if (workflowBusinessDataList.size() > 0) {// 删除
			for (WorkflowBusinessData tmp : workflowBusinessDataList)
				workflowBusinessDataIds = tmp.getId() + "," + workflowBusinessDataIds;
			workflowDAO.deleteWorkflowBusinessDataBatch(workflowBusinessDataIds.split(","));
		}

		// 然后添加
		return workflowDAO.addWorkflowBusinessData(new WorkflowBusinessData(StringUtil.gengerteUUID(), processInstaceId,
				businessDataId, businessTableName, new Date()));
	}

	@Override
	public int addWorkflowBusinessData(String[] processInstaceIds, String[] businessDataIds) {

		return 0;
	}

	@Override
	public String queryProcessInstanceId(String businessDataId, String businessTableName) {

		return workflowDAO.queryProcessInstanceId(businessDataId, businessTableName);
	}

	/**
	 * 获取流程执行记录（日志）
	 */
	@Override
	public EUDataGridResult traceInfo(String businessKey, String procDefKey, int page, int pageSize) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		List<WorkflowTrace> list;
		// 分页处理
		PageHelper.startPage(page, pageSize);

		params.put("opDataId", businessKey);
		params.put("procDefKey", procDefKey);
		list = workflowTraceDAO.queryWorkflowTrace(params);

		// 取记录总条数
		PageInfo<WorkflowTrace> pageInfo = new PageInfo<WorkflowTrace>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	/**
	 * 流程跟踪图
	 *
	 * @param processInstanceId
	 *            流程实例ID
	 * @return 封装了各种节点信息
	 */
	@Override
	public List<Map<String, Object>> traceImage(String processInstanceId) throws Exception {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		HistoricProcessInstance historicProcessInstance;

		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
		if (execution != null) {// 流程未结束，读运行表
			Object property = PropertyUtils.getProperty(execution, "activityId");
			String activityId = "";
			if (property != null) {
				activityId = property.toString();
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
			List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

			List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
			for (ActivityImpl activity : activitiList) {

				boolean currentActiviti = false;
				String id = activity.getId();

				// 当前节点
				if (id.equals(activityId)) {
					currentActiviti = true;
				}

				Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance,
						currentActiviti);

				activityInfos.add(activityImageInfo);
			}

			return activityInfos;
		} else {// 流程已经结束，读历史表
			historyService = processEngine.getHistoryService();
			historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();

			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());
			List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

			List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
			for (ActivityImpl activity : activitiList) {

				Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, null, false);

				if ("endEvent".equals(activity.getProperty("type"))) {
					activityImageInfo.put("isEnd", true);
				}
				activityInfos.add(activityImageInfo);
			}

			return activityInfos;

		}

	}

	@Override
	public InputStream loadByProcessInstance(String resourceType, String processInstanceId) {
		HistoricProcessInstance historicProcessInstance;

		historyService = processEngine.getHistoryService();
		historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

		String resourceName = "";
		if (resourceType.equals("image")) {
			resourceName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("xml")) {
			resourceName = processDefinition.getResourceName();
		}
		return repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
	}

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 *
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
			boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("任务类型", WorkflowUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			/*
			 * 当前节点的task
			 */
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			/*
			 * 当前任务的分配角色
			 */
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 *
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 *
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId())
					.taskDefinitionKey(activitiId).singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 *
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 *
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}

	@Override
	public boolean isCurrentAssignee(String businessDataId, String businessTableName, String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<WorkflowBusinessData> wfbd;

		// 1、查询当前登录人有待办的流程实例
		ParamsDto dto=new ParamsDto();
		dto.setAssignee(userId);
		List<TodoDTO> todoDTOList = (List<TodoDTO>) (queryTodo(dto, -1, -1)).getRows();
		// 2.查询当前记录对应的流程实例
		if (null != businessDataId)
			params.put("businessDataId", businessDataId);
		if (null != businessTableName && !"".equalsIgnoreCase(businessTableName))
			params.put("businessTableName", businessTableName);
		wfbd = queryWorkflowBusinessData(params);// 正常情况下，wbd只有一个返回值
		// 3.如果2包含1，则当前人是当前记录的当前流程的处理人
		if (wfbd != null && todoDTOList != null) {
			for (TodoDTO toTemp : todoDTOList) {
				for (WorkflowBusinessData wtmp : wfbd) {
					if (wtmp.getProcessInstanceId().equalsIgnoreCase(toTemp.getProcessInstanceId())) {
						return true;
					}
				}

			}

		}
		return false;
	}

	@Override
	public List<WorkflowBusinessData> queryWorkflowBusinessDataIncluingAssignee(Map<String, Object> params) {
		List<WorkflowBusinessData> wfbd;

		// 1、查询当前登录人有待办的流程实例
		
		ParamsDto dto=new ParamsDto();
		List<TodoDTO> todoDTOList = (List<TodoDTO>) (queryTodo(dto,
				-1, -1)).getRows();
		// 2.查询当前记录对应的流程实例
		wfbd = queryWorkflowBusinessData(params);
		// 3.如果2包含1，则当前人是当前记录的当前流程的处理人
		if (wfbd != null && todoDTOList != null) {
			for (WorkflowBusinessData temp : wfbd) {
				for (TodoDTO toTemp : todoDTOList)
					if (temp.getProcessInstanceId().equalsIgnoreCase(toTemp.getProcessInstanceId())) {
						temp.setIsCurrentAssignee("1");// 当前登录人是当前task的候选人之一
						break;
					}
			}
		}
		return wfbd;
	}

	/**
	 * 根据ActivityId 查询出来想要活动Activity
	 * 
	 * @param id
	 * @return
	 */
	public ActivityImpl queryTargetActivity(String id) {

		RepositoryService repositoryService = processEngine.getRepositoryService();
		ReadOnlyProcessDefinition deployedProcessDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition("ziyouliu:1:4");
		List<ActivityImpl> activities = (List<ActivityImpl>) deployedProcessDefinition.getActivities();
		for (ActivityImpl activityImpl : activities) {
			if (activityImpl.getId().equals(id)) {
				return activityImpl;
			}
		}
		return null;
	}

	/**
	 * 查询当前的活动节点
	 */
	public ActivityImpl qureyCurrentTask(String processDefinitionId) {
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// String processDefinitionId="ziyouliu:1:4";
		Execution execution = runtimeService.createExecutionQuery().processDefinitionId(processDefinitionId)
				.singleResult();
		String activityId = execution.getActivityId();
		ActivityImpl currentActivity = queryTargetActivity(activityId);
		System.out.println(currentActivity.getId() + "" + currentActivity.getProperty("name"));

		return currentActivity;
	}

	/**
	 * 自由跳转的方式: 这种方式是通过 重写命令 ，推荐这种方式进行实现(这种方式的跳转，最后可以通过taskDeleteReason 来进行查询 )
	 */

	public void jump(final String processInstanceId, final String targetAct) {

		((RuntimeServiceImpl) processEngine.getRuntimeService()).getCommandExecutor().execute(new Command<Object>() {
			public Object execute(CommandContext commandContext) {
				// TODO 以下应该是一个循环，因为如果存在并行流程(一个时间点流程同时在多个节点),那么execution就有可能有多个
				ExecutionEntity execution = commandContext.getExecutionEntityManager()
						.findExecutionById(processInstanceId);
				execution.destroyScope("jump");
				ProcessDefinitionImpl processDefinition = execution.getProcessDefinition();
				ActivityImpl findActivity = processDefinition.findActivity(targetAct);
				execution.executeActivity(findActivity);
				return execution;
			}

		});
	}

	@Override
	public String reject(String taskId, String targetActId, String opinion) {
		taskService.claim(taskId, UserSecurityManager.getAttribute("account").toString());
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(task.getProcessDefinitionId());
		ActivityImpl targetActivity = definition.findActivity(targetActId);
		ActivityImpl curActivity = definition.findActivity(task.getTaskDefinitionKey());
		// 实现跳转
		ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		String username = UserSecurityManager.getAttribute("name").toString();
		variables.put(WorkflowConstant.WORKFLOW_INSTANCE_PREVIOUS_USERNAME, username);
		String procDefKey = processInstance.getProcessDefinitionKey();
		addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), task.getId(), task.getName(), new Date(),
				(String) UserSecurityManager.getAttribute("account"),
				WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_PASS_REJECT, opinion, processInstance.getBusinessKey(),
				procDefKey));
		managementService
				.executeCommand(new NodeJumpTaskCmd(task.getExecutionId(), targetActivity, variables, curActivity));
		Task nTask = (Task) taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		
		if(nTask.getCategory()!=null&&"start".equals(nTask.getCategory().trim())){
			return "01";
		}
		else{
			return "02";
		}
	}

	@Override
	public List<ActivityImpl> getBeforeActivities(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(task.getProcessDefinitionId());

		ActivityImpl curActivity = definition.findActivity(task.getTaskDefinitionKey());

		List<ActivityImpl> result = new ArrayList();

		while (curActivity != null && "userTask".equals(curActivity.getProperty("type"))) {
			List<PvmTransition> inTransitions = curActivity.getIncomingTransitions();
			if (inTransitions.size() > 0) {
				ActivityImpl temp = definition.findActivity(inTransitions.get(0).getSource().getId());
				if (temp != null && !temp.getId().equals(curActivity.getId())
						&& "userTask".equals(temp.getProperty("type"))) {
					result.add(temp);
					curActivity = temp;
				} else {
					break;
				}

			} else {
				break;
			}
		}
		return result;
	}

	@Override
	public Boolean isNextActivityEnd(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(task.getProcessDefinitionId());

		ActivityImpl curActivity = definition.findActivity(task.getTaskDefinitionKey());

		List<PvmTransition> inTransitions = curActivity.getOutgoingTransitions();

		if (inTransitions.size() > 0) {
			ActivityImpl temp = definition.findActivity(inTransitions.get(0).getSource().getId());
			if (temp != null && !temp.getId().equals(curActivity.getId())
					&& "endEvent".equals(temp.getProperty("type"))) {

				return true;
			}

		}
		return false;
	}

	/**
	 * @param processDefinationKey:流程定义id
	 */
	public void jumpToEnd(String processDefinationKey, String businessDataId) {
		Map<String, Object> params = new HashMap<String, Object>();
		String processInstanceId = "";
		String businessTableName = "";
		String endActId = "";// 设计器中的结束act的id

		switch (processDefinationKey) {
		case IConstants.WORKFLOW_PROCESS_DEFINE_KEY_REPORT: {
			businessTableName = IConstants.TABLE_NAME_REPORT_PERIOD;
			endActId = "endevent1";
			break;
		}
		}
		// 1.根据businessKeys找到processInstanceid
		processInstanceId = workflowDAO.queryProcessInstanceId(businessDataId, businessTableName);
		// 根据processInstanceid去结束流程
		if (null != processInstanceId && !"".equalsIgnoreCase(processInstanceId)) {
			jump(processInstanceId, endActId);
			ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			addWorkflowTrack(new WorkflowTrace(processInstanceId, null, "结束", new Date(),
					(String) UserSecurityManager.getAttribute("account"), "结束流程", "强制结束流程", businessDataId,
					instance.getProcessDefinitionKey()));
		}
	}

	@Override
	public void claimAndComplete(String processInstanceId, String userId, String actionName) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean complete(String taskId, String userId, String type, String opinion) {
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.claim(taskId, userId);
		ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String bid = instance.getBusinessKey();
		addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), task.getId(), task.getName(), new Date(),
				userId, WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_PASS_TEXT, opinion, bid,
				instance.getProcessDefinitionKey()));

		Map<String, Object> variables = new HashMap<String, Object>();
		String username = UserSecurityManager.getAttribute("name").toString();
		variables.put(WorkflowConstant.WORKFLOW_INSTANCE_PREVIOUS_USERNAME, username);
		taskService.complete(taskId, variables);
		ProcessInstance newState = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		boolean end = newState == null || newState.isEnded();

		if (end) {
			HistoricProcessInstance hInstance=	this.historyService.createHistoricProcessInstanceQuery().
					processInstanceId(task.getProcessInstanceId()).includeProcessVariables().singleResult();

			BigDecimal amount = (BigDecimal) hInstance.getProcessVariables()
					.get(WorkflowConstant.WORKFLOW_APPROVE_AMOUNT_KEY);
			String processDefinationName = processEngine.getRepositoryService()
					.getProcessDefinition(hInstance.getProcessDefinitionId()).getName();
			String processDefinationKey=hInstance.getProcessDefinitionKey();
			
			String title = "【"+processDefinationName+"】："+hInstance.getProcessVariables().get(WorkflowConstant.WORKFLOW_INSTANCE_TITLE_KEY);
			
			String creatorId=hInstance.getProcessVariables().get(WorkflowConstant.WORKFLOW_INSTANCE_CREATOR_KEY).toString();
			this.addRemind(title, title, creatorId, "09");
			addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), null,
					WorkflowConstant.WORKFLOW_ACTIVITY_TYPE_END_TEXT, new Date(), userId,
					WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_COMPLETE_TEXT, opinion, bid,
					instance.getProcessDefinitionKey()));
		}

		return end;
	}

	@Override
	public void suspendProcess(String taskId, String userId) {
		// TODO Auto-generated method stu
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String bid = processInstance.getBusinessKey();
		String procDefKey = processInstance.getProcessDefinitionKey();
		addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), task.getId(), task.getName(), new Date(),
				userId, WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_QUIT_TEXT, "无", bid, procDefKey));
		processEngine.getRuntimeService().suspendProcessInstanceById(processInstance.getId());

	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey, businessKey,
				params);
	}

	@Override
	public void startProcessAndCompleteSubmitActivity(String processDefinitionKey, String businessKey,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		String username = UserSecurityManager.getAttribute("name").toString();
		params.put(WorkflowConstant.WORKFLOW_INSTANCE_PREVIOUS_USERNAME, username);
		ProcessInstance pInstance = this.startProcessInstanceByKey(processDefinitionKey, businessKey, params);
		Task task = taskService.createTaskQuery().processInstanceId(pInstance.getProcessInstanceId()).singleResult();
		String userId = params.get("creatorId").toString();
		addWorkflowTrack(
				new WorkflowTrace(task.getProcessInstanceId(), null, WorkflowConstant.WORKFLOW_ACTIVITY_TYPE_START_TEXT,
						new Date(), userId, WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_SUBMIT_TEXT, "", businessKey,
						pInstance.getProcessDefinitionKey()));
		taskService.complete(task.getId());
	}

	@Override
	public void trans(String taskId, String userId, String opinion) {
		// TODO Auto-generated method stub

		Employee e = this.getEmployeeByID(userId);
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), task.getId(), task.getName(), new Date(),
				e.getAccount(), WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_TANS_TEXT, "无",
				processInstance.getBusinessKey(), processInstance.getProcessDefinitionKey()));
		if (task.getAssignee() == null) {
			taskService.claim(taskId, e.getAccount());
		} else {
			taskService.setAssignee(taskId, e.getAccount());
		}

	}

	@Override
	public Employee getEmployeeByID(String id) {
		return this.employeeDAO.selectEmployeeByKey(Long.parseLong(id));
	}

	@Override
	public List<Map> getAllTraceInfo(String id, String procDefKey) {
		// TODO Auto-generated method stub
		return this.workflowTraceDAO.getAllTraceInfo(id, procDefKey);
	}

	@Override
	public boolean complete(String taskId, String userId, Map<String, Object> variables, String type, String opinion) {
		// TODO Auto-generated method stub
		Task task = (Task) taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.claim(taskId, userId);
		ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String bid = instance.getBusinessKey();
		String opType = WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_PASS_TEXT;
		if (WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_RESUBMIT.equals(type)) {
			opType = WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_RESUBMIT_TEXT;
		}
		addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), task.getId(), task.getName(), new Date(),
				userId, opType, opinion, bid, instance.getProcessDefinitionKey()));

		String username = UserSecurityManager.getAttribute("name").toString();
		variables.put(WorkflowConstant.WORKFLOW_INSTANCE_PREVIOUS_USERNAME, username);
		taskService.complete(taskId, variables);
		ProcessInstance newState = processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		boolean end = newState == null || newState.isEnded();

		if (end) {
			
			HistoricProcessInstance hInstance=	this.historyService.createHistoricProcessInstanceQuery().
					processInstanceId(task.getProcessInstanceId()).includeProcessVariables().singleResult();

			BigDecimal amount = (BigDecimal) hInstance.getProcessVariables()
					.get(WorkflowConstant.WORKFLOW_APPROVE_AMOUNT_KEY);
			String processDefinationName = processEngine.getRepositoryService()
					.getProcessDefinition(hInstance.getProcessDefinitionId()).getName();
			String processDefinationKey=hInstance.getProcessDefinitionKey();
			
			String title = "【"+processDefinationName+"】："+hInstance.getProcessVariables().get(WorkflowConstant.WORKFLOW_INSTANCE_TITLE_KEY);
			
			String creatorId=hInstance.getProcessVariables().get(WorkflowConstant.WORKFLOW_INSTANCE_CREATOR_KEY).toString();
			this.addRemind(title, title, creatorId, "09");
			addWorkflowTrack(new WorkflowTrace(task.getProcessInstanceId(), null,
					WorkflowConstant.WORKFLOW_ACTIVITY_TYPE_END_TEXT, new Date(), userId,
					WorkflowConstant.WORKFLOW_INSTANCE_OP_TYPE_COMPLETE_TEXT, opinion, bid,
					instance.getProcessDefinitionKey()));
		}

		return end;
	}

	@Override
	public EUDataGridResult queryWorkflowUsers(Integer page, Integer rows, UserDto condition) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<Map> list = this.workflowDAO.queryUsers(condition);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public Map getWorkflowInfo(Long id, String status, String processId) {
		// TODO Auto-generated method stub
		String pid = null;
		if ("03".equals(status) || "04".equals(status)) {
			HistoricProcessInstance p = this.historyService.createHistoricProcessInstanceQuery()
					.processDefinitionKey(processId).processInstanceBusinessKey(id.toString())
					.orderByProcessInstanceId().desc().singleResult();
			pid = p.getId();
		} else if ("02".equals(status)||"01".equals(status)) {
			ProcessInstance p = this.processEngine.getRuntimeService().createProcessInstanceQuery()
					.processInstanceBusinessKey(id.toString(), processId).singleResult();
			pid = p.getId();
		} else {
			return null;
		}
		Map map = new HashMap();

		map.put("pid", pid);
		/*
		 * List<Map>
		 * list=this.workflowTraceDAO.getAllTraceInfo(id.toString(),processId);
		 * map.put("list", list);
		 */
		return map;
	}

	@Override
	public String getProDefKey(String tid, String type) {
		// TODO Auto-generated method stub
		if ("01".equals(type)) {
			ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(tid).singleResult();
			if (instance != null) {
				return instance.getProcessDefinitionKey();
			} else {
				HistoricProcessInstance hpi = this.historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(tid).singleResult();
				if (hpi != null) {
					return hpi.getProcessDefinitionKey();
				}
			}
		} else if ("02".equals(type)) {
			Task task = (Task) taskService.createTaskQuery().taskId(tid).singleResult();
			ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).singleResult();
		}
		return null;
	}

	/**
	 * 返回流程跟踪相关的json数据
	 * 
	 * @Override public JsonNode getModelJSON(String processInstanceId) {
	 * 
	 *           RuntimeService runtimeService =
	 *           processEngine.getRuntimeService(); ProcessInstance
	 *           processInstance =
	 *           (ProcessInstance)runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	 *           if (processInstance == null) { logger.error("No process
	 *           instance found with id " + processInstanceId); } BpmnModel
	 *           pojoModel =
	 *           repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
	 *           if ((pojoModel == null) ||
	 *           (pojoModel.getLocationMap().isEmpty())) { logger.error("Process
	 *           definition could not be found with id " +
	 *           processInstance.getProcessDefinitionId()); }
	 *           List<HistoricActivityInstance> activityInstances =
	 *           this.historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
	 * 
	 *           Set<String> completedActivityInstances = new HashSet();
	 *           Set<String> currentElements = new HashSet(); if
	 *           (CollectionUtils.isNotEmpty(activityInstances)) { for
	 *           (HistoricActivityInstance activityInstance : activityInstances)
	 *           { if (activityInstance.getEndTime() != null) {
	 *           completedActivityInstances.add(activityInstance.getActivityId());
	 *           } else { currentElements.add(activityInstance.getActivityId());
	 *           } } } List<Job> jobs =
	 *           managementService.createJobQuery().processInstanceId(processInstanceId).list();
	 *           Map<String, Execution> executionMap; if
	 *           (CollectionUtils.isNotEmpty(jobs)) { List<Execution> executions
	 *           =
	 *           runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
	 *           executionMap = new HashMap(); for (Execution execution :
	 *           executions) { executionMap.put(execution.getId(), execution); }
	 *           for (Job job : jobs) { if
	 *           (executionMap.containsKey(job.getExecutionId())) {
	 *           currentElements.add(((Execution)executionMap.get(job.getExecutionId())).getActivityId());
	 *           } } } List<String> completedFlows =
	 *           gatherCompletedFlows(completedActivityInstances,
	 *           currentElements, pojoModel);
	 * 
	 *           Set<String> completedElements = new
	 *           HashSet(completedActivityInstances);
	 *           completedElements.addAll(completedFlows);
	 * 
	 *           ObjectNode displayNode = processProcessElements(pojoModel,
	 *           completedElements, currentElements); ArrayNode
	 *           completedActivities; if (completedActivityInstances != null) {
	 *           completedActivities =
	 *           displayNode.putArray("completedActivities"); for (String
	 *           completed : completedActivityInstances) {
	 *           completedActivities.add(completed); } } ArrayNode
	 *           currentActivities; if (currentElements != null) {
	 *           currentActivities = displayNode.putArray("currentActivities");
	 *           for (String current : currentElements) {
	 *           currentActivities.add(current); } } ArrayNode
	 *           completedSequenceFlows; if (completedFlows != null) {
	 *           completedSequenceFlows =
	 *           displayNode.putArray("completedSequenceFlows"); for (String
	 *           current : completedFlows) {
	 *           completedSequenceFlows.add(current); } }
	 * 
	 *           return displayNode;
	 * 
	 *           }
	 * 
	 * 
	 * 
	 *           protected ObjectNode processProcessElements(BpmnModel
	 *           pojoModel, Set<String> completedElements, Set<String>
	 *           currentElements) { ObjectNode displayNode =
	 *           this.objectMapper.createObjectNode(); GraphicInfo diagramInfo =
	 *           new GraphicInfo();
	 * 
	 *           ArrayNode elementArray = this.objectMapper.createArrayNode();
	 *           ArrayNode flowArray = this.objectMapper.createArrayNode(); if
	 *           (CollectionUtils.isNotEmpty(pojoModel.getPools())) { ArrayNode
	 *           poolArray = this.objectMapper.createArrayNode(); boolean
	 *           firstElement = true; for (Pool pool : pojoModel.getPools()) {
	 *           ObjectNode poolNode = this.objectMapper.createObjectNode();
	 *           poolNode.put("id", pool.getId()); poolNode.put("name",
	 *           pool.getName()); GraphicInfo poolInfo =
	 *           pojoModel.getGraphicInfo(pool.getId());
	 *           fillGraphicInfo(poolNode, poolInfo, true); Process process =
	 *           pojoModel.getProcess(pool.getId()); if ((process != null) &&
	 *           (CollectionUtils.isNotEmpty(process.getLanes()))) { ArrayNode
	 *           laneArray = this.objectMapper.createArrayNode(); for (Lane lane
	 *           : process.getLanes()) { ObjectNode laneNode =
	 *           this.objectMapper.createObjectNode(); laneNode.put("id",
	 *           lane.getId()); laneNode.put("name", lane.getName());
	 *           fillGraphicInfo(laneNode,
	 *           pojoModel.getGraphicInfo(lane.getId()), true);
	 *           laneArray.add(laneNode); } poolNode.put("lanes", laneArray); }
	 *           poolArray.add(poolNode);
	 * 
	 *           double rightX = poolInfo.getX() + poolInfo.getWidth(); double
	 *           bottomY = poolInfo.getY() + poolInfo.getHeight(); double
	 *           middleX = poolInfo.getX() + poolInfo.getWidth() / 2.0D; if
	 *           ((firstElement) || (middleX < diagramInfo.getX())) {
	 *           diagramInfo.setX(middleX); } if ((firstElement) ||
	 *           (poolInfo.getY() < diagramInfo.getY())) {
	 *           diagramInfo.setY(poolInfo.getY()); } if (rightX >
	 *           diagramInfo.getWidth()) { diagramInfo.setWidth(rightX); } if
	 *           (bottomY > diagramInfo.getHeight()) {
	 *           diagramInfo.setHeight(bottomY); } firstElement = false; }
	 *           displayNode.put("pools", poolArray); } else {
	 *           diagramInfo.setX(9999.0D); diagramInfo.setY(1000.0D); } for
	 *           (Process process : pojoModel.getProcesses()) {
	 *           processElements(process.getFlowElements(), pojoModel,
	 *           elementArray, flowArray, diagramInfo, completedElements,
	 *           currentElements); processArtifacts(process.getArtifacts(),
	 *           pojoModel, elementArray, flowArray, diagramInfo); }
	 *           displayNode.put("elements", elementArray);
	 *           displayNode.put("flows", flowArray);
	 * 
	 *           displayNode.put("diagramBeginX", diagramInfo.getX());
	 *           displayNode.put("diagramBeginY", diagramInfo.getY());
	 *           displayNode.put("diagramWidth", diagramInfo.getWidth());
	 *           displayNode.put("diagramHeight", diagramInfo.getHeight());
	 *           return displayNode; }
	 * 
	 * 
	 *           protected void processElements(Collection<FlowElement>
	 *           elementList, BpmnModel model, ArrayNode elementArray, ArrayNode
	 *           flowArray, GraphicInfo diagramInfo, Set<String>
	 *           completedElements, Set<String> currentElements) { for
	 *           (FlowElement element : elementList) { ObjectNode elementNode =
	 *           this.objectMapper.createObjectNode(); if (completedElements !=
	 *           null) { elementNode.put("completed",
	 *           completedElements.contains(element.getId())); } if
	 *           (currentElements != null) { elementNode.put("current",
	 *           currentElements.contains(element.getId())); } if ((element
	 *           instanceof SequenceFlow)) { SequenceFlow flow =
	 *           (SequenceFlow)element; elementNode.put("id", flow.getId());
	 *           elementNode.put("type", "sequenceFlow");
	 *           elementNode.put("sourceRef", flow.getSourceRef());
	 *           elementNode.put("targetRef", flow.getTargetRef());
	 *           List<GraphicInfo> flowInfo =
	 *           model.getFlowLocationGraphicInfo(flow.getId()); if
	 *           (CollectionUtils.isNotEmpty(flowInfo)) { ArrayNode
	 *           waypointArray = this.objectMapper.createArrayNode(); for
	 *           (GraphicInfo graphicInfo : flowInfo) { ObjectNode pointNode =
	 *           this.objectMapper.createObjectNode();
	 *           fillGraphicInfo(pointNode, graphicInfo, false);
	 *           waypointArray.add(pointNode); fillDiagramInfo(graphicInfo,
	 *           diagramInfo); } elementNode.put("waypoints", waypointArray);
	 * 
	 *           String className = element.getClass().getSimpleName(); if
	 *           (this.propertyMappers.containsKey(className)) {
	 *           elementNode.put("properties",
	 *           ((InfoMapper)this.propertyMappers.get(className)).map(element));
	 *           } flowArray.add(elementNode); } } else { elementNode.put("id",
	 *           element.getId()); elementNode.put("name", element.getName());
	 * 
	 *           GraphicInfo graphicInfo =
	 *           model.getGraphicInfo(element.getId()); if (graphicInfo != null)
	 *           { fillGraphicInfo(elementNode, graphicInfo, true);
	 *           fillDiagramInfo(graphicInfo, diagramInfo); } String className =
	 *           element.getClass().getSimpleName(); elementNode.put("type",
	 *           className); fillEventTypes(className, element, elementNode); if
	 *           ((element instanceof ServiceTask)) { ServiceTask serviceTask =
	 *           (ServiceTask)element; if ("mail".equals(serviceTask.getType()))
	 *           { elementNode.put("taskType", "mail"); } else if
	 *           ("camel".equals(serviceTask.getType())) {
	 *           elementNode.put("taskType", "camel"); } else if
	 *           ("mule".equals(serviceTask.getType())) {
	 *           elementNode.put("taskType", "mule"); } } if
	 *           (this.propertyMappers.containsKey(className)) {
	 *           elementNode.put("properties",
	 *           ((InfoMapper)this.propertyMappers.get(className)).map(element));
	 *           } elementArray.add(elementNode); if ((element instanceof
	 *           SubProcess)) { SubProcess subProcess = (SubProcess)element;
	 *           processElements(subProcess.getFlowElements(), model,
	 *           elementArray, flowArray, diagramInfo, completedElements,
	 *           currentElements); processArtifacts(subProcess.getArtifacts(),
	 *           model, elementArray, flowArray, diagramInfo); } } } }
	 * 
	 *           protected void processArtifacts(Collection<Artifact>
	 *           artifactList, BpmnModel model, ArrayNode elementArray,
	 *           ArrayNode flowArray, GraphicInfo diagramInfo) { for (Artifact
	 *           artifact : artifactList) { if ((artifact instanceof
	 *           Association)) { ObjectNode elementNode =
	 *           this.objectMapper.createObjectNode(); Association flow =
	 *           (Association)artifact; elementNode.put("id", flow.getId());
	 *           elementNode.put("type", "association");
	 *           elementNode.put("sourceRef", flow.getSourceRef());
	 *           elementNode.put("targetRef", flow.getTargetRef());
	 *           fillWaypoints(flow.getId(), model, elementNode, diagramInfo);
	 *           flowArray.add(elementNode); } else { ObjectNode elementNode =
	 *           this.objectMapper.createObjectNode(); elementNode.put("id",
	 *           artifact.getId()); if ((artifact instanceof TextAnnotation)) {
	 *           TextAnnotation annotation = (TextAnnotation)artifact;
	 *           elementNode.put("text", annotation.getText()); } GraphicInfo
	 *           graphicInfo = model.getGraphicInfo(artifact.getId()); if
	 *           (graphicInfo != null) { fillGraphicInfo(elementNode,
	 *           graphicInfo, true); fillDiagramInfo(graphicInfo, diagramInfo);
	 *           } String className = artifact.getClass().getSimpleName();
	 *           elementNode.put("type", className);
	 * 
	 *           elementArray.add(elementNode); } } }
	 * 
	 *           protected void fillWaypoints(String id, BpmnModel model,
	 *           ObjectNode elementNode, GraphicInfo diagramInfo) {
	 *           List<GraphicInfo> flowInfo =
	 *           model.getFlowLocationGraphicInfo(id); ArrayNode waypointArray =
	 *           this.objectMapper.createArrayNode(); for (GraphicInfo
	 *           graphicInfo : flowInfo) { ObjectNode pointNode =
	 *           this.objectMapper.createObjectNode();
	 *           fillGraphicInfo(pointNode, graphicInfo, false);
	 *           waypointArray.add(pointNode); fillDiagramInfo(graphicInfo,
	 *           diagramInfo); } elementNode.put("waypoints", waypointArray); }
	 * 
	 *           protected void fillEventTypes(String className, FlowElement
	 *           element, ObjectNode elementNode) { if
	 *           (this.eventElementTypes.contains(className)) { Event event =
	 *           (Event)element; if
	 *           (CollectionUtils.isNotEmpty(event.getEventDefinitions())) {
	 *           EventDefinition eventDef =
	 *           (EventDefinition)event.getEventDefinitions().get(0); ObjectNode
	 *           eventNode = this.objectMapper.createObjectNode(); if ((eventDef
	 *           instanceof TimerEventDefinition)) { TimerEventDefinition
	 *           timerDef = (TimerEventDefinition)eventDef;
	 *           eventNode.put("type", "timer"); if
	 *           (StringUtils.isNotEmpty(timerDef.getTimeCycle())) {
	 *           eventNode.put("timeCycle", timerDef.getTimeCycle()); } if
	 *           (StringUtils.isNotEmpty(timerDef.getTimeDate())) {
	 *           eventNode.put("timeDate", timerDef.getTimeDate()); } if
	 *           (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
	 *           eventNode.put("timeDuration", timerDef.getTimeDuration()); } }
	 *           else if ((eventDef instanceof ErrorEventDefinition)) {
	 *           ErrorEventDefinition errorDef = (ErrorEventDefinition)eventDef;
	 *           eventNode.put("type", "error"); if
	 *           (StringUtils.isNotEmpty(errorDef.getErrorCode())) {
	 *           eventNode.put("errorCode", errorDef.getErrorCode()); } } else
	 *           if ((eventDef instanceof SignalEventDefinition)) {
	 *           SignalEventDefinition signalDef =
	 *           (SignalEventDefinition)eventDef; eventNode.put("type",
	 *           "signal"); if
	 *           (StringUtils.isNotEmpty(signalDef.getSignalRef())) {
	 *           eventNode.put("signalRef", signalDef.getSignalRef()); } } else
	 *           if ((eventDef instanceof MessageEventDefinition)) {
	 *           MessageEventDefinition messageDef =
	 *           (MessageEventDefinition)eventDef; eventNode.put("type",
	 *           "message"); if
	 *           (StringUtils.isNotEmpty(messageDef.getMessageRef())) {
	 *           eventNode.put("messageRef", messageDef.getMessageRef()); } }
	 *           elementNode.put("eventDefinition", eventNode); } } }
	 * 
	 *           protected void fillGraphicInfo(ObjectNode elementNode,
	 *           GraphicInfo graphicInfo, boolean includeWidthAndHeight) {
	 *           commonFillGraphicInfo(elementNode, graphicInfo.getX(),
	 *           graphicInfo.getY(), graphicInfo.getWidth(),
	 *           graphicInfo.getHeight(), includeWidthAndHeight); }
	 * 
	 *           protected void commonFillGraphicInfo(ObjectNode elementNode,
	 *           double x, double y, double width, double height, boolean
	 *           includeWidthAndHeight) { elementNode.put("x", x);
	 *           elementNode.put("y", y); if (includeWidthAndHeight) {
	 *           elementNode.put("width", width); elementNode.put("height",
	 *           height); } }
	 * 
	 *           protected void fillDiagramInfo(GraphicInfo graphicInfo,
	 *           GraphicInfo diagramInfo) { double rightX = graphicInfo.getX() +
	 *           graphicInfo.getWidth(); double bottomY = graphicInfo.getY() +
	 *           graphicInfo.getHeight(); double middleX = graphicInfo.getX() +
	 *           graphicInfo.getWidth() / 2.0D; if (middleX <
	 *           diagramInfo.getX()) { diagramInfo.setX(middleX); } if
	 *           (graphicInfo.getY() < diagramInfo.getY()) {
	 *           diagramInfo.setY(graphicInfo.getY()); } if (rightX >
	 *           diagramInfo.getWidth()) { diagramInfo.setWidth(rightX); } if
	 *           (bottomY > diagramInfo.getHeight()) {
	 *           diagramInfo.setHeight(bottomY); } }
	 * 
	 *           protected List<String> gatherCompletedFlows(Set<String>
	 *           completedActivityInstances, Set<String>
	 *           currentActivityinstances, BpmnModel pojoModel) { int index;
	 *           List<String> completedFlows = new ArrayList(); List<String>
	 *           activities = new ArrayList(completedActivityInstances);
	 *           activities.addAll(currentActivityinstances); for (FlowElement
	 *           activity : pojoModel.getMainProcess().getFlowElements()) { if
	 *           ((activity instanceof FlowNode)) { index =
	 *           activities.indexOf(activity.getId()); if ((index >= 0) &&
	 *           (index + 1 < activities.size())) { List<SequenceFlow>
	 *           outgoingFlows = ((FlowNode)activity).getOutgoingFlows(); for
	 *           (SequenceFlow flow : outgoingFlows) { String destinationFlowId
	 *           = flow.getTargetRef(); if
	 *           (destinationFlowId.equals(activities.get(index + 1))) {
	 *           completedFlows.add(flow.getId()); } } } } }
	 * 
	 *           return completedFlows; }
	 */

	public void addRemind(String title,String content,String recieverAccount,String type){
		 Remind record=new Remind();
		 record.setType(type);
		 record.setContent(content);
		 record.setTitle(title);
		 record.setSenderName(UserSecurityManager.getAttribute("name").toString());
		 record.setSenderId(Long.parseLong(UserSecurityManager.getAttribute("ID").toString()));
		 record.setSendTime(new Date());
		 record.setReceiverId(this.employeeDAO.getEmployeeByAccount(recieverAccount).getId());
		 this.remindDAO.insert( record);
	}

	@Override
	public EUDataGridResult queryHisTask(String userId, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		List<TodoDTO> todoDTOList = new ArrayList();
		String processDefinationKey = null;
		String businessKey = null;
		TodoDTO todoDTO = null;
		EUDataGridResult result = new EUDataGridResult();
		List<HistoricTaskInstance> tasks = null;

		if (page == -1 || rows == -1) {
			page = 1;
			rows = 10;
		}
		
		// 根据当前人的ID查询
		/*HistoricTaskInstanceQuery hisTaskQuery = this.historyService.
				createHistoricTaskInstanceQuery().taskAssignee(userId).finished().orderByTaskCreateTime().desc();
		tasks = hisTaskQuery.listPage((page - 1) * rows, rows);
		*/
		
		Map map=new HashMap();
		
		
		map.put("assignee", userId);
		EUDataGridResult list=this.querySingleHiTask(map, page, rows);
		tasks=(List<HistoricTaskInstance>) list.getRows();
		
		// 根据流程的业务ID查询实体并关联
		for (HistoricTaskInstance task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			HistoricProcessInstance hpi = this.historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).includeProcessVariables().singleResult();
			if (hpi == null) {
				continue;
			}
			String processDefinationId = hpi.getProcessDefinitionId();
			processDefinationKey = hpi.getProcessDefinitionKey();
			businessKey = hpi.getBusinessKey();

			BigDecimal amount = (BigDecimal) hpi.getProcessVariables()
					.get(WorkflowConstant.WORKFLOW_APPROVE_AMOUNT_KEY);

			String approveUrl = task.getFormKey();
			String processDefinationName = processEngine.getRepositoryService()
					.getProcessDefinition(processDefinationId).getName();

			String title = "【"+processDefinationName+"】："+hpi.getProcessVariables().get(WorkflowConstant.WORKFLOW_INSTANCE_TITLE_KEY);
			
			todoDTO = new TodoDTO();
			todoDTO.setTitle(title);
			todoDTO.setApproveUrl(approveUrl);
			todoDTO.setBusinessDataId(businessKey);
			todoDTO.setCreateTime(task.getCreateTime());
			todoDTO.setTaskId(task.getId());
			todoDTO.setProcessInstanceId(processInstanceId);
			todoDTO.setProcessDefinationKey(processDefinationId);
			todoDTO.setProcessDefinationName(processDefinationName);
			todoDTO.setTaskName(task.getName());
			todoDTO.setType(task.getCategory());
			todoDTO.setCompleteTime(task.getEndTime());
			todoDTOList.add(todoDTO);
		}
		result.setTotal(list.getTotal());
		result.setRows(todoDTOList);
		return result;
	}

	@Override
	public EUDataGridResult querySingleHiTask(Map map,Integer page,Integer rows){
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<HistoricTaskInstance> list = this.activitiExtendDao.querySingleHiTask(map);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<HistoricTaskInstance> pageInfo = new PageInfo<HistoricTaskInstance>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public EUDataGridResult queryUserTask(ParamsDto dto,Integer page,Integer rows){
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		List<Task> list = this.activitiExtendDao.queryUserTask(dto);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<Task> pageInfo = new PageInfo<Task>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	
}
