package com.mszq.uas.uasserver.controller;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.mapper.*;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.ldap.LdapClient;
import com.mszq.uas.uasserver.ldap.Person;
import com.mszq.uas.uasserver.service.AppSecretVerifyService;
import com.mszq.uas.uasserver.service.IpBlackCheckService;
import com.mszq.uas.uasserver.util.AESCoder;
import com.mszq.uas.uasserver.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class DataSyncController {

    @Autowired
    @Qualifier("config")
    private Config config;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AppAccountMapper appAccountMapper;
    @Autowired
    private PasswordMapper passwordMapper;
    @Autowired
    private OrgMapper orgMapper;
    @Autowired
    private OrgTypeMapper orgTypeMapper;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private LdapClient ldapClient;
    @Autowired
    private AppSecretVerifyService appSecretVerifyService;
    @Autowired
    private IpBlackCheckService ipBlackCheckService;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value="/datasync/update_user",method = RequestMethod.POST)
    public @ResponseBody
    UpdateUserResponse updateUser(@RequestBody UpdateUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        UpdateUserResponse response = new UpdateUserResponse();

        UserExample ue = new UserExample();
        ue.createCriteria().andJobNumberEqualTo(request.getUser().getJobNumber());
        List<User> userList = userMapper.selectByExample(ue);
        if(userList == null || userList.size() == 0){
            //插入
            User user = request.getUser();
            user.setCreateTime(new Date());
            user.setModifyTime(new Date());
            int ret = userMapper.insert(request.getUser());
            if(ret == 0){
                response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                response.setMsg("插入失败");
                return response;
            }
            response.setUserId(user.getId());
        }else{
            //更新
            User user = userList.get(0);
            user.setModifyTime(new Date());
            request.getUser().setId(user.getId());
            int ret = userMapper.updateByPrimaryKey(request.getUser());
            if(ret == 0){
                response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
                response.setMsg("更新失败");
                return response;
            }
            response.setUserId(user.getId());
        }

        //同步ldap数据，如果失败则记录信息，不影响正常运行
        try{
             List<Person> ldapPersons = ldapClient.search(request.getUser().getJobNumber());
            if(ldapPersons == null || ldapPersons.size() == 0){
                ldapClient.create(request.getUser().getJobNumber(), request.getUser().getName(),"mszq@123");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("更新用户成功");
        return response;
    }

    @RequestMapping(value="/datasync/delele_user",method = RequestMethod.POST)
    public @ResponseBody
    DelUserResponse delUser(@RequestBody DelUserExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelUserResponse response = new DelUserResponse();

        UserExample ue = new UserExample();
        ue.createCriteria().andJobNumberEqualTo(request.getJobNumber());
        int ret = userMapper.deleteByExample(ue);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
            return response;
        }else{
            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                ldapClient.delete(request.getJobNumber());
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/datasync/add_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    AddIdToAppResponse addIdToApp(@RequestBody AddIdToAppExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddIdToAppResponse response = new AddIdToAppResponse();

        int ret = appAccountMapper.insert(request.getAppAccount());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/datasync/del_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    DelIdToAppResponse delIdToApp(@RequestBody DelIdToAppExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelIdToAppResponse response = new DelIdToAppResponse();

        AppAccountExample aae = new AppAccountExample();
        aae.createCriteria().andUserIdEqualTo(request.getAppId()).andAppIdEqualTo(request.getUserId());
        int ret = appAccountMapper.deleteByExample(aae);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
            return response;
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/datasync/change_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    ChangeIdToAppResponse changeIdToApp(@RequestBody ChangeIdToAppExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ChangeIdToAppResponse response = new ChangeIdToAppResponse();

        AppAccountExample aae = new AppAccountExample();
        aae.createCriteria().andUserIdEqualTo(request.getAppAccount().getUserId()).andAppIdEqualTo(request.getAppAccount().getAppId());
        int ret = appAccountMapper.updateByExample(request.getAppAccount(),aae);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("更新失败");
            return response;
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/datasync/change_password",method = RequestMethod.POST)
    public @ResponseBody
    ChangePasswordResponse changePassword(@RequestBody ChangePasswordExRequest request, HttpServletRequest httpRequest) throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ChangePasswordResponse response = new ChangePasswordResponse();

        //密码解密
        String oldPassword = AESCoder.decrypt(request.getOldPassword(), config.getAesKey());
        String newPassword = AESCoder.decrypt(request.getNewPassword(), config.getAesKey());

        PasswordExample pe = new PasswordExample();
        long userId = 0;
        if(request.getUserId() == 0) {
            userId = findUserIdByJobNumber(request.getJobNumber());
            pe.createCriteria().andUserIdEqualTo(userId);
        }else{
            pe.createCriteria().andUserIdEqualTo(request.getUserId());
        }

        List<Password> passwords = passwordMapper.selectByExample(pe);
        if(passwords == null || passwords.size() == 0){
            Password password = new Password();
            password.setPassword(MD5Utils.MD5Encode(newPassword,"UTF-8"));
            password.setUserId(userId);
            passwordMapper.insert(password);

            response.setCode(CODE.SUCCESS);
            response.setMsg("未设置密码，直接重置密码");
            return response;
        }else {
            Password password = passwords.get(0);
            if(!password.getPassword().equals(MD5Utils.MD5Encode(oldPassword,"UTF-8"))){
                response.setCode(CODE.BIZ.PASSOWRD_NOT_MATCH);
                response.setMsg("密码不匹配");
                return response;
            }

            password.setPassword(MD5Utils.MD5Encode(newPassword,"UTF-8"));
            passwordMapper.updateByPrimaryKey(password);
        }

        //同步ldap数据，如果失败则记录信息，不影响正常运行
        try{
            if(request.getUserId() == 0 && request.getJobNumber() != null) {
                ldapClient.modify(request.getJobNumber(), request.getNewPassword());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/reset_password",method = RequestMethod.POST)
    public @ResponseBody
    ResetPasswordResponse resetPassword(@RequestBody ResetPasswordExRequest request, HttpServletRequest httpRequest) throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ResetPasswordResponse response = new ResetPasswordResponse();

        //密码解密
        String newPassword = AESCoder.decrypt(request.getNewPassword(), config.getAesKey());

        PasswordExample pe = new PasswordExample();
        long userId = 0;
        if(request.getUserId() == 0) {
            userId = findUserIdByJobNumber(request.getJobNumber());
            pe.createCriteria().andUserIdEqualTo(userId);
        }else{
            pe.createCriteria().andUserIdEqualTo(request.getUserId());
        }

        List<Password> passwords = passwordMapper.selectByExample(pe);
        if(passwords == null || passwords.size() == 0){
            Password password = new Password();
            password.setPassword(MD5Utils.MD5Encode(newPassword,"UTF-8"));
            password.setUserId(userId);
            passwordMapper.insert(password);

            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                if(request.getUserId() == 0 && request.getJobNumber() != null) {
                    ldapClient.modify(request.getJobNumber(), request.getNewPassword());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("未设置密码，直接重置密码成功");
            return response;
        }else {
            Password password = passwords.get(0);
            password.setPassword(MD5Utils.MD5Encode(newPassword, "UTF-8"));
            passwordMapper.updateByPrimaryKey(password);

            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                if(request.getUserId() == 0 && request.getJobNumber() != null) {
                    ldapClient.modify(request.getJobNumber(), request.getNewPassword());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/datasync/get_users",method = RequestMethod.POST)
    public @ResponseBody
    GetUsersResponse getUsers(@RequestBody GetUsersExRequest request, HttpServletRequest httpRequest) throws ParseException, AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetUsersResponse response = new GetUsersResponse();

        UserExample ue = new UserExample();
        UserExample.Criteria c = ue.createCriteria();
        if(request.getOrgType() != 0)
            c.andOrgTypeEqualTo(request.getOrgType());

        if(request.getOrgId() != 0)
            c.andOrgIdEqualTo(request.getOrgId());

        if(request.getIdNumber() != null && !"".equals(request.getIdNumber()))
            c.andIdNumberEqualTo(request.getIdNumber());

        if(request.getIdType() != null && request.getIdType() != 0)
            c.andIdTypeEqualTo(request.getIdType());

        if(request.getUserId() != 0)
            c.andIdEqualTo(request.getUserId());

        if(request.getJobNumber() != null && !"".equals(request.getJobNumber()))
            c.andJobNumberEqualTo(request.getJobNumber());

        if(request.getEndUpdateTime() != null && !"".equals(request.getEndUpdateTime()))
            c.andModifyTimeLessThanOrEqualTo(dateFormat.parse(request.getEndUpdateTime()));

        if(request.getStartUpdateTime() != null && !"".equals(request.getStartUpdateTime()))
            c.andModifyTimeGreaterThanOrEqualTo(dateFormat.parse(request.getStartUpdateTime()));

        List<User> userList = userMapper.selectByExample(ue);
        response.setData(userList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/get_app_account_id",method = RequestMethod.POST)
    public @ResponseBody
    GetAppAccountIdResponse getAppAccountId(@RequestBody GetAppAccountIdExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetAppAccountIdResponse response = new GetAppAccountIdResponse();

        AppAccountExample aae = new AppAccountExample();
        AppAccountExample.Criteria c = aae.createCriteria();
        if(request.getUserIds() != null && request.getUserIds().size() > 0) {
            c.andUserIdIn(request.getUserIds());
            List<AppAccount> appAccountList = appAccountMapper.selectByExample(aae);
            response.setData(appAccountList);
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }else if(request.getJobNumbers() != null && request.getJobNumbers().size() > 0){
            c.andJobNumberIn(request.getJobNumbers());
            List<AppAccount> appAccountList = appAccountMapper.selectByExample(aae);
            response.setData(appAccountList);
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }else{
            response.setCode(CODE.BIZ.NOT_QUERY_CONDITION);
            response.setMsg("没有查询条件");
            return response;
        }

    }

    @RequestMapping(value="/datasync/add_org",method = RequestMethod.POST)
    public @ResponseBody
    AddOrgResponse addOrg(@RequestBody AddOrgExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddOrgResponse response = new AddOrgResponse();

        int ret = orgMapper.insert(request.getOrg());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/del_org",method = RequestMethod.POST)
    public @ResponseBody
    DelOrgResponse delOrg(@RequestBody DelOrgExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelOrgResponse response = new DelOrgResponse();

        OrgExample oe = new OrgExample();
        OrgExample.Criteria c = oe.createCriteria();
        if(request.getOrgId() != 0)
            c.andOrgIdEqualTo(request.getOrgId());

        if(request.getOrgType() != 0)
            c.andOrgTypeEqualTo(request.getOrgType());

        List<Org> orgs = orgMapper.selectByExample(oe);
        if(orgs == null || orgs.size() == 0){
            response.setCode(CODE.BIZ.FAIL_SELECT_SQL);
            response.setMsg("查找失败");
            return response;
        }

        Org org = orgs.get(0);
        org.setStatus((short)Constant.ORG_STATUS.UNSIGNED);
        int ret = orgMapper.updateByExample(org,oe);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("更新失败");
            return response;
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/add_app",method = RequestMethod.POST)
    public @ResponseBody
    AddAppResponse getOrgs(@RequestBody AddAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddAppResponse response = new AddAppResponse();

        int ret = appMapper.insert(request.getApp());
        if(ret == 0){
            response .setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/del_app",method = RequestMethod.POST)
    public @ResponseBody
    DelAppResponse getOrgs(@RequestBody DelAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelAppResponse response = new DelAppResponse();

        int ret = appMapper.deleteByPrimaryKey(request.getAppId());
        if(ret == 0){
            response .setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
            return response;
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/get_orgs",method = RequestMethod.POST)
    public @ResponseBody
    GetOrgsResponse getOrgs(@RequestBody GetOrgsExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetOrgsResponse response = new GetOrgsResponse();

        OrgExample oe = new OrgExample();
        OrgExample.Criteria c = oe.createCriteria();
        if(request.getGrade() != 0)
            c.andGradeEqualTo(request.getGrade());

        if(request.getName() != null && !"".equals(request.getName()))
            c.andNameEqualTo(request.getName());

        if(request.getOrgId() != 0)
            c.andOrgIdEqualTo(request.getOrgId());

        if(request.getOrgType() != 0)
            c.andOrgTypeEqualTo((short)request.getOrgType());

        if(request.getParentOrgId() != 0)
            c.andParentOrgIdEqualTo(request.getParentOrgId());

        if(request.getStatus() != 0)
            c.andStatusEqualTo((short)request.getStatus());

        List<Org> orgList = orgMapper.selectByExample(oe);
        response.setData(orgList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/modify_org",method = RequestMethod.POST)
    public @ResponseBody
    ModifyOrgResponse modifyOrg(@RequestBody ModifyOrgExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ModifyOrgResponse response = new ModifyOrgResponse();

        OrgExample oe = new OrgExample();
        oe.createCriteria().andOrgIdEqualTo(request.getOrg().getId()).andOrgTypeEqualTo(request.getOrg().getOrgType());
        int ret = orgMapper.updateByExample(request.getOrg(),oe);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("更新失败");
            return response;
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/datasync/add_org_type",method = RequestMethod.POST)
    public @ResponseBody
    AddOrgTypeResponse addOrgType(@RequestBody AddOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddOrgTypeResponse response = new AddOrgTypeResponse();

        OrgTypeExample  ote = new OrgTypeExample();
        int ret = orgTypeMapper.insert(request.getOrgType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @RequestMapping(value="/datasync/del_org_type",method = RequestMethod.POST)
    public @ResponseBody
    DelOrgTypeResponse delOrgType(@RequestBody DelOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelOrgTypeResponse response = new DelOrgTypeResponse();

        OrgTypeExample ote = new OrgTypeExample();
        ote.createCriteria().andIdEqualTo(request.getOrgType());
        int ret = orgTypeMapper.deleteByExample(ote);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
            return response;
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    private long findUserIdByJobNumber(String jobNumber){
        UserExample ue = new UserExample();
        ue.createCriteria().andJobNumberEqualTo(jobNumber);
        List<User> users = userMapper.selectByExample(ue);
        if(users == null || users.size() == 0){
            return 0;
        }else{
            return users.get(0).getId();
        }
    }

}