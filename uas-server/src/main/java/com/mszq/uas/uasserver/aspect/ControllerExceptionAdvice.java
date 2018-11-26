package com.mszq.uas.uasserver.aspect;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(AppSecretMatchException.class)
    public @ResponseBody com.mszq.uas.uasserver.bean.Response handleAppSecretMatchException(AppSecretMatchException ex) {
        com.mszq.uas.uasserver.bean.Response result = new com.mszq.uas.uasserver.bean.Response();
        result.setCode(CODE.SYS.APP_SECRET_NOT_MATCH);
        result.setMsg("应用编码和应用Secret不匹配");
        return result;
    }

    @ExceptionHandler(IpForbbidenException.class)
    public @ResponseBody com.mszq.uas.uasserver.bean.Response handleIpForbbidenException(IpForbbidenException ex) {
        com.mszq.uas.uasserver.bean.Response result = new com.mszq.uas.uasserver.bean.Response();
        result.setCode(CODE.BIZ.ILLEGAL_REMOTE_IP);
        result.setMsg("IP请求不合法，请检查IP黑名单");
        return result;
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody com.mszq.uas.uasserver.bean.Response handleIpForbbidenException(Exception ex) {
        com.mszq.uas.uasserver.bean.Response result = new com.mszq.uas.uasserver.bean.Response();
        result.setCode(CODE.SYS.UNKOWN_EXCEPTION);
        result.setMsg("未知异常:"+ex.getMessage());
        return result;
    }
}
