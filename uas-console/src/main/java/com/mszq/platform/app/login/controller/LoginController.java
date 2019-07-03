package com.mszq.platform.app.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.support.json.JSONUtils;
import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.shiro.UserSecurityManager;
import com.mszq.platform.util.CaptchaUtil;
import com.mszq.platform.util.EncryptHelper;
import com.mszq.platform.util.MD5Function;

@Controller
public class LoginController {
	private final Logger logger=LoggerFactory.getLogger(LoginController.class);
	/**
	 * 
	 * @param code
	 * @param password
	 * @param model
	 * 
	 * 
	 */
	@Autowired
	private IEmployeeService employeeService;
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody CustomResult login(HttpServletRequest request, @RequestParam("code") String code, @RequestParam("password") String password,@RequestParam("verCode") String verCode){
		CustomResult result = null;
		try{
	         String randomString = (String)request.getSession().getAttribute("randomString");
	         if(!verCode.equalsIgnoreCase(randomString)){
	        	 result = CustomResult.error("");
	        	 return result;
	         }
	         if(employeeService.getEmployeeByAccount(code)==null){
	        	 result = CustomResult.error(""); 
	        	 return result;
	         }
	         password = MD5Function.getMD5Digest(password);
	      /*  String randomString = (String)UserSecurityManager.getCurrentSession().getAttribute("randomString");*/
			UsernamePasswordToken token=new UsernamePasswordToken(code,password);//password应该按照目前数据库中密码的加密方式进行加密后再作为参数
			Subject subject=SecurityUtils.getSubject();
			if(subject.isAuthenticated()){
				//TODO 如果用户已经登录，则踢出
//				SecurityUtils.kickOutUser(accountName);
			}
			subject.login(token);
//			boolean isAuthenticated=subject.isAuthenticated();
			result = CustomResult.ok((String)UserSecurityManager.getAttribute("name"));
		}catch(Exception e){
			logger.error("",e);
			result = CustomResult.error("");
		}finally{
			request.getSession().removeAttribute("randomString");
		}
		return result;
	}
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	public String check(HttpServletRequest request){
		
		logger.info("sessionid={}",request.getSession().getId());
		return "1";
	}
	
	
	/**
     * 未授权处理
     * @return {String}
     */
	@RequestMapping("/unauth")
	@ResponseBody
    public CustomResult unauth() {
//        if (SecurityUtils.getSubject().isAuthenticated() == false) {
//            return "redirect:/platform/app/login.html";
//        }
		logger.info("拦截到未授权的操作");
        return CustomResult.error("没有该操作权限！");
//        return "redirect:/platform/app/error.html";
        //return "unauth";
        
    }

    /**
     * 退出
     * @return {Result}
     */
    @PostMapping("/logout")
    @ResponseBody
    public CustomResult logout() {
        logger.info("登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return CustomResult.ok("成功");
    }
//    验证码
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        CaptchaUtil.outputCaptcha(request, response);
    }
}
