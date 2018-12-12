package com.mszq.uas.uasserver.controller;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.UasServerApplication;
import com.mszq.uas.uasserver.bean.*;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UasServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    @LocalServerPort
    private int port;

    private URL base;

    private HttpHeaders headers = new HttpHeaders();
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    @Qualifier("config")
    private Config config;
    final long APPID=1L;
    final String SECRET="1";

    private String JOB_NUMBER_1 = "6666666";
    private String JOB_NUMBER_2 = "66666666622";
    private String JOB_NUMBER_3 = "66666666623";

    private String ID_1 ="711502198658121560";
    private String ID_2 ="711502198658121561";
    private String ID_3 ="711502198658121562";

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        //创建用户1
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            User user = new User();
            user.setIdNumber(ID_1);
            user.setEmail("xxxxx@126.com");
            user.setIdType((short) 1);
            user.setMobile("1858686542");
            user.setJobNumber(JOB_NUMBER_1);
            user.setName("测试用户AAAA");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        //创建用户2
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            User user = new User();
            user.setIdNumber(ID_2);
            user.setEmail("xx222xx@126.com");
            user.setIdType((short) 1);
            user.setMobile("1858776542");
            user.setJobNumber(JOB_NUMBER_2);
            user.setName("测试用户AAAA");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.UNSIGNED);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        //创建用户3
        {
            UpdateUserExRequest request = new UpdateUserExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            User user = new User();
            user.setIdNumber(ID_3);
            user.setEmail("xx222xx@126.com");
            user.setIdType((short) 1);
            user.setMobile("1858776542");
            user.setJobNumber(JOB_NUMBER_3);
            user.setName("测试用户AAAA");
            user.setOrgId(1L);
            user.setOrgType((short) 1);
            user.setStatus(Constant.USER_STATUS.OK);
            request.setUser(user);

            ResponseEntity<UpdateUserResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/update_user", request, UpdateUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }

        //设置用户1的密码
        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setJobNumber(JOB_NUMBER_1);
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        //设置用户2的密码
        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setJobNumber(JOB_NUMBER_2);
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
        //设置用户3的密码
        {
            ResetPasswordExRequest request = new ResetPasswordExRequest();
            request.set_appId(APPID);
            request.set_secret(SECRET);
            request.setJobNumber(JOB_NUMBER_3);
            request.setNewPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));

            ResponseEntity<ResetPasswordResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/datasync/reset_password", request, ResetPasswordResponse.class, "");
            System.out.println(String.format("测试结果为：%s", response.getBody()));
            Assert.assertEquals(0, response.getBody().getCode());
        }
    }

    @After
    public void tearDown() throws Exception{
        {
            DelUserExRequest req = new DelUserExRequest();
            req.setJobNumber(JOB_NUMBER_1);
            req.set_appId(APPID);
            req.set_secret(SECRET);
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/delele_user", req, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
        {
            DelUserExRequest req = new DelUserExRequest();
            req.setJobNumber(JOB_NUMBER_2);
            req.set_appId(APPID);
            req.set_secret(SECRET);
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/delele_user", req, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
        {
            DelUserExRequest req = new DelUserExRequest();
            req.setJobNumber(JOB_NUMBER_3);
            req.set_appId(APPID);
            req.set_secret(SECRET);
            ResponseEntity<DelUserResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/datasync/delele_user", req, DelUserResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
    }
    @Test
    public void auth() throws Exception {
        //正常认证
        {
            AuthExRequest request = new AuthExRequest();
            request.setJobNumber(JOB_NUMBER_1);
            request.setPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));
            ResponseEntity<AuthResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/ua/auth", request, AuthResponse.class, "");
            System.out.println(String.format("测试结果为：%s", resp.getBody()));
            Assert.assertEquals(0, resp.getBody().getCode());
        }
        //账户状态为注销
        {
            AuthExRequest request = new AuthExRequest();
            request.setJobNumber(JOB_NUMBER_2);
            request.setPassword(AESCoder.encrypt("123456", "SMW+RuTwO5ObncmeF5NjMA=="));
            ResponseEntity<AuthResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/ua/auth", request, AuthResponse.class, "");
            System.out.println(String.format("测试结果为：[%s]%s", resp.getBody().getCode(), resp.getBody().getMsg()));
            Assert.assertEquals(CODE.BIZ.AUTH_FAIL, resp.getBody().getCode());
            Assert.assertEquals(resp.getBody().getMsg().contains("注销"), true);
        }
        //联系认证错误多次，账户锁定
        {
            for(int i=0;i<config.getErrorTry()-1;i++) {
                AuthExRequest request = new AuthExRequest();
                request.setJobNumber(JOB_NUMBER_3);
                request.setPassword(AESCoder.encrypt("12345611", "SMW+RuTwO5ObncmeF5NjMA=="));
                ResponseEntity<AuthResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/ua/auth", request, AuthResponse.class, "");
                System.out.println(String.format("测试结果为：[%s]%s", resp.getBody().getCode(), resp.getBody().getMsg()));
                Assert.assertEquals(resp.getBody().getMsg().contains(""+(config.getErrorTry()-i-1)), true);
            }

            AuthExRequest request = new AuthExRequest();
            request.setJobNumber(JOB_NUMBER_3);
            request.setPassword(AESCoder.encrypt("12345611", "SMW+RuTwO5ObncmeF5NjMA=="));
            ResponseEntity<AuthResponse> resp = this.restTemplate.postForEntity(this.base.toString() + "/ua/auth", request, AuthResponse.class, "");
            System.out.println(String.format("测试结果为：[%s]%s", resp.getBody().getCode(), resp.getBody().getMsg()));
            Assert.assertEquals(resp.getBody().getMsg().contains("锁定"), true);
        }
    }

    @Test
    public void signout() {
    }
}