package com.mszq.platform.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mszq.platform.base.shiro.UserSecurityManager;

public class Log4j2Util {
	 private DataSource dataSource=null;
	 private static Logger logger;
	 
	 public static enum LogLevel{
		 INFO,WARN,ERROR
	 }
	 
	 private Log4j2Util() {  
	    	try {  
	    		Class.forName("com.mysql.jdbc.Driver");  
	    	} catch (ClassNotFoundException e) {  
	    		e.printStackTrace();  
	    		System.exit(0);  
	    	}  
	         
	    	Properties properties = new Properties();  
	    	InputStream in=Log4j2Util.class.getResourceAsStream("/jdbc.properties");
	    	try {
				properties.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	//jdbc.properties中的key与druid的key不同，因此只能转换一下
	    	Map pro=new HashMap();
	    	pro.put("url", properties.getProperty("jdbc.url"));
	    	pro.put("username", properties.getProperty("jdbc.username"));
	    	pro.put("password", properties.getProperty("jdbc.password"));
	    	
	    	try {
				this.dataSource =DruidDataSourceFactory.createDataSource(pro);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} 
	 
	 private static interface Singleton {  
		 final Log4j2Util INSTANCE = new Log4j2Util();  
	 }
	 
	   

	 public static Connection getDatabaseConnection() throws SQLException {  
	    	return Singleton.INSTANCE.dataSource.getConnection();  
	}  
	
	/**
	 * 
	 * @param logger log4j2对象
	 * @param account 当前登录的账号
	 * @param name    当前登录人
	 */
	public static void LogIntoDB(Logger logger,LogLevel level,String message){
		MDC.put("account",(String)UserSecurityManager.getAttribute("account"));
		MDC.put("name",(String)UserSecurityManager.getAttribute("name"));
		switch(level){
			case INFO:logger.info(message);break;
			case WARN:logger.warn(message);break;
			case ERROR:logger.error(message);break;
			default:logger.info(message);
		}
	}
	
	

	  

}
