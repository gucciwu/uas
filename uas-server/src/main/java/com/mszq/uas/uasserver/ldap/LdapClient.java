package com.mszq.uas.uasserver.ldap;

import com.mszq.uas.uasserver.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.*;
import org.springframework.ldap.support.LdapNameBuilder;
import sun.misc.BASE64Encoder;

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
                a = attributes.get("displayName");
                if (a != null) person.setDisplayName((String)a.get());
                a = attributes.get("mail");
                if (a != null) person.setEmail((String)a.get());
                a = attributes.get("mobile");
                if (a != null) person.setMobile((String)a.get());
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
            if (fullname != null && !"".equals(fullname)) {
                attrs.put("sn", fullname);
                attrs.put("displayName", fullname);
            }

            if (email != null && !"".equals(email)) {
                attrs.put("mail", email);
            }

            if (mobile != null && !"".equals(mobile)){
                attrs.put("mobile", mobile);
            }

            attrs.put("userPassword", digestMD5(password));
            ldapTemplate.bind("cn=" + username+",ou="+groupName, null, attrs);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean create1(final String username, final String fullname, final String password, final String email, final String mobile) {

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
            if (fullname != null && !"".equals(fullname)) {
                attrs.put("sn", fullname);
                attrs.put("displayName", fullname);
            }

            if (email != null && !"".equals(email)) {
                attrs.put("mail", email);
            }

            if (mobile != null && !"".equals(mobile)){
                attrs.put("mobile", mobile);
            }

            attrs.put("userPassword", digestMD5(password));
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
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", digestMD5(password))));
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

    public boolean modify1(final String username, final String fullname, final String password, final String email, final String mobile) {
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
                list.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", digestMD5(password))));
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

    public static String digestMD5(String md5pass){
//        String md5pass = MD5Utils.parseStrToMd5L32(_md5pass);

        BASE64Encoder base64en = new BASE64Encoder();
        byte[] baKeyword = new byte[md5pass.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(md5pass.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String newstr = base64en.encode(baKeyword);
        return "{MD5}"+newstr;
    }

    public static void main(String[] args){
        String pwd = "Liu_Guo_1982";
        String md5 = MD5Utils.parseStrToMd5L32(pwd);
        System.out.println(md5);
        String s = digestMD5(md5);
        System.out.println(s);
    }
}
