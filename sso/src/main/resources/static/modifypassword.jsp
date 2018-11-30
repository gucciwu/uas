<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Object userid=request.getAttribute("userid");
com.apex.cas3.bean.UserBean userBean=(com.apex.cas3.bean.UserBean)session.getAttribute(com.apex.cas3.Constants.SESSION_USER);
if(userBean==null){
	request.getRequestDispatcher("index.html")
	.forward(request, response);
	return;
}
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String issuccess=request.getParameter("success");
String redirecturl=request.getParameter("redirect");
if(redirecturl==null&&session.getAttribute(com.apex.cas3.Constants.SESSION_SERVICE)!=null){
	redirecturl=session.getAttribute(com.apex.cas3.Constants.SESSION_SERVICE).toString();
}
String appid=request.getParameter("appid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<link rel="shortcut icon" href="./images/favicon.ico">
<title>宏源证券统一认证 | 用户密码修改</title>
<meta name="description" content="宏源证券,统一认证,用户密码修改">
<meta name="keywords" content="宏源证券,统一认证,用户密码修改">
<link rel="stylesheet" type="text/css" href="./css/biz189f00.css">
<link rel="stylesheet" type="text/css" href="./css/contacts2013182c6b.css">
<link rel="stylesheet" type="text/css" href="./css/tooltipster.css" />
<link rel="stylesheet" href="css/prettyLoader.css" type="text/css" media="screen"/>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="./js/jquery.tooltipster.js"></script>
<script src="js/jquery.prettyLoader.js" type="text/javascript"></script>
<script type="text/javascript" src="./js/sticky.min.js"></script>
<link rel="stylesheet" href="./css/sticky.min.css" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		$.prettyLoader();
		
	   $('#oldpassinfo').tooltipster({
       		content: $('<p>统一认证账户当前密码</p>')
	   });
	   $('#newpassinfo').tooltipster({
       		content: $('<strong>密码设置规范</strong>：<br><hr>'+
						'<p>1.大于等于6位<br>2.密码必须包含数字<br>3.密码必须包含字母</p>')
	   });
	   
	   //$.sticky("<center><b>注意</b></center><p>统一认证后台已与OA系统对接，OA账户的密码将会同时被修改。</p>");
    });

	var bSubmit = false;
	var gotourl='<%=redirecturl%>';
	
	function submit_1(){
		
		if(checkNull("opass","旧密码"))return;
		if(checkNull("pass","新密码"))return;
		if(checkNull("cpass","确认新密码"))return;
		var opass=$("#opass").val();
		var pass=$("#pass").val();
		var cpass=$("#cpass").val();
		if(pass != cpass){
			alert("新密码和确认密码不匹配，请重新输入");
			$("#pass").focus();

			$("#pass").val("");
			$("#cpass").val("");
			return;
		}
		$.prettyLoader.show();
		
		$.ajax({
	 		url:'modify', 
	 		type: 'POST',
	 		async:true,
	 		data: {opass:opass,pass:pass,cpass:cpass}, 
	 		dataType: 'json',
			timeout: 30000,
			cache:false, 
	 		success: function(json){
	 			if(json.code=='1'){
		 			alert(json.msg);
		 			if(gotourl != null && gotourl!=''&& gotourl!='null')
	 					window.location.href=gotourl;
	 			}else{
		 			alert(json.msg);
	 				$("#pass").focus();
	 				$("#opass").val("");
					$("#pass").val("");
					$("#cpass").val("");
				}
	 		},
	 		error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert("请求失败，错误信息："+textStatus);
	 		}
	 	});
		
	}
	function checkNull(id,name){
		var val=$("#"+id).val();
		if(!val){
			alert(name+"不可为空");
			$("#"+id)[0].focus();
			return true;
		}else{
			return false;
		}
	}
	function fnSubmit(){
		if(bSubmit)
			return false;
		bSubmit = true;

		return true;
	}
	function EnterSumbit()
	{
		if( event.keyCode == 13 )
		{
			if(fnSubmit()){
				var forms = document.getElementsByName('loginForm');
				if (forms.length==1){
					forms[0].submit();
				}
			}	
		}
	}
	<%
	Object obj=request.getAttribute("msg");
	if(obj!=null)out.println("alert('"+obj.toString()+"');");
	%>
	
	
</script>
</head>
<body>
	<header class="header">
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
	</header>
	<nav class="nav">
		<ul class="wd" id="navBar">
			<li><a href="#" class="">统一认证个人账户管理</a></li>
		</ul>
	</nav>
	<div id="main" class="mainPanel wd txt_left">
		<div class="company">
			<aside class="left_panel">
				<ul class="sidetd">
					<li>
						<a href="main.jsp">个人信息<span class="arrow_right"></span></a>
						<p class="line"></p>
					</li>
					<li>
						<a href="applist.jsp">应用列表<span class="arrow_right"></span></a>
						<p class="line"></p>
					</li>
					<li>
						<a href="modifypassword.jsp" class="selected">修改密码<span class="arrow_right"></span></a>
						<p class="line"></p>
					</li>
				</ul>
			</aside>
			<div class="right_panel">
				<div class="register_main ">
				<div class="register_step_box">
				<div class="register_form_tip">
					注意：
							<br>1.密码大于等于6位，必须包含数字、字母
							<br>2.修改后，OA系统的密码需5分钟生效
							<br>3.凡接入统一认证的系统账户密码都将变更
							<br>4.详细情况请参见<a href='manual.pdf'>用户手册</a>
				</div>
	<div class="register_form_1">
		<div class="register_form_title biz_font_yahei">
			修改用户密码
		</div>
		<form name="loginForm" method="post">
			<input type="hidden" name="validate" value="login">
			<div class="register_form_item">
				<label class="register_form_label" for="oldpassword"><em class="register_require">*</em>旧密码：</label>
				<div class="register_form_content">
					<input type="password" class="register_form_input w220"
							id="opass" autocomplete="off" name="opass" value="" maxlength="32">
					<img id="oldpassinfo" src="./images/info.png" class="tooltip"/>
				</div>
			</div>
			<input type="hidden" value="">
			<div class="register_form_item">
				<label class="register_form_label" for="newpassword"><em class="register_require">*</em>新密码：</label>
				<div class="register_form_content">
					<input type="password" class="register_form_input w220" 
						id="pass" autocomplete="off" name="pass" value=""><!--register_form_pw2-->
					<img id="newpassinfo" src="./images/info.png" class="tooltip"/>
				</div>
			</div>
			<div class="register_form_item">
				<label class="register_form_label" for="confirmpassword"><em
					class="register_require">*</em>确认密码：</label>
				<div class="register_form_content">
					<input type="password" class="register_form_input w220"
						id="cpass" autocomplete="off" name="cpass" value="">
				</div>
			</div>
			<div class="register_form_item">
				<div class="register_form_content">
					<input type="button" class="register_form_handmsg" onclick="submit_1();" value="提交信息">
				</div>
			</div>
		</form>
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