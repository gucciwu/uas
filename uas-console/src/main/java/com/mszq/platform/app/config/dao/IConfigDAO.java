package com.mszq.platform.app.config.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.Employee;

@MapperScan
public interface IConfigDAO {
	public List<Config> queryAll(Map<String,String> params);
	public Config getConfigByCode(@Param("code") String code);
	public String getConfigValueByCode(@Param("code") String code);
	public int create(Config config);
	public int update(Config config);
	public int deleteBatch(String[] ids);
	/**
	 * 通过code值更新value信息
	 * @param config
	 * @return
	 */
	public int updateValueBycode(Config config);
}
