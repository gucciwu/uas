package com.mszq.uas.uasserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.UasServerApplication;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.model.App;
import com.mszq.uas.uasserver.dao.model.AppAccount;
import com.mszq.uas.uasserver.dao.model.Role;
import com.mszq.uas.uasserver.dao.model.User;
import com.mszq.uas.uasserver.util.AESCoder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UasServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SSOControllerTest {

    @LocalServerPort
    private int port;

    private URL base;
    @Autowired
    @Qualifier("config")
    private Config config;
    private HttpHeaders headers = new HttpHeaders();
    @Autowired
    private TestRestTemplate restTemplate;

    private String sessionId;
    long appId = 0;
    long roleId = 0;
    final String JOB_NUMBER ="333333";
    final String MOBILE = "1810888542";
    long userId = 0;
    private String token;
    private long expireTime;
    final long APPID=1L;
    final String SECRET="1";

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        //创建应用
        {
            AddAppRequest request = new AddAppRequest();
            App app = new App();
            app.setSecret("aaa");
            app.setName("测试系统A");
            app.setOrgType((short) 1);
            app.setPath("");
            request.setApp(app);
            request.set_secret(SECRET);
            request.set_appId(APPID);
            ResponseEntity<AddAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/add_app",request, AddAppResponse.class, "");
            Assert.assertEquals(200,response.getStatusCodeValue());
            Assert.assertEquals(0,response.getBody().getCode());
            appId = response.getBody().getAppId();
        }
        //创建角色
        {
            AddRoleExRequest request = new AddRoleExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            Role r = new Role();
            r.setRoleName("测试账户ABCD");
            r.setRoleTypeId((int)1);
            request.setRole(r);
            ResponseEntity<AddRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role", request, AddRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            roleId = response.getBody().getRoleId();
        }
        //创建用户
        {
            GetUsersExRequest errorRequest = new GetUsersExRequest();
            ResponseEntity<GetUsersResponse> errorresponse = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_users", errorRequest, GetUsersResponse.class, "");
            Assert.assertEquals(200, errorresponse.getStatusCodeValue());
            Assert.assertEquals(errorresponse.getBody().getCode(), CODE.SYS.APP_SECRET_NOT_MATCH);

            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            User user = new User();
            user.setIdNumber("611502198658121430");
            user.setEmail("yyyyy@126.com");
            user.setIdType((short) 1);
            user.setMobile(MOBILE);
            user.setJobNumber(JOB_NUMBER);
            user.setName("哈哈哈哈");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(CODE.SUCCESS, response.getBody().getCode());
            userId = response.getBody().getUserId();
        }
        //设置密码
        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setJobNumber(JOB_NUMBER);
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        //将应用付给角色
        {
            AddAppToRoleExRequest request = new AddAppToRoleExRequest();
            request.setAppId(appId);
            request.setRoleId(roleId);
            request.set_secret(SECRET);
            request.set_appId(APPID);
            ResponseEntity<AddAppToRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_app_to_role", request, AddAppToRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
        //将角色付给用户
        {
            AddRoleToUserExRequest request = new AddRoleToUserExRequest();
            request.setRoleId(roleId);
            request.setUserId(userId);
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setAutoAddAccount(true);

            ResponseEntity<AddRoleToUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role_to_user", request, AddRoleToUserResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }

        //用户认证生成session
        //正常认证
        {
            AuthExRequest request = new AuthExRequest();
            request.setJobNumber(JOB_NUMBER);
            request.setPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));
            ResponseEntity<AuthResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/ua/auth", request, AuthResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
            sessionId = resp.getBody().getSessionId();
        }
    }

    @After
    public void tearDown() throws Exception {
        //取消用户的角色
        {
            DelRoleToUserExRequest request = new DelRoleToUserExRequest();
            request.setRoleId(roleId);
            request.setUserId(userId);
            request.setAutoDelAccount(true);
            request.set_appId(APPID);
            request.set_secret(SECRET);

            ResponseEntity<DelRoleToUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role_to_user", request, DelRoleToUserResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
        //删除用户
        {
            DelUserExRequest request = new DelUserExRequest();
            request.setJobNumber(JOB_NUMBER);
            request.set_secret(SECRET);
            request.set_appId(APPID);
            ResponseEntity<DelUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_user", request, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }

        //删除角色附有的应用
        {
            DelAppToRoleExRequest request = new DelAppToRoleExRequest();
            request.setAppId(appId);
            request.setRoleId(roleId);
            request.set_secret(SECRET);
            request.set_appId(APPID);

            ResponseEntity<DelAppToRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_app_to_role", request, DelAppToRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
        //删除角色
        {
            DelRoleExRequest request = new DelRoleExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setRoleId(roleId);

            ResponseEntity<DelRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role", request, DelRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
        //删除应用
        {
            DelAppRequest request = new DelAppRequest();
            request.setAppId(appId);
            request.set_appId(APPID);
            request.set_secret(SECRET);
            ResponseEntity<DelAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_app",request, DelAppResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
    }

    @Test
    public void requireAndVerify() {
        {
            RequireTokenExRequest request = new RequireTokenExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setAppId(appId);
            request.setSessionId(sessionId);
            ResponseEntity<RequireTokenResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/sso/require_token",request, RequireTokenResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            Assert.assertNotNull(response.getBody().getToken());
            Assert.assertEquals(config.getTokenTimeout(),response.getBody().getExpireTime());
            expireTime = response.getBody().getExpireTime();
            token = response.getBody().getToken();
        }

        {
            VerifyTokenExRequest request = new VerifyTokenExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setToken(token);
            ResponseEntity<VerifyTokenResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/sso/verify_token",request, VerifyTokenResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            Assert.assertEquals(JOB_NUMBER,response.getBody().getJobNumber());
        }
        //等待token过期后在测试
        {
            try {
                Thread.sleep(expireTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VerifyTokenExRequest request = new VerifyTokenExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setToken(token);
            ResponseEntity<VerifyTokenResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/sso/verify_token",request, VerifyTokenResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.BIZ.TOKEN_NOT_EXIST);
        }
    }
}