package com.mszq.platform.app.workflow.dao;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.workflow.dto.ParamsDto;
@MapperScan
public interface IActivitiExtendDao {
	List<HistoricTaskInstance> querySingleHiTask(Map map);

	List<Task> queryUserTask(ParamsDto dto);
}
