package com.mszq.platform.base.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


public class PlatformExceptionHandler implements HandlerExceptionResolver{
	private final Logger logger=LoggerFactory.getLogger(PlatformExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);
        logger.error("",ex);
        /*可以按照不同的异常类别，专向不同的错误页面
        if(ex instanceof BusinessException) return new ModelAndView("erroe-businsess",model);
        */
        response.setHeader("Content-Disposition", "");//避免文件下载时设置了response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		return new ModelAndView("platform/app/error.html",model);
	}

}
