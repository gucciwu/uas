<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*;" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache">
<link rel="shortcut icon" href="./images/favicon.ico">
<title>宏源证券统一认证 | 用户密码重置</title>
<meta name="description" content="宏源证券,统一认证,用户密码重置">
<meta name="keywords" content="宏源证券,统一认证,用户密码重置">
<link rel="stylesheet" type="text/css" href="css/biz189f00.css">
<link rel="stylesheet" type="text/css" href="css/contacts2013182c6b.css">
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
<link rel="stylesheet" href="css/prettyLoader.css" type="text/css" media="screen"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script src="js/jquery.prettyLoader.js" type="text/javascript"></script>
<script type="text/javascript" src="./js/sticky.min.js"></script>
<link rel="stylesheet" href="./css/sticky.min.css" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		$.prettyLoader();
		
	   $('#accountinfo').tooltipster({
       		content: $('<p>1.正式员工的用户账号与公司 OA 系统的用户名相同<br>2.非员工的经纪人（及非全日制员工）的用户账号是其经纪人编号</p>')
	   });
	   $('#nameinfo').tooltipster({
       		content: $('<p>1.员工的姓名以人力系统录入的用户名为准<br>2.非员工的经纪人（及非全日制员工）姓名以新营销服务平台录入信息为准</p>')
	   });
	   $('#idinfo').tooltipster({
       		content: $('<p>如使用过15位身份证号码的用户，请确认在人力系统或新营销服务平台中登录的身份证位数</p>')
	   });
	   $('#phoneinfo').tooltipster({
       		content: $('<p>手机号码发生变更的用户将无法收到密码重置短信，请登录人力系统或新营销平台修改跟人信息</p>')
	   });
	   
	   //$.sticky("<center><b>注意</b></center><p>统一认证后台已与OA系统对接，OA账户的密码将会同时被重置。</p>");
    });
	
	//loading dialog
	var wbox;

	function submit_1(){
		if(!checkNull("userid","登录账号")) {return false;}
		if(!checkNull("username","姓名")) {return false;}
		if(!checkNull("sfzh","身份证号")) {return false;}
		if(!checkNull("sjhm","手机号码")) {return false;}
		var userid=$("#userid").val();
		var username=$("#username").val();
		var sfzh=$("#sfzh").val();
		var sjhm=$("#sjhm").val();
		var postfunc = $.post("ResetPasswordServlet",
			{"userid":userid,"username":username,"sfzh":sfzh,"sjhm":sjhm},
			function(json){
				//alert("is ok");
				alert(json.msg);
				if(json.code==1){
					window.location.href="index.html";
				}
				$.prettyLoader.hide();
			},"json").error(function(){$.prettyLoader.hide();alert("网络异常，请再试一次！");});
		
		$.prettyLoader.show();
	}

	function checkNull(id,name){
		var val=$("#"+id).val();
		if(!val){
			alert(name+"不可为空");
			$("#"+id).focus();
			return false;
		}else{
			return true;
		}
	}
</script>
</head>
<body>
	<header class="header">
		<div class="wd txt_left">
			<div class="logo left">
				<a  href="#"><img src="./images/logo.png"></a>
			</div>
			<div class="setinfo right" id="SetInfo">
				<span class=""><a href="Troubleshooter.pdf" target="_blank">常见问题</a></span>
				<span class=""><a href="index.html">返回</a></span>
			</div>
		</div>
	</header>
	<nav class="nav" style="clear:both;">
		<ul class="wd" id="navBar">
			<li><a href="#" class="">统一认证个人账户管理</a></li>
		</ul>
	</nav>
	<div id="loadingbar"></div>
		<div class="register_main ">
	<div class="register_content_1 register_content">
		<div class="register_step_box">
			<div class="register_form_tip">
				说明：
				<br>1.修改后，OA系统的密码需5分钟生效
				<br>2.凡接入统一认证的系统账户密码都将变更
				<br>3.如需修改个人信息，请参见<a href="manual.pdf">用户手册</a>。
			</div>
			<div class="register_form_1">
				<div class="register_form_title biz_font_yahei">
					重置用户密码
				</div>
				<iframe id="actionFrame" name="actionFrame" style="display: none"></iframe>
				<form id="frm" method="post" >
					<input type="hidden" name="action" value="signup"><input
						type="hidden" name="t" value="biz_rf_mgr">
					<div class="register_form_item">
						<label class="register_form_label" for="reigster_account"><em
							class="register_require">*</em>登录账号：</label>
						<div class="register_form_content">
							<input type="text" class="register_form_input w220"
								id="userid" autocomplete="off" name="userid" value=""
								maxlength="32">
							<img id="accountinfo" src="./images/info.png" class="tooltip"/>
						</div>
					</div>
					<div class="register_form_item">
						<label class="register_form_label" for="reigster_account"><em
							class="register_require">*</em>姓名：</label>
						<div class="register_form_content">
							<input type="text" class="register_form_input w220"
								id="username" autocomplete="off" name=username value=""
								maxlength="32">
							<img id="nameinfo" src="./images/info.png" class="tooltip"/>
						</div>
					</div>
					<div class="register_form_item">
						<label class="register_form_label" for="reigster_account"><em
							class="register_require">*</em>身份证号：</label>
						<div class="register_form_content">
							<input type="text" class="register_form_input w220"
								id="sfzh" autocomplete="off" name="sfzh" value=""
								maxlength="32">
							<img id="idinfo" src="./images/info.png" class="tooltip"/>
						</div>
					</div>
					<div class="register_form_item">
						<label class="register_form_label" for="reigster_account"><em
							class="register_require">*</em>手机号：</label>
						<div class="register_form_content">
							<input type="text" class="register_form_input w220"
								id="sjhm" autocomplete="off" name="sjhm" value=""
								maxlength="32">
							<img id="phoneinfo" src="./images/info.png" class="tooltip"/>
						</div>
					</div>
					
					<div class="register_form_item">
						<div class="register_form_content">
							<input type="button" class="register_form_handmsg" onclick="submit_1();" value="提交" 
								style="filter: alpha(opacity = 50);">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
	<div class="wd txt_center" style="margin: 35px auto;">
		<div class="navPageBottom">
			<a href="about.html">关于统一认证</a><span style="color: #798699"> | </span>
			<a href="Troubleshooter.pdf">常见问题</a><span style="color: #798699"> | </span>
			<a href="manual.pdf">用户手册</a><span style="color: #798699"></span>
		</div>
		<div class="copyright addrtitle" style="padding-top: 4px;">宏源证券股份有限公司&nbsp;&nbsp;</div>
	</div>
</body>
</html>