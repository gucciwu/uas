package com.mszq.platform.app.role.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.role.dao.IRoleDAO;
import com.mszq.platform.app.role.dto.RolePermissionWebDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.PermissionRole;
import com.mszq.platform.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    IRoleDAO roleDAO;
	
//	@Override
//	public List<Long> getPermissionsByRoleID(Long roleID) {
//		return roleDAO.getPermissionsByRoleID(roleID);
//	}

	@Override
	public EUDataGridResult queryAll(int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<Role> list = roleDAO.queryAll();
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<Role> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public List<Tree> getRoleTree() {
		List<Tree> trees = new ArrayList<Tree>();
		List<Role> roles = roleDAO.queryAll();
		for (Role role : roles) {
			Tree tree = new Tree();
			tree.setId(role.getId());
			tree.setText(role.getName());
			trees.add(tree);
		}
		return trees;
	}

	@Override
	public int insert(Role record) {
		record.setUpdateTime(new Date());
		return roleDAO.insert(record);
	}

	@Override
	@Transactional
	public int deleteBatch(String[] ids) {
		roleDAO.deletePermissionMappingBatch(ids);
		roleDAO.deleteEmployeeMappingBatch(ids);
		return roleDAO.deleteBatch(ids);
	}

	@Override
	@RequiresPermissions("/role/update")
	public int updateByPrimaryKey(Role record) {
		record.setUpdateTime(new Date());
		return roleDAO.updateByPrimaryKey(record);
	}

	@Override
	public List<Long> getPermissionIdListByRoleId(Long id) {
		return roleDAO.getPermissionIdListByRoleId(id);
	}

	@Override
	@Transactional
	public int updateRolePermission(Long id, String resourceIds) {
		//给角色授权时，先删除旧的mapping，再插入新的
		int count=0;
		List<PermissionRole> prList = new ArrayList<PermissionRole>();
		roleDAO.deletePermissionMappingBatch(new String[]{id.toString()});
		String[] resourceIdArray = resourceIds.split(",");
        for (String resourceId : resourceIdArray) {
        	if(!"".equals(resourceId.trim())){
        		PermissionRole pr = new PermissionRole();
            	pr.setRoleId(id);
            	pr.setResourceId(Long.parseLong(resourceId));
            	prList.add(pr);
        	}
        }
        if (prList.size() > 0) {
        	count = roleDAO.insertRolePermissionBatch(prList);
		}
        return count;
	}
	
	public List<RolePermissionWebDTO> queryAdminRoleAndPermissionByEmployeeId(long employeeId){
		return roleDAO.queryAdminRoleAndPermissionByEmployeeId(employeeId);
	}

	@Override
	public Role queryRoleById(long id) {
		// TODO Auto-generated method stub
		return roleDAO.queryRoleById(id);
	}

}
