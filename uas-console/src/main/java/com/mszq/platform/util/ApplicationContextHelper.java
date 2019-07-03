package com.mszq.platform.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringHelper.setApplicationContext(applicationContext);
		GlobalConfig.loadAllDictionary();
		GlobalConfig.loadAllConfig();
	}

}
