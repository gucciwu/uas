package com.mszq.platform.app.holiday.dao;

import java.util.Date;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface IHolidayDAO {
	public int countHoliday(Map<String,Object> params);

}
