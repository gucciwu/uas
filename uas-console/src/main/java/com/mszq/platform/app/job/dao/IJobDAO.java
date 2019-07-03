package com.mszq.platform.app.job.dao;

import java.util.List;
import java.util.Map;

import com.mszq.platform.entity.Job;

public interface IJobDAO {
	    int deleteByPrimaryKey(Integer id);
	    int insert(Job record);
	    int insertSelective(Job record);
	    Job selectByPrimaryKey(Integer idInteger);
	    int updateByPrimaryKeySelective(Job record);
	    int updateByPrimaryKey(Job record);
		List<Job> queryAll(Map<String, Object> params);
		List<Job> selectAll();
		String selectJobNameById(Integer id);
}
