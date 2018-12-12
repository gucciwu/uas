package com.mszq.uas.uasserver.controller;

import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.service.DataSyncControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@RestController
@Api(tags={"数据同步"},value="数据同步")
public class DataSyncController {

    @Autowired
    private DataSyncControllerService service;

    @ApiOperation(value="更新用户", notes="如果用户不存在则创建，如果存在则更新")
    @RequestMapping(value="/datasync/update_user",method = RequestMethod.POST)
    public @ResponseBody
    UpdateUserResponse updateUser(@RequestBody UpdateUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.updateUser(request, httpRequest);
    }
    @ApiOperation(value="删除用户", notes="不删除用户只是将数据库记录的字段设置为“注销”状态")
    @RequestMapping(value="/datasync/delele_user",method = RequestMethod.POST)
    public @ResponseBody
    DelUserResponse delUser(@RequestBody DelUserExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.delUser(request,httpRequest);
    }

    @ApiOperation(value="为用户添加应用账号", notes="每个对接统一认证的应用都有自己的账户系统，需要将用户在自身系统的账户ID等级到统一认证中")
    @RequestMapping(value="/datasync/add_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    AddIdToAppResponse addIdToApp(@RequestBody AddIdToAppExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.addIdToApp(request,httpRequest);
    }

    @ApiOperation(value="删除用户的应用账号", notes="每个对接统一认证的应用系统都有自己的账户系统，应用账户为该应用系统内部用户的ID")
    @RequestMapping(value="/datasync/del_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    DelIdToAppResponse delIdToApp(@RequestBody DelIdToAppExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return delIdToApp(request,httpRequest);
    }
    @ApiOperation(value="修改用户的应用账号", notes="每个对接统一认证的应用系统都有自己的账户系统，应用账户为该应用系统内部用户的ID")
    @RequestMapping(value="/datasync/change_id_to_app",method = RequestMethod.POST)
    public @ResponseBody
    ChangeIdToAppResponse changeIdToApp(@RequestBody ChangeIdToAppExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.changeIdToApp(request,httpRequest);
    }
    @ApiOperation(value="修改密码", notes="密码需要采用AES算法加密，密钥串请联系管理员")
    @RequestMapping(value="/datasync/change_password",method = RequestMethod.POST)
    public @ResponseBody
    ChangePasswordResponse changePassword(@RequestBody ChangePasswordExRequest request, HttpServletRequest httpRequest) throws Exception {
        return  service.changePassword(request,httpRequest);
    }
    @ApiOperation(value="重置密码", notes="密码需要采用AES算法加密，密钥串请联系管理员")
    @RequestMapping(value="/datasync/reset_password",method = RequestMethod.POST)
    public @ResponseBody
    ResetPasswordResponse resetPassword(@RequestBody ResetPasswordExRequest request, HttpServletRequest httpRequest) throws Exception {
        return service.resetPassword(request,httpRequest);
    }
    @ApiOperation(value="查询用户信息", notes="根据查询条件全量返回用户信息，目前没有实现分页")
    @RequestMapping(value="/datasync/get_users",method = RequestMethod.POST)
    public @ResponseBody
    GetUsersResponse getUsers(@RequestBody GetUsersExRequest request, HttpServletRequest httpRequest) throws ParseException, AppSecretMatchException, IpForbbidenException {
        return service.getUsers(request,httpRequest);
    }
    @ApiOperation(value="查询应用系统的账户号", notes="每个对接统一认证的应用系统都有自己的账户系统。通过该接口可以查询到客户在不同应用系统的账户ID")
    @RequestMapping(value="/datasync/get_app_account_id",method = RequestMethod.POST)
    public @ResponseBody
    GetAppAccountIdResponse getAppAccountId(@RequestBody GetAppAccountIdExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.getAppAccountId(request,httpRequest);
    }
    @ApiOperation(value="更新组织机构信息", notes="组织机构信息如果不存在则创建，否则更新")
    @RequestMapping(value="/datasync/update_org",method = RequestMethod.POST)
    public @ResponseBody
    UpdateOrgResponse addOrg(@RequestBody UpdateOrgRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return  service.addOrg(request,httpRequest);
    }
    @ApiOperation(value="删除组织机构信息", notes="并不删除组织机构信息，而是在数据库中将该组织机构信息的记录设置为注销状态")
    @RequestMapping(value="/datasync/del_org",method = RequestMethod.POST)
    public @ResponseBody
    DelOrgResponse delOrg(@RequestBody DelOrgExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.delOrg(request,httpRequest);
    }
    @ApiOperation(value="添加应用", notes="对接统一认证的应用系统，都需要在统一认证系统中注册")
    @RequestMapping(value="/datasync/add_app",method = RequestMethod.POST)
    public @ResponseBody
    AddAppResponse addApp(@RequestBody AddAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.addApp(request,httpRequest);
    }

    @ApiOperation(value="查询应用", notes="对接统一认证的应用系统，都需要在统一认证系统中注册")
    @RequestMapping(value="/datasync/get_apps",method = RequestMethod.POST)
    public @ResponseBody
    GetAppResponse getApp(@RequestBody GetAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.getApp(request,httpRequest);
    }

    @ApiOperation(value="删除应用", notes="将应用系统信息在统一认证中删除")
    @RequestMapping(value="/datasync/del_app",method = RequestMethod.POST)
    public @ResponseBody
    DelAppResponse delApp(@RequestBody DelAppRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.delApp(request,httpRequest);
    }
    @ApiOperation(value="查询组织机构信息", notes="条件查询组织机构信息，未实现分页功能")
    @RequestMapping(value="/datasync/get_orgs",method = RequestMethod.POST)
    public @ResponseBody
    GetOrgsResponse getOrgs(@RequestBody GetOrgsExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.getOrgs(request,httpRequest);
    }
    @ApiOperation(value="添加组织机构分类", notes="组织机构分类的目的是为了应对不同应用系统中存在不同结构的组织机构，默认情况下组织机构应该完全依据HR系统的组织机构")
    @RequestMapping(value="/datasync/add_org_type",method = RequestMethod.POST)
    public @ResponseBody
    AddOrgTypeResponse addOrgType(@RequestBody AddOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.addOrgType(request,httpRequest);
    }
    @ApiOperation(value="删除组织机构分类", notes="")
    @RequestMapping(value="/datasync/del_org_type",method = RequestMethod.POST)
    public @ResponseBody
    DelOrgTypeResponse delOrgType(@RequestBody DelOrgTypeExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.delOrgType(request,httpRequest);
    }
}