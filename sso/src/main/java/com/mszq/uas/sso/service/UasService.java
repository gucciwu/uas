package com.mszq.uas.sso.service;

import com.alibaba.fastjson.JSON;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.sso.Config;
import com.mszq.uas.sso.bean.*;
import com.mszq.uas.sso.model.App;
import com.mszq.uas.sso.model.Org;
import com.mszq.uas.sso.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UasService {
    @Autowired
    @Qualifier("ssoConfig")
    private Config config;

    private Map<Long, App> appMap;

    @Autowired
    private RestTemplate restTemplate;

    public RequireTokenResponse getToken(String appId, String sessionId) {
        RequireTokenExRequest request = new RequireTokenExRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        request.setAppId(Long.parseLong(appId));
        request.setSessionId(sessionId);
        ResponseEntity<RequireTokenResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/sso/require_token", request, RequireTokenResponse.class, "");
        if (response.getStatusCodeValue() == 200) {
            return response.getBody();
        }else{
            return null;
        }
    }

    @Scheduled(fixedRate = 300000)
    public void refreshAppList() {
        if (appMap == null)
            appMap = new HashMap<Long, App>();

        GetAppRequest request = new GetAppRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        ResponseEntity<GetAppResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/datasync/get_apps", request, GetAppResponse.class, "");
        if (response.getBody().getCode() == CODE.SUCCESS) {
            for (App app : response.getBody().getData())
                appMap.put(app.getId(), app);
        }
    }

    public App getApp(Long appId) {
        if (appMap == null)
            refreshAppList();

        App app = appMap.get(appId);
        return app;
    }

    public void logout(String sessionid) {
        SignoutExRequest request = new SignoutExRequest();
        request.setSessionId(sessionid);
        ResponseEntity<AuthResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/ua/signout", request, AuthResponse.class, "");
    }

    public ModifyPassData modifyPassword(String jobNumber, String oldPassword, String newPassword) throws Exception {
        ChangePasswordExRequest request = new ChangePasswordExRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        request.setJobNumber(jobNumber);
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);

        ResponseEntity<ChangePasswordResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/datasync/change_password", request, ChangePasswordResponse.class, "");
        if (response.getBody().getCode() != CODE.SUCCESS) {
            System.out.println(response.getBody().getMsg());
        }

        ModifyPassData data = new ModifyPassData();
        data.setCode(response.getBody().getCode());
        data.setMsg(response.getBody().getMsg());
        return data;
    }

    public AuthResponse auth(String jobNumber, String password) {
        AuthExRequest request = new AuthExRequest();
        request.setJobNumber(jobNumber);
        AuthResponse response = new AuthResponse();
        try {
            request.setPassword(password);
            ResponseEntity<AuthResponse> resp = this.restTemplate.postForEntity(config.getHostUrl() + "/ua/auth", request, AuthResponse.class, "");
            return resp.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(CODE.BIZ.AUTH_FAIL);
            response.setMsg(e.getMessage());
        }

        return response;
    }

    public User getUser(String jobNumber) {
        GetUsersExRequest request = new GetUsersExRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        request.setJobNumber(jobNumber);
        ResponseEntity<GetUsersResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/datasync/get_users", request, GetUsersResponse.class, "");
        if (response.getBody().getCode() == CODE.SUCCESS && response.getBody().getData().size() > 0) {
            User user = JSON.parseObject(JSON.toJSONString(response.getBody().getData().get(0)), User.class);
            return user;
        } else {
            return null;
        }
    }

    public ResetPasswordResponse resetPassword(String jobNumber, String password) throws Exception {
        ResetPasswordExRequest request = new ResetPasswordExRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        request.setJobNumber(jobNumber);
        request.setNewPassword(password);
        ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
        if(response.getStatusCodeValue() != 200){
            return null;
        }else{
            return response.getBody();
        }
    }

    public Org getOrg(long orgId, short orgType) {
        GetOrgsExRequest request = new GetOrgsExRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        request.setOrgId(orgId);
        request.setOrgType(orgType);

        ResponseEntity<GetOrgsResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/datasync/get_orgs", request, GetOrgsResponse.class, "");
        if (response.getBody().getCode() == CODE.SUCCESS && response.getBody().getData().size() > 0) {
            Org org = JSON.parseObject(JSON.toJSONString(response.getBody().getData().get(0)), Org.class);
            return org;
        } else {
            return null;
        }
    }

    public VerifyTokenResponse validate(String token) {
        VerifyTokenExRequest request = new VerifyTokenExRequest();
        request.set_appId(config.getAppId());
        request.set_secret(config.getSecret());
        request.setToken(token);
        ResponseEntity<VerifyTokenResponse> response = this.restTemplate.postForEntity(config.getHostUrl() + "/sso/verify_token", request, VerifyTokenResponse.class, "");
        return response.getBody();
    }

}
