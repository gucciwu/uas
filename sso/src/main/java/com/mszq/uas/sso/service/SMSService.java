package com.mszq.uas.sso.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SMSService {

    @Value("${oto.sms.url}")
    private String url;
    @Value("${oto.sms.lx}")
    private String lx;
    @Value("${oto.sms.username}")
    private String username;
    @Value("${oto.sms.password}")
    private String password;

    public String sendSMS(String phoneNum, String Content, String bmlx) throws IOException {
        String returninfo = "";
        String content = "";
        content = java.net.URLEncoder.encode(Content, bmlx);
        if (bmlx == null) {
            content = Content;
        } else if (bmlx.equals("")) {
            content = java.net.URLEncoder.encode(Content);
        } else {
            content = java.net.URLEncoder.encode(Content, bmlx);
        }
        String strurl = url + "?lx=" + lx + "&DLZH=" + username + "&DLMM=" + password + "&SJHM=" + phoneNum + "&XXNR=" + content;
        URL url = new URL(strurl);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("GET");
        huc.setRequestProperty("Accept", "*/*");
        huc.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
        huc.setRequestProperty("Content-Language", "en-US");
        huc.setRequestProperty("Accept-Language", "zh-CN");
        huc.setRequestProperty("Content-type", "text/html");
        System.out.println(strurl);
        BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            returninfo = line;
        }
        huc.connect();
        br.close();
        return returninfo;
    }
}
