package com.mszq.platform.app.remind.dao;

import java.util.List;
import java.util.Map;

import com.mszq.platform.entity.Remind;
import com.mszq.platform.entity.RemindRule;

public interface IRemindDAO {
    int deleteByPrimaryKey(Long id);
    int insert(Remind record);
    int insertSelective(Remind record);
    public void insertRemindBatch(List<Remind> remindList);
    Remind selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Remind record);
    int updateByPrimaryKey(Remind record);
	List<Remind> queryAllTitle();
	List<Remind> queryAll(Map<String, Object> params);
	int selectBySenderId(Long id);
	List<Remind> queryUserTitle(Map<String, Object> params);
	List<Remind> queryUser(Map<String, Object> params);
	
	
	/**
	 * 查询提醒规则
	 * @return
	 */
	List<RemindRule> queryRemindRule();
	
	

}