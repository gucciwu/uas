package com.mszq.uas.ssodemo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
 
import java.io.FileOutputStream;
import java.io.OutputStream;   
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;  
import javax.net.ssl.SSLSocket;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;  
import javax.net.ssl.TrustManagerFactory;  
import javax.net.ssl.X509TrustManager;

public class Utils {

	public static boolean isNotNull(String s) {
		return s != null && !"".equals(s);
	}

	public static int[] getStringToInt(String[] s) {

		int[] in = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			if("".equals(s[i]))continue;
			in[i] = Integer.parseInt(s[i]);
		}

		return in;
	}

	public static String getTime() {
		String dateTime = MessageFormat.format("{0,date,yyyyMMddHHmmss}",
				new Object[] { new java.sql.Date(System.currentTimeMillis()) });
		return dateTime;

	}

	public static String toNormal(String value) {

		return value.trim().replace(",", ";").replaceAll(" ", "");
	}

	public static String listToString(List<String> list) {
		String returnValue = "";
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				returnValue += list.get(i);
			} else {
				returnValue += list.get(i) + ",";

			}
		}
		return returnValue;
	}

	public static String passToCharArray(String pwd) {

		char[] a = pwd.toCharArray();
		char tmp = 'Y';
		int[] aa = new int[a.length];
		String n = "";
		for (int i = 0; i < a.length; i++) {
			aa[i] = a[i] ^ tmp;
			n = n + aa[i];
		}
		return n;
	}

	public static String encrypt(String str, char c) {
		char[] cs = str.toCharArray();
		int[] as = new int[cs.length];
		String s = "";
		for (int i = 0; i < cs.length; i++) {
			as[i] = cs[i] ^ c;
			char c1 = (char) as[i];
			s = s + c1;
		}
		s = Bytes2HexString(s.getBytes());
		return s;
	}

	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static String Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return plainText;
		}
	}

	/**
	 * ����bit���������
	 * 
	 * @param bit
	 * @return
	 */
	public static String getRandomNumber(int bit) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 1; i <= bit; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	/**
	 * ������ת����ʽ��Ϊ�ַ�
	 * 
	 * @param date
	 *            ���������
	 * @param format
	 *            ��ʽ���ַ�
	 * @return ���ظ�ʽ������ַ�
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}
	/**
	 * ȥ��ǰ�󶺺�
	 * @param s
	 * @return
	 */
	public static String handlerDot(String s){
		if(s.charAt(0)==','){
			s=s.substring(1);
		}
		if(s.charAt(s.length()-1)==','){
			s=s.substring(0,s.length()-1);
		}
		return s;
	}

	public static final String getUrlContent(String url,String charset){
		StringBuilder sb=new StringBuilder();
		try {
			URL url2 = new URL(url);
			HttpURLConnection huc = (HttpURLConnection)url2.openConnection();
			huc.setDoOutput(true);
			huc.setRequestMethod("POST");
			huc.setRequestProperty("Content-Type","text/xml;charset="+charset);
			InputStreamReader reader=new InputStreamReader(huc.getInputStream(),charset);
			BufferedReader br=new BufferedReader(reader);
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {  
        String host;  
        int port;  
        char[] passphrase;  
        if ((args.length == 1) || (args.length == 2)) {  
            String[] c = args[0].split(":");  
            host = c[0];  
            port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);  
            String p = (args.length == 1) ? "changeit" : args[1];  
            passphrase = p.toCharArray();  
        } else {  
            System.out  
                    .println("Usage: java InstallCert <host>[:port] [passphrase]");  
            return;  
        }  
  
        File file = new File("jssecacerts");  
        if (file.isFile() == false) {  
            char SEP = File.separatorChar;  
            File dir = new File(System.getProperty("java.home") + SEP + "lib"  
                    + SEP + "security");  
            file = new File(dir, "jssecacerts");  
            if (file.isFile() == false) {  
                file = new File(dir, "cacerts");  
            }  
        }  
        System.out.println("Loading KeyStore " + file + "...");  
        InputStream in = new FileInputStream(file);  
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());  
        ks.load(in, passphrase);  
        in.close();  
  
        SSLContext context = SSLContext.getInstance("TLS");  
        TrustManagerFactory tmf = TrustManagerFactory  
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());  
        tmf.init(ks);  
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf  
                .getTrustManagers()[0];  
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);  
        context.init(null, new TrustManager[] { tm }, null);  
        SSLSocketFactory factory = context.getSocketFactory();  
  
        System.out  
                .println("Opening connection to " + host + ":" + port + "...");  
        SSLSocket socket = (SSLSocket) factory.createSocket(host, port);  
        socket.setSoTimeout(10000);  
        try {  
            System.out.println("Starting SSL handshake...");  
            socket.startHandshake();  
            socket.close();  
            System.out.println();  
            System.out.println("No errors, certificate is already trusted");  
        } catch (SSLException e) {  
            System.out.println();  
            e.printStackTrace(System.out);  
        }  
  
        X509Certificate[] chain = tm.chain;  
        if (chain == null) {  
            System.out.println("Could not obtain server certificate chain");  
            return;  
        }  
  
        BufferedReader reader = new BufferedReader(new InputStreamReader(  
                System.in));  
  
        System.out.println();  
        System.out.println("Server sent " + chain.length + " certificate(s):");  
        System.out.println();  
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");  
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        for (int i = 0; i < chain.length; i++) {  
            X509Certificate cert = chain[i];  
            System.out.println(" " + (i + 1) + " Subject "  
                    + cert.getSubjectDN());  
            System.out.println("   Issuer  " + cert.getIssuerDN());  
            sha1.update(cert.getEncoded());  
            System.out.println("   sha1    " + toHexString(sha1.digest()));  
            md5.update(cert.getEncoded());  
            System.out.println("   md5     " + toHexString(md5.digest()));  
            System.out.println();  
        }  
  
        System.out  
                .println("Enter certificate to add to trusted keystore or 'q' to quit: [1]");  
        String line = reader.readLine().trim();  
        int k;  
        try {  
            k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;  
        } catch (NumberFormatException e) {  
            System.out.println("KeyStore not changed");  
            return;  
        }  
  
        X509Certificate cert = chain[k];  
        String alias = host + "-" + (k + 1);  
        ks.setCertificateEntry(alias, cert);  
  
        OutputStream out = new FileOutputStream("jssecacerts");  
        ks.store(out, passphrase);  
        out.close();  
  
        System.out.println();  
        System.out.println(cert);  
        System.out.println();  
        System.out  
                .println("Added certificate to keystore 'jssecacerts' using alias '"  
                        + alias + "'");  
    }  
  
    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();  
  
    private static String toHexString(byte[] bytes) {  
        StringBuilder sb = new StringBuilder(bytes.length * 3);  
        for (int b : bytes) {  
            b &= 0xff;  
            sb.append(HEXDIGITS[b >> 4]);  
            sb.append(HEXDIGITS[b & 15]);  
            sb.append(' ');  
        }  
        return sb.toString();  
    }  
  
    private static class SavingTrustManager implements X509TrustManager {  
  
        private final X509TrustManager tm;  
        private X509Certificate[] chain;  
  
        SavingTrustManager(X509TrustManager tm) {  
            this.tm = tm;  
        }  
  
        public X509Certificate[] getAcceptedIssuers() {  
            throw new UnsupportedOperationException();  
        }  
  
        public void checkClientTrusted(X509Certificate[] chain, String authType)  
                throws CertificateException {  
            throw new UnsupportedOperationException();  
        }  
  
        public void checkServerTrusted(X509Certificate[] chain, String authType)  
                throws CertificateException {  
            this.chain = chain;  
            tm.checkServerTrusted(chain, authType);  
        }  
    }  
}
