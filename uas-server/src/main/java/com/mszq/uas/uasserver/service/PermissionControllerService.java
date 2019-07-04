package com.mszq.uas.uasserver.service;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.mapper.*;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.exception.OperationFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PermissionControllerService {

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

    @Transactional
    public AddRoleTypeResponse addRoleType(AddRoleTypeExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddRoleTypeResponse response = new AddRoleTypeResponse();

        int ret = roleTypeMapper.insert(request.getRoleType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            throw new OperationFailureException(response);
        }else{
            response.setRoleTypeId(request.getRoleType().getId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }
    @Transactional
    public DelRoleTypeResponse delRoleType(DelRoleTypeExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelRoleTypeResponse response = new DelRoleTypeResponse();
        int ret = roleTypeMapper.deleteByPrimaryKey(request.getRoleType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
            throw new OperationFailureException(response);
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    public GetRoleAppsResponse getRoleApps(GetRoleAppsRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());
        GetRoleAppsResponse response = new GetRoleAppsResponse();

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        response.setData(getRoleAllApps(request.getRoleId()));
        return response;
    }

    private List<App> getRoleAllApps(Long roleId){
        List<Long> roleIds = roleMapper.selectAllParentRoleIds(roleId);
        if(roleIds.size() == 0){
            roleIds.add(0L);
        }
        List<Long> appIds = roleAppMapper.findAllRoleAppByRoleIds(roleIds);

        List<App> apps = appMapper.selectAll();
        List<App> roleApps = new ArrayList<App>();
        for(Long id:appIds){
            for(App app:apps){
                if(app.getId() == id) {
                    roleApps.add(app);
                    break;
                }
            }
        }
        return roleApps;
    }

    @Transactional
    public AddRoleResponse addRole(AddRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {

        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        AddRoleResponse response = new AddRoleResponse();

        RoleExample re = new RoleExample();
        re.createCriteria().andRoleCodeEqualTo(request.getRole().getRoleCode())
                .andOrgIdEqualTo(request.getRole().getOrgId()==null?0:request.getRole().getOrgId())
                .andOrgTypeEqualTo(request.getRole().getOrgType()==null?0:request.getRole().getOrgType());
        List<Role> isRoleExist = roleMapper.selectByExample(re);
        if(isRoleExist != null && isRoleExist.size() > 0){
            Role role = isRoleExist.get(0);
            if(role.getStatus() == Constant.ROLE_STATUS.OK){
                response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                response.setMsg("角色以存在，创建失败");
                throw new OperationFailureException(response);
            }
        }

        long roleId = 0;
        request.getRole().setModifyTime(new Date());
        request.getRole().setStatus(Constant.ROLE_STATUS.OK);
        if(isRoleExist == null || isRoleExist.size() == 0) {
            int ret = roleMapper.insert(request.getRole());
            if(ret == 0){
                response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                response.setMsg("插入失败");
                throw new OperationFailureException(response);
            }
            roleId = request.getRole().getId();
        }else{
            Role role = isRoleExist.get(0);
            role.setStatus(Constant.ROLE_STATUS.OK);
            role.setModifyTime(new Date());
            role.setParentId(request.getRole().getParentId());
            role.setRoleTypeId(request.getRole().getRoleTypeId());
            role.setRoleName(request.getRole().getRoleName());
            role.setComment(request.getRole().getComment());
            role.setOrgId(request.getRole().getOrgId());
            role.setOrgType(request.getRole().getOrgType());
            int ret = roleMapper.updateByPrimaryKeySelective(role);
            if(ret == 0){
                response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
                response.setMsg("更新失败");
                throw new OperationFailureException(response);
            }
            roleId = role.getId();
        }

        List<Long> parentRoleIds = roleMapper.selectAllParentRoleIds(request.getRole().getParentId());
        if(parentRoleIds.size() > 0) {
            List<Long> appIds = roleAppMapper.findAllRoleAppByRoleIds(parentRoleIds);
            for (Long id : appIds) {
                roleAppMapper.insertAppToRole(roleId, id);
            }
        }
        response.setRoleId(roleId);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    @Transactional
    public DelRoleResponse delRole(DelRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelRoleResponse response = new DelRoleResponse();
        //首先判断该角色是否有子角色，如果有则不允许删除，必须首先删除子角色
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andParentIdEqualTo(request.getRoleId());
        List<Role> childrenRoles = roleMapper.selectByExample(roleExample);
        if(childrenRoles!=null && childrenRoles.size() > 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            String msg = "";
            for(Role r:childrenRoles){
                msg = msg+"["+r.getRoleName()+";"+r.getId()+"],";
            }
            response.setMsg("该角色为父角色，必须首先删除其所有子角色，子角色包括："+msg);
            throw new OperationFailureException(response);
        }

        Role role = roleMapper.selectByPrimaryKey(request.getRoleId());
        if(role == null){
            response.setCode(CODE.BIZ.NOT_EXIST_RECORD);
            response.setMsg("删除失败");
            throw new OperationFailureException(response);
        }

        role.setStatus(Constant.ROLE_STATUS.UNSIGNED);
        role.setModifyTime(new Date());
        int ret = roleMapper.updateByPrimaryKey(role);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("删除失败");
            throw new OperationFailureException(response);
        }else{
            //删除角色分配的应用
            RoleAppExample rae = new RoleAppExample();
            rae.createCriteria().andRoleIdEqualTo(request.getRoleId());
            if(roleAppMapper.deleteByExample(rae) == 0){
                response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
                response.setMsg("删除角色分配的App失败");
                throw new OperationFailureException(response);
            }

            //所有用户都需要删除该角色，其AppAccount
            UserRoleExample ure = new UserRoleExample();
            ure.createCriteria().andRoleIdEqualTo(request.getRoleId());
            List<UserRole> userRoles = userRoleMapper.selectByExample(ure);
            for(UserRole ur:userRoles){
                DelRoleToUserExRequest req = new DelRoleToUserExRequest();
                req.set_secret(request.get_secret());
                req.set_appId(request.get_appId());
                req.setAutoDelAccount(true);
                req.setRoleId(request.getRoleId());
                req.setUserId(ur.getUserId());
                Response r = delRoleToUser(req,httpRequest);
                if(r.getCode() != CODE.SUCCESS){
                    response.setCode(r.getCode());
                    response.setMsg(r.getMsg());
                    throw new OperationFailureException(response);
                }
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    public GetRoleResponse getRoles(GetRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {

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

        if(request.getRoleCode() != null && !"".equals(request.getRoleCode()))
            c.andRoleCodeEqualTo(request.getRoleCode());

        if(request.getOrgId() != 0)
            c.andOrgIdEqualTo(request.getOrgId());

        if(request.getOrgType() != 0)
            c.andOrgTypeEqualTo((short)request.getOrgType());

        if(request.getComment() != null && !"".equals(request.getComment()))
            c.andCommentLike(request.getComment());

        List<Role> roleList = roleMapper.selectByExample(re);
        response.setData(roleList);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;

    }

    @Transactional
    public ModifyRoleResponse modifyRole(ModifyRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());
        ModifyRoleResponse response = new ModifyRoleResponse();

        Role role = roleMapper.selectByPrimaryKey(request.getRole().getId());
        if(role == null){
            response.setCode(CODE.BIZ.NOT_EXIST_RECORD);
            response.setMsg("角色不存在，更新失败");
            throw new OperationFailureException(response);
        }

        RoleExample re = new RoleExample();
        re.createCriteria().andIdEqualTo(request.getRole().getId());
        request.getRole().setModifyTime(new Date());
        int ret = roleMapper.updateByExample(request.getRole(),re);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_UPDATE_SQL);
            response.setMsg("更新失败");
            throw new OperationFailureException(response);
        }else{
            //如果角色更新了父角色，则需要调整其所有分配的应用
            if(request.getRole().getParentId() != null && request.getRole().getParentId() != 0){
                if(role.getParentId() != request.getRole().getParentId()){
                    //需要将重新分配所有的父角色所拥有的应用
                    //老的父角色所拥有的应用列表
                    List<Long> oldParentRoleIds = roleMapper.selectAllParentRoleIds(role.getParentId());
                    if(oldParentRoleIds.size()==0){
                        oldParentRoleIds.add(0L);
                    }
                    List<Long> oldParantAppIds = roleAppMapper.findAllRoleAppByRoleIds(oldParentRoleIds);

                    //新的父角色所拥有的应用列表
                    List<Long> newParentRoleIds = roleMapper.selectAllParentRoleIds(request.getRole().getParentId());
                    if(newParentRoleIds.size() == 0){
                        newParentRoleIds.add(0L);
                    }
                    List<Long> newParantAppIds = roleAppMapper.findAllRoleAppByRoleIds(newParentRoleIds);
                    //先判断，那些角色在老父角色中有，但是在新的父角色中没有，需要删除
                    List<Long> needToDelAppIds = new ArrayList<Long>();
                    for(Long appId:oldParantAppIds){
                        boolean isFound = false;
                        for(Long i:newParantAppIds){
                            if(appId == i){
                                isFound = true;
                                break;
                            }
                        }
                        if(isFound == false)
                            needToDelAppIds.add(appId);
                    }

                    //在判断，那些角色在老父角色中没有，但是在新的父角色中有，需要插入
                    List<Long> needToAddAppIds = new ArrayList<Long>();
                    for(Long appId:newParantAppIds){
                        boolean isFound = false;
                        for(Long i:oldParantAppIds){
                            if(appId == i){
                                isFound = true;
                                break;
                            }
                        }
                        if(isFound == false)
                            needToAddAppIds.add(appId);
                    }


                    UserRoleExample ure = new UserRoleExample();
                    ure.createCriteria().andRoleIdEqualTo(request.getRole().getId());
                    List<UserRole> userRoles = userRoleMapper.selectByExample(ure);
                    //删除多余的RoleApp ,该角色所有相关用户的AppAccount需要发生变化
                    for(Long appId:needToDelAppIds){
                        RoleAppExample rae = new RoleAppExample();
                        rae.createCriteria().andRoleIdEqualTo(role.getId()).andAppIdEqualTo(appId);
                        List<RoleApp> list = roleAppMapper.selectByExample(rae);
                        for(RoleApp rr:list){
                            ret = roleAppMapper.deleteByPrimaryKey(rr.getId());
                            if(ret == 0) {
                                response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
                                response.setMsg("删除原有父角色的应用失败");
                                throw new OperationFailureException(response);
                            }

                            for(UserRole ur:userRoles){
                                DelRoleToUserExRequest req = new DelRoleToUserExRequest();
                                req.setAutoDelAccount(true);
                                req.setRoleId(ur.getRoleId());
                                req.setUserId(ur.getUserId());
                                Response r = delRoleToUser(req,httpRequest);
                                if(r.getCode() != CODE.SUCCESS){
                                    response.setCode(r.getCode());
                                    response.setMsg(r.getMsg());
                                    throw new OperationFailureException(response);
                                }
                            }
                        }
                    }
                    //添加新增的RoleApp ,该角色所有相关用户的AppAccount需要发生变化
                    for(Long appId:needToAddAppIds){
                        RoleApp r = new RoleApp();
                        r.setAppId(appId);
                        r.setRoleId(request.getRole().getId());
                        ret = roleAppMapper.insert(r);
                        if(ret == 0) {
                            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                            response.setMsg("插入角色的应用失败");
                            throw new OperationFailureException(response);
                        }else{
                            for(UserRole ur:userRoles){
                                AddRoleToUserExRequest req = new AddRoleToUserExRequest();
                                req.set_appId(request.get_appId());
                                req.set_secret(request.get_secret());
                                req.setUserId(ur.getUserId());
                                req.setAutoAddAccount(true);
                                req.setRoleId(ur.getRoleId());
                                Response resp = addRoleToUser(req,httpRequest);
                                if(resp.getCode() != CODE.SUCCESS){
                                    response.setCode(resp.getCode());
                                    response.setMsg(resp.getMsg());
                                    throw new OperationFailureException(response);
                                }
                            }
                        }
                    }
                }
            }

            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }
    @Transactional
    public AddAppToRoleResponse addAppToRole(AddAppToRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());
        AddAppToRoleResponse response = new AddAppToRoleResponse();

        List<App> haveApps = this.getRoleAllApps(request.getRoleId());
        for(App a: haveApps){
            if(a.getId() == request.getAppId()){
                response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                response.setMsg("该角色已经分配了该应用的访问权限，添加失败");
                return response;
            }
        }

        RoleApp roleApp = new RoleApp();
        roleApp.setAppId(request.getAppId());
        roleApp.setRoleId(request.getRoleId());

        int ret = roleAppMapper.insert(roleApp);
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("向角色表添加应用失败");
            throw new OperationFailureException(response);
        }

        List<Role> childrenRoles = roleMapper.selectAllChildrenRole(request.getRoleId());
        for(Role r:childrenRoles){
            if(r.getId().longValue() == roleApp.getRoleId().longValue()) {
                continue;
            }

            RoleApp ra = new RoleApp();
            ra.setRoleId(r.getId());
            ra.setAppId(request.getAppId());
            if(roleAppMapper.insert(ra) == 0){
                response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
                response.setMsg("向角色的子角色添加应用失败");
                throw new OperationFailureException(response);
            }
        }
        List<Long> roleIds = new ArrayList<Long>();
        roleIds.add(0L);//to avoid null
        for(Role r:childrenRoles) {
            roleIds.add(r.getId());
        }
        List<Long> relativedUserIds = userRoleMapper.selectUserIdByRoleId(roleIds);
        for(Long userId:relativedUserIds){
            appAccountMapper.insertAppAccount(request.getAppId(),userId);
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    @Transactional
    public DelAppToRoleResponse delAppToRole(DelAppToRoleExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(),request.get_secret());

        DelAppToRoleResponse response = new DelAppToRoleResponse();

        RoleAppExample rae = new RoleAppExample();
        RoleAppExample.Criteria c = rae.createCriteria();
        Role role = null;
        Long appId = request.getAppId();
        if(request.getRoleAppId() != 0){
            c.andIdEqualTo(request.getRoleAppId());
            RoleApp roleApp = roleAppMapper.selectByPrimaryKey(request.getRoleAppId());
            if(roleApp == null){
                response.setCode(CODE.BIZ.NOT_EXIST_RECORD);
                response.setMsg("找不到角色");
                return response;
            }
            appId = roleApp.getAppId();
            role = roleMapper.selectByPrimaryKey(roleApp.getRoleId());
        }else{
            c.andRoleIdEqualTo(request.getRoleId()).andAppIdEqualTo(request.getAppId());
            role = roleMapper.selectByPrimaryKey((request.getRoleId()));
        }

        if(role == null){
            response.setCode(CODE.BIZ.NOT_EXIST_RECORD);
            response.setMsg("找不到角色");
            return response;
        }

        //父角色所拥有的应用列表
        List<Long> parentRoleIds = roleMapper.selectAllParentRoleIds(role.getParentId());
        if(parentRoleIds.size()==0){
            parentRoleIds.add(0L);
        }
        List<Long> parentAppIds = roleAppMapper.findAllRoleAppByRoleIds(parentRoleIds);

        boolean isParentApp = false;
        for(Long id : parentAppIds){
            if(id == appId){
                isParentApp = true;
                break;
            }
        }
        if(!isParentApp) {
            //只有当父角色不拥有改应用时才删除该记录
            roleAppMapper.deleteByExample(rae);
            List<Role> childrenRoles = roleMapper.selectAllChildrenRole(role.getId());
            for (Role r : childrenRoles) {
                if (r.getId().longValue() == request.getRoleId())
                    continue;

                RoleAppExample eae = new RoleAppExample();
                eae.createCriteria().andRoleIdEqualTo(r.getId()).andAppIdEqualTo(appId);
                roleAppMapper.deleteByExample(eae);
            }

            List<Long> roleIds = new ArrayList<Long>();
            roleIds.add(0L);//to avoid null.
            for (Role r : childrenRoles) {
                roleIds.add(r.getId());
            }
            List<Long> relativedUserIds = userRoleMapper.selectUserIdByRoleId(roleIds);
            relativedUserIds.add(0L);//to avoid
            appAccountMapper.removeAppAccountByUserId(relativedUserIds, appId);
        }

        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        return response;
    }
    @Transactional
    public AddRoleToUserResponse addRoleToUser(AddRoleToUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(), request.get_secret());

        AddRoleToUserResponse response = new AddRoleToUserResponse();

        //判断用户是否已经添加该角色
        UserRoleExample ex = new UserRoleExample();
        ex.createCriteria().andUserIdEqualTo(request.getUserId()).andRoleIdEqualTo(request.getRoleId());
        List<UserRole> userRoleList = userRoleMapper.selectByExample(ex);
        if(userRoleList != null && userRoleList.size() > 0){
            response.setCode(CODE.SUCCESS);
            response.setMsg("该用户已经添加了该角色，跳过处理");
            throw new OperationFailureException(response);
        }

        UserRole userRole = new UserRole();
        userRole.setRoleId(request.getRoleId());
        userRole.setUserId(request.getUserId());

        int ret = userRoleMapper.insert(userRole);
        if (ret == 0) {
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
            throw new OperationFailureException(response);
        } else {
            //判断是否自动添加子账号
            if (request.isAutoAddAccount()) {
                //获取用户信息
                User user = userMapper.selectByPrimaryKey(request.getUserId());
                if (user == null) {
                    response.setCode(CODE.BIZ.FAIL_SELECT_SQL);
                    response.setMsg("用户信息不存在");
                    throw new OperationFailureException(response);
                }

                //获取用户已经添加的子账户信息
                AppAccountExample e = new AppAccountExample();
                e.createCriteria().andUserIdEqualTo(user.getId());
                List<AppAccount> appAccountList = appAccountMapper.selectByExample(e);
                Map<Long, Long> map = new HashMap<Long,Long>();
                for(AppAccount aa:appAccountList)
                    map.put(aa.getAppId(),aa.getUserId());

                //根据角色为用户添加子账号，所有的子账户默认是工号
                List<App> apps = this.getAllApps(request.getUserId());
                for (App a : apps) {
                    //判断是否已经添加了子账号，如果添加了则跳过
                    Long userId = map.get(a.getId());
                    if(userId != null && userId == request.getUserId())
                        continue;

                    //没有添加则添加子账户
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
                        throw new OperationFailureException(response);
                    }
                }
            }
            response.setUserRoleId(userRole.getId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        }
    }
    @Transactional
    public DelRoleToUserResponse delRoleToUser(DelRoleToUserExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
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
                throw new OperationFailureException(response);
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

    public GetUserRolesResponse<UserRole> getUserRoles(GetUserRolesRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException, OperationFailureException {
        ipBlackCheckService.isBlackList(httpRequest);
        appSecretVerifyService.verifyAppSecret(request.get_appId(), request.get_secret());

        GetUserRolesResponse response = new GetUserRolesResponse();

        UserRoleExample ure = new UserRoleExample();
        ure.createCriteria().andUserIdEqualTo(request.getUserId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(ure);
        response.setCode(CODE.SUCCESS);
        response.setMsg("成功");
        response.setData(userRoles);
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
