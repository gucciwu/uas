package com.mszq.uas.uasserver.controller;

import com.alibaba.fastjson.JSON;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.UasServerApplication;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.model.Role;
import com.mszq.uas.uasserver.dao.model.RoleType;
import com.mszq.uas.uasserver.dao.model.User;
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
            request.set_appId(1L);
            request.set_secret("1");
            ResponseEntity<AddRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role_type", request, AddRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            roleTypeId = response.getBody().getRoleTypeId();
        }

        {
            DelRoleTypeExRequest request = new DelRoleTypeExRequest();
            request.setRoleType(roleTypeId);
            request.set_appId(1L);
            request.set_secret("1");

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
            request.set_appId(1L);
            request.set_secret("1");
            RoleType rt = new RoleType();
            rt.setName("测试角色分类");
            request.setRoleType(rt);
            ResponseEntity<AddRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/add_role_type", request, AddRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            roleTypeId = response.getBody().getRoleTypeId();
        }
        {
            AddRoleExRequest request = new AddRoleExRequest();
            request.set_appId(1L);
            request.set_secret("1");
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
            request.set_appId(1L);
            request.set_secret("1");
            request.setRoleName(NAME);

            ResponseEntity<GetRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/get_roles", request, GetRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);

            Role role = JSON.parseObject(JSON.toJSONString(response.getBody().getData().get(0)), Role.class);
            Assert.assertEquals(NAME, role.getRoleName());
        }

        {
            DelRoleExRequest request = new DelRoleExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setRoleId(roleId);

            ResponseEntity<DelRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role", request, DelRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }

        {
            GetRoleExRequest request = new GetRoleExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setRoleName(NAME);

            ResponseEntity<GetRoleResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/get_roles", request, GetRoleResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
            Assert.assertEquals(0,response.getBody().getData().size());
        }

        {
            DelRoleTypeExRequest request = new DelRoleTypeExRequest();
            request.set_appId(1L);
            request.set_secret("1");
            request.setRoleType(roleTypeId);

            ResponseEntity<DelRoleTypeResponse> response = this.restTemplate.postForEntity(this.base.toString() + "/permission/del_role_type", request, DelRoleTypeResponse.class, "");
            Assert.assertEquals(response.getBody().getCode(), CODE.SUCCESS);
        }
    }

    @Test
    public void delRole() {
    }

    @Test
    public void modifyRole() {
    }

    @Test
    public void addAppToRole() {
    }

    @Test
    public void delAppToRole() {
    }

    @Test
    public void addRoleToUser() {
    }

    @Test
    public void addRoleToUser1() {
    }
}