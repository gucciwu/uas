package com.mszq.platform.app.orgnization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.orgnization.dao.IOrgnizationDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.Orgnization;
import com.mszq.platform.util.ComboOption;

@Service
public class OrgnizationServiceImpl implements IOrgnizationService{

	@Autowired
	private IOrgnizationDAO orgnizationDAO;
	
	private List<Orgnization> orgAll=new ArrayList<Orgnization>();
	private Orgnization org;
	
	@Override
	public EUDataGridResult queryByParams(Map<String,String> params,int page, int pageSize) {
		// 分页处理
		PageHelper.startPage(page, pageSize);
		List<Orgnization> list = orgnizationDAO.queryByParams(params);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<Orgnization> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		result.setTotal(list.size());
		return result;
	}
	
	@Override
	public EUDataGridResult queryAll() {
		// 分页处理
//		PageHelper.startPage(page, rows);
		List<Orgnization> list = orgnizationDAO.queryAll();
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
//		PageInfo<Orgnization> pageInfo = new PageInfo<>(list);
//		result.setTotal(pageInfo.getTotal());
		result.setTotal(list.size());
		return result;
	}
	
	public List<Long> getAllChildren(Long parentId){
		List<Long> result = new  ArrayList<Long>();
		List<Long> parentIds = new ArrayList<>();
		parentIds.add(parentId);
		result.add(parentId);
		List<Orgnization> allNodes = orgnizationDAO.queryAll();
		if(allNodes != null){
			result = findById(parentIds,allNodes, result);
		}
		return result;
	};
	
	private List<Long> findById(List<Long> parentIds,List<Orgnization> allNodes, List<Long> result){
		if (parentIds != null) {
			for (int i = 0; i < parentIds.size(); i++) {
				List<Long> children = new ArrayList<Long>();
				for (int j = 0; j < allNodes.size(); j++) {
					if (allNodes.get(j).getParentOrgnizationId() == parentIds.get(i)) {
						children.add(allNodes.get(j).getId());
					}
				}
				result.addAll(children);
				findById(children,allNodes, result);
				
			}
		}
		return result;
	}

	@Override
	public List<Tree> getOrgnizationTree() {
		List<Tree> root = new  ArrayList<Tree>();
		List<Orgnization> list = orgnizationDAO.queryAll();
		if(list != null){
			
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getParentOrgnizationId() == null) {
					Tree tree = new Tree();
					tree.setId(list.get(i).getId());
					tree.setText(list.get(i).getName());
					root.add(tree);
				}
			}
			root = constructTree(list, root);
		}
		return root;
	}
	
	private List<Tree> constructTree(List<Orgnization> nodes, List<Tree> root) {
		if (root != null) {
			for (int i = 0; i < root.size(); i++) {
				List<Tree> children = new ArrayList<Tree>();
				for (int j = 0; j < nodes.size(); j++) {
					if (nodes.get(j).getParentOrgnizationId() == root.get(i).getId()) {
						Tree tree = new Tree();
						tree.setId(nodes.get(j).getId());
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

	@Override
	public int insert(Orgnization record) {
		record.setUpdateTime(new Date());
		record.setCreateTime(new Date());
		return orgnizationDAO.insert(record);
	}

	@Override
	public int deleteBatch(String[] ids) {
		return orgnizationDAO.deleteBatch(ids);
	}
	
	@Override
    public int deleteBatchByFids(String[] ids) {
        return orgnizationDAO.deleteBatchByFids(ids);
    }
	
	@Override
	public int getChildrenBykey(String[] ids){
		return orgnizationDAO.getChildrenBykey(ids);
	}

	@Override
	public int updateByPrimaryKey(Orgnization record) {
		record.setUpdateTime(new Date());
		return orgnizationDAO.updateByPrimaryKey(record);
	}


	@Override
	public List<Orgnization> selectByName(String name){
		return orgnizationDAO.selectByName(name);
	}
	@Override
	public List<Orgnization> selectByRegisterCode(String registerCode){
		return orgnizationDAO.selectByRegisterCode(registerCode);
	}

	@Override
	public List<Orgnization> queryAllChildOrgnization(long parentOrgnizationId) {
		orgAll.clear();
		queryAllChildOrgnizationNest(parentOrgnizationId);
		return orgAll;
	}
	
	private void queryAllChildOrgnizationNest(long parentOrgnizationId){
		List<Orgnization> temp=orgnizationDAO.queryAllChildOrgnization(parentOrgnizationId);
		orgAll.addAll(temp);
		for(Orgnization org:temp){
			queryAllChildOrgnizationNest(org.getId());
		}
	}

	@Override
	public Orgnization queryRootOrgnization(long curentOrgnizationId) {
		org=orgnizationDAO.selectById(curentOrgnizationId);
		if(org.getParentOrgnizationId()!=null) {
			org=orgnizationDAO.selectParentOrgnizationById(curentOrgnizationId);
			if(org!=null&&org.getParentOrgnizationId()!=null){
				org=queryRootOrgnization(org.getParentOrgnizationId());
			}
		}
		return org;
		
	}

	@Override
	public Long checkOrgnizationByNameOrRegisterCode(String registerCode) {
		return orgnizationDAO.checkOrgnizationByNameOrRegisterCode(registerCode);
	}

	@Override
	public int updateByRegisterCode(Orgnization record) {
		record.setUpdateTime(new Date());
		return orgnizationDAO.updateByRegisterCode(record);
	}

	@Override
	public List<ComboOption> ComboOption(String orgName) {
		// TODO Auto-generated method stub
		return orgnizationDAO.queryForCombo(orgName);
	}


	

}
