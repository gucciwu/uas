package com.mszq.platform.app.uas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.config.dao.IConfigDAO;
import com.mszq.platform.app.uas.common.AESCoder;
import com.mszq.platform.app.uas.dao.IUasAppDAO;
import com.mszq.platform.app.uas.dao.IUasPasswordDAO;
import com.mszq.platform.app.uas.dao.IUasUserDAO;
import com.mszq.platform.app.uas.dto.UasAppDto;
import com.mszq.platform.app.uas.dto.UasUserDto;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.UasPassword;
import com.mszq.platform.entity.UasUser;
import com.mszq.platform.util.MD5Function;

import net.sf.json.JSONObject;

@Service
public class UasUserServiceImpl implements IUasUserService {
	@Autowired
	IConfigDAO configDAO;

	@Autowired
	IUasAppDAO uasAppDAO;

	@Autowired
	IUasUserDAO uasUserDAO;

	@Autowired
	IUasPasswordDAO uasPasswordDAO;

	@Autowired
	ApiSenderService apiSender;

	@Override
	public EUDataGridResult selectList(Map<String, Object> condition, int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<UasUserDto> list = uasUserDAO.selectList(condition);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<UasUserDto> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	@Transactional
	public int insert(UasUser record) throws RuntimeException {
		int ret = -1;
		try {
			UasAppDto uasApp = uasAppDAO.selectByPrimaryKey(new Long(0));
			JSONObject obj = new JSONObject();
			obj.put("_appId", 0);
			obj.put("_devInfo", "");
			obj.put("_secret", uasApp.getSecret());
			obj.put("user", record);

			apiSender.setMethod("/datasync/update_user");
			apiSender.setObj(obj);
			apiSender.send();
			ret = 1;
		} catch (Exception e) {
			ret = 0;
			throw new RuntimeException(e.getMessage());
		}
		return ret;
	}

	@Override
	@Transactional
	public int update(UasUser record) throws RuntimeException {
		int ret = -1;
		try {
			UasAppDto uasApp = uasAppDAO.selectByPrimaryKey(new Long(0));
			JSONObject obj = new JSONObject();
			obj.put("_appId", 0);
			obj.put("_devInfo", "");
			obj.put("_secret", uasApp.getSecret());
			obj.put("user", record);

			apiSender.setMethod("/datasync/update_user");
			apiSender.setObj(obj);
			apiSender.send();
			ret = 1;
		} catch (Exception e) {
			ret = 0;
			throw new RuntimeException(e.getMessage());
		}

		return ret;
	}

	@Override
	public int deleteBatch(String[] ids) {
		int ret = 0;
		for (String id : ids) {
			Long key = Long.parseLong(id);
			ret = ret + uasUserDAO.deleteByPrimaryKey(key);
		}
		return ret;
	}

	@Override
	public UasPassword getPassword(Long userId) {
		return uasPasswordDAO.selectByPrimaryKey(userId);
	}

	@Override
	public int savePassword(UasPassword password) {
		int ret = 0;
		//String passwordText = password.getPassword();
		// UasPassword pwd = uasPasswordDAO.selectByUserId(password.getUserId());
		//
		// if (pwd != null && pwd.getId() != null) {
		// pwd.setPassword(MD5Function.getMD5Digest(passwordText));
		// ret = uasPasswordDAO.updateByPrimaryKeySelective(pwd);
		// } else {
		// password.setPassword(MD5Function.getMD5Digest(passwordText));
		// ret = uasPasswordDAO.insertSelective(password);
		// }

		UasAppDto uasApp = uasAppDAO.selectByPrimaryKey(new Long(0));
		UasUserDto uasUserDto = uasUserDAO.selectByPrimaryKey(password.getUserId());

		// String keyAes = configDAO.getConfigValueByCode("keyAes");

		JSONObject obj = new JSONObject();
		obj.put("_appId", 0);
		obj.put("_devInfo", "");
		obj.put("_secret", uasApp.getSecret());
		obj.put("jobNumber", uasUserDto.getJobNumber());

		// obj.put("newPassword", AESCoder.encrypt(password.getPassword(), keyAes));
		//System.out.println("==================================>>>>>"+MD5Function.getMD5Digest(password.getPassword()));
		obj.put("newPassword", MD5Function.getMD5Digest(password.getPassword()));
		obj.put("userId", uasUserDto.getId());

		apiSender.setMethod("/datasync/reset_password1");
		apiSender.setObj(obj);
		apiSender.send();

		ret = 1;

		return ret;
	}
}
