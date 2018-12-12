package com.mszq.uas.ssodemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
public class SSOConfig {
    private String ssoPortalUrl;

    private int appid;
    private String ssoValidateUrl;
    private String ssoLogoutUrl;
    private String hostUrl;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getSsoValidateUrl() {
        return ssoValidateUrl;
    }

    public void setSsoValidateUrl(String ssoValidateUrl) {
        this.ssoValidateUrl = ssoValidateUrl;
    }

    public String getSsoLogoutUrl() {
        return ssoLogoutUrl;
    }

    public void setSsoLogoutUrl(String ssoLogoutUrl) {
        this.ssoLogoutUrl = ssoLogoutUrl;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getSsoPortalUrl() {
        return ssoPortalUrl;
    }

    public void setSsoPortalUrl(String ssoPortalUrl) {
        this.ssoPortalUrl = ssoPortalUrl;
    }
}
