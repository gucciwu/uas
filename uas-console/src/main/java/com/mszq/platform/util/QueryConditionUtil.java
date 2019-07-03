package com.mszq.platform.util;

public class QueryConditionUtil {

	public static String getStrInCondition(String str, String split) {
		if (StringUtil.isEmpty(str)) {
			return str;
		} else {
			if (StringUtil.isEmpty(split)) {
				split = ",";
			}
			String[] spls = str.split(split);
			StringBuffer sb = new StringBuffer();
			for (String spl : spls) {
				sb.append("'");
				sb.append(spl);
				sb.append("',");
			}
			String result = sb.toString();
			return sb.substring(0, sb.length() - 1);
		}
	}
	
	public static String getNumInCondition(String str, String split) {
		if (StringUtil.isEmpty(str)) {
			return str;
		} else {
			if (StringUtil.isEmpty(split)) {
				split = ",";
			}
			String[] spls = str.split(split);
			StringBuffer sb = new StringBuffer();
			for (String spl : spls) {
				sb.append(spl);
				sb.append(",");
			}
			String result = sb.toString();
			return sb.substring(0, sb.length() - 1);
		}
	}

}
