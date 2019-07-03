package com.mszq.platform.app.uas.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.uas.dao.IUasAppDAO;
import com.mszq.platform.app.uas.dto.UasAppDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasApp;

@Service
public class UasAppServiceImpl implements IUasAppService {
	@Autowired
	IUasAppDAO uasAppDAO;

	@Override
	public EUDataGridResult selectList(Map<String, Object> condition, int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasAppDto> list = uasAppDAO.selectList(condition);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasAppDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int insert(UasApp record) {
		return uasAppDAO.insertSelective(record);
	}

	@Override
	public int update(UasApp record) {
		return uasAppDAO.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteBatch(String[] ids) {
		int ret = 0;
		for (String id : ids) {
			Long key = Long.parseLong(id);
			ret = ret + uasAppDAO.deleteByPrimaryKey(key);
		}
		return ret;
	}
	
	@Override
	public List<Tree> getAppTree() {
		List<Tree> root = new  ArrayList<Tree>();
		List<UasAppDto> list = uasAppDAO.selectList(null);
		
		if(list != null){
			
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i)!=null){
					Tree tree = new Tree();
					tree.setId(list.get(i).getId());
					tree.setText(list.get(i).getName());
					tree.setSort(i);
					root.add(tree);
				}
			}
			Collections.sort(root);//升序排列
		}
		return root;
	}

}
