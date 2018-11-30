<%@page import="java.util.List"%>
<%@page import="com.apex.cas3.bean.AppBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Object userid=request.getAttribute("userid");
com.apex.cas3.bean.UserBean userBean=(com.apex.cas3.bean.UserBean)session.getAttribute(com.apex.cas3.Constants.SESSION_USER);
if(userBean==null){
	request.getRequestDispatcher("index.html")
	.forward(request, response);
	return;
}
List<AppBean> listApp=AppBean.getListApp();//request.getRealPath("app")

Object temp=request.getSession().getAttribute("MUST_EDIT_PWD");
if(temp!=null&&"true".equals(temp.toString())){
	request.getRequestDispatcher("modifypassword.jsp")
	.forward(request, response);
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="./images/favicon.ico">
<title>宏源证券统一认证 | 接入应用列表</title>
<meta name="description" content="宏源证券,统一认证,接入应用列表">
<meta name="keywords" content="宏源证券,统一认证,接入应用列表">
<link rel="stylesheet" type="text/css" href="./css/biz189f00.css">
<link rel="stylesheet" type="text/css" href="./css/contacts2013182c6b.css">
</head>
<body>
	<header class="header">
		<div class="wd txt_left">
			<div class="wd txt_left">
				<div class="logo left">
					<a href="#"><img src="./images/logo.png"></a>
				</div>
				<div class="setinfo right" id="SetInfo">
					<span class="userGreet">您好，<%=userBean.getName() %></span>
					<span class=""><a href="Troubleshooter.pdf" target="_blank">常见问题</a></span>
					<span class="lastset"><a href="logout">退出</a></span>
				</div>
			</div>
		</div>
	</header>
	<nav class="nav">
		<ul class="wd" id="navBar">
			<li><a href="#" class="">统一认证个人账户管理</a></li>
		</ul>
	</nav>
	<div id="loadingbar"></div>
	<div id="main" class="mainPanel wd txt_left" >
		<div class="company">
			<aside class="left_panel">
				<ul class="sidetd">
					<li>
						<a href="main.jsp">个人信息<span class="arrow_right"></span></a>
						<p class="line"></p>
					</li>
					<li>
						<a href="applist.jsp" class="selected">应用列表<span class="arrow_right"></span></a>
						<p class="line"></p>
					</li>
					<li>
						<a href="modifypassword.jsp">修改密码<span class="arrow_right"></span></a>
						<p class="line"></p>
					</li>
				</ul>
			</aside>
			<div class="right_panel">
				<div class="content_block" style="padding-top: 0; background: #FFFFFF;">
					<div class="con_body">
						<table class="listTable blockall with_move member_list_table"
							style="width: 100%; table-layout: fixed; zoom: 1;">
							<colgroup class="caption f_size">
								<col class="col_name" style="width: 25%;">
								<col class="col_email" style="width: 60%;">
								<col class="col_operation" style="width: 15%;">
							</colgroup>
							<thead>
								<tr>
									<th colspan="3" style="width: 100%; margin: 5px 10px 0 19px;"><h2>应用清单</h2></th>
								</tr>
							</thead>
							<tbody>
								<% 
									int index=0;
									for(AppBean bean:listApp){
										if(!bean.isVisible())continue;
								%>
								<tr>
									<td class="name"></span><span><h2> <%=bean.getName() %> </h2></span></td>
									<td class="email"><span><%=bean.getContent() %></span></td>
									<td >
										<a href="<%=bean.getUrl() %>" class="register_goto"></a>
									</td>
								</tr>
								<%
									index++;
									}
								%>
							</tbody>
						</table>
						<div class=" page_tool">
							<span class="right"></span>
							<div class="r_obj left" style="float: left;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="wd txt_center" style="margin: 35px auto;">
		<div class="wd txt_center" style="margin: 35px auto;">
			<div class="navPageBottom">
				<a href="about.html">关于统一认证</a><span style="color: #798699"> | </span>
				<a href="Troubleshooter.pdf">常见问题</a><span style="color: #798699"> | </span>
				<a href="manual.pdf">用户手册</a><span style="color: #798699"></span>
			</div>
			<div class="copyright addrtitle" style="padding-top: 4px;">宏源证券股份有限公司&nbsp;&nbsp;</div>
		</div>
	</div>
</body>
</html>