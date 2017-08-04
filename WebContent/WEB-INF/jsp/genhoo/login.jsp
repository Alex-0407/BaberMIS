<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScriptLoader loader = new ScriptLoader(new String[]{"jquery","bootstrap","responsive"});
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>人事局用户登录-国家工作人员招考报名系统</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

.header{
	background-color: #51a351;
	background:linear-gradient(45deg, #050f06 0%,#005DAF 100%);
	background: -moz-linear-gradient(45deg, #050f06 0%, #005DAF 100%);
	background: -o-linear-gradient(45deg, #050f06 0%,#005DAF 100%);
	background: -ms-linear-gradient(45deg, #050f06 0%,#005DAF 100%);
}

.logo{
	padding: 10px 0;
	float: none;
	margin: 0;
	position: relative;
}

.content{
	width:890px;
	position: relative;
	margin: 50px auto 0px;
	border: 4px solid #eee;
	border-radius:4px;
	overflow: auto;
	zoom:1;
	height: 352px;
}

.left{
	position: absolute;
	width:400px;
	height: 350px;
	border-right: 1px solid #e5e5e5;
}

.right{
	width: 390px;
	position: absolute;
	left: 400px;
	padding: 50px 50px 0px 50px;
}

.u-help{
	position: relative;
	top:-5px; 
	display: none;
}

.u-errormessage{
	color: #da4f49;
}

.logo-link{
	display: inline-block;
	padding: 0px 10px;
	border-right: 1px solid #e5e5e5;
}

.welcome{
	font-size: 26px;
	font-family: 'Microsoft Yahei';
	color: #fff;
	vertical-align: middle;
	margin-left: 10px;
}

</style>
</head>
<body>
	
	<div class="header">
		<div class="container">
			<div class="logo">
				<a href="index.jsp" class="logo-link">
					<img alt="" src="image/icon/logo-new.png">
				</a>
				<span class="welcome">欢迎登录</span>
			</div>
		</div>
	</div>
	
	<div class="content">
		<div class="left">
		
		</div>
		
		<div class="right">
			<form action="Admin/loginHandler" class="" id="login-form">
				<fieldset>
					<legend>人事局用户登录</legend>
					<label>登录名</label>
    				<input type="text" class="input-large" name="username" placeholder="输入用户名" id="js-username">
    				<span class="help-inline u-help u-errormessage" id="js-username-helpinfo">请输入用户名</span>
    				
    				<label>密码</label>
    				<input type="password" class="input-large" name="password" id="js-password" placeholder="请输入密码"/>
    				<span class="help-inline u-help u-errormessage" id="js-password-helpinfo">请输入密码</span>
    				<span class="help-block u-errormessage" id="js-server-helpinfo">&nbsp;</span>
    				
    				<input type="submit" value="登录" class="btn btn-primary" id="js-btn-login"/>
    				
				</fieldset>
			</form>
		</div>
		
	</div>
	
<script>
	$(document).ready(function(e){
		
		var usernameMessage = $("#js-username-helpinfo");
		var passwordMessage = $("#js-password-helpinfo");
		var serverMessage = $("#js-server-helpinfo");
		var username  = $("#js-username");
		var password = $("#js-password");
		var loginBtn = $("#js-btn-login");
		
		username.focus();
		
		username.on("input",function(e){
			if($(this).val()==""){
				usernameMessage.show();
			}else {
				usernameMessage.hide();
			}
		});
		
		
		password.on("input",function(e){
			if($(this).val()==""){
				passwordMessage.show();
			}else {
				passwordMessage.hide();
			}
		});
		
		
		$("#login-form").submit(function(e){
			e.preventDefault();
			var action =  $(this).attr("action");
			if(username.val()==""){
				usernameMessage.show();
				return;
			}
			
			if(password.val() == ""){
				passwordMessage.show();
				return;
			}
			
			loginBtn.val("正在登录...");
			
			var formData = [{name: 'username', value: username.val()}, {name: 'password', value: password.val()}];
			
			$.post(action,formData,function(data){
				console.info(data);
				if(data.OK){
					serverMessage.html("&nbsp;");
					loginBtn.removeClass("btn-primary").addClass("btn-success");
					loginBtn.val("登录成功,正在跳转...");
					window.location.href="Admin/index";
				}else{
					serverMessage.html(data.message);
					loginBtn.val("登录");
				}
			});
			
		});
		
	});
</script>
</body>
</html>
