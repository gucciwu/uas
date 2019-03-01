package com.mszq.uas.ssodemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.ssodemo.SSOConfig;
import com.mszq.uas.ssodemo.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class DemoController {

    @Autowired
    private SSOConfig config;
    @RequestMapping(value = "/getinfo", method = RequestMethod.GET)
    protected @ResponseBody
    String getinfo(HttpServletRequest request, HttpServletResponse response){
        return ((JSONObject)request.getSession().getAttribute(AuthFilter.SESSION_OBJECT)).toJSONString();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    protected @ResponseBody
    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        javax.servlet.http.HttpServletResponse res = (javax.servlet.http.HttpServletResponse) response;
        request.getSession().invalidate();
        res.sendRedirect(config.getSsoLogoutUrl()+"?service="+config.getHostUrl()+"&appid="+config.getAppid());
    }
}
