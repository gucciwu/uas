<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css"
	href="./css/contacts2013182c6b.css">
<link rel="stylesheet" type="text/css" href="./css/tooltipster.css" />
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.tooltipster.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#oldpassinfo').tooltipster({
							content : $('<p>统一认证账户当前密码</p>')
						});
						$('#newpassinfo')
								.tooltipster(
										{
											content : $('<strong>密码设置规范</strong>：<br><hr>'
													+ '<p>1.大于等于6位<br>2.密码必须包含数字<br>3.密码必须包含字母</p>')
										});
					});

	var bSubmit = false;

	function submit_1() {

		if (checkNull("opass", "旧密码"))
			return;
		if (checkNull("pass", "新密码"))
			return;
		if (checkNull("cpass", "确认新密码"))
			return;
		var opass = $("#opass").val();
		var pass = $("#pass").val();
		var cpass = $("#cpass").val();
		if (pass != cpass) {
			alert("新密码和确认密码不匹配，请重新输入");
			$("#pass").focus();

			$("#pass").val("");
			$("#cpass").val("");
			return;
		}

		$.ajax({
			url : 'modify',
			type : 'POST',
			async : false,
			data : {
				opass : opass,
				pass : pass,
				cpass : cpass
			},
			dataType : 'json',
			timeout : 30000,
			cache : false,
			success : function(json) {
				if (json.code == '1') {
					alert("新密码设置成功");
					if (gotourl != null && gotourl != '' && gotourl != 'null')
						window.location.href = gotourl;
				} else {
					alert(json.msg);
					$("#pass").focus();
					$("#opass").val("");
					$("#pass").val("");
					$("#cpass").val("");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("请求失败，错误信息：" + textStatus);
			}
		});

	}
	function checkNull(id, name) {
		var val = $("#" + id).val();
		if (!val) {
			alert(name + "不可为空");
			$("#" + id)[0].focus();
			return true;
		} else {
			return false;
		}
	}
	function fnSubmit() {
		if (bSubmit)
			return false;
		bSubmit = true;

		return true;
	}
	function EnterSumbit() {
		if (event.keyCode == 13) {
			if (fnSubmit()) {
				var forms = document.getElementsByName('loginForm');
				if (forms.length == 1) {
					forms[0].submit();
				}
			}
		}
	}
<%Object obj = request.getAttribute("msg");
			if (obj != null)
				out.println("alert('" + obj.toString() + "');");%>
	
</script>
<style type="text/css">
.topLink {
	height: 26px;
	padding-top: 20px;
}

.qqmaillogo {
	float: left;
	padding-left: 4px;
}

.wd {
	width: 800px;
	clear: both;
	margin: 0 auto;
}

input {
	margin: 0;
}

.regPanel h2 {
	margin: 0;
	line-height: 32px;
	font-size: 16px;
	font-family: '微软雅黑', 'arial', '黑体';
}

.regPanel {
	
}

label {
	vertical-align: middle;
}

input.txt,textarea.txt,select.txt,option.txt {
	font-size: 14px;
	padding: 2px 3px;
	*padding: 4px 3px 0;
}

select.txt {
	font-size: 14px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	-webkit-border-radius: 3px;
	boder-color: #C3C3C3;
}

.formPanel {
	padding: 20px 0 30px;
}

.formPanel .icon_finish_m,.formPanel .icon_info_m {
	margin: 0 5px;
}

.formPanel .tr {
	margin-bottom: 20px;
	line-height: 24px;
	padding: 2px 0;
}

.formPanel label.column {
	float: left;
	text-align: right;
	width: 90px;
	height: 24px;
	line-height: 24px;
}

.formPanel input.txt {
	width: 230px;
	height: 20px;
}

.formPanel .tipsInfo {
	margin-left: 90px;
	margin-top: -8px;
}

.middleAlign {
	vertical-align: middle;
}

.no_unline {
	text-decoration: none;
}

.infobar {
	padding: 5px 10px;
}

.selectOone {
	margin-top: 32px;
	overflow: hidden;
	zoom: 1;
	_padding-bottom: 40px;
}

.selectOone li {
	list-style: none;
	width: 48%;
	float: left;
	text-align: center;
}

.selectOone li.num1 {
	border-right: 1px solid #dcdfe2;
}

.selectOone li.num3 {
	border-top: 1px solid #dcdfe2;
	width: 100%;
}

.selectOone li.num3 .title {
	line-height: 46px;
	padding-left: 15px;
	background: #f5f4f4;
	_background: #fff;
	font-weight: lighter;
	font-family: "\5FAE\8F6F\96C5\9ED1";
	font-size: 14px;
	text-align: left;
}

.selectOone li .item {
	margin: 0;
	display: block;
	padding-left: 0px;
	margin: 85px 0 0 0;
	height: 260px;
	text-align: center;
}

.selectOone li a:hover {
	text-decoration: none;
}

.selectOone li .item .title {
	line-height: 36px;
	font-size: 18px;
	font-weight: lighter;
	font-family: "\5FAE\8F6F\96C5\9ED1";
}

.selectOone li .info {
	line-height: 24px;
	font-size: 12px;
	text-align: center;
	margin: 0 auto;
	display: block;
	color: #a0a0a0;
}

.selectOone li .btn_gray {
	width: 140px;
	padding: 7px 0;
	font-size: 14px;
	font-weight: bold;
	margin: 30px auto 0 auto;
}
</style>
</head>
<body>
	<header class="header">
		<div class="wd txt_left">
			<div class="logo left">
				<a href="#"><img src="./images/logo.png"></a>
			</div>
			<div class="setinfo right" id="SetInfo">
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
	<div name="Content" class="mainPanel wd txt_left"
		style="margin-top: 20px;">
		<div class="regPanel">
			<form action="/cgi-bin/bizmail_portal" method="POST">
				<input type="hidden" name="action" value="recovery_stat"><input
					type="hidden" name="t" value="biz_rf_mgr">
				<div class="b_size" style="padding: 35px">
					<h2 style="border-width: 0 0 1px 0" class="bd">密码找回问题</h2>
					<div class="formPanel">
						<div class="tr">
							<label class="column">帐号或域名：</label>
							<select style="padding:2px 3px;">
								  <option value ="volvo">Volvo</option>
								  <option value ="saab">Saab</option>
								  <option value="opel">Opel</option>
								  <option value="audi">Audi</option>
							</select>
							<span class="valid_icon"></span>
						</div>
						<div class="addrtitle tr f_size tipsInfo"
							style="line-height: 18px; margin-bottom: 15px;">
							用户需要从登录界面进入“统一认证个人账户管理”后，设置个人的密码找回问题。
						</div>
						<div>
							<div class="tr">
								<label class="column">问题答案：</label>
								<input type="text" name="verifycode" class="txt" value="">
							</div>
						</div>
						<div>
							<div class="tr">
								<label class="column">验证码：</label>
								<span>
									<input type="text" name="verifycode" class="txt" value="">
								</span>
								<span>
								<img style="height: 52px; width: 128px; border-radius: 0 3px 3px 0;"
									src="/cgi-bin/getverifyimage?aid=23000101&amp;0.3459009400103241">
								</span>
							</div>
						</div>
					</div>
					<div class="submitColumn" style="padding-left: 105px;">
						<input type="submit" value="下一步" class="btnSubmit b_size">&nbsp;<a
							href="/login">取消</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="wd txt_center" style="margin: 35px auto;">
		<div class="wd txt_center" style="margin: 35px auto;">
			<div class="navPageBottom">
				<a href="about.html">关于统一认证</a><span style="color: #798699">
					| </span> <a href="Troubleshooter.pdf">常见问题</a><span
					style="color: #798699"> | </span> <a href="manual.pdf">用户手册</a><span
					style="color: #798699"></span>
			</div>
			<div class="copyright addrtitle" style="padding-top: 4px;">宏源证券股份有限公司&nbsp;&nbsp;</div>
		</div>
	</div>
</body>
</html>