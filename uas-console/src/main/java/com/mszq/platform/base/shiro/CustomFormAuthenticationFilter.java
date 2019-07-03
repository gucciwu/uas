package com.mszq.platform.base.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                // 放行 allow them to see the login page ;)
                return true;
            }
        } else {
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            
            if (this.isAjax(httpRequest)) {
                
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);  
                httpServletResponse.setStatus(401);
                 
                return false;
    
            } else {  
                saveRequestAndRedirectToLogin(request, response); 
            }  

            return false;
        }
    }
    
    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request){
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }
    
}