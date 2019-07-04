package com.mszq.uas.uasserver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.bean.ModifyAppRequest;
import com.mszq.uas.uasserver.bean.ModifyAppResponse;
import com.mszq.uas.uasserver.dao.mapper.*;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.ldap.LdapClient;
import com.mszq.uas.uasserver.ldap.Person;
import com.mszq.uas.uasserver.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DataSyncControllerService {
    private static Logger logger = LoggerFactory.getLogger(DataSyncControllerService.class);

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

    @Transactional
    public UpdateUserResponse updateUser(UpdateUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        UpdateUserResponse response = new UpdateUserResponse();

        UserExample ue = new UserExample();
        ue.createCriteria().andJobNumberEqualTo(request.getUser().getJobNumber());
        List<User> userList = userMapper.selectByExample(ue);

        UserExample ue1 = new UserExample();
        ue1.createCriteria().andIdTypeEqualTo(request.getUser().getIdType()).andIdNumberEqualTo(request.getUser().getIdNumber());
        List<User> userList1 = userMapper.selectByExample(ue1);

        boolean isCreate = false;
        boolean isUpdate = false;
        String initPassword = "";
        if((userList == null || userList.size() == 0) && (userList1 == null || userList1.size() == 0)){
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
            isCreate = true;

            //创建初始密码
            PasswordExample pe = new PasswordExample();
            pe.createCriteria().andUserIdEqualTo(user.getId());

            List<Password> passwords = passwordMapper.selectByExample(pe);
            if(user.getIdNumber().length() >= 6){
                initPassword = user.getIdNumber().substring(user.getIdNumber().length()-6);
            }else{
                initPassword = "mszq@123";
            }

            if(passwords == null || passwords.size() == 0){
                Password password = new Password();
                password.setPassword(MD5Utils.parseStrToMd5L32(initPassword));
                password.setUserId(user.getId());
                password.setModifyTime(new Date());
                passwordMapper.insert(password);
            }
        }else{
            User user = null;
            if(userList.size() > 0 ){
                user = userList.get(0);
            }else if(userList1.size() > 0){
                user = userList1.get(0);
            }
            //更新
            user.setModifyTime(new Date());
            request.getUser().setId(user.getId());
            int ret = userMapper.updateByPrimaryKey(request.getUser());
            if(ret == 0){
                response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
                response.setMsg("更新失败");
                return response;
            }
            response.setUserId(user.getId());
            isUpdate = true;
        }

        //同步ldap数据，如果失败则记录信息，不影响正常运行
        try{
            if(isCreate) {
                List<Person> ldapPersons = ldapClient.search(request.getUser().getJobNumber());
                if (ldapPersons == null || ldapPersons.size() == 0) {
                    ldapClient.create(request.getUser().getJobNumber(), request.getUser().getName(), initPassword, request.getUser().getEmail(), request.getUser().getMobile());
                }
            }

            if(isUpdate){
                if(request.getUser().getStatus()!=Constant.USER_STATUS.UNSIGNED) {
                    List<Person> ldapPersons = ldapClient.search(request.getUser().getJobNumber());
                    if (ldapPersons == null || ldapPersons.size() == 0) {
                        ldapClient.create(request.getUser().getJobNumber(), request.getUser().getName(), initPassword, request.getUser().getEmail(), request.getUser().getMobile());
                    }else {
                        ldapClient.modify(request.getUser().getJobNumber(), request.getUser().getName(), null, request.getUser().getEmail(), request.getUser().getMobile());
                    }
                }else{
                    ldapClient.delete(request.getUser().getJobNumber());
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("更新用户成功");
        return response;
    }

    @Transactional
    public DelUserResponse delUser(DelUserExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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
//        user.setIdNumber(user.getIdNumber()+"-"+);//删除用户时需要修改用户的身份证，避免用户再次入职时因身份证重复导致数据添加失败
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
    @Transactional
    public AddIdToAppResponse addIdToApp(AddIdToAppExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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

    @Transactional
    public DelIdToAppResponse delIdToApp(DelIdToAppExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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
    @Transactional
    public ChangeIdToAppResponse changeIdToApp(ChangeIdToAppExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

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
    @Transactional
    public ChangePasswordResponse changePassword(ChangePasswordExRequest request, HttpServletRequest httpRequest) throws Exception {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ChangePasswordResponse response = new ChangePasswordResponse();

        //判断用户是否存在
        UserExample ue = new UserExample();
        UserExample.Criteria c = ue.createCriteria();
        if(request.getUserId() != 0)
            c.andIdEqualTo(request.getUserId());

        if(request.getJobNumber() != null && !"".equals(request.getJobNumber()))
            c.andJobNumberEqualTo(request.getJobNumber());

        List<User> userList = userMapper.selectByExample(ue);
        if(userList == null || userList.size() == 0){
            response.setCode(CODE.BIZ.USER_NOT_EXIST);
            response.setMsg("用户不存在");
            return response;
        }
        User user = userList.get(0);

        //密码解密

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
            password.setPassword(MD5Utils.parseStrToMd5L32(request.getNewPassword()));
            password.setUserId(user.getId());
            password.setModifyTime(new Date());
            passwordMapper.insert(password);

            response.setCode(CODE.SUCCESS);
            response.setMsg("未设置密码，直接重置密码");
            return response;
        }else {
            Password password = passwords.get(0);
            if(!password.getPassword().equals(MD5Utils.parseStrToMd5L32(request.getOldPassword()))){
                response.setCode(CODE.BIZ.PASSOWRD_NOT_MATCH);
                response.setMsg("密码不匹配");
                return response;
            }

            password.setPassword(MD5Utils.parseStrToMd5L32(request.getNewPassword()));
            password.setModifyTime(new Date());
            passwordMapper.updateByPrimaryKey(password);
        }

        //同步ldap数据，如果失败则记录信息，不影响正常运行
        try{
            if(request.getUserId() == 0 && request.getJobNumber() != null) {
                ldapClient.modify(request.getJobNumber(),null, MD5Utils.parseStrToMd5L32(request.getNewPassword()),null,null);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordExRequest request, HttpServletRequest httpRequest) throws Exception {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ResetPasswordResponse response = new ResetPasswordResponse();

        //判断用户是否存在
        UserExample ue = new UserExample();
        UserExample.Criteria c = ue.createCriteria();
        if(request.getUserId() != 0)
            c.andIdEqualTo(request.getUserId());

        if(request.getJobNumber() != null && !"".equals(request.getJobNumber()))
            c.andJobNumberEqualTo(request.getJobNumber());

        List<User> userList = userMapper.selectByExample(ue);
        if(userList == null || userList.size() == 0){
            response.setCode(CODE.BIZ.USER_NOT_EXIST);
            response.setMsg("用户不存在");
            return response;
        }
        User user = userList.get(0);

        PasswordExample pe = new PasswordExample();
        long userId = 0;
        if(request.getUserId() == 0) {
            userId = findUserIdByJobNumber(request.getJobNumber());
            pe.createCriteria().andUserIdEqualTo(userId);
        }else{
            pe.createCriteria().andUserIdEqualTo(request.getUserId());
            request.setJobNumber(user.getJobNumber());
        }

        List<Password> passwords = passwordMapper.selectByExample(pe);
        if(passwords == null || passwords.size() == 0){
            Password password = new Password();
            password.setPassword(MD5Utils.parseStrToMd5L32(request.getNewPassword()));
            password.setUserId(user.getId());
            password.setModifyTime(new Date());
            passwordMapper.insert(password);

            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                if(request.getJobNumber() != null) {
                    ldapClient.modify(request.getJobNumber(), null, MD5Utils.parseStrToMd5L32(request.getNewPassword()),null,null);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("未设置密码，直接重置密码成功");
            return response;
        }else {
            Password password = passwords.get(0);
            password.setModifyTime(new Date());
            password.setPassword(MD5Utils.parseStrToMd5L32(request.getNewPassword()));
            passwordMapper.updateByPrimaryKey(password);

            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                if(request.getJobNumber() != null) {
                    ldapClient.modify(request.getJobNumber(),null, MD5Utils.parseStrToMd5L32(request.getNewPassword()),null,null);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @Transactional
    public ResetPasswordResponse resetPassword1(ResetPasswordExRequest request, HttpServletRequest httpRequest) throws Exception {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ResetPasswordResponse response = new ResetPasswordResponse();

        //判断用户是否存在
        UserExample ue = new UserExample();
        UserExample.Criteria c = ue.createCriteria();
        if(request.getUserId() != 0)
            c.andIdEqualTo(request.getUserId());

        if(request.getJobNumber() != null && !"".equals(request.getJobNumber()))
            c.andJobNumberEqualTo(request.getJobNumber());

        List<User> userList = userMapper.selectByExample(ue);
        if(userList == null || userList.size() == 0){
            response.setCode(CODE.BIZ.USER_NOT_EXIST);
            response.setMsg("用户不存在");
            return response;
        }
        User user = userList.get(0);

        //密码解密
        String newPassword = request.getNewPassword();

        PasswordExample pe = new PasswordExample();
        long userId = 0;
        if(request.getUserId() == 0) {
            userId = findUserIdByJobNumber(request.getJobNumber());
            pe.createCriteria().andUserIdEqualTo(userId);
        }else{
            pe.createCriteria().andUserIdEqualTo(request.getUserId());
            request.setJobNumber(user.getJobNumber());
        }

        List<Password> passwords = passwordMapper.selectByExample(pe);
        if(passwords == null || passwords.size() == 0){
            Password password = new Password();
            password.setPassword(newPassword);
            password.setUserId(user.getId());
            password.setModifyTime(new Date());
            passwordMapper.insert(password);

            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                if(request.getJobNumber() != null) {
                    ldapClient.modify(request.getJobNumber(), null, newPassword,null,null);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("未设置密码，直接重置密码成功");
            return response;
        }else {
            Password password = passwords.get(0);
            password.setPassword(newPassword);
            password.setModifyTime(new Date());
            passwordMapper.updateByPrimaryKey(password);

            //同步ldap数据，如果失败则记录信息，不影响正常运行
            try{
                if(request.getJobNumber() != null) {
                    ldapClient.modify(request.getJobNumber(),null, newPassword,null,null);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }
    public GetUsersResponse getUsers(GetUsersExRequest request, HttpServletRequest httpRequest) throws ParseException, AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        if(request.getPageSize() != 0){
            PageHelper.startPage(request.getPageNum(), request.getPageSize());
        }

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

        if(request.getStatus() != 0)
            c.andStatusEqualTo(request.getStatus());

        if(request.getJobNumber() != null && !"".equals(request.getJobNumber()))
            c.andJobNumberEqualTo(request.getJobNumber());

        if(request.getEndUpdateTime() != null && !"".equals(request.getEndUpdateTime()))
            c.andModifyTimeLessThanOrEqualTo(dateFormat.parse(request.getEndUpdateTime()));

        if(request.getStartUpdateTime() != null && !"".equals(request.getStartUpdateTime()))
            c.andModifyTimeGreaterThanOrEqualTo(dateFormat.parse(request.getStartUpdateTime()));

        List<User> userList = userMapper.selectByExample(ue);
        if(request.getPageSize() != 0) {
            PageInfo<User> pageInfo = new PageInfo<User>(userList);

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");

            response.setData(pageInfo.getList());
            response.setPageNum(pageInfo.getPageNum());
            response.setPageSize(pageInfo.getPageSize());
            response.setPages(pageInfo.getPages());
            response.setTotal(pageInfo.getTotal());
        }else {
            response.setData(userList);
            response.setPageNum(0);
            response.setPageSize(userList.size());
            response.setPages(1);
            response.setTotal((long)userList.size());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }
    public GetAppAccountIdResponse getAppAccountId(GetAppAccountIdExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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
    @Transactional
    public UpdateOrgResponse updateOrg(UpdateOrgRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        UpdateOrgResponse response = new UpdateOrgResponse();

        OrgExample oe = new OrgExample();
        oe.createCriteria().andOrgIdEqualTo(request.getOrg().getOrgId()).andOrgTypeEqualTo(request.getOrg().getOrgType());

        List<Org> orgList = orgMapper.selectByExample(oe);
        if(orgList!= null && orgList.size()>0) {
            Org org = orgList.get(0);
            request.getOrg().setId(org.getId());
            request.getOrg().setModifyTime(new Date());

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
            request.getOrg().setModifyTime(new Date());
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
    @Transactional
    public DelOrgResponse delOrg( DelOrgExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

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
        org.setModifyTime(new Date());
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
    @Transactional
    public AddAppResponse addApp(AddAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddAppResponse response = new AddAppResponse();

        int ret = appMapper.insert(request.getApp());
        if(ret == 0){
            response .setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        }

        appSecretVerifyService.refreshAppList();
        response.setAppId(request.getApp().getId());
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    public GetAppResponse getApp(GetAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetAppResponse response = new GetAppResponse();

        AppExample ae = new AppExample();
        AppExample.Criteria c = ae.createCriteria();

        if(request.getAppId() == 0 && (request.getName() == null || "".equals(request.getName()))) {
            List<App> appList = appMapper.selectAll();
            response.setData(appList);
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }

        if(request.getAppId() > 0){
            c.andIdEqualTo(request.getAppId());

        }else if(request.getName() != null && !"".equals(request.getName())) {
            c.andNameEqualTo(request.getName());
        }
        List<App> appList = appMapper.selectByExample(ae);
        response.setData(appList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    @Transactional
    public DelAppResponse delApp(DelAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelAppResponse response = new DelAppResponse();

        int ret = appMapper.deleteByPrimaryKey(request.getAppId());
        if(ret == 0){
            response .setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
            return response;
        }

        appSecretVerifyService.refreshAppList();
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    public GetOrgsResponse getOrgs(GetOrgsExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException, ParseException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        if(request.getPageSize() != 0){
            PageHelper.startPage(request.getPageNum(), request.getPageSize());
        }

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

        if(request.getEndUpdateTime() != null && !"".equals(request.getEndUpdateTime()))
            c.andModifyTimeLessThanOrEqualTo(dateFormat.parse(request.getEndUpdateTime()));

        if(request.getStartUpdateTime() != null && !"".equals(request.getStartUpdateTime()))
            c.andModifyTimeGreaterThanOrEqualTo(dateFormat.parse(request.getStartUpdateTime()));

        List<Org> orgList = orgMapper.selectByExample(oe);
        if(request.getPageSize() != 0) {
            PageInfo<Org> pageInfo = new PageInfo<Org>(orgList);

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");

            response.setData(pageInfo.getList());
            response.setPageNum(pageInfo.getPageNum());
            response.setPageSize(pageInfo.getPageSize());
            response.setPages(pageInfo.getPages());
            response.setTotal(pageInfo.getTotal());
        }else {
            response.setData(orgList);
            response.setPageNum(0);
            response.setPageSize(orgList.size());
            response.setPages(1);
            response.setTotal((long)orgList.size());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }




        return response;
    }
    @Transactional
    public AddOrgTypeResponse addOrgType(AddOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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
    @Transactional
    public DelOrgTypeResponse delOrgType(DelOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {

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

    @Transactional
    public ModifyAppResponse modifyApp(ModifyAppRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        ModifyAppResponse response = new ModifyAppResponse();

        AppExample ae = new AppExample();
        ae.createCriteria().andIdEqualTo(request.getApp().getId());
        int ret = appMapper.updateByExample(request.getApp(),ae);
        if(ret == 0){
            response .setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("更新失败");
            return response;
        }

        appSecretVerifyService.refreshAppList();
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
}