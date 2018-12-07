package com.mszq.uas.uasserver.aspect;

import com.alibaba.fastjson.JSON;
import com.mszq.uas.uasserver.bean.Request;
import com.mszq.uas.uasserver.bean.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Aspect
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("execution(* com.mszq.uas.uasserver..*ControllerService.*(..))")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        //请求前
        Object objects[] = joinPoint.getArgs();
        String ipAddr = null;
        String url = null;
        String req = null;
        for(Object object:objects){
            if(object instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest)object;
                ipAddr = getRemoteHost(request);
                url = request.getRequestURL().toString();
            }else if(object instanceof Request){
                req = JSON.toJSONString(object);
            }
        }
        logger.trace("请求源IP:【{}】,请求URL:【{}】,请求参数:【{}】", ipAddr, url, req);
        //执行
        Object result = joinPoint.proceed();
        //请求后
        String resp = JSON.toJSONString(result);
        logger.trace("请求源IP:【{}】,请求URL:【{}】,返回参数:【{}】", ipAddr, url, resp);
        return result;
    }

    /**
     * 获取目标主机的ip
     *
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}