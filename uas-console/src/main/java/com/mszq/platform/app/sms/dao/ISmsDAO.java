package com.mszq.platform.app.sms.dao;

import com.mszq.platform.entity.Sms;

public interface ISmsDAO {
    int deleteByPrimaryKey(Integer id);
    int insert(Sms record);
    int insertSelective(Sms record);
    Sms selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Sms record);
    int updateByPrimaryKey(Sms record);
}