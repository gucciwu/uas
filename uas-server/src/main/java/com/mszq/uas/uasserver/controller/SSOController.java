package com.mszq.uas.uasserver.controller;

import com.mszq.uas.uasserver.bean.RequireTokenExRequest;
import com.mszq.uas.uasserver.bean.RequireTokenResponse;
import com.mszq.uas.uasserver.bean.VerifyTokenExRequest;
import com.mszq.uas.uasserver.bean.VerifyTokenResponse;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.service.SSOControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags={"单点登录"},value="单点登录")
@RestController
public class SSOController {
    @Autowired
    private SSOControllerService service;
    @ApiOperation(value="申请单点登录令牌", notes="接口将返回一个token（令牌），以及一个过期时间。在过期时间内令牌有效")
    @RequestMapping(value="/sso/require_token",method = RequestMethod.POST)
    public @ResponseBody
    RequireTokenResponse require(@RequestBody RequireTokenExRequest request, HttpServletRequest httpRequest) throws AppSecretMatchException, IpForbbidenException {
        return service.require(request,httpRequest);
    }
    @ApiOperation(value="校验令牌有效性", notes="校验令牌有效性，成功校验后将返回用户登录目标应用系统的账户ID")
    @RequestMapping(value="/sso/verify_token",method = RequestMethod.POST)
    public @ResponseBody
    VerifyTokenResponse verify(@RequestBody VerifyTokenExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException, AppSecretMatchException {
        return service.verify(request,httpRequest);
    }

}
