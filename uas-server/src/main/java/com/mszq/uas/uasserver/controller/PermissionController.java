package com.mszq.uas.uasserver.controller;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.bean.*;
import com.mszq.uas.uasserver.dao.mapper.RoleAppMapper;
import com.mszq.uas.uasserver.dao.mapper.RoleMapper;
import com.mszq.uas.uasserver.dao.mapper.RoleTypeMapper;
import com.mszq.uas.uasserver.dao.model.RoleApp;
import com.mszq.uas.uasserver.dao.model.RoleAppExample;
import com.mszq.uas.uasserver.dao.model.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {

    @Autowired
    private RoleTypeMapper roleTypeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAppMapper roleAppMapper;

    @RequestMapping("/permission/add_role_type")
    public @ResponseBody
    AddRoleTypeResponse addRoleType(@RequestBody AddRoleTypeExRequest request) {
        AddRoleTypeResponse response = new AddRoleTypeResponse();
        int ret = roleTypeMapper.insert(request.getRoleType());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping("/permission/del_role_type")
    public @ResponseBody
    DelRoleTypeResponse delRoleType(@RequestBody DelRoleTypeExRequest request) {
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

    @RequestMapping("/permission/add_role")
    public @ResponseBody
    AddRoleResponse addRole(@RequestBody AddRoleExRequest request) {
        AddRoleResponse response = new AddRoleResponse();

        int ret = roleMapper.insert(request.getRole());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_INSERT_SQL);
            response.setMsg("插入失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping("/permission/del_role")
    public @ResponseBody
    DelRoleResponse delRole(@RequestBody DelRoleExRequest request) {
        DelRoleResponse response = new DelRoleResponse();
        int ret = roleMapper.deleteByPrimaryKey(request.getRoleId());
        if(ret == 0){
            response.setCode(CODE.BIZ.FAIL_DELETE_SQL);
            response.setMsg("删除失败");
        }else{
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
        }
        return response;
    }

    @RequestMapping("/permission/modify_role")
    public @ResponseBody
    ModifyRoleResponse modifyRole(@RequestBody ModifyRoleExRequest request) {
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
    @RequestMapping("/permission/add_app_to_role")
    public @ResponseBody
    AddAppToRoleResponse addAppToRole(@RequestBody AddAppToRoleExRequest request) {
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

    @RequestMapping("/permission/del_app_to_role")
    public @ResponseBody
    DelAppToRoleResponse delAppToRole(@RequestBody DelAppToRoleExRequest request) {

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
}
