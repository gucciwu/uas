package com.mszq.uas.uasserver.controller;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.UasServerApplication;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.mapper.AppMapper;
import com.mszq.uas.uasserver.dao.model.App;
import com.mszq.uas.uasserver.dao.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UasServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataSyncControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addApp() {
//        AddAppRequest request = new AddAppRequest();
//        App app = new App();
//        app.setSecret("aaa");
//        app.setName("测试系统A");
//        app.setOrgType((short) 1);
//        app.setPath("");
//        request.setApp(app);
//        ResponseEntity<AddAppResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/add_app",request, AddAppResponse.class, "");
//
////        Assert.assertNotNull(response);
//        Assert.assertEquals(200,response.getStatusCodeValue());

    }

    @Test
    public void updateUser() {

        UpdateUserExRequest request = new UpdateUserExRequest();
        request.set_appId(1L);
        request.set_secret("1");
        User user = new User();
        user.setIdNumber("511502198658121430");
        user.setEmail("liugss@126.com");
        user.setIdType((short) 1);
        user.setMobile("1810686542");
        user.setJobNumber("112078");
        user.setName("六三");
        user.setOrgId(1L);
        user.setOrgType((short) 1);
        user.setStatus(Constant.USER_STATUS.OK);
        request.setUser(user);

        ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user",request, UpdateUserResponse.class, "");
        System.out.println(String.format("测试结果为：%s", response.getBody()));
        Assert.assertEquals(200,response.getStatusCodeValue());

        user.setMobile("1810686551");
        response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user",request, UpdateUserResponse.class, "");
        System.out.println(String.format("测试结果为：%s", response.getBody()));
        Assert.assertEquals(200,response.getStatusCodeValue());

        GetUsersExRequest request1 = new GetUsersExRequest();
        request1.setJobNumber(user.getJobNumber());
        ResponseEntity<GetUsersResponse> response1 = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_users",request, GetUsersResponse.class, "");
        Assert.assertEquals(200,response1.getStatusCodeValue());
        Assert.assertEquals(response1.getBody().getCode(),CODE.BIZ.APP_SECRET_NOT_MATCH);

        request1.set_appId(1L);
        request1.set_secret("1");
        response1 = this.restTemplate.postForEntity(this.base.toString() + "/datasync/get_users",request, GetUsersResponse.class, "");
        Assert.assertEquals(200,response1.getStatusCodeValue());
        Assert.assertEquals(response1.getBody().getCode(),CODE.SUCCESS);
        User u = (User)response1.getBody().getData().get(0);
        Assert.assertEquals("1810686551",u.getMobile());
    }

    @Test
    public void delUser() {
    }

    @Test
    public void addIdToApp() {
    }

    @Test
    public void delIdToApp() {
    }

    @Test
    public void changeIdToApp() {
    }

    @Test
    public void changePassword() {
    }

    @Test
    public void resetPassword() {
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void getAppAccountId() {
    }

    @Test
    public void addOrg() {
    }

    @Test
    public void delOrg() {
    }

    @Test
    public void getOrgs() {
    }

    @Test
    public void modifyOrg() {
    }

    @Test
    public void addOrgType() {
    }

    @Test
    public void delOrgType() {
    }
}