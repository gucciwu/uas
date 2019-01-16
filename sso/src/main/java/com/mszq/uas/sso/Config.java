package com.mszq.uas.sso;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component("ssoConfig")
@ConfigurationProperties(prefix = "uas.sso")
public class Config {
    private String hostUrl;
    private String aesKey;
    private long appId;
    private String secret;
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String host) {
        this.hostUrl = host;
    }
}
