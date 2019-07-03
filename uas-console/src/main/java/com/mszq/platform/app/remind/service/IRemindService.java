package com.mszq.platform.app.remind.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Agenda;
import com.mszq.platform.entity.Remind;
import com.mszq.platform.entity.RemindRule;

public interface IRemindService {

	public void insertRemind(Agenda calendar) throws ParseException;
	public void insertRemindBatch(List<Remind> remindList);
	public EUDataGridResult getRemindList(Map<String, Object> params, Integer page, Integer rows);
	
	public EUDataGridResult queryRemindRule(Map<String, Object> params, Integer page, Integer rows);
	
	public List<RemindRule> queryRemindRule(Map<String, Object> params);
	
	EUDataGridResult getRemindWorkflowTitleList(Map<String, Object> params,Integer page, Integer rows);
	EUDataGridResult getTitleList(Map<String, Object> params, Integer page, Integer rows);

}
