package com.mszq.uas.uasserver.controller;

import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.service.PermissionControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags={"权限分配"},value="权限分配")
@RestController
public class PermissionController {

    @Autowired
    private PermissionControllerService service;

    @ApiOperation(value="添加角色分类", notes="")
    @RequestMapping(value="/permission/add_role_type",method = RequestMethod.POST)
    public @ResponseBody
    AddRoleTypeResponse addRoleType(@RequestBody AddRoleTypeExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.addRoleType(request,httpRequest);
    }
    @ApiOperation(value="删除角色分类", notes="")
    @RequestMapping(value="/permission/del_role_type",method = RequestMethod.POST)
    public @ResponseBody
    DelRoleTypeResponse delRoleType(@RequestBody DelRoleTypeExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.delRoleType(request,httpRequest);
    }
    @ApiOperation(value="添加角色", notes="")
    @RequestMapping(value="/permission/add_role",method = RequestMethod.POST)
    public @ResponseBody
    AddRoleResponse addRole(@RequestBody AddRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.addRole(request,httpRequest);
    }
    @ApiOperation(value="删除角色", notes="")
    @RequestMapping(value="/permission/del_role",method = RequestMethod.POST)
    public @ResponseBody
    DelRoleResponse delRole(@RequestBody DelRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.delRole(request,httpRequest);
    }
    @ApiOperation(value="查询角色", notes="")
    @RequestMapping(value="/permission/get_roles",method = RequestMethod.POST)
    public @ResponseBody
    GetRoleResponse getRoles(@RequestBody GetRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.getRoles(request,httpRequest);
    }
    @ApiOperation(value="修改角色", notes="")
    @RequestMapping(value="/permission/modify_role",method = RequestMethod.POST)
    public @ResponseBody
    ModifyRoleResponse modifyRole(@RequestBody ModifyRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.modifyRole(request,httpRequest);
    }
    @ApiOperation(value="为角色添加应用", notes="可以为角色指定访问应用的权限，用户拥有多个角色，则可以访问多个角色分配的全集应用")
    @RequestMapping(value="/permission/add_app_to_role",method = RequestMethod.POST)
    public @ResponseBody
    AddAppToRoleResponse addAppToRole(@RequestBody AddAppToRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.addAppToRole(request,httpRequest);
    }
    @ApiOperation(value="为角色删除应用", notes="可以为角色指定访问应用的权限，用户拥有多个角色，则可以访问多个角色分配的全集应用")
    @RequestMapping(value="/permission/del_app_to_role",method = RequestMethod.POST)
    public @ResponseBody
    DelAppToRoleResponse delAppToRole(@RequestBody DelAppToRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.delAppToRole(request,httpRequest);
    }
    @ApiOperation(value="为用户分配角色", notes="用户分配角色后才能够访问该角色下的应用。该接口在分配和可以通过autoAddAccount参数自动为用户添加相应应用系统的账户ID（规则为：ID=工号）。")
    @RequestMapping(value="/permission/add_role_to_user",method = RequestMethod.POST)
    public @ResponseBody
    AddRoleToUserResponse addRoleToUser(@RequestBody AddRoleToUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.addRoleToUser(request,httpRequest);
    }
    @ApiOperation(value="为用户删除角色", notes="删除角色后，该角色下的应用系统将不能够再访问")
    @RequestMapping(value="/permission/del_role_to_user",method = RequestMethod.POST)
    public @ResponseBody
    DelRoleToUserResponse delRoleToUser(@RequestBody DelRoleToUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.delRoleToUser(request,httpRequest);
    }
}
