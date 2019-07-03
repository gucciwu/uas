package com.mszq.platform.app.uas.common;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;

public class SslUtils {
	private static void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	public static void ignoreSsl()

	{
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};
		try {
			trustAllHttpsCertificates();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	public static String getRequest(String url, int timeOut) throws RuntimeException {
		try {
			URL u = new URL(url);
			if ("https".equalsIgnoreCase(u.getProtocol())) {
				ignoreSsl();
			}
			URLConnection conn = u.openConnection();
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			return IOUtils.toString(conn.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String postRequest(String urlAddress, String args, int timeOut) throws RuntimeException {
		URL url = null;
		URLConnection u;
		try {
			url = new URL(urlAddress);
			if ("https".equalsIgnoreCase(url.getProtocol())) {
				ignoreSsl();
			}
			u = url.openConnection();
			u.setRequestProperty("Connection", "Keep-Alive");
			u.setRequestProperty("Charset", "UTF-8");
			// 设置文件类型:
			u.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			u.setRequestProperty("accept", "application/json");

			u.setDoInput(true);
			u.setDoOutput(true);
			u.setConnectTimeout(timeOut);
			u.setReadTimeout(timeOut);

			OutputStreamWriter osw = new OutputStreamWriter(u.getOutputStream(), "UTF-8");
			
			System.out.println(urlAddress);
			System.out.println(args);

			osw.write(args);
			osw.flush();
			osw.close();
			//u.getOutputStream();
			return IOUtils.toString(u.getInputStream(), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			//String a = getRequest("https://www.mszq.com:5411/ssoReg.do?userCode=102414", 3000);
			//System.out.println(a);
			
			System.out.println(postRequest("http://1.1.1.82:8080/datasync/reset_password","{\"_appId\":0,\"_devInfo\":\"\",\"_secret\":\"sso.mszq.com\",\"jobNumber\":\"109923\",\"newPassword\":\"2ffa00438ed30154cd1bf2b882262831\",\"userId\":2971}", 5000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class miTM implements TrustManager, X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		}
	}
}