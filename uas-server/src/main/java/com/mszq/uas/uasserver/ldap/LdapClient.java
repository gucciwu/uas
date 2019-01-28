package com.mszq.uas.uasserver.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.*;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class LdapClient {

    @Autowired
    private Environment env;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Value("${uas.server.ldap.base.group}")
    private String groupName;

    public boolean authenticate(final String username, final String password) {
        try {
            contextSource.getContext("cn=" + username + ",ou="+groupName+"," + env.getRequiredProperty("spring.ldap.base"), password);
            return true;
        }catch (AuthenticationException ex){
            return false;
        }
    }

    public List<Person> search(final String username) {

        String filter = "cn=" + username;
        List<Person> list = ldapTemplate.search("ou="+groupName, filter, new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                Person person = new Person();
                Attribute a = attributes.get("cn");
                if (a != null) person.setUsername((String)a.get());
                a = attributes.get("sn");
                if (a != null) person.setName((String)a.get());
                return person;
            }
        });

        return list;
    }

    public boolean  create(final String username, final String fullname, final String password, final String email, final String mobile) {

        try {
            // 基类设置
            BasicAttribute ocattr = new BasicAttribute("objectClass");
            ocattr.add("top");
            ocattr.add("person");
            ocattr.add("inetOrgPerson");
            ocattr.add("organizationalPerson");
            // 用户属性
            Attributes attrs = new BasicAttributes();
            attrs.put(ocattr);
            attrs.put("cn", username);
            attrs.put("sn", fullname);
            attrs.put("displayName", fullname);
            attrs.put("mail", email);
            attrs.put("mobile", mobile);
            attrs.put("userPassword", digestSHA(password));
            ldapTemplate.bind("cn=" + username+",ou="+groupName, null, attrs);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean modify(final String username, final String fullname, final String password, final String email, final String mobile) {
        try {
            List<ModificationItem> list = new ArrayList<ModificationItem>();

            if(fullname != null) {
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("displayName", fullname)));
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", fullname)));
            }
            if(email!= null){
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mail", email)));
            }

            if(password != null){
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", digestSHA(password))));
            }

            if(mobile != null){
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mobile", mobile)));
            }

            ModificationItem[] items = new ModificationItem[list.size()];
            list.toArray(items);
            ldapTemplate.modifyAttributes("cn=" + username+",ou="+groupName, items);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(String username){
        try {
            ldapTemplate.unbind("cn=" + username+",ou="+groupName);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64
                    .getEncoder()
                    .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{SHA}" + base64;
    }
}
