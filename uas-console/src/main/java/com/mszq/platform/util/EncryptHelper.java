package com.mszq.platform.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 字符串加密算法
 * @author 
 *
 */
public final class EncryptHelper {
	/**
	 * Logger for this class
	 */
	private static Logger log = Logger.getLogger(EncryptHelper.class);
	/**
	 * 加密，return 加密后的字节数组
	 * @param k 密钥
	 * @param str被加密字符串
	 * @param ari加密算法 
	 */
	public static byte[] encrypt(SecretKey k, byte[] prib, String ari,String provider) {
		Security.addProvider(new BouncyCastleProvider());
		Cipher c = null;
		try {
			c = Cipher.getInstance(ari, provider);
		} catch (NoSuchAlgorithmException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		}
		try {
			c.init(Cipher.ENCRYPT_MODE, k);
		} catch (InvalidKeyException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		}
		byte[] encode = null;
		try {
			encode = c.doFinal(prib);
		} catch (IllegalStateException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		} catch (BadPaddingException e) {
			log.error("encrypt(SecretKey, byte[], String, String)", e);
			e.printStackTrace();
		}
		return encode;
	}

	/**
	 * 加密密码
	 * @param a 密码原文
	 * @return
	 */
	public static String encryptPwd(String a) {
		byte[] bEnpriv = null;
		try {
			bEnpriv = encrypt((SecretKey) getkey(), a.getBytes(),"1.2.840.113549.3.4", "BC");
		} catch (IOException e) {
			log.error("encryptPwd(String)", e); //$NON-NLS-1$
			e.printStackTrace();
		}
		return new BASE64Encoder().encode(bEnpriv);
	}
	
	public static String decrypt(String ens) throws Exception
	{
		try {
			Base64 b64 = new Base64();
			byte[] by = b64.decode(ens.getBytes());
			SecretKey key = (SecretKey)getkey();
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(by);
			return (new String(cipherText, "UTF-8")).replace("", "").replace("\0", "");
		}
		catch(Exception e) {
			log.error("decrypt(String)", e); //$NON-NLS-1$
			throw new Exception("异常", e);
		}
	}

	/**
	 * 返回对称密钥
	 * 
	 * @return
	 * @throws IOException
	 */
	private static Key getkey() throws IOException {
		String encKey = "tyX+dyNeTgKFXZw1toui5w==";
		BASE64Decoder decode = new BASE64Decoder();
		SecretKey keySpec = new SecretKeySpec(decode.decodeBuffer(encKey),"1.2.840.113549.3.4");
		return keySpec;
	}

	public static void main(String[] args) throws Exception {
		String pass = encryptPwd("123456");
		System.out.println(pass);
	}
}
