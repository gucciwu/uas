<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String serviceUrl1 = (String)request.getSession().getAttribute("TYRZ_SERVICE");
String oauid = (String)request.getParameter("oauid");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<link rel="shortcut icon" href="./images/favicon.ico">
<title>宏源证券统一认证 | OA密码同步</title>
<meta name="description" content="宏源证券,统一认证,OA密码同步">
<meta name="keywords" content="宏源证券,统一认证,OA密码同步">
<link rel="stylesheet" type="text/css" href="./css/biz189f00.css">
<link rel="stylesheet" type="text/css" href="./css/contacts2013182c6b.css">
<link rel="stylesheet" type="text/css" href="./css/tooltipster.css" />
<link rel="stylesheet" href="css/prettyLoader.css" type="text/css" media="screen"/>
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="./js/jquery.tooltipster.js"></script>
<script src="js/jquery.prettyLoader.js" type="text/javascript"></script>
<script type="text/javascript">
	var gotourl = '<%=serviceUrl1%>';
	var oauid='<%=oauid%>';

	$(document).ready(function() {
		$.prettyLoader();
	
		$('#oauidinfo').tooltipster({
	    	content: $('<p>用户OA账户号</p>')
		});
		
		$('#oldpassinfo').tooltipster({
		   	content: $('<p>用户原OA系统的账户密码</p>')
		});
		$('#newpassinfo').tooltipster({
	   		content: $('<strong>密码设置规范</strong>：<br><hr>'+
			'<p>1.大于等于6位<br>2.密码必须包含数字<br>3.密码必须包含字母</p>')
		});
		
		if(oauid != 'null'){
			$("input[id='oauid']").attr("value",oauid);
			$("div[id='account']").css("display","none");
		}
    });

	var bSubmit = false;
	
	function submit_1(){
		
		if(checkNull("oauid","账户"))return;
		if(checkNull("opass","旧密码"))return;
		if(checkNull("pass","新密码"))return;
		if(checkNull("cpass","确认新密码"))return;
		var oauid=$("#oauid").val();
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
	 		url:'changeoapassword', 
	 		type: 'POST',
	 		async:true,
	 		data: {oauid:oauid,opass:opass,pass:pass,cpass:cpass}, 
	 		dataType: 'json',
			timeout: 30000,
			cache:false, 
	 		success: function(json){
	 			$.prettyLoader.hide();
	 			if(json.code=='1'){
		 			alert("新密码设置成功");
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
	 			$.prettyLoader.hide();
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
				<span class=""><a href="oa.pdf" target="_blank">说明</a></span>
				<span class="lastset"><a href="index.html">返回登录</a></span>
			</div>
		</div>
	</header>
	<nav class="nav">
		<ul class="wd" id="navBar">
			<li><a href="#" class="">OA密码同步</a></li>
		</ul>
	</nav>
	<div id="main" class="mainPanel wd txt_left">
		<div class="company">
			<div class="change_oa_pwd_panel">
				<div class="register_main ">
				<div class="register_step_box" style="width: auto;background: none;">
				<div class="register_form_tip" style="width: 350px;float:right;overflow:hidden;">
					<h1 style="color:red;">通知</h1>
						<b>
							<p>由于您首次通过统一认证登录OA系统，为确保账户安全，请修改您的OA账户密码，统一认证将对OA账户的密码进行同步：</p>
							<br>1.“账号”输入框请输入您的OA账号，“旧密码”输入框输入原OA账户密码，首次登录OA用户OA初始密码为:password
							<br>1.新密码必须大于等于6位，必须包含数字、字母
							<br>2.修改后，OA系统的密码需5分钟才能生效，请耐心等待
							<br>3.凡接入统一认证的应用系统（如新营销平台、投顾系统等），账户密码都将更新为本次设置的密码
							<br>4.如您使用了foxmail,Outlook等邮件收发客户端，请您及时更新密码
						</b>
				</div>
	<div class="register_form_1">
		<div class="register_form_title biz_font_yahei">
			修改用户密码
		</div>
		<form name="loginForm" method="post">
			<input type="hidden" name="validate" value="login">
			<div class="register_form_item" id="account">
				<label class="register_form_label" for="oauid"><em class="register_require">*</em>账号：</label>
				<div class="register_form_content">
					<input type="text" class="register_form_input w220"
							id="oauid" autocomplete="off" name="oauid" value="" maxlength="32">
					<img id="oauidinfo" src="./images/info.png" class="tooltip"/>
				</div>
			</div>
			<input type="hidden" value="">
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