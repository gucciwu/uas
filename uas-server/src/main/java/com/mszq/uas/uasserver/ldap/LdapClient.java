package com.mszq.uas.uasserver.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.*;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class LdapClient {

    @Autowired
    private Environment env;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private LdapTemplate ldapTemplate;

    public boolean authenticate(final String username, final String password) {
        try {
            contextSource.getContext("cn=" + username + ",ou=users," + env.getRequiredProperty("spring.ldap.base"), password);
            return true;
        }catch (AuthenticationException ex){
            return false;
        }
    }

    public List<Person> search(final String username) {

        String filter = "cn=" + username;
        List<Person> list = ldapTemplate.search("ou=users", filter, new AttributesMapper() {
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

    public void create(final String username, final String fullname, final String password) {
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("cn", username)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", fullname);
        context.setAttributeValue("userPassword", digestSHA(password));

        ldapTemplate.bind(context);
    }

    public void modify(final String username, final String password) {

        List<Person> personList = search(username);
        if(personList == null || personList.isEmpty())
            return;

        Person person = personList.get(0);

        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("cn", username)
                .build();
        DirContextOperations context = ldapTemplate.lookupContext(dn);

        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", person.getName());
        context.setAttributeValue("userPassword", digestSHA(password));

        ldapTemplate.modifyAttributes(context);
    }

    public void delete(String username){
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("cn", username)
                .build();
        ldapTemplate.unbind(dn);
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
