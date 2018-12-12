package com.mszq.uas.uasserver.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.ConfigurationException;

import org.apache.commons.codec.binary.Base64;
/**
 * AES Coder<br/>
 * secret key length: 128bit, default: 128 bit<br/>
 * mode: ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding: Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 * @author Aub
 * 
 */

/**
 * AES对称加密算法
 * 
 * @see
 *      ==========================================================================
 *      =================================
 * 这里演示的是其Java6.0的实现,理所当然的BouncyCastle也支持AES对称加密算法
 * 另外,我们也可以以AES算法实现为参考,完成RC2,RC4和Blowfish算法的实现
 *      ==========================================================================
 *      =================================
 *  由于DES的不安全性以及DESede算法的低效,于是催生了AES算法(Advanced Encryption Standard)
 *  该算法比DES要快,安全性高,密钥建立时间短,灵敏性好,内存需求低,在各个领域应用广泛
 *  目前,AES算法通常用于移动通信系统以及一些软件的安全外壳,还有一些无线路由器中也是用AES算法构建加密协议
 *
 *      ==========================================================================
 *      =================================
 *  由于Java6.0支持大部分的算法,但受到出口限制,其密钥长度不能满足需求
 *  所以特别需要注意的是:如果使用256位的密钥,则需要无政策限制文件(Unlimited Strength Jurisdiction Policy
 *      Files)
 *  不过Sun是通过权限文件local_poblicy.jar和US_export_policy.jar做的相应限制
 *      ,我们可以在Sun官网下载替换文件,减少相关限制
 *  网址为http://www.oracle.com/technetwork/java/javase/downloads/index.html
 *  在该页面的最下方找到Java Cryptography Extension (JCE) Unlimited Strength
 *      Jurisdiction Policy Files 6,点击下载
 *  http://download.oracle.com/otn-pub/java/jce_policy/6/jce_policy-6.zip
 *  http://download.oracle.com/otn-pub/java/jce/7/UnlimitedJCEPolicyJDK7.zip
 *  然后覆盖本地JDK目录和JRE目录下的security目录下的文件即可
 *
 *      ==========================================================================
 *      =================================
 */
public class AESCoder {

	/**
	 * 加密
	 * @param src 加密字符串
	 * @param key 密钥
	 * @return 加密后的字符串
	 */
	public static String encrypt(String src, String key) throws Exception {
		// 判断密钥是否为空
		if (key == null) {
			System.out.print("密钥不能为空");
			return null;
		}

		// 密钥补位
		int plus= 16-key.length();
		byte[] data = key.getBytes("utf-8");
		byte[] raw = new byte[16];
		byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
		for(int i=0;i<16;i++)
		{
			if (data.length > i)
				raw[i] = data[i];
			else
				raw[i] = plusbyte[plus];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");	// 算法/模式/补码方式
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));

		//return new Base64().encodeToString(encrypted);//base64
		return binary(encrypted, 16); //十六进制
	}

	/**
	 * 解密
	 * @param src 解密字符串
	 * @param key 密钥
	 * @return 解密后的字符串
	 */
	public static String decrypt(String src, String key) throws Exception {
		try {
			// 判断Key是否正确
			if (key == null) {
				System.out.print("Key为空null");
				return null;
			}

			// 密钥补位
			int plus= 16-key.length();
			byte[] data = key.getBytes("utf-8");
			byte[] raw = new byte[16];
			byte[] plusbyte={ 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
			for(int i=0;i<16;i++)
			{
				if (data.length > i)
					raw[i] = data[i];
				else
					raw[i] = plusbyte[plus];
			}

			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			//byte[] encrypted1 = new Base64().decode(src);//base64
			byte[] encrypted1 = toByteArray(src);//十六进制

			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original,"utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 将byte[]转为各种进制的字符串
	 * @param bytes byte[]
	 * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);	// 这里的1代表正数
	}

	/**
	 * 16进制的字符串表示转成字节数组
	 *
	 * @param hexString 16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		if (hexString.isEmpty())
			throw new IllegalArgumentException("this hexString must not be empty");

		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}
	
	public static String encodeBase64ForURL(String in){
		String buf = in.replace('+', '-');
		buf = buf.replace('/', '_');
		buf = buf.replace('=', '.');
		return buf;
	}
	
	public static String decodeBase64ForUrl(String in) {
		String buf = in.replace('-', '+');
		buf = buf.replace('-', '/');
		buf = buf.replace('.', '=');
		return in;
	}

	public static void  main(String[] args) throws Exception {
		String m = AESCoder.encrypt("123456","42fbe357e0ef4474bb91c5b3e8a9e6f2");
		System.out.println(m);
		System.out.println(AESCoder.decrypt(m,"42fbe357e0ef4474bb91c5b3e8a9e6f2"));

	}
}
