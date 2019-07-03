package com.mszq.platform.app.workflow.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.mszq.platform.app.config.service.IConfigService;
import com.mszq.platform.app.workflow.dto.ParamsDto;
import com.mszq.platform.app.workflow.dto.TodoDTO;
import com.mszq.platform.app.workflow.dto.UserDto;
import com.mszq.platform.app.workflow.service.IWorkflowService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.WorkflowBusinessData;
import com.mszq.platform.entity.WorkflowTrace;
import com.mszq.platform.util.ComboOption;
import com.mszq.platform.util.Variable;


@Controller
@RequestMapping(value="/workflow")
public class WorkflowController {
	private final Logger logger=LoggerFactory.getLogger(WorkflowController.class);
	@Resource
	IConfigService configService;
	@Resource
	IWorkflowService workflowService;
	
	
	/**
	 * 查询待办事项(含已签收与未签收的)
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/queryTodo",method=RequestMethod.GET)
	@ResponseBody
	public EUDataGridResult queryTodo(Integer page, Integer rows, ParamsDto dto,HttpServletResponse response){
		if(page==null) page=1;
		if(rows==null) rows=5;
		return workflowService.queryTodo(dto,page, rows);		
	}
	
	
	/**
	 * 查询已办事项
	 * @return
	 */
	@RequestMapping(value="/queryHisTask",method=RequestMethod.GET)
	@ResponseBody
	public EUDataGridResult queryHisTask(Integer page, Integer rows, HttpServletResponse response){
		if(page==null) page=1;
		if(rows==null) rows=5;
		return workflowService.queryHisTask(UserSecurityManager.getAttribute("account").toString(),page, rows);		
	}
	
	
	
	
	@RequestMapping(value = "/comboActivity")
	@ResponseBody
	public List<ComboOption>  comboActivity(HttpServletRequest req) {
		String taskId=req.getParameter("taskId");
		
		List<ActivityImpl> acts=this.workflowService.getBeforeActivities(taskId);
		
		List<ComboOption> result=new ArrayList<ComboOption>();
		
		for(ActivityImpl temp:acts){
			ComboOption com=new ComboOption();
			com.setText(temp.getProperty("name").toString());
			com.setValue(temp.getId());
			result.add(com);
		}
		Collections.reverse(result);//
		return result;
	}
	
	@RequestMapping(value="/queryWorkflowUsers")
	@ResponseBody
	public EUDataGridResult queryWorkflowUsers(Integer page, Integer rows, UserDto userDto,HttpServletResponse response){
		if(page==null) page=1;
		if(rows==null) rows=10;
		return workflowService.queryWorkflowUsers(page, rows,userDto);		
	}
	
	
	
	/**
	 * 一条业务数据可以多次启动流程，但是在启动新流程之前，旧的流程必须结束
	 * 这里返回的就是多条流程实例，这些流程实例按照创建时间进行排序，最后一条就是最新的流程
	 * @param processInstanceId
	 * @param businessDataIds
	 * @param businessTableName
	 * @return
	 */
	@RequestMapping(value="/queryWorkflowBusinessData",method=RequestMethod.GET)
	@ResponseBody
	public List<WorkflowBusinessData> queryWorkflowBusinessData(String processInstanceId,String[] businessDataIds,String businessTableName){
		//2.查询当前记录对应的流程实例
		Map<String,Object> params=new HashMap();
		 if(null!=processInstanceId&&!"".equalsIgnoreCase(processInstanceId)) params.put("processInstanceId",processInstanceId );
		 if(null!=businessDataIds) params.put("businessDataIds",businessDataIds );
		 if(null!=businessTableName&&!"".equalsIgnoreCase(businessTableName)) params.put("businessTableName",businessTableName );
		 return workflowService.queryWorkflowBusinessData(params);
	}
	
	
	
    /**
     * 返回流程定义文件(image或者xml)
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance")
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
    	InputStream resourceAsStream= workflowService.loadByProcessInstance(resourceType,processInstanceId);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    
    /**
     * 输出跟踪流程信息（流程图）
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/process/trace/image")
    @ResponseBody
    public List<Map<String, Object>> traceImage(@RequestParam("pid") String processInstanceId) throws Exception {
        List<Map<String, Object>> activityInfos = workflowService.traceImage(processInstanceId);
        return activityInfos;
    }
    
    /**
     * 输出跟踪流程信息（log）
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/process/trace/info")
    @ResponseBody
    public EUDataGridResult traceInfo(HttpServletRequest req,Integer page, Integer rows) throws Exception {
    	String bid=req.getParameter("bid");
    	String pid=req.getParameter("pid");
    	String procDefKey=this.workflowService.getProDefKey(pid,"01");
        return workflowService.traceInfo(bid,procDefKey,page,rows);
    }
    
	/**
	 * 获取流程跟踪页面，6.0.0
	 * @param processInstanceId
	 * @return
	 */
    
    /*  
	@RequestMapping(value = "/tracking/{processInstanceId}", method = RequestMethod.GET)
	@ResponseBody
	public JsonNode tracking(@PathVariable String processInstanceId) {
		//TODO 6.0版本的跟踪功能在流程完成后无法运行，只好改为5.19的版本
		JsonNode jsonNode=null;
		jsonNode=workflowService.getModelJSON(processInstanceId);
		return jsonNode;
	}
	*/
	
	
	/**
     * 完成任务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/complete/{processInstanceId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String complete(@PathVariable("processInstanceId") String processInstanceId, Variable var) {
    	Map<String, Object> variables = var.getVariableMap();
        try {
            //1，先签收：claim
        	
        	//2.再完成
        	workflowService.complete(processInstanceId,variables);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{processInstanceId, var.getVariableMap(), e});
            return "error";
        }
    }
    
    
    @RequestMapping(value = "/jumpToEnd", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void jumpToEnd(String processDefinationKey,String businessDataId) {
    	workflowService.jumpToEnd(processDefinationKey, businessDataId);
    }
    
    
    
    
    //--------------------------------------------以下为暂时不用的方法-----------------------------------------------------------------
	@RequestMapping(value="/queryProcessInstanceId",method=RequestMethod.GET)
	@ResponseBody
	public String queryProcessInstanceId(String businessDataId,String businessTableName){
		return workflowService.queryProcessInstanceId(businessDataId,businessTableName);		
	}
	
	
}

