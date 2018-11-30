package com.mszq.uas.uasserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.UasServerApplication;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.util.AESCoder;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UasServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataSyncControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    private HttpHeaders headers = new HttpHeaders();


    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addApp() {
        long appId = 0;
        {
            AddAppRequest request = new AddAppRequest();
            App app = new App();
            app.setSecret("aaa");
            app.setName("测试系统A");
            app.setOrgType((short) 1);
            app.setPath("");
            request.setApp(app);
            ResponseEntity<AddAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/add_app", request, AddAppResponse.class, "");
            Assert.assertEquals(200, response.getStatusCodeValue());
            appId = response.getBody().getAppId();
        }

        {
            GetAppRequest request = new GetAppRequest();
            request.set_appId(1L);
            request.set_secret("1");
            ResponseEntity<GetAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_apps", request, GetAppResponse.class, "");
            Assert.assertEquals(200, response.getStatusCodeValue());
            List<App> appList= response.getBody().getData();
            Assert.assertTrue(appList.size()>0);
        }

        //删除应用
        {
            DelAppRequest request = new DelAppRequest();
            request.setAppId(appId);
            request.set_appId(1L);
            request.set_secret("1");
            ResponseEntity<DelAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_app",request, DelAppResponse.class, "");
            Assert.assertEquals(200,response.getStatusCodeValue());
        }
    }

    @Test
    public void updateUser() throws InterruptedException {
        final String JOB_NUMBER ="112078";
        final String MOBILE_1 = "1810686542";
        final String MOBILE_2 = "1810686551";
        User selectUser = null;
        {
            GetUsersExRequest errorRequest = new GetUsersExRequest();
            ResponseEntity<GetUsersResponse> errorresponse = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_users", errorRequest, GetUsersResponse.class, "");
            Assert.assertEquals(200, errorresponse.getStatusCodeValue());
            Assert.assertEquals(errorresponse.getBody().getCode(), CODE.SYS.APP_SECRET_NOT_MATCH);

            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            User user = new User();
            user.setIdNumber("511502198658121430");
            user.setEmail("liugss@126.com");
            user.setIdType((short) 1);
            user.setMobile(MOBILE_1);
            user.setJobNumber(JOB_NUMBER);
            user.setName("六三");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(200, response.getStatusCodeValue());
        }

//        Thread.sleep(100);
        {
            GetUsersExRequest request = new GetUsersExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER);
            ResponseEntity<GetUsersResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_users", request, GetUsersResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            selectUser = JSON.parseObject(JSON.toJSONString(response.getBody().getData().get(0)), User.class);
            Assert.assertEquals(selectUser.getJobNumber(), JOB_NUMBER);
        }
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            selectUser.setMobile(MOBILE_2);
            request.setUser(selectUser);
            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(200, response.getStatusCodeValue());
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }

        {
            GetUsersExRequest request = new GetUsersExRequest();
            request = new GetUsersExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(selectUser.getJobNumber());
            ResponseEntity<GetUsersResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_users", request, GetUsersResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            User u = JSON.parseObject(JSON.toJSONString(response.getBody().getData().get(0)), User.class);
            Assert.assertEquals(MOBILE_2, u.getMobile());
        }
    }

    @Test
    public void adddelUser() {
        final String JOB_NUMBER = "112089";
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            User user = new User();
            user.setIdNumber("511502198658121560");
            user.setEmail("xxxxx@126.com");
            user.setIdType((short) 1);
            user.setMobile("1858686542");
            user.setJobNumber(JOB_NUMBER);
            user.setName("双击六个六");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        {
            DelUserExRequest req = new DelUserExRequest();
            req.setJobNumber(JOB_NUMBER);
            req.set_appId(1L);
            req.set_secret("1");
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_user", req, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
    }

    @Test
    public void IdToAppCRUD() {

        final String JOB_NUMBER_1 = "999999";
        final String JOB_NUMBER_2 = "888888";
        final long USER_ID = 5555L;
        final long APP_ID = 5555L;
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            User user = new User();
            user.setIdNumber("511111198658121430");
            user.setEmail("ssss@126.com");
            user.setIdType((short) 1);
            user.setMobile("1810611542");
            user.setJobNumber(JOB_NUMBER_1);
            user.setName("六22三");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(200, response.getStatusCodeValue());
        }
        {
            AddIdToAppExRequest request = new AddIdToAppExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            AppAccount aa = new AppAccount();
            aa.setAccount("abc");
            aa.setAppId(APP_ID);
            aa.setJobNumber(JOB_NUMBER_1);
            aa.setOrgId(4L);
            aa.setOrgType((short) 1);
            aa.setUserId(USER_ID);
            request.setAppAccount(aa);

            ResponseEntity<AddIdToAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/add_id_to_app", request, AddIdToAppResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            ChangeIdToAppExRequest request = new ChangeIdToAppExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            AppAccount bb = new AppAccount();
            bb.setAccount("abc");
            bb.setAppId(APP_ID);
            bb.setJobNumber(JOB_NUMBER_2);
            bb.setOrgId(4L);
            bb.setOrgType((short) 1);
            bb.setUserId(USER_ID);
            request.setAppAccount(bb);

            ResponseEntity<ChangeIdToAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/change_id_to_app", request, ChangeIdToAppResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            GetAppAccountIdExRequest request = new GetAppAccountIdExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setUserId(USER_ID);

            ResponseEntity<GetAppAccountIdResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_app_account_id", request, GetAppAccountIdResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());

            GetAppAccountIdResponse resp = response.getBody();
            JSONArray array = JSON.parseArray(JSON.toJSONString(response.getBody().getData()));
            AppAccount appAccount1 = null;
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                AppAccount _aa = JSON.parseObject(o.toJSONString(), AppAccount.class);
                if (JOB_NUMBER_2.equals(_aa.getJobNumber())) {
                    appAccount1 = _aa;
                    break;
                }
            }
            Assert.assertNotNull(appAccount1);
        }

        {
            DelIdToAppExRequest request = new DelIdToAppExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setAppId(APP_ID);
            request.setUserId(USER_ID);

            ResponseEntity<DelIdToAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_id_to_app", request, DelIdToAppResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            DelUserExRequest request = new DelUserExRequest();
            request.setJobNumber(JOB_NUMBER_1);
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_user", request, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
    }

    @Test
    public void changePassword() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        final String JOB_NUMBER_1 = "999999";
        final long USER_ID = 5555L;
        final long APP_ID = 5555L;
        long userId = 0;
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            User user = new User();
            user.setIdNumber("511111198658121430");
            user.setEmail("ssss@126.com");
            user.setIdType((short) 1);
            user.setMobile("1810611542");
            user.setJobNumber(JOB_NUMBER_1);
            user.setName("六22三");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(200, response.getStatusCodeValue());
            userId = response.getBody().getUserId();
        }
        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        {
            ChangePasswordExRequest request = new ChangePasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setOldPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ChangePasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/change_password", request, ChangePasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            ChangePasswordExRequest request = new ChangePasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setOldPassword(AESCoder.encrypt("654321", "SMW+RuTwO5ObncmeF5NjMA=="));
            request.setNewPassword(AESCoder.encrypt("111111", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ChangePasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/change_password", request, ChangePasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(CODE.BIZ.PASSOWRD_NOT_MATCH, response.getBody().getCode());
        }

        {
            ChangePasswordExRequest request = new ChangePasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setOldPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ChangePasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/change_password", request, ChangePasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        {
            ChangePasswordExRequest request = new ChangePasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setOldPassword(AESCoder.encrypt("654321", "SMW+RuTwO5ObncmeF5NjMA=="));
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ChangePasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/change_password", request, ChangePasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(CODE.BIZ.PASSOWRD_NOT_MATCH, response.getBody().getCode());
        }
        {
            DelUserExRequest request = new DelUserExRequest();
            request.setJobNumber(JOB_NUMBER_1);
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_user", request, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
    }

    @Test
    public void resetPassword() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        final String JOB_NUMBER_1 = "999999";
        final long USER_ID = 5555L;
        final long APP_ID = 5555L;

        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            User user = new User();
            user.setIdNumber("511111198658121430");
            user.setEmail("ssss@126.com");
            user.setIdType((short) 1);
            user.setMobile("1810611542");
            user.setJobNumber(JOB_NUMBER_1);
            user.setName("六22三");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(200, response.getStatusCodeValue());
        }
        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setJobNumber(JOB_NUMBER_1);
            request.setNewPassword(AESCoder.encrypt("654321", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        {
            DelUserExRequest request = new DelUserExRequest();
            request.setJobNumber(JOB_NUMBER_1);
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_user", request, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
    }

    @Test
    public void OrgCRUD() {
        short orgTypeId = 1;
        long innerId = 0;
        final long ORG_ID_1 = 9999L;
        final long ORG_ID_2 = 9999L;
        {
            AddOrgTypeExRequest request = new AddOrgTypeExRequest();
            OrgType ot = new OrgType();
            ot.setComment("测试");
            request.setOrgType(ot);
            request.set_appId(1L);
            request.set_secret("1");

            ResponseEntity<AddOrgTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/add_org_type", request, AddOrgTypeResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
            orgTypeId = response.getBody().getOrgTypeId();
        }

        {
            UpdateOrgRequest  request = new UpdateOrgRequest();
            request.set_appId(1L);
            request.set_secret("1");
            Org org = new Org();
            org.setName("测试部门");
            org.setGrade(1);
            org.setStatus(Constant.ORG_STATUS.OK);
            org.setComment("测试");
            org.setOrgId(ORG_ID_1);
            org.setOrgType(orgTypeId);
            request.setOrg(org);

            ResponseEntity<UpdateOrgResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_org", request, UpdateOrgResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            GetOrgsExRequest  request = new GetOrgsExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setOrgId(ORG_ID_1);
            request.setOrgType(orgTypeId);

            ResponseEntity<GetOrgsResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_orgs", request, GetOrgsResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());

            GetOrgsResponse resp = response.getBody();
            JSONArray array = JSON.parseArray(JSON.toJSONString(response.getBody().getData()));
            Org org = null;
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Org _org = JSON.parseObject(o.toJSONString(), Org.class);
                if (ORG_ID_1==_org.getOrgId()) {
                    org = _org;
                    break;
                }
            }
            Assert.assertNotNull(org);
        }

        {
            UpdateOrgRequest  request = new UpdateOrgRequest();
            request.set_appId(1L);
            request.set_secret("1");
            Org org = new Org();
            org.setName("测试部门");
            org.setGrade(1);
            org.setStatus(Constant.ORG_STATUS.UNSIGNED);
            org.setComment("测试");
            org.setOrgId(ORG_ID_1);
            org.setOrgType(orgTypeId);
            request.setOrg(org);

            ResponseEntity<UpdateOrgResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_org", request, UpdateOrgResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            GetOrgsExRequest  request = new GetOrgsExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setOrgId(ORG_ID_1);
            request.setOrgType(orgTypeId);
            request.setStatus(Constant.ORG_STATUS.UNSIGNED);

            ResponseEntity<GetOrgsResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_orgs", request, GetOrgsResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());

            GetOrgsResponse resp = response.getBody();
            JSONArray array = JSON.parseArray(JSON.toJSONString(response.getBody().getData()));
            Org org = null;
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Org _org = JSON.parseObject(o.toJSONString(), Org.class);
                if (ORG_ID_1==_org.getOrgId()) {
                    org = _org;
                    break;
                }
            }
            Assert.assertNotNull(org);
        }

        {
            DelOrgExRequest  request = new DelOrgExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setOrgId(ORG_ID_1);
            request.setOrgType(orgTypeId);

            ResponseEntity<DelOrgResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_org", request, DelOrgResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        {
            GetOrgsExRequest  request = new GetOrgsExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setOrgId(ORG_ID_1);
            request.setOrgType(orgTypeId);

            ResponseEntity<GetOrgsResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_orgs", request, GetOrgsResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());

            GetOrgsResponse resp = response.getBody();
            JSONArray array = JSON.parseArray(JSON.toJSONString(response.getBody().getData()));
            Org org = null;
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                Org _org = JSON.parseObject(o.toJSONString(), Org.class);
                if (ORG_ID_1==_org.getOrgId()) {
                    org = _org;
                    break;
                }
            }
            Assert.assertEquals((short)org.getStatus(),Constant.ORG_STATUS.UNSIGNED);
        }

        {
            DelOrgTypeExRequest request = new DelOrgTypeExRequest();
            request.setOrgType(orgTypeId);
            request.set_appId(1L);
            request.set_secret("1");

            ResponseEntity<DelOrgTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/del_org_type", request, DelOrgTypeResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
    }
}