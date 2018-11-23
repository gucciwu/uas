package com.mszq.uas.uasserver.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.bean.Response;
import com.mszq.uas.uasserver.dao.mapper.IpListMapper;
import com.mszq.uas.uasserver.dao.model.IpList;
import com.mszq.uas.uasserver.dao.model.IpListExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 访问安全控制拦截器，目前考虑实现：
 * IP黑名单
 */
public class IpBlackListInterceptor implements HandlerInterceptor {
    @Autowired
    private IpListMapper ipListMapper;

    private String[] blackIps;

    //刷新IP列表指令
    private static final String REFRESH_IP_LIST_URL = "/config/refresh_ip_list";

    private void refreshBlackList(){
        List<IpList> list = ipListMapper.selectAll();
        blackIps = new String[list.size()];
        for(int i=0;i<list.size();i++) {
            blackIps[i] = list.get(i).getIp();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //判断是否为刷新IP黑名单指令
        if(requestURI.indexOf(REFRESH_IP_LIST_URL) != -1 || blackIps == null){
            refreshBlackList();
            return true;
        }

        String remoteIp = request.getRemoteAddr();
        String orderIdStr = null;
        if (requestURI != null) {
            for (String ip : blackIps) {
                if (remoteIp.equals(ip)) {
                    Response resp = new Response();
                    resp.setCode(CODE.BIZ.ILLEGAL_REMOTE_IP);
                    resp.setMsg("IP请求不合法，请检查IP黑名单");
                    returnJson(response,resp);
                    return false;
                }
            }
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, Response _response) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSONString(_response));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
