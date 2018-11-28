package com.mszq.uas.uasserver.controller;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.bean.AuthExRequest;
import com.mszq.uas.uasserver.bean.AuthResponse;
import com.mszq.uas.uasserver.bean.SignoutExRequest;
import com.mszq.uas.uasserver.bean.SignoutResponse;
import com.mszq.uas.uasserver.dao.mapper.PasswordMapper;
import com.mszq.uas.uasserver.dao.mapper.UserMapper;
import com.mszq.uas.uasserver.dao.model.*;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import com.mszq.uas.uasserver.redis.model.Session;
import com.mszq.uas.uasserver.redis.storage.DAO;
import com.mszq.uas.uasserver.service.IpBlackCheckService;
import com.mszq.uas.uasserver.util.AESCoder;
import com.mszq.uas.uasserver.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
@Api(tags={"身份认证"},value="身份认证")
public class AuthController {

    @Autowired
    @Qualifier("config")
    private Config config;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DAO dao;
    @Autowired
    private PasswordMapper passwordMapper;

    @Autowired
    private IpBlackCheckService ipBlackCheckService;

    @ApiOperation(value="身份认证", notes="提交身份认证信息，员工编号+密码。密码采用AES算法加密，密钥请联系管理员")
    @RequestMapping(value="/ua/auth",method = RequestMethod.POST)
    public @ResponseBody AuthResponse auth(@RequestBody AuthExRequest request, HttpServletRequest httpRequest) throws Exception {

        ipBlackCheckService.isBlackList(httpRequest);

        AuthResponse response = new AuthResponse();
        if(request.getJobNumber() == null || request.getPassword() == null){
            response.setCode(CODE.BIZ.AUTH_FAIL);
            response.setMsg("输入信息不完整");
            return response;
        }

        String id = null;
        String password = null;
        try {
            id = request.getJobNumber();
            password = AESCoder.decrypt(request.getPassword(), config.getAesKey());
        } catch (BadPaddingException|InvalidKeyException|NoSuchAlgorithmException|NoSuchPaddingException|IllegalBlockSizeException e) {
            e.printStackTrace();
            response.setCode(CODE.BIZ.AUTH_FAIL);
            response.setMsg("输入信息解密失败");
            return response;
        }

        UserExample ue = new UserExample();
        ue.createCriteria().andJobNumberEqualTo(id);
        List<User> users = userMapper.selectByExample(ue);
        if(users != null && users.size() > 0){
            User user = users.get(0);

            //判断用户状态是否正常
            if(user.getStatus() == Constant.USER_STATUS.SUSPEND){
                response.setCode(CODE.BIZ.AUTH_FAIL);
                response.setMsg("用户账户已挂起，请联系管理员");
                return response;
            }else if(user.getStatus() == Constant.USER_STATUS.UNSIGNED){
                response.setCode(CODE.BIZ.AUTH_FAIL);
                response.setMsg("用户账户已注销");
                return response;
            }

            //判断是否已经被锁定
            long left = dao.findLocker(user.getId().toString());
            if(left != 0){
                String time = this.getGapTime(left);
                response.setCode(CODE.BIZ.LOCKED);
                response.setMsg("账户已被锁定，还需要等待："+time);
                return response;
            }

            PasswordExample pe = new PasswordExample();
            pe.createCriteria().andUserIdEqualTo(user.getId());
            List<Password> passwords = passwordMapper.selectByExample(pe);
            if(passwords != null && passwords.size()>0){
                Password p = passwords.get(0);
                String md5Password = MD5Utils.MD5Encode(password,"UTF-8");
                if(p.getPassword().equals(md5Password)){
                    String ipAddr = getRemoteHost(httpRequest);
                    user.setLastLoginIp(ipAddr);
                    int loginCount = user.getLoginCount()==null?0:user.getLoginCount();
                    user.setLoginCount(loginCount+1);
                    user.setLastLoginInfo(request.get_devInfo());
                    userMapper.updateByPrimaryKey(user);

                    Session session = new Session();
                    session.setSessionId(this.genSessionId(user.getId()));
                    session.setUserId(user.getId());
                    session.setJobNumber(user.getJobNumber());
                    try {
                        dao.addSession(session);
                        response.setSessionId(session.getSessionId());
                        response.setCode(CODE.SUCCESS);
                        response.setMsg("成功");
                        response.setJobNumber(request.getJobNumber());
                        response.setUserId(user.getId());
                        return response;
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.setCode(CODE.BIZ.AUTH_FAIL);
                        response.setMsg("创建会话失败");
                        return response;
                    }
                }else{
                    try {
                        int errorCount = dao.findErrorCount(user.getId().toString());
                        if(errorCount >= config.getErrorTry()){
                            long l = dao.findLocker(user.getId().toString());
                            String time = this.getGapTime(l);
                            response.setCode(CODE.BIZ.LOCKED);
                            response.setMsg("连续认证错误次数超过"+config.getErrorTry()+"次，账户已被锁定，还需要等待："+time);
                            return response;
                        }else{
                            errorCount = errorCount+1;
                            dao.updateErrorCount(user.getId().toString(),errorCount);
                            int l = config.getErrorTry()-errorCount;
                            response.setCode(CODE.BIZ.LOCKED);
                            if(l == 0){
                                dao.addLocker(user.getId().toString());
                                response.setCode(CODE.BIZ.LOCKED);
                                String time = this.getGapTime(config.getLockTime());
                                response.setMsg("连续认证错误次数超过"+config.getErrorTry()+"次，账户已被锁定，还需要等待："+time);
                                return response;
                            }else {
                                response.setMsg("用户名或密码错误，您还有" + l + "次认证机会");
                            }
                            return response;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        response.setCode(CODE.BIZ.AUTH_FAIL);
        response.setMsg("用户名或密码错误");
        return response;
    }

    @ApiOperation(value="注销登录", notes="注销登录，销毁会话信息")
    @RequestMapping(value="/ua/signout",method = RequestMethod.POST)
    public @ResponseBody
    SignoutResponse signout(@RequestBody SignoutExRequest request, HttpServletRequest httpRequest) throws IpForbbidenException {

        ipBlackCheckService.isBlackList(httpRequest);
        SignoutResponse response = new SignoutResponse();
        try {
            dao.deleteSession(request.getSessionId());
            response.setCode(CODE.SUCCESS);
            response.setMsg("成功");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(CODE.BIZ.SESSION_NOT_EXIST);
            response.setMsg("创建会话失败");
            return response;
        }
    }

    /***
     * 采用MD5算法获取SessionID
     * @param userId
     * @return
     */
    private String genSessionId(long userId) {
        // 根据MD5算法生成MessageDigest对象
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Random ran = new Random();
            String str = ran.nextInt(1000)+""+userId+System.currentTimeMillis();
            byte[] srcData = str.getBytes();
            // 使用srcBytes更新摘要
            md5.update(srcData);
            byte[] byteArray = md5.digest();

            StringBuffer md5StrBuff = new StringBuffer();

            //将加密后的byte数组转换为十六进制的字符串,否则的话生成的字符串会乱码
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
                    md5StrBuff.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                }else{
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }

            return md5StrBuff.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private String getGapTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        return diffTime;
    }

}
