package com.mszq.platform.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;


public class StringUtil {
	public static String gengerteUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static Boolean isEmpty(String data){
		if(data==null)
			return true;
		if("".equals(data)){
			return true;
		}
		return false;
	}
	
	
	public static String formateString(Object o,int length,boolean left,Character c){
		if(o==null){
			return null;
		}
		String r=o.toString();
		if(r.length()>=length){
			if(left){
				return r.substring(0, length-1);
			}else{
				return r.substring(r.length()-length,r.length()-1);
			}
		}else{
			StringBuffer sb=new StringBuffer();
			
			for(int i=0;i<length-r.length();i++){
				sb.append(c.toString());
			}
			if(left){
				return sb.toString()+r;
			}else{
				sb.append(r);
				return sb.toString();
			}
		}
	
	}
	
	// 提取成对括号内的字符串
	public static String extractBracketInnerStr(String str) {
	        if(!str.contains("{") || !str.contains("}")){
	            return "";
	        }
	        List<String> result = new ArrayList();
	        int m = 0, n = 0, count = 0;
	        for(int i = 0; i < str.length(); i++){
	            if(str.charAt(i) == '{'){
	                if(count == 0) m = i;
	                count ++;
	            }

	            if(str.charAt(i) == '}'){
	                count--;
	                if (count == 0) {
	                    n = i;
	                    result.add(str.substring(m+1,n).trim());
	                }
	            }
	        }

	        // 检验括号是否配对
	        if(count != 0){
	            return "";
	        }
	        return StringUtils.join(result,",");
	    }
}
