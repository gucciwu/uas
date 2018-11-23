package com.mszq.uas.uasserver.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.bean.Response;
import com.mszq.uas.uasserver.dao.mapper.AppMapper;
import com.mszq.uas.uasserver.dao.mapper.IpListMapper;
import com.mszq.uas.uasserver.dao.model.App;
import com.mszq.uas.uasserver.dao.model.AppExample;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访问安全控制拦截器，目前考虑实现：
 * IP黑名单
 */
public class AppSecretInterceptor implements HandlerInterceptor {
    @Autowired
    private AppMapper appMapper;

    private Map<Long, String> appSecrets;

    //刷新IP列表指令
    private static final String REFRESH_APP_LIST = "/config/refresh_app_list";


    private void refreshAppList(){
        List<App> list = appMapper.selectAll();
        appSecrets = new HashMap<Long,String>();
        for(App app:list){
            appSecrets.put(app.getId(),app.getSecret());
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //判断是否为刷新APP列表
        if(requestURI.indexOf(REFRESH_APP_LIST) != -1 || appSecrets == null){
            refreshAppList();
            return true;
        }

        String submitMehtod = request.getMethod();
        // GET
        if (submitMehtod.equals("GET")) {
            String appId = request.getParameter("_appId");
            String secret = request.getParameter("_secret");
            try{
                long id = Long.parseLong(appId);
                String _secret = appSecrets.get(id);
                if(_secret != null && _secret.equals(secret)){
                    return true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            JSONObject obj = (JSONObject)JSON.parse(getRequestPostStr(request));
            long appId = obj.getLong("_appId");
            String secret = obj.getString("_secret");
            String _secret = appSecrets.get(appId);
            if(_secret != null && _secret.equals(secret)){
                return true;
            }
        }

        Response resp = new Response();
        resp.setCode(CODE.BIZ.APP_SECRET_NOT_MATCH);
        resp.setMsg("应用编码和应用Secret不匹配");
        returnJson(response,resp);
        return false;
    }

    private String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

    private byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
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
