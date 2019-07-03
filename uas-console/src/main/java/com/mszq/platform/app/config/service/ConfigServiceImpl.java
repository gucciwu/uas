package com.mszq.platform.app.config.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.config.dao.IConfigDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Config;
import com.mszq.platform.util.GlobalConfig;

import net.sf.ehcache.search.aggregator.Count;

@Service
public class ConfigServiceImpl implements IConfigService{
	@Resource
	IConfigDAO configDAO;

	@Override
	public EUDataGridResult getList(Map<String,String> params,int page, int pageSize) {
		// 分页处理
				PageHelper.startPage(page, pageSize);
				List<Config> list = configDAO.queryAll(params);
				// 取记录总条数
				PageInfo<Config> pageInfo = new PageInfo<Config>(list);
				// 创建一个返回值对象
				EUDataGridResult result = new EUDataGridResult();
				result.setRows(list);
				
				result.setTotal(pageInfo.getTotal());
				return result;
	}

	@Override
	public List<Config> queryAll() {
		return configDAO.queryAll(new HashMap<String,String>());
	}
	
	@Override
	public Config getConfigByCode(String code) {
		return configDAO.getConfigByCode(code);
	}

	@Override
	public int create(Config config) {
		config.setStatus(1);
		config.setCreateTime(new Date());
		config.setUpdateTime(new Date());
		//TODO 人员的id要设置一下，等entity中改完了再说
		config.setCreatorID(1);
		
		int count = configDAO.create(config);
		GlobalConfig.loadAllConfig();
		return count;
	}

	@Override
	public int update(Config config) {
		config.setUpdateTime(new Date());
		int count =configDAO.update(config);
		GlobalConfig.loadAllConfig();
		return count;
	}

	@Override
	public int deleteBatch(String[] ids) {
		int count = configDAO.deleteBatch(ids);
		GlobalConfig.loadAllConfig();
		return count;
	}

	@Override
	public String getConfigValueByCode(String code) {
		// TODO Auto-generated method stub
		return configDAO.getConfigValueByCode(code);
	}
	
	/**
	 * 通过code值更新value信息
	 * @param config
	 * @return
	 */
	@Override
	public int updateValueBycode(Config config){
		return configDAO.updateValueBycode(config);
	}
}
