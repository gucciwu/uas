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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags={"数据同步"},value="数据同步")
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

    @ApiOperation(value="更新用户", notes="如果用户不存在则创建，如果存在则更新")
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
    @ApiOperation(value="删除用户", notes="不删除用户只是将数据库记录的字段设置为“注销”状态")
    @RequestMapping(value="/datasync/delele_user",method = RequestMethod.POST)
    public @ResponseBody
    DelUserResponse delUser(@RequestBody DelUserExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelUserResponse response = new DelUserResponse();

        UserExample ue = new UserExample();
        ue.createCriteria().andJobNumberEqualTo(request.getJobNumber());
        List<User> userList = userMapper.selectByExample(ue);
        if(userList == null && userList.size() == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("账户不存在");
            return response;
        }

        User user = userList.get(0);
        user.setStatus(Constant.USER_STATUS.UNSIGNED);
        user.setModifyTime(new Date());
        int ret = userMapper.updateByPrimaryKey(user);
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

    @ApiOperation(value="为用户添加应用账号", notes="每个对接统一认证的应用都有自己的账户系统，需要将用户在自身系统的账户ID等级到统一认证中")
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

    @ApiOperation(value="删除用户的应用账号", notes="每个对接统一认证的应用系统都有自己的账户系统，应用账户为该应用系统内部用户的ID")
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
    @ApiOperation(value="修改用户的应用账号", notes="每个对接统一认证的应用系统都有自己的账户系统，应用账户为该应用系统内部用户的ID")
    @RequestMapping(value="/datasync/change_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    ChangeIdToAppResponse changeIdToApp(@RequestBody ChangeIdToAppExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ChangeIdToAppResponse response = new ChangeIdToAppResponse();

        AppAccountExample aae = new AppAccountExample();
        aae.createCriteria().andUserIdEqualTo(request.getAppAccount().getUserId()).andAppIdEqualTo(request.getAppAccount().getAppId());

        List<AppAccount> appAccountList = appAccountMapper.selectByExample(aae);
        if(appAccountList != null && appAccountList.size() > 0 ) {

            AppAccount appAccount = appAccountList.get(0);
            request.getAppAccount().setId(appAccount.getId());

            int ret = appAccountMapper.updateByExample(request.getAppAccount(), aae);
            if (ret == 0) {
                response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
                response.setMsg("更新失败");
                return response;
            } else {
                response.setCode(CODE.SUCCESS);
                response.setMsg("成功");
                return response;
            }
        }else{
            response.setCode(CODE.BIZ.NOT_EXIST_RECORD);
            response.setMsg("记录不存在");
            return response;
        }
    }
    @ApiOperation(value="修改密码", notes="密码需要采用AES算法加密，密钥串请联系管理员")
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
    @ApiOperation(value="重置密码", notes="密码需要采用AES算法加密，密钥串请联系管理员")
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
    @ApiOperation(value="查询用户信息", notes="根据查询条件全量返回用户信息，目前没有实现分页")
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
    @ApiOperation(value="查询应用系统的账户号", notes="每个对接统一认证的应用系统都有自己的账户系统。通过该接口可以查询到客户在不同应用系统的账户ID")
    @RequestMapping(value="/datasync/get_app_account_id",method = RequestMethod.POST)
    public @ResponseBody
    GetAppAccountIdResponse getAppAccountId(@RequestBody GetAppAccountIdExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetAppAccountIdResponse response = new GetAppAccountIdResponse();

        AppAccountExample aae = new AppAccountExample();
        AppAccountExample.Criteria c = aae.createCriteria();
        if(request.getUserId() > 0) {
            c.andUserIdEqualTo(request.getUserId());
        }

        if(request.getJobNumber() != null && !"".equals(request.getJobNumber())){
            c.andJobNumberEqualTo(request.getJobNumber());
        }

        if(request.getAppId() > 0){
            c.andAppIdEqualTo(request.getAppId());
        }

        List<AppAccount> appAccountList = appAccountMapper.selectByExample(aae);
        response.setData(appAccountList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;

    }
    @ApiOperation(value="更新组织机构信息", notes="组织机构信息如果不存在则创建，否则更新")
    @RequestMapping(value="/datasync/update_org",method = RequestMethod.POST)
    public @ResponseBody
    UpdateOrgResponse addOrg(@RequestBody UpdateOrgRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        UpdateOrgResponse response = new UpdateOrgResponse();

        OrgExample oe = new OrgExample();
        oe.createCriteria().andOrgIdEqualTo(request.getOrg().getOrgId()).andOrgTypeEqualTo(request.getOrg().getOrgType());

        List<Org> orgList = orgMapper.selectByExample(oe);
        if(orgList!= null && orgList.size()>0) {
            Org org = orgList.get(0);
            request.getOrg().setId(org.getId());

            int ret = orgMapper.updateByExample(request.getOrg(), oe);
            if (ret == 0) {
                response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
                response.setMsg("更新失败");
                return response;
            } else {
                response.setInnerOrgId(org.getId());
                response.setCode(CODE.SUCCESS);
                response.setMsg("成功");
                return response;
            }
        }else{
            //记录不存在，则插入
            int ret = orgMapper.insert(request.getOrg());
            if(ret == 0){
                response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                response.setMsg("插入失败");
                return response;
            }

            response.setInnerOrgId(request.getOrg().getId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }
    @ApiOperation(value="删除组织机构信息", notes="并不删除组织机构信息，而是在数据库中将该组织机构信息的记录设置为注销状态")
    @RequestMapping(value="/datasync/del_org",method = RequestMethod.POST)
    public @ResponseBody
    DelOrgResponse delOrg(@RequestBody DelOrgExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelOrgResponse response = new DelOrgResponse();

        OrgExample oe = new OrgExample();
        OrgExample.Criteria c = oe.createCriteria();
        c.andOrgIdEqualTo(request.getOrgId()).andOrgTypeEqualTo(request.getOrgType());

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
    @ApiOperation(value="添加应用", notes="对接统一认证的应用系统，都需要在统一认证系统中注册")
    @RequestMapping(value="/datasync/add_app",method = RequestMethod.POST)
    public @ResponseBody
    AddAppResponse addApp(@RequestBody AddAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddAppResponse response = new AddAppResponse();

        int ret = appMapper.insert(request.getApp());
        if(ret == 0){
            response .setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }

        response.setAppId(request.getApp().getId());
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @ApiOperation(value="查询应用", notes="对接统一认证的应用系统，都需要在统一认证系统中注册")
    @RequestMapping(value="/datasync/get_apps",method = RequestMethod.POST)
    public @ResponseBody
    GetAppResponse getApp(@RequestBody GetAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetAppResponse response = new GetAppResponse();

        AppExample ae = new AppExample();
        AppExample.Criteria c = ae.createCriteria();
        if(request.getAppId() > 0)
            c.andIdEqualTo(request.getAppId());

        if(request.getName() != null && !"".equals(request.getName()))
            c.andNameEqualTo(request.getName());

        List<App> appList = appMapper.selectByExample(ae);
        response.setData(appList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    @ApiOperation(value="删除应用", notes="将应用系统信息在统一认证中删除")
    @RequestMapping(value="/datasync/del_app",method = RequestMethod.POST)
    public @ResponseBody
    DelAppResponse delApp(@RequestBody DelAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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
    @ApiOperation(value="查询组织机构信息", notes="条件查询组织机构信息，未实现分页功能")
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
    @ApiOperation(value="添加组织机构分类", notes="组织机构分类的目的是为了应对不同应用系统中存在不同结构的组织机构，默认情况下组织机构应该完全依据HR系统的组织机构")
    @RequestMapping(value="/datasync/add_org_type",method = RequestMethod.POST)
    public @ResponseBody
    AddOrgTypeResponse addOrgType(@RequestBody AddOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddOrgTypeResponse response = new AddOrgTypeResponse();

        OrgTypeExample ote = new OrgTypeExample();
        int ret = orgTypeMapper.insert(request.getOrgType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }
        response.setOrgTypeId(request.getOrgType().getId());
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    @ApiOperation(value="删除组织机构分类", notes="")
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