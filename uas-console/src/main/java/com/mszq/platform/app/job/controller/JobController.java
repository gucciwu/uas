package com.mszq.platform.app.job.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.mszq.platform.app.job.service.IJobService;
import com.mszq.platform.app.job.service.IScheduleJobService;
import com.mszq.platform.app.job.service.ScheduledJob;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Job;
import com.mszq.platform.entity.JobLog;
import com.mszq.platform.entity.ScheduleEntity;

@Controller
@RequestMapping(value = "/job")
public class JobController {
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
//	@Autowired
//	IJobService jobService;

	@Autowired
	private IScheduleJobService scheduleJobService;

//	@RequestMapping(value = "/list")
//	@ResponseBody
//	public EUDataGridResult getJobList(String name, Integer page, Integer rows) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		if (null != name && !"".equalsIgnoreCase(name)) {
//			params.put("name", "%" + name + "%");
//		}
//		return jobService.getJobList(params, page, rows);
//	}
//
//	@RequestMapping(value = "/create", method = RequestMethod.POST)
//	@ResponseBody
//	public CustomResult create(Job job) throws ClassNotFoundException {
//		CustomResult result = null;
//		int count = jobService.insert(job);
//		if (count == 1) {
//			result = CustomResult.ok("操作成功");
//		} else if (count == -1) {
//			result = CustomResult.error("时间表达式输入有误！");
//		} else if (count == -2) {
//			result = CustomResult.error("任务class表达式输入有误！");
//		} else {
//			result = CustomResult.error("新增异常！");
//		}
//		return result;
//	}
//
//	@RequestMapping(value = "/update")
//	@ResponseBody
//	public CustomResult update(Job job) throws SchedulerException {
//		CustomResult result = null;
//		int count = jobService.updateByPrimaryKey(job);
//		if (count == 1) {
//			result = CustomResult.ok("操作成功");
//		} else if (count == -1) {
//			result = CustomResult.error("任务已启用，不能修改");
//		} else if (count == -2) {
//			result = CustomResult.error("时间表达式输入有误！");
//		} else if (count == -3) {
//			result = CustomResult.error("任务class表达式输入有误！");
//		} else {
//			result = CustomResult.error("修改异常！");
//		}
//		return result;
//	}
//
//	@RequestMapping(value = "/deleteBatch")
//	@ResponseBody
//	public CustomResult delete(Integer id) {
//		int result = jobService.deleteByPrimaryKey(id);
//		if (result > 0) {
//			return CustomResult.ok("删除成功！");
//		} else if (result == 0) {
//			return CustomResult.error("数据不存在！");
//		} else if (result == -1) {
//			return CustomResult.error("任务已启用，不能删除");
//		} else {
//			return CustomResult.error("删除异常！");
//		}
//	}
//
//	@RequestMapping(value = "/start")
//	@ResponseBody
//	public CustomResult start(Integer id) throws ClassNotFoundException {
//		int count = jobService.startJobById(id);
//		if (count == 1) {
//			return CustomResult.ok("任务启用成功！");
//		} else if (count == -1) {
//			return CustomResult.error("任务已启用，不需重新启用");
//		} else {
//			return CustomResult.error("任务启用异常！");
//		}
//	}
//
//	@RequestMapping(value = "/stop")
//	@ResponseBody
//	public CustomResult stop(Integer id) throws SchedulerException {
//		int count = jobService.StopJobById(id);
//		if (count == 1) {
//			return CustomResult.ok("任务停用成功！");
//		} else if (count == -1) {
//			return CustomResult.error("任务已停用，不需重新停用");
//		} else {
//			return CustomResult.error("任务停用异常！");
//		}
//	}

	/**
	 * 任务列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jobList")
	public EUDataGridResult getAllJobs() {
		List<ScheduleEntity> scheduleEntities = scheduleJobService.getAllScheduleJob();
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(scheduleEntities);
		
		result.setTotal(scheduleEntities.size());
		return result;
	}


	@ResponseBody
	@RequestMapping("/createJob")
	public CustomResult create(ScheduleEntity scheduleEntity) {
		CustomResult result = null;
		// 判断表达式
		boolean f = CronExpression.isValidExpression(scheduleEntity.getCronExpression());
		if (!f) {
			return CustomResult.error("cron表达式有误，不能被解析！");
		}
		try {
			scheduleEntity.setStatus("1");
			scheduleJobService.add(scheduleEntity);
			result = CustomResult.ok("操作成功");
		} catch (ClassNotFoundException e) {
			logger.error("",e);
			result = CustomResult.error("找不到指定的类！");
		} catch (SchedulerException e) {
			logger.error("",e);
			if (e.getMessage().contains("because one already exists with this identification")) {
				result = CustomResult.error("任务组中存在同样的任务名！");
			} else {
				result = CustomResult.error("未知原因,添加任务失败！");
			}
		} catch (Exception e) {
			logger.error("",e);
			result = CustomResult.error("后台异常！");
		}
		return result;
	}

	/**
	 * 暂停任务
	 */

	@ResponseBody
	@RequestMapping("/stopJob")
	public Object stop(String jobName, String jobGroup) {
		CustomResult result = null;
		try {
			scheduleJobService.stopJob(jobName, jobGroup);
			result = CustomResult.ok("停用成功!");
		} catch (SchedulerException e) {
			logger.error("",e);
			result = CustomResult.error("后台异常！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public CustomResult delete(String jobName, String jobGroup) {
		CustomResult result = null;
		try {
			scheduleJobService.delJob(jobName, jobGroup);
			result = CustomResult.ok("删除成功!");
			
		} catch (SchedulerException e) {
			logger.error("",e);
			result = CustomResult.error("后台异常！");
		}
		return result;
	}

	/**
	 * 修改表达式
	 */

	@ResponseBody
	@RequestMapping("/update")
	public CustomResult update(ScheduleEntity scheduleEntity) {
		CustomResult result = null;
		// 验证cron表达式
		boolean f = CronExpression.isValidExpression(scheduleEntity.getCronExpression());
		if (!f) {
			return CustomResult.error("cron表达式有误，不能被解析！");
		}
		try {
			scheduleJobService.modifyTrigger(scheduleEntity.getJobName(), scheduleEntity.getJobGroup(),
					scheduleEntity.getCronExpression());
			result = CustomResult.ok("修改成功!");
		} catch (SchedulerException e) {
			logger.error("",e);
			result = CustomResult.error("后台异常！");
		}
		return result;
	}

	/**
	 * 立即运行一次
	 */

	@ResponseBody
	@RequestMapping("/startNow")
	public Object stratNow(String jobName, String jobGroup) {
		CustomResult result = null;
		try {
			scheduleJobService.startNowJob(jobName, jobGroup);
			result = CustomResult.ok("操作成功!");
		} catch (SchedulerException e) {
			logger.error("",e);
			result = CustomResult.error("后台异常！");
		}
		return result;
	}

	/**
	 * 恢复
	 */

	@ResponseBody
	@RequestMapping("/resume")
	public Object resume(String jobName, String jobGroup) {
		CustomResult result = null;
		try {
			scheduleJobService.restartJob(jobName, jobGroup);
			result = CustomResult.ok("启用成功!");
		} catch (SchedulerException e) {
			logger.error("",e);
			result = CustomResult.error("后台异常！");
		}
		return result;
	}

	/**
	 * 获取所有trigger
	 */
	public void getTriggers(HttpServletRequest request) {
		List<ScheduleEntity> scheduleEntities = scheduleJobService.getTriggersInfo();
		request.setAttribute("triggers", scheduleEntities);
	}
}
