package com.mszq.platform.app.announcement.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.announcement.dao.IAnnouncementDAO;
import com.mszq.platform.app.announcement.dto.AnnouncementDTO;
import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.entity.Announcement;
import com.mszq.platform.entity.Config;

@Service
public class AnnouncementServiceImpl implements IAnnouncementService{
	@Resource
	IAnnouncementDAO announcementDAO;
	@Resource
	IEmployeeService employeeService;
	@Override
	public EUDataGridResult getList(Map<String, Object> params,int page, int pageSize){
		// 分页处理
		PageHelper.startPage(page, pageSize);
		List<Announcement> list=announcementDAO.queryAll(params);
		// 取记录总条数
		PageInfo<Announcement> pageInfo=new PageInfo<Announcement>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	@Override
	public EUDataGridResult getPublicList(Map<String, Object> params, Integer page, Integer rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		List<AnnouncementDTO> list=announcementDAO.queryAllPublic(params);
		// 取记录总条数
		PageInfo<AnnouncementDTO> pageInfo=new PageInfo<AnnouncementDTO>(list);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	@Override
	public List<Announcement> getTitleList() {
		return announcementDAO.queryAllTitle();
	}
	@Override
	public int create(Announcement announcement) {
		announcement.setStatus((byte) 0);
		announcement.setCreatorTime(new Date());
		announcement.setUpdateTime(new Date());
		//获取当前登录者id
		Long userID = (Long)UserSecurityManager.getAttribute("ID");
		announcement.setCreatorId(userID.intValue());
		return announcementDAO.insert(announcement);
	}
	@Override
	public int deleteBatch(String[] ids) {
       for(String ido:ids){
    	  int id = Integer.parseInt(ido);
    	  if(announcementDAO.selectByPrimaryKey(id).getStatus().equals((byte) 1)){
    		  return -1;
    	  }
       } 
		return announcementDAO.deleteBatch(ids);
	}
	@Override
	public int update(Announcement announcement) {
		if(announcement.getStatus().equals((byte)1)){
			return -1;
		}else{
			announcement.setUpdateTime(new Date());
			return announcementDAO.updateByPrimaryKeySelective(announcement);
		}
	}
	@Override
	public int publicBatch(String[] ids) {
		
		return announcementDAO.publicBatch(ids);
	}
	@Override
	public int backBatch(String[] ids) {
		
		return announcementDAO.backBatch(ids);
	}
	@Override
	public int topBatch(String[] ids) {
		return announcementDAO.topBatch(ids);
	}
	@Override
	public int noTopBatch(String[] ids) {
		return announcementDAO.noTopBatch(ids);
	}
	@Override
	public Announcement findAnnouncementById(Integer id) {
		return announcementDAO.selectByPrimaryKey(id);
		
	}
	@Override
	public AnnouncementDTO findAnnouncementDTOById(Integer id) {
		Announcement announcement=announcementDAO.selectByPrimaryKey(id);
		AnnouncementDTO announcementDTO=new AnnouncementDTO();
		announcementDTO.setContent(announcement.getContent());
		announcementDTO.setCreatorId(announcement.getCreatorId());
		announcementDTO.setCreatorTime(announcement.getCreatorTime());
		announcementDTO.setId(announcement.getId());
		announcementDTO.setIsTop(announcement.getIsTop());
		announcementDTO.setStatus(announcement.getStatus());
		announcementDTO.setTitle(announcement.getTitle());
		announcementDTO.setType(announcement.getType());
		announcementDTO.setUpdateTime(announcement.getUpdateTime());
		announcementDTO.setValidDate(announcement.getValidDate());
		announcementDTO.setCreateName(employeeService.getEmployeeByID(announcement.getCreatorId()).getName());
		return announcementDTO;
	}




}
