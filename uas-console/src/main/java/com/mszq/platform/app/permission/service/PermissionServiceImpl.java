package com.mszq.platform.app.permission.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.permission.dao.IPermissionDAO;
import com.mszq.platform.app.permission.dto.PermissionWebDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.MenuTree;
import com.mszq.platform.base.Tree;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Permission;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	private IPermissionDAO permissionDAO;
	
//	private PermissionWebDTO permissionWebDTO;
//	private Map<String,Object> params=new HashMap<String,Object>();
//	private List<Permission> permissions;
//	private String url="";
//	private String key="";
//	private Map<String ,String> buttonMap=new HashMap<String,String>();

	@Override
	public List<Permission> queryPermissionsByUserID(Long userID) {
		List<Permission> list = permissionDAO.queryPermissionsByUserID(userID);
		List<Permission> permissions = new ArrayList<>();
		if(list != null){
			for (Permission permission : list) {
				if (permission != null && permission.getType() != null && permission.getType().intValue() == 1) {
					permissions.add(permission);
				}
			}
		}
		return permissions;
	}

	@Override
	public EUDataGridResult queryAll() {
		// 分页处理
//		PageHelper.startPage(page, rows);
		List<Permission> list = permissionDAO.queryAll();
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
//		PageInfo<Orgnization> pageInfo = new PageInfo<>(list);
//		result.setTotal(pageInfo.getTotal());
		result.setTotal(list.size());
		return result;
	}

	@Override
	public List<Tree> getPermissionTree() {
		List<Tree> root = new  ArrayList<Tree>();
		List<Permission> list = permissionDAO.queryAll();
		
		if(list != null){
			
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i)!=null){
					if (list.get(i).getParentId() == null && list.get(i).getStatus().equals(1)) {
						Tree tree = new Tree();
						tree.setId(list.get(i).getId());
						tree.setText(list.get(i).getName());
						tree.setSort(list.get(i).getSort());
						root.add(tree);
					}
				}
			}
			Collections.sort(root);//升序排列
			root = constructTree(list, root);
		}
		return root;
	}
	
	
	
	private List<Tree> constructTree(List<Permission> nodes, List<Tree> root) {
		if (root != null) {
			for (int i = 0; i < root.size(); i++) {
				List<Tree> children = new ArrayList<Tree>();
				for (int j = 0; j < nodes.size(); j++) {
					if(nodes.get(j) != null){
						if (root.get(i).getId().equals(nodes.get(j).getParentId()) && nodes.get(j).getStatus().equals(1)) {
							Tree tree = new Tree();
							tree.setId(nodes.get(j).getId());
							tree.setText(nodes.get(j).getName());
							tree.setSort(nodes.get(j).getSort());
							tree.setPid(root.get(i).getId());
							children.add(tree);
						}
					}
				}
				Collections.sort(children);//升序排列
				constructTree(nodes, children);
				root.get(i).setChildren(children);
			}
		}
		return root;
	}
	
	
	private List<MenuTree> getMenuTree(List<Permission> list) {
		List<MenuTree> root = new  ArrayList<MenuTree>();
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i) != null){
					if (list.get(i).getParentId() == null 
							&& list.get(i).getStatus() != null
							&& list.get(i).getStatus().equals(1)) {
						MenuTree tree = new MenuTree();
						tree.setId(list.get(i).getId());
						tree.setTitle(list.get(i).getName());
						tree.setIcon(list.get(i).getIcon());
						tree.setSort(list.get(i).getSort());
						if(list.get(i).getName().equals("系统管理")){
							tree.setIsCurrent(true);
						}
						root.add(tree);
					}
				}
			}
			Collections.sort(root);//升序排列
			root = constructMenuTree(list, root, true);
		}
		return root;
	}
	
	
	
	private List<MenuTree> constructMenuTree(List<Permission> nodes, List<MenuTree> root, boolean isSecondLevel) {
		if (root != null) {
			for (int i = 0; i < root.size(); i++) {
				List<MenuTree> children = new ArrayList<MenuTree>();
				for (int j = 0; j < nodes.size(); j++) {
					if(nodes.get(j) != null){
						if (root.get(i).getId().equals(nodes.get(j).getParentId()) && nodes.get(j).getStatus() != null
								&& nodes.get(j).getType().equals(0) && nodes.get(j).getStatus().equals(1)) {
							MenuTree tree = new MenuTree();
							tree.setId(nodes.get(j).getId());
							tree.setTitle(nodes.get(j).getName());
							tree.setIcon(nodes.get(j).getIcon());
							tree.setSort(nodes.get(j).getSort());
							tree.setHref(nodes.get(j).getUrl());
							tree.setPid(root.get(i).getId());
							if(nodes.get(j).getName().equals("首页")){
								tree.setIsCurrent(true);
							}
							children.add(tree);
						}
					}
				}
				Collections.sort(children);
				//把所有第二级菜单的第一个元素置为选中状态，界面显示更友好
				if(isSecondLevel){
					if(children.size() > 0){
						children.get(0).setIsCurrent(true);
					}
				}
				constructMenuTree(nodes, children, false);
				root.get(i).setChildren(children);
			}
		}
		return root;
	}

	@Override
	public int insert(Permission record) {
		record.setUpdateTime(new Date());
		return permissionDAO.insert(record);
	}

	@Override
	public int deleteBatch(String[] ids) {
		return permissionDAO.deleteBatch(ids);
	}
	
	@Override
	public int getChildrenBykey(String[] ids){
		return permissionDAO.getChildrenBykey(ids);
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		record.setUpdateTime(new Date());
		return permissionDAO.updateByPrimaryKey(record);
	}

	@Override
	public PermissionWebDTO queryPermissions() {
		Map<String,Object> params=new HashMap<String,Object>();
		List<Permission> permissions;
		String url="";
		String key="";
		Map<String ,String> buttonMap=new HashMap<String,String>();
		Long userID = (Long)UserSecurityManager.getAttribute("ID");
		List<Permission> list = permissionDAO.queryPermissionsByUserID(userID);
//		List<Permission> list = permissionDAO.queryAll();
		buttonMap.clear();
		params.clear();
		permissions = new ArrayList<>();
		if(list != null){
			for (Permission permission : list) {
				if (permission != null && permission.getType() != null && permission.getType().intValue() == 1) {
					permissions.add(permission);
				}
			}
		}
//		params.put("type", 1);
//		permissions=permissionDAO.queryPermissionByParams(params);
		//将button permission放到map中，一个页面的所有button都放到一个key下
		for(Permission p:permissions){
			url=p.getUrl().replaceAll("/", "-");;
			key=url.substring(0, url.lastIndexOf("-"));
			if(buttonMap.containsKey(key)) {
				buttonMap.put(key,buttonMap.get(key).concat(","+url));
			}else{
				buttonMap.put(key,url);
			}
		}
		PermissionWebDTO permissionWebDTO=new PermissionWebDTO(getMenuTree(list),buttonMap);
		return permissionWebDTO;
	}
}
