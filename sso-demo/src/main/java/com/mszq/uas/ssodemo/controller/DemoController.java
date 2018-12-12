package com.mszq.uas.ssodemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.ssodemo.filter.AuthFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class DemoController {

    @RequestMapping(value = "/getinfo", method = RequestMethod.GET)
    protected @ResponseBody
    String getinfo(HttpServletRequest request, HttpServletResponse response){
        return ((JSONObject)request.getSession().getAttribute(AuthFilter.SESSION_OBJECT)).toJSONString();
    }
}
