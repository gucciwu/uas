package com.mszq.platform.util;

import org.springframework.context.ApplicationContext;

public class SpringHelper {
	
	private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringHelper.applicationContext = applicationContext;
    }

    public static <T> T popBean(Class<T> clazz) {
        if (applicationContext == null)
            return null;
        return applicationContext.getBean(clazz);
    }

    public static <T> T popBean(String name, Class<T> clazz) {
        if (applicationContext == null)
            return null;
        return applicationContext.getBean(name, clazz);
    }
}
