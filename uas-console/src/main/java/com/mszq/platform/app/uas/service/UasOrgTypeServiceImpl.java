package com.mszq.platform.app.uas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.uas.dao.IUasOrgTypeDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasOrgType;

@Service
public class UasOrgTypeServiceImpl implements IUasOrgTypeService {
    @Autowired
    IUasOrgTypeDAO uasOrgTypeDao;
	@Override
	public EUDataGridResult queryAll(int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasOrgType> list = uasOrgTypeDao.queryAll();
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasOrgType> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public List<UasOrgType> query() {
		return uasOrgTypeDao.queryAll();
	}
	
	@Override
	public int update(UasOrgType record) {
		return uasOrgTypeDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	@Transactional
	public int deleteBatch(String[] ids) {
		int ret = 0;
		for (String id : ids) {
			Short key = Short.parseShort(id);
			ret = ret + uasOrgTypeDao.deleteByPrimaryKey(key);
		}
		return ret;
	}

	@Override
	@Transactional
	public int insert(UasOrgType record) {
		return uasOrgTypeDao.insertSelective(record);
	}
}
