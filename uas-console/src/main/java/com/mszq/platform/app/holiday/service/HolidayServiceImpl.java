package com.mszq.platform.app.holiday.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mszq.platform.app.holiday.dao.IHolidayDAO;
import com.mszq.platform.util.DateConverter;

@Service
public class HolidayServiceImpl implements IHolidayService{
	@Resource
	IHolidayDAO holidayDAO;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public int countHoliday(String startDate, String endDate) {
		Map params=new HashMap();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return holidayDAO.countHoliday(params);
		
	}

	/**
	 * 获得自startDate开始的第days个交易日（下一交易日即days为1，上一交易日为-1）
	 * 
	 * @param startDate
	 * @param days，可正，可负，非0
	 * @return
	 */
	@Override
	public Date getTradingDate(Date startDate, int days) {
		Date tmp = null;
		int holidayNum = 0;
		if (days == 0)
			return tmp = startDate;
		else {
			tmp = DateConverter.getDate(startDate, days);
		}
		holidayNum = countHoliday(format.format(startDate), format.format(tmp));// 有几个节假日就等于还差几个交易日,(,]
		if (holidayNum > 0) {
			tmp = getTradingDate(tmp, holidayNum);
		}
		return tmp;
	}
	
	
	

}
