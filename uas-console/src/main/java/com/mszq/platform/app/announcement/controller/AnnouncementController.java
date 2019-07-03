package com.mszq.platform.app.announcement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.announcement.dto.AnnouncementDTO;
import com.mszq.platform.app.announcement.service.IAnnouncementService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Announcement;
import com.mszq.platform.entity.Config;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IAnnouncementService announcementService;
	@RequestMapping(value = "/list")
	@ResponseBody
	public EUDataGridResult getAnnouncementList(String title ,Byte isTop,String startTime,String endTime,Integer page,Integer rows){
		Map<String, Object> params = new HashMap<String, Object>();
	    if(null!=title&&!"".equalsIgnoreCase(title)){
	    	params.put("title", "%" + title + "%");
	    }
	    if(null!=isTop&&-1!=isTop){
	    	params.put("isTop", isTop);
	    }
	    if(null!=startTime&&!"".equalsIgnoreCase(startTime)){
	    	params.put("startTime", startTime);
	    }
	    if(null!=endTime&&!"".equalsIgnoreCase(endTime)){
	    	params.put("endTime", endTime);
	    }
	   return announcementService.getList(params, page, rows);
	}
	@RequestMapping(value = "/publicAnolist")
	@ResponseBody
	public EUDataGridResult getPublicAnnouncementList(String title ,String startTime,String endTime,Integer page,Integer rows){
		Map<String, Object> params = new HashMap<String, Object>();
	    if(null!=title&&!"".equalsIgnoreCase(title)){
	    	params.put("title", "%" + title + "%");
	    }
	    if(null!=startTime&&!"".equalsIgnoreCase(startTime)){
	    	params.put("startTime", startTime);
	    }
	    if(null!=endTime&&!"".equalsIgnoreCase(endTime)){
	    	params.put("endTime", endTime);
	    }
	   return announcementService.getPublicList(params, page, rows);
	}
	@RequestMapping(value = "/titleList")
	@ResponseBody
	public List<Announcement> getAnnouncementTitleList(){
		List<Announcement> titleList=announcementService.getTitleList();
	    return titleList;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult create(Announcement announcement) {
		CustomResult result = null;
		int count=announcementService.create(announcement);
		if (count == 1) {
			result = CustomResult.ok("操作成功！");
		}
		return result;
				
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult update(Announcement announcement) {
		int result = announcementService.update(announcement);
		if (result > 0) {
			return CustomResult.ok("修改成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else if(result==-1){
			return CustomResult.error("公告已发布，不能修改！");
		}else {
			return CustomResult.error("修改异常！");
		}
	}
	@RequestMapping(value = "/updateOne", method = RequestMethod.POST)
	@ResponseBody
	public Announcement updateOne(Integer id) { 
	    return  announcementService.findAnnouncementById(id);
	}
	@RequestMapping(value = "/lookOne", method = RequestMethod.POST)
	@ResponseBody
	public AnnouncementDTO lookOne(Integer id) { 
	    return  announcementService.findAnnouncementDTOById(id);
	}
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult delete(String[] ids) {
		int result = announcementService.deleteBatch(ids);
		if (result > 0) {
			return CustomResult.ok("删除成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		}else if(result==-1){
			return CustomResult.error("存在已经发布的公告，已经发布的公告不\n\n能删除，请重新选择！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	@RequestMapping(value = "/publicBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult publicbatch(String[] ids) {
		int result = announcementService.publicBatch(ids);
		if (result > 0) {
			return CustomResult.ok("发布成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	@RequestMapping(value = "/backBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult backbatch(String[] ids) {
		int result = announcementService.backBatch(ids);
		if (result > 0) {
			return CustomResult.ok("撤回成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	@RequestMapping(value = "/topBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult topbatch(String[] ids) {
		int result = announcementService.topBatch(ids);
		if (result > 0) {
			return CustomResult.ok("置顶成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	@RequestMapping(value = "/noTopBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult noTopbatch(String[] ids) {
		int result = announcementService.noTopBatch(ids);
		if (result > 0) {
			return CustomResult.ok("取消置顶成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
}
