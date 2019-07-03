package com.mszq.platform.app.config.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Config;

public interface IConfigService {
	public EUDataGridResult getList(Map<String,String> param,int page, int pageSize) ;
	public Config getConfigByCode(String code);
	public String getConfigValueByCode(String code);
	public int create(Config config);
	public int update(Config config);
	public int deleteBatch(String[] ids);
	public List<Config> queryAll();
	/**
	 * 通过code值更新value信息
	 * @param config
	 * @return
	 */
	public int updateValueBycode(Config config);
}
