package com.mszq.platform.app.uas.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.uas.dto.UasRoleAppDto;
import com.mszq.platform.app.uas.service.IUasRoleAppService;
import com.mszq.platform.base.CustomResult;

@Controller
@RequestMapping("/uasRoleApp")
public class UasRoleAppController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired(required = true)
	private IUasRoleAppService uasRoleAppService;

	@RequestMapping(value = "/getAppListByRole")
    @ResponseBody
	public CustomResult getAppListByRole(@RequestParam(value = "roleId") Long roleId) {
		CustomResult result = null;
		List<UasRoleAppDto> roleApps = uasRoleAppService.getAppListByRole(roleId);
    	if (roleApps != null) {
			result = CustomResult.ok("成功",roleApps);
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
        try {
        	int count = uasRoleAppService.saveRolePermission(id, resourceIds);
    		if (count >= 0) {
    			result = CustomResult.ok("授权成功！");
    		} else {
    			result = CustomResult.error("授权失败！");
    		}
        } catch(Exception e) {
        	result = CustomResult.error(e.getMessage());
        }
		
		return result;
    }
}
