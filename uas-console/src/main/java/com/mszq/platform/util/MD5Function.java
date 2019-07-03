package com.mszq.platform.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class MD5Function {
	static String ss="123";
	static String ms="d9b1d7db4cd6e70935368a1efb10e377";
	static String sss="";
	
	public static String getMD5Digest(String msg) {
	    try {
	      MessageDigest md5 = MessageDigest.getInstance("MD5");
	      md5.update(msg.getBytes());
	      byte[] val = md5.digest();

	      String s = toPlusHex(val);
	      return s;
	    } catch (NoSuchAlgorithmException e) {
	    }
	    return null;
	  }
	  public static String toPlusHex(byte[] val) {
		  StringBuffer buffer = new StringBuffer();

		  for (byte b : val) {
              // 与运算
              int number = b & 0xff;// 加盐
              String str = Integer.toHexString(number);
              if (str.length() == 1) {
                  buffer.append("0");
              }
              buffer.append(str);
          }

		return buffer.toString();
	  }
	  
	  
	  public static void main(String[] args){
//		  System.out.println(MD5Function.getMD5Digest(ss));
//		  if(ms.equalsIgnoreCase(MD5Function.getMD5Digest(ss))) System.out.println(true);
//		  Customer a =new Customer();
//		  a.setCode("aaa");
//		  List<Customer> list =new ArrayList<Customer>();
//		  list.add(a);
//		  Customer b=list.get(0);
//		  b.setCode("bbb");
		  
	  }
	  
	  


}
