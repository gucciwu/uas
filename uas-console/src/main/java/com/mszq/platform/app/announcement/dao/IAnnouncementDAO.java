package com.mszq.platform.app.announcement.dao;

import java.util.List;
import java.util.Map;

import com.mszq.platform.app.announcement.dto.AnnouncementDTO;
import com.mszq.platform.entity.Announcement;

public interface IAnnouncementDAO {
    int deleteByPrimaryKey(Integer id);
    int insert(Announcement record);
    int insertSelective(Announcement record);
    Announcement selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Announcement record);
    int updateByPrimaryKey(Announcement record);
    
    public List<Announcement> queryAll(Map<String, Object> params);
	int deleteBatch(String[] ids);
	int publicBatch(String[] ids);
	int backBatch(String[] ids);
	int topBatch(String[] ids);
	int noTopBatch(String[] ids);
	List<Announcement> queryAllTitle();
	List<AnnouncementDTO> queryAllPublic(Map<String, Object> params);
}