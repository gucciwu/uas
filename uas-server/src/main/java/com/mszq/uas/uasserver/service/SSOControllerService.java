package com.mszq.uas.uasserver.service;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.bean.RequireTokenExRequest;
import com.mszq.uas.uasserver.bean.RequireTokenResponse;
import com.mszq.uas.uasserver.bean.VerifyTokenExRequest;
import com.mszq.uas.uasserver.bean.VerifyTokenResponse;
import com.mszq.uas.uasserver.dao.mapper.*;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.redis.model.Session;
import com.mszq.uas.uasserver.redis.model.Token;
import com.mszq.uas.uasserver.redis.storage.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.List;

@Service
public class SSOControllerService {
    @Autowired
    @Qualifier("config")
    private Config config;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleAppMapper roleAppMapper;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AppAccountMapper appAccountMapper;
    @Autowired
    private DAO dao;

    @Autowired
    private AppSecretVerifyService appSecretVerifyService;
    @Autowired
    private IpBlackCheckService ipBlackCheckService;
    public RequireTokenResponse require(RequireTokenExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(), request.get_secret());

        RequireTokenResponse response = new RequireTokenResponse();
        //查找会话session
        Session session = null;
        try {
            session = dao.findSession(request.getSessionId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(session == null){
            response.setCode(CODE.BIZ.SESSION_NOT_EXIST);
            response.setMsg("会话不存在");
            return response;
        }

        //查找用户
        UserExample ue = new UserExample();
        ue.createCriteria().andIdEqualTo(session.getUserId());
        List<User> users = userMapper.selectByExample(ue);
        if(users == null || users.size() == 0){
            response.setCode(CODE.BIZ.USER_NOT_EXIST);
            response.setMsg("用户不能存在");
            return response;
        }

        //查找用户访问系统权限
        UserRoleExample ure = new UserRoleExample();
        ure.createCriteria().andUserIdEqualTo(users.get(0).getId());
        List<UserRole>  userRoles = userRoleMapper.selectByExample(ure);
        if(users == null || users.size() == 0){
            response.setCode(CODE.BIZ.USER_HAS_NO_ROLE);
            response.setMsg("用户没有分配角色");
            return response;
        }

        RoleApp ra = null;
        for(UserRole ur:userRoles) {
            RoleAppExample rae = new RoleAppExample();
            rae.createCriteria().andAppIdEqualTo(request.getAppId()).andRoleIdEqualTo(ur.getRoleId());
            List<RoleApp> roleApps = roleAppMapper.selectByExample(rae);
            if(roleApps != null && roleApps.size() > 0){
                ra = roleApps.get(0);
                break;
            }
        }

        if(ra == null){
            response.setCode(CODE.BIZ.NO_APP_AUTH_FOR_ROLE);
            response.setMsg("角色下没有配置系统使用权限");
            return response;
        }

        AppExample ae = new AppExample();
        ae.createCriteria().andIdEqualTo(ra.getAppId());
        List<App> apps = appMapper.selectByExample(ae);
        if(apps == null || apps.size() == 0){
            response.setCode(CODE.BIZ.APP_NOT_EXIST);
            response.setMsg("应用不存在");
            return response;
        }

        //生成令牌
        Token token = new Token();
        token.setExpireTime(config.getTokenTimeout());
        token.setAllocatedTime(System.currentTimeMillis());
        token.setSessionId(request.getSessionId());
        token.setToken(genToken());
        token.setAppId(request.getAppId());
        try {
            dao.addToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(CODE.BIZ.FAILURE_IN_CREATE_TOKEN);
            response.setMsg("创建token失败");
            return response;
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        response.setExpireTime(token.getExpireTime());
        response.setRedirectPath(apps.get(0).getPath());
        response.setToken(token.getToken());
        return response;
    }
    public VerifyTokenResponse verify(VerifyTokenExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(), request.get_secret());

        VerifyTokenResponse response = new VerifyTokenResponse();
        //查询token是否存在
        Token token = null;
        try {
            token = dao.findToken(request.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(token == null){
            response.setCode(CODE.BIZ.TOKEN_NOT_EXIST);
            response.setMsg("Token不匹配");
            return response;
        }

        //查询session
        Session session = null;
        try {
            session = dao.findSession(token.getSessionId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(session == null){
            response.setCode(CODE.BIZ.SESSION_NOT_EXIST);
            response.setMsg("会话不存在");
            return response;
        }

        //查找用户在应用中的ID
        AppAccountExample aae = new AppAccountExample();
        aae.createCriteria().andAppIdEqualTo(token.getAppId()).andUserIdEqualTo(session.getUserId());
        List<AppAccount> appAccounts = appAccountMapper.selectByExample(aae);
        if(appAccounts == null || appAccounts.size() == 0){
            response.setCode(CODE.BIZ.NOT_APP_ACCOUNT_ID);
            response.setMsg("没有应用的账户");
            return response;
        }

        response.setJobNumber(session.getJobNumber());
        response.setSessionId(session.getSessionId());
        response.setUserId(session.getUserId());
        response.setAppAccount(appAccounts.get(0).getAccount());
        response.setOrgId(appAccounts.get(0).getOrgId());
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    public static String genToken(){
        // 根据MD5算法生成MessageDigest对象
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String str = ""+System.currentTimeMillis();
            byte[] srcData = str.getBytes();
            // 使用srcBytes更新摘要
            md5.update(srcData);
            byte[] byteArray = md5.digest();

            StringBuffer md5StrBuff = new StringBuffer();

            //将加密后的byte数组转换为十六进制的字符串,否则的话生成的字符串会乱码
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
                    md5StrBuff.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                }else{
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }

            return md5StrBuff.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
