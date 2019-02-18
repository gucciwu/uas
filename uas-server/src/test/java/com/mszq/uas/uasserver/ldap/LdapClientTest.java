package com.mszq.uas.uasserver.ldap;

import com.mszq.uas.uasserver.UasServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UasServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LdapClientTest {

    @Autowired
    private LdapClient ldapClient;

    @Test
    public void create() {
//        ldapClient.create("abc","abc","abc",null,null);
    }

    @Test
    public void modify() {
        ldapClient.modify("abc","abc","abc","aaa@12.com","999999999");
    }

    @Test
    public void delete() {

    }
}
