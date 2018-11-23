package com.mszq.uas.uasserver;

import com.mszq.uas.uasserver.ldap.LdapClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UasServerApplicationTests {

//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private PersonService service;
    @Autowired
    private LdapClient client;
    @Test
    public void contextLoads() {

//        Person per = new Person();
//        per.setPassword("abc");
//        per.setUsername("liugy");
//
//        Name dn = LdapNameBuilder.newInstance()
////                .add("ou", "user")
//                .add("cn","liugy")
//                .build();
//
//        per.setId(dn);
//        service.create("liugy1","abc");
//        client.create("llll","dddd");
//        System.out.println(client.search("llll1"));
//        client.authenticate("llll","dddd");
//        client.modify("llll","aaa");
//        client.authenticate("llll","aaa1");
//        client.delete("lll");
//        System.out.println(client.search("llll"));
//        personRepository.findAll().forEach(p -> {
//            System.out.println(p);
//        });
//        service.findAll().forEach(p -> {
//            System.out.println(p);
//        });
    }

}
