package com.mszq.platform.app.uas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.uas.dao.IUasAppAccountDAO;
import com.mszq.platform.app.uas.dto.UasAppAccountDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasAppAccount;

@Service
public class UasAppAccountServiceImpl implements IUasAppAccountService {
	@Autowired
	IUasAppAccountDAO uasAppAccountDAO;
	
	@Override
	public EUDataGridResult selectList(Map<String, Object> condition, int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasAppAccountDto> list = uasAppAccountDAO.selectList(condition);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasAppAccountDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int insert(UasAppAccount record) {
		return uasAppAccountDAO.insertSelective(record);
	}

	@Override
	public int update(UasAppAccount record) {
		return uasAppAccountDAO.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteBatch(String[] ids) {
		int ret = 0;
		for (String id : ids) {
			Long key = Long.parseLong(id);
			ret = ret + uasAppAccountDAO.deleteByPrimaryKey(key);
		}
		return ret;
	}

}
