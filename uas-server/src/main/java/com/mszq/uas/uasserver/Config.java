package com.mszq.uas.uasserver;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.stereotype.Component;

@Configuration
@Component("config")
@ConfigurationProperties(prefix="uas.server")
public class Config {
    private int DEFAULT_SESSION_TIMEOUT = 28800;
    private long sessionTimeout;
    private long tokenTimeout;
    private String aesKey;
    private int errorTry;
    private long lockTime;

    public int getErrorTry() {
        return errorTry;
    }

    public void setErrorTry(int errorTry) {
        this.errorTry = errorTry;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public long getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(long tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        if(sessionTimeout <= 0){
            this.sessionTimeout = DEFAULT_SESSION_TIMEOUT;
        }else {
            this.sessionTimeout = sessionTimeout;
        }
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(@NonNull String aesKey) {
        this.aesKey = aesKey;
    }

}
