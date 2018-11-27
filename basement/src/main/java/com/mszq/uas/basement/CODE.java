package com.mszq.uas.basement;

public class CODE {
	
	/**成功*/
	public static final int SUCCESS = 0;
	
	public class SYS{
		
		/**BP网关无响应*/
		public static final int NO_RESPONSE_FROM_BPGATEWAY = 101;
		/**BP返回状态不为0*/
		public static final int BP_GATEWAY_ERROR = 102;
		
		/**数据库异常*/
		public static final int DATABASE_EXCEPTION = 301;
		/**执行SQL语句失败*/
		public static final int FAILURE_IN_EXECUTING_SQL = 302;
		/**缺少请求参数*/
		public static final int REQUIRED_PARAMETER_IS_NOT_PRESENT = 303;
		/**参数解析失败*/
		public static final int COULD_NOT_READ_JSON = 304;
		/**参数验证失败*/
		public static final int FAIL_VERIFY_PARAM = 305;
		/**参数绑定失败*/
		public static final int FAIL_BIND_PARAM = 306;
		/**请求合法性校验失败*/
		public static final int FAIL_VERIFY_REQUEST_VALIDITY = 307;
		/**不支持当前请求方法*/
		public static final int REQUEST_METHOD_NOT_SUPPORTED = 308;
		/**不支持当前媒体类型*/
		public static final int CONTENT_TYPE_NOT_SUPPORTED = 309;
		/**Internal Server Error*/
		public static final int INTERNAL_SERVER_ERROR = 310;

		/**应用系统校验串错误*/
		public static final int APP_SECRET_NOT_MATCH = 100;
		/**未知异常*/
		public static final int UNKOWN_EXCEPTION = 99999;
		
		
	    
	}
	
	

	public class BIZ{
		/**客户SSO -1~-99*/
		/**账户被锁定*/
		public static final int LOCKED = -1;
		/**认证失败*/
		public static final int AUTH_FAIL = -2;
		/**会话不存在*/
		public static final int SESSION_NOT_EXIST = -3;
		/**用户没有分配角色*/
		public static final int USER_HAS_NO_ROLE = -4;
		/**用户不存在*/
		public static final int USER_NOT_EXIST = -5;
		/**角色下没有配置系统使用权限*/
		public static final int NO_APP_AUTH_FOR_ROLE = -6;
		/**应用不存在*/
		public static final int APP_NOT_EXIST = -7;
		/**应用注册信息不匹配*/
		public static final int SECRET_NOT_MATCH = -8;
		/**创建token失败*/
		public static final int FAILURE_IN_CREATE_TOKEN = -9;
		/**token不存在*/
		public static final int TOKEN_NOT_EXIST = -10;
		/**没有应用的账户*/
		public static final int NOT_APP_ACCOUNT_ID = -11;
		/**插入SQL失败*/
		public static final int FAIL_INSERT_SQL = -12;
		/**更新SQL失败*/
		public static final int FAIL_UPDATE_SQL = -13;
		/**删除SQL失败*/
		public static final int FAIL_DELETE_SQL = -14;
		/**查询SQL失败*/
		public static final int FAIL_SELECT_SQL = -15;
		/**密码错误*/
		public static final int PASSOWRD_NOT_MATCH = -16;
		/**没有查询条件*/
		public static final int NOT_QUERY_CONDITION = -17;
		/**违反数据库唯一性限制*/
		public static final int DUPLICATE_KEY_ERROR = -18;
		/**请求来自IP黑名单*/
		public static final int ILLEGAL_REMOTE_IP = -19;
		/**数据库中记录不存在*/
		public static final int NOT_EXIST_RECORD = -20;
	}
}
