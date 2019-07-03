package com.mszq.platform.app.uas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.uas.dao.IUasOrgDAO;
import com.mszq.platform.app.uas.dto.UasOrgDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasOrg;

@Service
public class UasOrgServiceImpl implements IUasOrgService {
	@Autowired
	IUasOrgDAO uasOrgDAO;

	@Override
	public EUDataGridResult selectList(Map<String, Object> condition, int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasOrgDto> list = uasOrgDAO.selectList(condition);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasOrgDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public EUDataGridResult queryAll(Map<String, Object> condition) {
		// 分页处理
		List<UasOrgDto> list = uasOrgDAO.queryAll(condition);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasOrgDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	

	@Override
	public int insert(UasOrg record) {
		return uasOrgDAO.insertSelective(record);
	}

	@Override
	public int update(UasOrg record) {
		return uasOrgDAO.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteBatch(String[] ids) {
		int ret = 0;
		for (String id : ids) {
			Long key = Long.parseLong(id);
			UasOrgDto uasOrgDto = uasOrgDAO.selectByPrimaryKey(key);
			int r = deleteTree(0, uasOrgDto, "================================================");
			ret = ret + r;
			//ret = ret + uasOrgDAO.deleteByPrimaryKey(key);
		}
		return ret;
	}
	
	
	@Override
	public List<Tree> getOrgnizationTree(Map<String, Object> condition) {
		List<Tree> root = new  ArrayList<Tree>();
		List<UasOrgDto> list = uasOrgDAO.queryAll(condition);
		if(list != null){
			
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getParentOrgId() == null) {
					Tree tree = new Tree();
					tree.setId(list.get(i).getOrgId());
					tree.setText(list.get(i).getName());
					root.add(tree);
				}
			}
			root = constructTree(list, root);
		}
		return root;
	}
	
	private List<Tree> constructTree(List<UasOrgDto> nodes, List<Tree> root) {
		if (root != null) {
			for (int i = 0; i < root.size(); i++) {

				List<Tree> children = new ArrayList<Tree>();
				for (int j = 0; j < nodes.size(); j++) {
					//System.out.println(root.get(i).getId() + "s:" + nodes.get(j).getName()+" pid:"+nodes.get(j).getParentOrgId());
					
					if (root.get(i).getId() != null 
							&& nodes.get(j).getParentOrgId()!= null
							&& nodes.get(j).getParentOrgId().longValue() == root.get(i).getId().longValue()) {
						Tree tree = new Tree();
						tree.setId(nodes.get(j).getOrgId());
						tree.setText(nodes.get(j).getName());
						tree.setPid(root.get(i).getId());
						children.add(tree);
					}
				}
				constructTree(nodes, children);
				root.get(i).setChildren(children);
			}
		}
 		return root;
	}

	
	
	private int deleteTree(int ret, UasOrgDto uasOrg, String prefix) {
		Map<String, Object> condition =new HashMap<String, Object>();
		condition.put("parentOrgId", uasOrg.getOrgId());
		condition.put("orgType", uasOrg.getOrgType());
		
		List<UasOrgDto> children = uasOrgDAO.selectList(condition);
		for (UasOrgDto child : children) {
			int childNum = deleteTree(ret, child, prefix + "    ");
			ret = ret + childNum;
		}
		//System.out.println(prefix+uasOrg.getOrgId().longValue() + " " + uasOrg.getName());
		uasOrgDAO.deleteByPrimaryKey(uasOrg.getId());
		return ret+1;
	}


}
