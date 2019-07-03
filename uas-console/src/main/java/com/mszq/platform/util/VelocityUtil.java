package com.mszq.platform.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {
	
	
	public static String getContent(String templateFile,Map<String,Object>data) throws Exception{  
 
		Properties properties=new Properties();
        //设置velocity资源加载方式为class
        properties.setProperty("resource.loader", "class");
        //设置velocity资源加载方式为file时的处理类
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //实例化一个VelocityEngine对象
        VelocityEngine velocityEngine=new VelocityEngine(properties);
        //实例化一个VelocityContext
        VelocityContext context=new VelocityContext(data);
        StringWriter writer=new StringWriter();
        //从src目录下加载hello.vm模板
        //假若在com.velocity.test包下有一个hello.vm文件,那么加载路径为com/velocity/test/hello.vm
        velocityEngine.mergeTemplate(templateFile, "UTF-8", context, writer);
		return writer.toString();
	}

}
