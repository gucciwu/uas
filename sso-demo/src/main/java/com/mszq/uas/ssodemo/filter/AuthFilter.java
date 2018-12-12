package com.mszq.uas.ssodemo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.ssodemo.SSOConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "authFilter")
public class AuthFilter implements Filter {

	@Autowired
	private SSOConfig config;
	@Autowired
	@Qualifier("myRestTemplate")
	private RestTemplate restTemplate;

	public final static String SESSION_OBJECT = "SSO_SESSION_OBJECT";

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		//判断是否存在会话
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession(true);
		Object obj = httpRequest.getSession().getAttribute(SESSION_OBJECT);
		if(session.getAttribute(SESSION_OBJECT) != null){
			// TODO 如果会话不为空，则进入应用系统

		}else {
			// TODO 如果会话不存在则进行权限检查
			String token = request.getParameter("token");
			//如果token不存在，则直接跳转到统一认证登录页面
			if(token  == null || "".equals(token)){
				javax.servlet.http.HttpServletResponse res = (javax.servlet.http.HttpServletResponse) response;
				res.sendRedirect(config.getSsoPortalUrl()+"?service="+config.getHostUrl()+"&appid="+config.getAppid());
				return;
			}else {
				// TODO 传入了令牌，则调用SSO的接口对令牌进行认证
				System.out.println("有令牌...跳转到CAS进行令牌认证...");
				System.out.println(config.getSsoValidateUrl() + "?token=" + token + "&appid=" + config.getAppid());
				ResponseEntity<String> resp = requestResource(config.getSsoValidateUrl() + "?token=" + token + "&appid=" + config.getAppid());
				if (resp.getStatusCodeValue() != 200) {
					// TODO 校验令牌接口调用失败,进行相应的处理
					String msg = "校验令牌失败,进行相应的处理";
					((HttpServletResponse) response).sendRedirect(config.getHostUrl() + "?msg=" + Base64.encodeBase64String(msg.getBytes()));
					return;
				} else {
					// TODO 校验令牌接口调用成功,进行相应的处理
					JSONObject r = (JSONObject) JSON.parse(resp.getBody());
					try {
						int code = r.getInteger("code");
						if (code == 0) {            //成功
							// TODO 将统一认证认证信息写入会话
							session.setAttribute(SESSION_OBJECT, r);  //TODO 这里讲登录成功后的数据写入了会话中。
							String msg = r.toJSONString();
							((HttpServletResponse) response).sendRedirect(config.getHostUrl());
						} else {                    //失败
							String msg = r.toJSONString();
							((HttpServletResponse) response).sendRedirect(config.getSsoPortalUrl() + "?msg=" + Base64.encodeBase64String(msg.getBytes()));
						}
					} catch (Exception e) {
						e.printStackTrace();
						((HttpServletResponse) response).sendRedirect(config.getSsoPortalUrl() + "?msg=" + Base64.encodeBase64String(e.getMessage().getBytes()));
					}

				}
			}
		}

		chain.doFilter(request, response);
	}

	public ResponseEntity<String> requestResource(String url) {
		return this.restTemplate.getForEntity(url, String.class, "");
	}
}
