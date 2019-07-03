package com.mszq.platform.base.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mszq.platform.base.CustomResult;
import com.mszq.platform.entity.Employee;

import net.sf.json.JSONObject;

public class ResourceCheckFilter extends AccessControlFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 表示是否允许访问 ，如果允许访问返回true，否则false；
	 * 
	 * @param mappedValue 表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request,response);
		String url = getPathWithinApplication(request);
		if(!url.toLowerCase().endsWith(".html")){
			return true;
		}
		if(!url.startsWith("/cms")){
			url = "/cms" + url;
		}
		
		boolean hasPermission = false;
		//采用白名单制来判断权限，缺陷是如果没配在资源表中的都需要编码加入到权限set中，否则页面无法显示，像index页面，流程跟踪页面等
//		boolean hasPermission =  subject.isPermitted(url);
		//采用黑名单制来判断权限，资源表中配置了说明是需要控制的，如果没有这个权限想访问就禁止
		if(subject.getPrincipals() == null){
			return false;
		}
		hasPermission =true;	
        return hasPermission;
	}

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回 true 表示需要继续处理；如果返回 false 表示该拦截器实例已经处理了，将直接返回即可。
	 * 
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
