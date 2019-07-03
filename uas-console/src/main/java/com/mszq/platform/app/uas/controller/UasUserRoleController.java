package com.mszq.platform.app.uas.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.uas.service.IUasUserRoleService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.entity.UasUserRole;

@Controller
@RequestMapping("/uasUserRole")
public class UasUserRoleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = true)
	private IUasUserRoleService uasUserRoleService;
	
	@RequestMapping(value = "/getRoleListByUser")
    @ResponseBody
	public CustomResult getRoleListByUser(@RequestParam(value = "userId") Long userId) {
		CustomResult result = null;
		List<UasUserRole> userRoles = uasUserRoleService.getRoleListByUser(userId);
    	if (userRoles != null) {
			result = CustomResult.ok("成功",userRoles);
		} else {
			result = CustomResult.error("失败");
		}
		return result;
	}
	
    /**
     * 授权
     *
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public CustomResult grant(Long id, String resourceIds) {
        CustomResult result = null;
		int count = uasUserRoleService.saveRolePermission(id, resourceIds);
		if (count >= 0) {
			result = CustomResult.ok("授权成功！");
		} else {
			result = CustomResult.error("授权失败！");
		}
		return result;
    }
}
