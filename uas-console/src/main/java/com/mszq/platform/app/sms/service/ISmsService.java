package com.mszq.platform.app.sms.service;
import com.mszq.platform.entity.Sms;
public interface ISmsService {

	String bulidMsgCode();

	String sendMsg(String phoneNum, String content);	
}
