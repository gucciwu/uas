package com.mszq.uas.uasserver;

import com.mszq.uas.uasserver.controller.AuthControllerTest;
import com.mszq.uas.uasserver.controller.DataSyncControllerTest;
import com.mszq.uas.uasserver.controller.PermissionControllerTest;
import com.mszq.uas.uasserver.controller.SSOControllerTest;
import com.mszq.uas.uasserver.ldap.LdapClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Suite.SuiteClasses({AuthControllerTest.class, DataSyncControllerTest.class,PermissionControllerTest.class,SSOControllerTest.class})
public class UasServerApplicationSuit {
}
