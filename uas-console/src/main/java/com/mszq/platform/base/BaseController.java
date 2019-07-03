package com.mszq.platform.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.sf.json.JSONObject;

public abstract class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	  /**
//     * 登录认证异常
//     */
//    @ExceptionHandler({ UnauthenticatedException.class, AuthenticationException.class })
//    public String authenticationException(HttpServletRequest request, HttpServletResponse response) {
//    	if (!isAjaxRequest(request)) {
//        	PrintWriter out = null;
//            try {
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("application/json; charset=utf-8");
//                out = response.getWriter();
//                out.write(JSONObject.fromObject(CustomResult.error("登陆失效，请重新登陆！")).toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (out != null) {
//                    out.close();
//                }
//            }
//            return null;
//        } else {
//        	return "redirect:/system/login";
//			//response.sendRedirect("/platform/app/login.html");
//        }
//    }
	/**
     * 权限异常
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response) {
    	PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONObject.fromObject(CustomResult.error("没有操作权限！")).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }
    
    boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}
