package com.mszq.uas.sso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.mszq.uas.sso.Constants;

public class AuthFilter implements Filter {


	//public static final String[] urls=new String[]{".pdf","/jcaptcha","/UserCheck","/logout","/login","/validate","/extoa","/ResetPasswordServlet","/resetPassword",".css",".png",".jpg",".gif",".ico"};
	//	servlet 过滤
	public static final String[] urls=new String[]{"/jcaptcha","/ApplyToken","/UserCheck","/logout","/login","/validate","/extoa","/ResetPasswordServlet","/changeoapassword","/resetPassword","/provideData?wsdl","/helloWorld?wsdl","/webservice","/services","/about.html","/test.html","/changeoapassword.jsp"};
	//  后缀过滤
	public static final String[] suffixs=new String[]{"pdf","js","css","png","jpg","gif","ico"};
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) throws IOException, ServletException {

		javax.servlet.http.HttpServletRequest r = (javax.servlet.http.HttpServletRequest) request;
		javax.servlet.http.HttpServletResponse res = (javax.servlet.http.HttpServletResponse) response;

		res.setHeader("Pragma","No-cache"); 
		res.setHeader("Cache-Control","no-cache"); 
		
		StringBuilder sb=new StringBuilder();
		sb.append(request.getScheme()).append("://").append(r.getHeader("host")).append(r.getRequestURI());
		 if(r.getQueryString()!=null)
		sb.append("?").append(r.getQueryString());
		//System.out.println(sb);
		Object sessionObject=r.getSession().getAttribute(Constants.SESSION_USER);
		//System.out.println(sb.toString());
		if(sessionObject==null){
			if(checkURL(sb.toString())){
				//	System.out .println("跳过");
				//跳过安全控制
			}else{
				//System.out .println("权限控制时注销："+sb.toString());
				res.sendRedirect("logout");
				return;
			}
		}


		filterChain.doFilter(request, response);
	}
	/**
	 * 检查为特殊URL时，跳过安全控制
	 * @return
	 */
	public boolean checkURL(String url){
		boolean b=false;
		for(String temp:urls){
			if(url.indexOf(temp)!=-1){
				b=true;break;
			}
		}
		for(String suffix:suffixs){
			if(url.endsWith(suffix)){
				b=true;break;
			}
		}
		return b;
	}

}
