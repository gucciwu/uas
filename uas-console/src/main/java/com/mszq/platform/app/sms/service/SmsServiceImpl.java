package com.mszq.platform.app.sms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.sms.dao.ISmsDAO;
import com.mszq.platform.entity.Sms;

@Service
public class SmsServiceImpl implements ISmsService{
	private static Logger log = LogManager.getLogger(SmsServiceImpl.class);
	@Resource
	ISmsDAO smsDAO;
	private String url;
	private String username;
	private String password;
    public void initParm(String paramFile) throws Exception{
    	
        Properties properties=new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("/"+paramFile);
        properties.load(is);
        url=properties.getProperty("sms.url");
        username=properties.getProperty("sms.username");
        password=properties.getProperty("sms.password");

    }
	private int insertSms(String mobile, String content) {
		Sms sms=new Sms();
		sms.setMobile(mobile);
		sms.setSendDate(new Date());
		sms.setContent(content);
		return smsDAO.insertSelective(sms);
	}

	/**
	 * @param url:发送短信接口服务
	 * @param lx:处理类型
	 * @param password:借口认证的密码
	 * @param phoneNum:手机号码
	 * @param content:短信内容
	 * @param bmlx:编码类型
	 * @throws IOException
	 * 
	 */
	public String SMS(String lx, String phoneNum, String Content, String bmlx) throws IOException {	
		log.debug("[method]SMS: start");
		log.info("[method]SMS: start");
	    try {
				initParm("spring-sms.properties");
				log.info("==================初始化文件没有错误=============" );
		    /*	 url = GlobalConfig.getConfgiValue("sms.url");
		    	 username = GlobalConfig.getConfgiValue("sms.username");
		    	 password = GlobalConfig.getConfgiValue("sms.password");*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.info("==================初始化文件错误=============" );
				e.printStackTrace();
			}
			log.info("==================username=============" + username);
			log.info("==================password=============" + password);
			log.info("==================Content=============" + Content);
			log.debug("==================username=============" + username);
            log.debug("==================password=============" + password);
            log.debug("==================Content=============" + Content);
			String returninfo = "";
			String content = "";
			content = URLEncoder.encode(Content, bmlx);
			if (bmlx == null)
				content = Content;
			else if (bmlx.equals(""))
				content = URLEncoder.encode(Content);
			else {
				content = URLEncoder.encode(Content, bmlx);
			}	
			log.info("==================encode_Content=============" + content);
			String strurl = url + "?Lx=" + lx + "&DLZH=" + username + "&DLMM=" + password + "&SJHM=" + phoneNum + "&XXNR=" + content+"&FSFS=0";
			log.info("==================url=============" + strurl);
			URL url = new URL(strurl);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			log.info("==================huc.getConnectTimeout()============="+huc.getConnectTimeout());
			huc.setRequestMethod("GET");
			huc.setRequestProperty("Accept", "*/*");
			huc.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
			huc.setRequestProperty("Content-Language", "en-US");
			huc.setRequestProperty("Accept-Language", "zh-CN");
			huc.setRequestProperty("Content-type", "text/html");		
			BufferedReader br = null;
			log.info("==================huc.getResponseCode()============="+huc.getResponseCode());
			log.info("==================HttpURLConnection.HTTP_OK============="+HttpURLConnection.HTTP_OK);
            log.debug("==================huc.getResponseCode()============="+huc.getResponseCode());
            log.debug("==================HttpURLConnection.HTTP_OK============="+HttpURLConnection.HTTP_OK);
			if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) { 
			    br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			}
			log.info("==================huc.getInputStream())=============" + huc.getInputStream());
			String line;
			log.info("==================br=============" + br);
			while ((line = br.readLine()) != null) {
				returninfo = line;
			}
			huc.connect();
			br.close();		
			insertSms(phoneNum, Content);
			return returninfo;
		}
		@Override
		public String bulidMsgCode() {
			Random rnd = new Random();
			StringBuffer code = new StringBuffer("");
			for (int i = 0; i < 4; i++) {
				code.append(rnd.nextInt(10));
			}
			log.info("==================code.toString()=============" + code.toString());
			return code.toString();
		}
		@Override
		public String sendMsg(String phoneNum, String content) {
			String ret = "";
			try {
				ret = SMS("0", phoneNum, content, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			}
			log.info("==================ret=============" + ret);
			return ret;
		}
}
