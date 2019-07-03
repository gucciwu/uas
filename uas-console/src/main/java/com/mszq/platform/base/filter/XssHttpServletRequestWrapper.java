package com.mszq.platform.base.filter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	HttpServletRequest orgRequest = null;

	/**
	 * 不需要过滤XSS的参数（如编辑器相关参数 密码等参数)
	 */
	private static final List<String> XSSWhiteList = Arrays.asList("password");

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}

	/**
	 * 覆盖getParameterValues方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 */
	@Override
	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		if (XSSWhiteList.contains(parameter)) {
			return values;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = xssEncode(values[i]);
		}
		return encodedValues;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 */
	@Override
	public String getParameter(String parameter) {

		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}

		if (XSSWhiteList.contains(parameter)) {
			return value;
		}

		return xssEncode(value);
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 *
	 * @param s
	 * @return
	 */
	public static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append("＞");// 全角大于号
				break;
			case '<':
				sb.append("＜");// 全角小于号
				break;
			case '\'':
				sb.append("’");// 全角单引号
				break;
			case '\"':
				sb.append("”");// 全角双引号
				break;
			case '&':
				sb.append("＆");// 全角&
				break;
			case '#':
				sb.append("＃");// 全角#
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '%':
				sb.append('％'); // 全角%
				break;
			case '|':
				sb.append('｜'); //
				break;
			case '$':
				sb.append('＄'); //
				break;
			case '@':
				sb.append('＠'); //
				break;
			case '(':
				sb.append('（'); //
				break;
			case ')':
				sb.append('）'); //
				break;
			case '+':
				sb.append('＋'); //
				break;
//			case ',':
//				sb.append('，'); //以,;分隔的多id等会使用类似特殊字符
//				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 获取最原始的request
	 *
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {
		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 *
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) req).getOrgRequest();
		}

		return req;
	}

}
