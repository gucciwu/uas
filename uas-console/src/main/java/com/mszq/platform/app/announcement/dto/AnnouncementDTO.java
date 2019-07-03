package com.mszq.platform.app.announcement.dto;

import com.mszq.platform.entity.Announcement;

public class AnnouncementDTO extends Announcement{
	private String createName;

	public String getCreateName() {
		return createName;
	}
	
	public void setCreateName(String createName) {
		this.createName = createName;
	}
  
}
