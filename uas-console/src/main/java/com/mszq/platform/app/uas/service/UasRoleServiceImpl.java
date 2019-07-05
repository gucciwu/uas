package com.mszq.platform.app.uas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mszq.platform.app.config.dao.IConfigDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.uas.dao.IUasAppDAO;
import com.mszq.platform.app.uas.dao.IUasRoleDAO;
import com.mszq.platform.app.uas.dto.UasAppDto;
import com.mszq.platform.app.uas.dto.UasRoleDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.UasRole;
import com.mszq.platform.util.Constants;
import com.mszq.platform.util.DateUtil;

import net.sf.json.JSONObject;

@Service
public class UasRoleServiceImpl implements IUasRoleService {
	@Autowired
	IUasRoleDAO uasRoleDAO;

	@Autowired
	IUasAppDAO uasAppDAO;

	@Autowired
	IConfigDAO configDAO;

	@Autowired
	ApiSenderService apiSender;

	private String appId = null;
	private String secret = null;

	private String getAppId(){
		if(appId == null){
			this.appId = configDAO.getConfigValueByCode("uasAppId");
		}
		return this.appId;
	}

	private String getSecret(){
		if(secret == null){
			this.secret =configDAO.getConfigValueByCode("uasSecret");
		}

		return this.secret;
	}

	@Override
	public EUDataGridResult selectList(@Param("condition") Map<String, Object> condition, int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasRoleDto> list = uasRoleDAO.selectList(condition);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasRoleDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	@Override
	public EUDataGridResult queryAll() {
		// 分页处理
		List<UasRoleDto> list = uasRoleDAO.selectList(null);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasRoleDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public int insert(UasRole record) {
		record.setModifyTime(DateUtil.dateToStr(new Date(), Constants.PATTERN_DATETIME));

		JSONObject obj = new JSONObject();
		obj.put("_appId", this.getAppId());
		obj.put("_devInfo", "");
		obj.put("_secret", this.getSecret());
		obj.put("role", record);

		apiSender.setMethod("/permission/add_role");
		apiSender.setObj(obj);
		apiSender.send();
		
		return 1;
		//return uasRoleDAO.insertSelective(record);
	}

	@Override
	public int update(UasRole record) {
		record.setModifyTime(DateUtil.dateToStr(new Date(), Constants.PATTERN_DATETIME));

		JSONObject obj = new JSONObject();
		obj.put("_appId", this.getAppId());
		obj.put("_devInfo", "");
		obj.put("_secret", this.getSecret());
		obj.put("role", record);

		apiSender.setMethod("/permission/modify_role");
		apiSender.setObj(obj);
		apiSender.send();
		//return uasRoleDAO.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public int deleteBatch(String[] ids) {
		
		int ret = 0;
		for (String id : ids) {
			Long key = Long.parseLong(id);
			JSONObject obj = new JSONObject();
			obj.put("_appId", this.getAppId());
			obj.put("_devInfo", "");
			obj.put("_secret", this.getSecret());
			obj.put("roleId", id);
			
			apiSender.setMethod("/permission/del_role");
			apiSender.setObj(obj);
			apiSender.send();
			
			ret = ret + 1;
		}
		return ret;
	}
	
	@Override
	public List<Tree> getRoleTree(Map<String, Object> condition) {
		List<Tree> root = new  ArrayList<Tree>();
		List<UasRoleDto> list = uasRoleDAO.queryAll(condition);
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getParentId() == null) {
					Tree tree = new Tree();
					tree.setId(list.get(i).getId());
					tree.setText(list.get(i).getRoleName());
					root.add(tree);
				}
			}
			root = constructTree(list, root);
		}
		return root;
	}

	private List<Tree> constructTree(List<UasRoleDto> nodes, List<Tree> root) {
		if (root != null) {
			for (int i = 0; i < root.size(); i++) {

				List<Tree> children = new ArrayList<Tree>();
				for (int j = 0; j < nodes.size(); j++) {
					//System.out.println(root.get(i).getId() + "s:" + nodes.get(j).getName()+" pid:"+nodes.get(j).getParentOrgId());
					if (root.get(i).getId() != null 
							&& nodes.get(j).getParentId()!= null
							&& nodes.get(j).getParentId().longValue() == root.get(i).getId().longValue()) {
						Tree tree = new Tree();
						tree.setId(nodes.get(j).getId());
						tree.setText(nodes.get(j).getRoleName());
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
	
	
	private int deleteTree(int ret, UasRoleDto uasRole, String prefix) {
		Map<String, Object> condition =new HashMap<String, Object>();
		condition.put("parentId", uasRole.getId());
		
		List<UasRoleDto> children = uasRoleDAO.selectList(condition);
		for (UasRoleDto child : children) {
			int childNum = deleteTree(ret, child, prefix + "    ");
			ret = ret + childNum;
		}
		//System.out.println(prefix+uasRole.getOrgId().longValue() + " " + uasOrg.getName());
		uasRoleDAO.deleteByPrimaryKey(uasRole.getId());
		return ret+1;
	}
	
	
	private int getChildernCount(UasRoleDto uasRole) {
		Map<String, Object> condition =new HashMap<String, Object>();
		condition.put("parentId", uasRole.getId());
		
		List<UasRoleDto> children = uasRoleDAO.selectList(condition);
		return children==null ? 0:children.size();
	}
}
