package com.mszq.platform.app.announcement.service;

import java.util.List;
import java.util.Map;

import com.mszq.platform.app.announcement.dto.AnnouncementDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Announcement;

public interface IAnnouncementService {

	EUDataGridResult getList(Map<String, Object> params, int page, int pageSize);

	int deleteBatch(String[] ids);

	int create(Announcement announcement);

	int update(Announcement announcement);

	int publicBatch(String[] ids);

	int backBatch(String[] ids);

	int topBatch(String[] ids);

	int noTopBatch(String[] ids);

	Announcement findAnnouncementById(Integer id);

	List<Announcement> getTitleList();

	EUDataGridResult getPublicList(Map<String, Object> params, Integer page, Integer rows);

	AnnouncementDTO findAnnouncementDTOById(Integer id);



}
