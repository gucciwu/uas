package com.mszq.uas.sso.service;

import org.springframework.stereotype.Service;

/**
 * 检查明文密码是否为弱密码
 *
 * @author lingx
 */
@Service
public class PasswordCheck {
    public static final String number = "1234567890";
    public static final String character = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 1、大于等于6位
     * 2、密码必须包含数字
     * 3、密码必须包含字母
     *
     * @param password
     * @return
     */
    public static final boolean check(String password) {
        password = password.replaceAll(" ", "");
        if (password == null || password.length() < 6) {
            return true;
        }
        int len = password.length();
        boolean isOk = false;
        for (int i = 0; i < len; i++) {
            if (number.indexOf(password.charAt(i)) == -1) {
                isOk = true;
                break;
            }
        }
        if (!isOk) return true;

        isOk = false;
        for (int i = 0; i < len; i++) {
            if (character.indexOf(password.charAt(i)) == -1) {
                isOk = true;
                break;
            }
        }
        return (!isOk);
    }
}
