package com.mszq.uas.uasserver.controller;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.mapper.*;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.service.AppSecretVerifyService;
import com.mszq.uas.uasserver.service.ConvertOrgIdService;
import com.mszq.uas.uasserver.service.IpBlackCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class PermissionController {

    @Autowired
    private RoleTypeMapper roleTypeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAppMapper roleAppMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private AppAccountMapper appAccountMapper;
    @Autowired
    private AppSecretVerifyService appSecretVerifyService;
    @Autowired
    private IpBlackCheckService ipBlackCheckService;
    @Autowired
    private ConvertOrgIdService convertOrgIdService;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/permission/add_role_type",method = RequestMethod.POST)
    public @ResponseBody
    AddRoleTypeResponse addRoleType(@RequestBody AddRoleTypeExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddRoleTypeResponse response = new AddRoleTypeResponse();
        int ret = roleTypeMapper.insert(request.getRoleType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
        }else{
            response.setRoleTypeId(request.getRoleType().getId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping(value="/permission/del_role_type",method = RequestMethod.POST)
    public @ResponseBody
    DelRoleTypeResponse delRoleType(@RequestBody DelRoleTypeExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelRoleTypeResponse response = new DelRoleTypeResponse();
        int ret = roleTypeMapper.deleteByPrimaryKey(request.getRoleType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping(value="/permission/add_role",method = RequestMethod.POST)
    public @ResponseBody
    AddRoleResponse addRole(@RequestBody AddRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddRoleResponse response = new AddRoleResponse();

        int ret = roleMapper.insert(request.getRole());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
        }else{
            response.setRoleId(request.getRole().getId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping(value="/permission/del_role",method = RequestMethod.POST)
    public @ResponseBody
    DelRoleResponse delRole(@RequestBody DelRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelRoleResponse response = new DelRoleResponse();
        int ret = roleMapper.deleteByPrimaryKey(request.getRoleId());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
        }else{
            //根据角色删除对应的应用系统子账号

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping(value="/permission/get_roles",method = RequestMethod.POST)
    public @ResponseBody
    GetRoleResponse getRoles(@RequestBody GetRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        GetRoleResponse response = new GetRoleResponse();
        RoleExample re = new RoleExample();
        RoleExample.Criteria c = re.createCriteria();
        if(request.getParentId() != 0)
            c.andParentIdEqualTo(request.getParentId());

        if(request.getRoleName() != null && !"".equals(request.getRoleName()))
            c.andRoleNameEqualTo(request.getRoleName());

        if(request.getRoleTypeId() != 0)
            c.andRoleTypeIdEqualTo(request.getRoleTypeId());

        if(request.getStatus() != 0)
            c.andStatusEqualTo(request.getStatus());

        List<Role> roleList = roleMapper.selectByExample(re);
        response.setData(roleList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;

    }

    @RequestMapping(value="/permission/modify_role",method = RequestMethod.POST)
    public @ResponseBody
    ModifyRoleResponse modifyRole(@RequestBody ModifyRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());
        ModifyRoleResponse response = new ModifyRoleResponse();

        RoleExample re = new RoleExample();
        re.createCriteria().andIdEqualTo(request.getRole().getId());
        int ret = roleMapper.updateByExample(request.getRole(),re);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("更新失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }
    @RequestMapping(value="/permission/add_app_to_role",method = RequestMethod.POST)
    public @ResponseBody
    AddAppToRoleResponse addAppToRole(@RequestBody AddAppToRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());
        AddAppToRoleResponse response = new AddAppToRoleResponse();

        RoleApp roleApp = new RoleApp();
        roleApp.setAppId(request.getAppId());
        roleApp.setRoleId(request.getRoleId());

        int ret = roleAppMapper.insert(roleApp);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping(value="/permission/del_app_to_role",method = RequestMethod.POST)
    public @ResponseBody
    DelAppToRoleResponse delAppToRole(@RequestBody DelAppToRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelAppToRoleResponse response = new DelAppToRoleResponse();

        RoleAppExample rae = new RoleAppExample();
        RoleAppExample.Criteria c = rae.createCriteria();
        if(request.getRoleAppId() != 0){
            c.andIdEqualTo(request.getRoleAppId());
        }if(request.getAppId() != 0 && request.getRoleId() != 0){
            c.andAppIdEqualTo(request.getAppId()).andRoleIdEqualTo(request.getRoleId());
        }else{
            response.setCode(CODE.BIZ.NOT_QUERY_CONDITION);
            response.setMsg("条件不足");
            return response;
        }

        int ret = roleAppMapper.deleteByExample(rae);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping(value="/permission/add_role_to_user",method = RequestMethod.POST)
    public @ResponseBody
    AddRoleToUserResponse addRoleToUser(@RequestBody AddRoleToUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(), request.get_secret());

        AddRoleToUserResponse response = new AddRoleToUserResponse();

        UserRole userRole = new UserRole();
        userRole.setRoleId(request.getRoleId());
        userRole.setUserId(request.getUserId());

        int ret = userRoleMapper.insert(userRole);
        if (ret == 0) {
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            return response;
        } else {
            //判断是否自动添加子账号
            if (request.isAutoAddAccount()) {
                //获取用户信息
                User user = userMapper.selectByPrimaryKey(request.getUserId());
                if (user == null) {
                    response.setCode(CODE.BIZ.FAIL_SELECT_SQL);
                    response.setMsg("用户信息不存在");
                    return response;
                }

                //根据角色为用户添加子账号，所有的子账户默认是工号
                List<App> apps = this.getAllApps(request.getUserId());
                for (App a : apps) {
                    AppAccount aa = new AppAccount();
                    aa.setUserId(request.getUserId());
                    aa.setOrgType(a.getOrgType());
                    aa.setOrgId(convertOrgIdService.convert(user.getOrgId(), user.getOrgType(), a.getOrgType()));
                    aa.setJobNumber(user.getJobNumber());
                    aa.setAppId(a.getId());
                    aa.setAccount(user.getJobNumber());
                    int retCode = appAccountMapper.insert(aa);
                    if (retCode == 0) {
                        response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                        response.setMsg("插入子账户失败");
                        return response;
                    }
                }
            }
            response.setUserRoleId(userRole.getId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }

    @RequestMapping(value="/permission/del_role_to_user",method = RequestMethod.POST)
    public @ResponseBody
    DelRoleToUserResponse addRoleToUser(@RequestBody DelRoleToUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(), request.get_secret());

        DelRoleToUserResponse response = new DelRoleToUserResponse();

        UserRoleExample ure = new UserRoleExample();
        ure.createCriteria().andUserIdEqualTo(request.getUserId()).andRoleIdEqualTo(request.getRoleId());
        List<UserRole> userRoleList = userRoleMapper.selectByExample(ure);
        if(userRoleList != null && userRoleList.size()>0){
            UserRole ur = userRoleList.get(0);

            int retCode = userRoleMapper.deleteByPrimaryKey(ur.getId());
            if(retCode == 0){
                response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
                response.setMsg("删除失败");
                return response;
            }

            if(request.isAutoDelAccount()){
                List<App> appList = this.getAllApps(ur.getUserId());

                List<Long> _appIdList = new ArrayList<Long>();
                for(App a:appList)
                    _appIdList.add(a.getId());

                AppAccountExample aae = new AppAccountExample();
                AppAccountExample.Criteria c = aae.createCriteria();
                if(_appIdList.size() == 0){
                    c.andUserIdEqualTo(request.getUserId());
                }else {
                    aae.createCriteria().andAppIdNotIn(_appIdList).andUserIdEqualTo(request.getUserId());
                }

                List<AppAccount> appAccountList = appAccountMapper.selectByExample(aae);
                if(appAccountList != null){
                    for(AppAccount appAccount:appAccountList)
                        appAccountMapper.deleteByPrimaryKey(appAccount.getId());
                }
            }
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }

    private List<App> getAllApps(long userId) {
        UserRoleExample ure = new UserRoleExample();
        ure.createCriteria().andUserIdEqualTo(userId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(ure);

        List<App> apps = new ArrayList<App>();
        if(userRoles != null){
            List<Long> _roleIdList = new ArrayList<Long>();
            for(UserRole ur:userRoles){
                _roleIdList.add(ur.getRoleId());
            }

            if(_roleIdList.size()>0) {
                RoleAppExample rae = new RoleAppExample();
                rae.createCriteria().andRoleIdIn(_roleIdList);
                List<RoleApp> roleAppList = roleAppMapper.selectByExample(rae);

                List<Long> _appIdList = new ArrayList<Long>();
                for (RoleApp ra : roleAppList) {
                    _appIdList.add(ra.getAppId());
                }

                if(_appIdList.size() > 0) {
                    AppExample ae = new AppExample();
                    ae.createCriteria().andIdIn(_appIdList);
                    apps = appMapper.selectByExample(ae);
                }
            }
        }
        return apps;
    }
}
