package com.mszq.platform.app.remind.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.remind.dao.IRemindDAO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Agenda;
import com.mszq.platform.entity.Remind;
import com.mszq.platform.entity.RemindRule;

@Service
public class RemindServiceImpl implements IRemindService {
	@Resource
	IRemindDAO remindDAO;

	@Override
	public void insertRemind(Agenda calendar) throws ParseException {
		// 检查remind表中是否已经插入该日历
		int count = remindDAO.selectBySenderId(calendar.getId());
		if (count > 0) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Remind remind = new Remind();
		remind.setTitle(calendar.getTitle());
		try {
			remind.setExpireTime(sdf.parse(calendar.getEnd()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		remind.setSenderId(calendar.getId());
		remind.setSendTime(sdf.parse(sdf.format(new Date())));
		remind.setStatus("0");
		int count2 = remindDAO.insert(remind);
		remindDAO.updateByPrimaryKey(remind);

	}

	@Override
	public void insertRemindBatch(List<Remind> remindList) {

		remindDAO.insertRemindBatch(remindList);
	}

	@Override
	public EUDataGridResult getTitleList(Map<String, Object> params, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		// 查看登录者的权限
		Long userID = (Long) UserSecurityManager.getAttribute("ID");

		params.put("userID", userID);

		
		PageHelper.startPage(page, rows);
		List<Remind> list;

		list = remindDAO.queryUserTitle(params);

		// 取记录总条数
		PageInfo<Remind> pageInfo = new PageInfo<Remind>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);

		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public EUDataGridResult getRemindList(Map<String, Object> params, Integer page, Integer rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<Remind> list;
		// 判断是否为管理员登录
		boolean isAdmin = (boolean) UserSecurityManager.getAttribute("isAdmin");
		if (isAdmin) {
			list = remindDAO.queryUserTitle(params);
		} else {
			// 查看登录者的权限
			Long userID = (Long) UserSecurityManager.getAttribute("ID");
			params.put("userID", userID);
			list = remindDAO.queryUser(params);
		}
		// 取记录总条数
		PageInfo<Remind> pageInfo = new PageInfo<Remind>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);

		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public EUDataGridResult queryRemindRule(Map<String, Object> params, Integer page, Integer rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<RemindRule> list;

		list = remindDAO.queryRemindRule();

		// 取记录总条数
		PageInfo<RemindRule> pageInfo = new PageInfo<RemindRule>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);

		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public List<RemindRule> queryRemindRule(Map<String, Object> params) {
		return remindDAO.queryRemindRule();
	}

	@Override
	public EUDataGridResult getRemindWorkflowTitleList(Map<String, Object> params, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		// 查看登录者的权限
		Long userID = (Long) UserSecurityManager.getAttribute("ID");

		params.put("userId", userID);
		params.put("type", "09");
		
		PageHelper.startPage(page, rows);
		List<Remind> list;

		list = remindDAO.queryUserTitle(params);

		// 取记录总条数
		PageInfo<Remind> pageInfo = new PageInfo<Remind>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);

		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
