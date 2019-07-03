package com.mszq.platform.app.log.dao;

import java.util.List;
import java.util.Map;

import com.mszq.platform.entity.Log;

public interface ILogDAO {
	public List<Log> queryAll(Map<String,String> params);

}
