package com.mszq.platform.app.holiday.service;

import java.util.Date;

public interface IHolidayService {
	
	public int countHoliday(String startDate,String endDate);
	public Date getTradingDate(Date startDate, int days);

}
