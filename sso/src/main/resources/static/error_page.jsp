<%@page import="java.util.List"%>
<%@page import="com.apex.cas3.bean.AppBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String appid = request.getParameter("appid");
	String userid = request.getParameter("userid");
	String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<link rel="shortcut icon" href="./images/favicon.ico">
<title>宏源证券统一认证 | 错误页面</title>
<meta name="description" content="宏源证券,统一认证,错误页面">
<meta name="keywords" content="宏源证券,统一认证,错误页面">
<link rel="stylesheet" type="text/css" href="./css/biz189f00.css">
<link rel="stylesheet" type="text/css" href="./css/contacts2013182c6b.css">
</head>
<body>
	<header class="header">
		<div class="wd txt_left">
			<div class="logo left">
				<a href="#"><img src="./images/logo.png"></a>
			</div>
		</div>
	</header>
	<div id="main" class="mainPanel wd txt_left">
		<div class="company">
				<div class="content_block"
					style="padding-top: 0; background: #FFFFFF;">
					<div class="domainMainPanel">
						<div class="con_body b_size">
						<h1 class="p_title">错误信息</h1>
						<div class="formPanel" style="padding: 0;">
							<section>
								<div class="settingDiv_l">应用编码（AppId）</div>
								<div class="settingDiv_r">
									<div><%=appid%></div>
								</div>
							</section>
							<section>
								<div class="settingDiv_l">用户编号（UserId）</div>
								<div class="settingDiv_r">
									<div><%=userid%></div>
								</div>
							</section>
							<section>
								<div class="settingDiv_l">错误信息</div>
								<div class="settingDiv_r">
									<div><%=msg%></div>
								</div>
							</section>
						</div>
					</div>
				</div>
					</div>
			</div>
		</div>
</body>
</html>