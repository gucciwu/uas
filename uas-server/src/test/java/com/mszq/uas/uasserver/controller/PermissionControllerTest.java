package com.mszq.uas.uasserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.UasServerApplication;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UasServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermissionControllerTest {
    @LocalServerPort
    private int port;

    private URL base;
    final long APPID=1L;
    final String SECRET="1";
    private HttpHeaders headers = new HttpHeaders();
    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    }

    @Test
    public void roleTypeAddDel() {
        long roleTypeId = 0;
        {
            AddRoleTypeExRequest request = new AddRoleTypeExRequest();
            RoleType rt = new RoleType();
            rt.setName("测试角色分类");
            request.setRoleType(rt);
            request.set_appId(APPID);
            request.set_secret(SECRET);
            ResponseEntity<AddRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role_type", request, AddRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            roleTypeId = response.getBody().getRoleTypeId();
        }

        {
            DelRoleTypeExRequest request = new DelRoleTypeExRequest();
            request.setRoleType(roleTypeId);
            request.set_appId(APPID);
            request.set_secret(SECRET);

            ResponseEntity<DelRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role_type", request, DelRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
    }

    @Test
    public void addRole() {
        long roleId = 0;
        long roleTypeId = 0;
        String NAME = "测试角色1111";
        {
            AddRoleTypeExRequest request = new AddRoleTypeExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            RoleType rt = new RoleType();
            rt.setName("测试角色分类");
            request.setRoleType(rt);
            ResponseEntity<AddRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role_type", request, AddRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            roleTypeId = response.getBody().getRoleTypeId();
        }
        {
            AddRoleExRequest request = new AddRoleExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            Role r = new Role();
            r.setRoleName(NAME);
            r.setRoleTypeId((int)roleTypeId);
            request.setRole(r);
            ResponseEntity<AddRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role", request, AddRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            roleId = response.getBody().getRoleId();
        }
        {
            GetRoleExRequest request = new GetRoleExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setRoleName(NAME);

            ResponseEntity<GetRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/get_roles", request, GetRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);

            Role role = JSON.parseObject(JSON.toJSONString(response.getBody().getData().get(0)), Role.class);
            Assert.assertEquals(NAME, role.getRoleName());
        }

        {
            DelRoleExRequest request = new DelRoleExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setRoleId(roleId);

            ResponseEntity<DelRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role", request, DelRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }

        {
            GetRoleExRequest request = new GetRoleExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setRoleName(NAME);

            ResponseEntity<GetRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/get_roles", request, GetRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            Assert.assertEquals(0,response.getBody().getData().size());
        }

        {
            DelRoleTypeExRequest request = new DelRoleTypeExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setRoleType(roleTypeId);

            ResponseEntity<DelRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role_type", request, DelRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
    }

    @Test
    public void assignPermissionToUser() {
        long appId = 0;
        long roleId = 0;
        final String JOB_NUMBER ="333333";
        final String MOBILE_1 = "1810888542";
        long userId = 0;
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
            user.setMobile(MOBILE_1);
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
        //查询是否已经分配了子账号
        {
            GetAppAccountIdExRequest request = new GetAppAccountIdExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setUserId(userId);
            request.setAppId(appId);

            ResponseEntity<GetAppAccountIdResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_app_account_id", request, GetAppAccountIdResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());

            GetAppAccountIdResponse resp = response.getBody();
            JSONArray array = JSON.parseArray(JSON.toJSONString(response.getBody().getData()));
            Assert.assertEquals(1,array.size());
            AppAccount appAccount1 = null;
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                AppAccount _aa = JSON.parseObject(o.toJSONString(), AppAccount.class);
                if (JOB_NUMBER.equals(_aa.getAccount())) {
                    appAccount1 = _aa;
                    break;
                }
            }
            Assert.assertNotNull(appAccount1);
        }
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
        //查询分配的子账号是否已经删除
        {
            GetAppAccountIdExRequest request = new GetAppAccountIdExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setUserId(userId);
            request.setAppId(appId);

            ResponseEntity<GetAppAccountIdResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_app_account_id", request, GetAppAccountIdResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());

            GetAppAccountIdResponse resp = response.getBody();
            JSONArray array = JSON.parseArray(JSON.toJSONString(response.getBody().getData()));
            Assert.assertEquals(0,array.size());
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
}