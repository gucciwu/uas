package com.mszq.platform.base.shiro;

import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class UserSecurityManager {
	
	
	public static Session getCurrentSession(){
		return SecurityUtils.getSubject().getSession();
	}
	
	/**
	 * 将属性保存到session中
	 * @param key
	 * @param value
	 */
	public static void setAttribute(String key,Object value){
		SecurityUtils.getSubject().getSession().setAttribute(key,value);
	}
	
	public static Object getAttribute(String key){
		 return SecurityUtils.getSubject().getSession().getAttribute(key);
	}
	
	/**
	 * 获取当前账号所有的角色
	 * @return
	 */
	public static String getCurrentAccountRoles(){
		return (String)UserSecurityManager.getCurrentSession().getAttribute("roles");
	}
	
	/**
	 * 获取当前账号所有的权限串
	 * @return
	 */
	public static Set<String> getCurrentAccountPermissions(String type){
		Session session=UserSecurityManager.getCurrentSession();
		if(type.equalsIgnoreCase("2")){//TODO 读取功能权限
			return (Set<String>)session.getAttribute("funcationPermissions");
		}
		if(type.equalsIgnoreCase("3")){//TODO 读取数据权限
			return (Set<String>)session.getAttribute("dataPermissions");
		}
		return null;
	}
	

}
