package com.mszq.platform.app.uas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.uas.dao.IUasRoleTypeDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasRoleType;

@Service
public class UasRoleTypeServiceImpl implements IUasRoleTypeService {
	@Autowired
	IUasRoleTypeDAO uasRoleTypeDao;

	@Override
	public EUDataGridResult queryAll(int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasRoleType> list = uasRoleTypeDao.queryAll();
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasRoleType> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public List<UasRoleType> query() {
		return uasRoleTypeDao.queryAll();
	}

	@Override
	@Transactional
	public int insert(UasRoleType record) {
		return uasRoleTypeDao.insertSelective(record);
	}

	@Override
	@Transactional
	public int update(UasRoleType record) {
		return uasRoleTypeDao.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
	public int deleteBatch(String[] ids) {
		int ret = 0;
		for (String id : ids) {
			Long key = Long.parseLong(id);
			ret = ret + uasRoleTypeDao.deleteByPrimaryKey(key);
		}
		return ret;
	}

}
