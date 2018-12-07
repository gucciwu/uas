package com.mszq.uas.uasserver.controller;

import com.mszq.uas.uasserver.bean.AuthExRequest;
import com.mszq.uas.uasserver.bean.AuthResponse;
import com.mszq.uas.uasserver.bean.SignoutExRequest;
import com.mszq.uas.uasserver.bean.SignoutResponse;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.service.AuthControllerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@Api(tags={"身份认证"},value="身份认证")
public class AuthController {

    @Autowired
    private AuthControllerService authService;

    @ApiOperation(value="身份认证", notes="提交身份认证信息，员工编号+密码。密码采用AES算法加密，密钥请联系管理员")
    @RequestMapping(value="/ua/auth",method = RequestMethod.POST)
    public @ResponseBody AuthResponse auth(@RequestBody AuthExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException {
        return authService.auth(request,httpRequest);
    }

    @ApiOperation(value="注销登录", notes="注销登录，销毁会话信息")
    @RequestMapping(value="/ua/signout",method = RequestMethod.POST)
    public @ResponseBody
    SignoutResponse signout(@RequestBody SignoutExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException {
        return authService.signout(request,httpRequest);
    }
}
