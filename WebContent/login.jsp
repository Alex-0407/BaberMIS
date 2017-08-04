<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	ScriptLoader loader = new ScriptLoader(new String[]{"base",
			"easyui", "artDialog", "childPage"});
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<style type="text/css">
#img {
	z-index: -1;
	filter: alpha(opacity = 85);
	position: absolute;
	width: 100%;
	height: 100%;
}
</style>

<script type="text/javascript">
	function randoms() {
		$("#random").attr("src",
				"admin/SecurityCode/code?t=" + new Date().getTime());
	}
	var loginAndRegDialog;
	var loginInputForm;
	$(function() {
		loginAndRegDialog = $('#loginAndRegDialog')
				.dialog(
						{
							width : 400, //宽度
							height : 200, //高度
							closable : false,
							model : true,
							buttons : [
									{
										text : '登录',

										handler : function() {
											//将数据序列化
											var parm = $("#loginInputForm")
													.serialize();
											//中文格式转换
											var pp = decodeURIComponent(parm,
													true);
											//post异步提交
											$
													.post(
															"admin/Baber/login",
															pp,
															function(data) {
																if (data.result == "success"
																		&& data.type == "理发师") {
																	window.location.href = "frame/baber_main.jsp";
																} else if (data.result == "success"
																		&& data.type == "管理员") {
																	window.location.href = "frame/admin_main.jsp";
																} else {
																	window.location.href = "login.jsp";
																}
															});
										}
									}, {
										text : '注册',
										handler : function() {
										}
									} ]
						})

		loginInputForm = $('#loginInputForm').form({
			url : "admin/SecurityCode/code",
			success : function(r) {
				r = $.parseJSON(r);
				if (r == 'ok') {
					loginAndRegDialog.dialog('close');
					$.messager.show({
						title : '提示',
						msg : '您登录成功'
					});
				} else {
					$.messager.alert('提示', '登录失败');
				}
			}
		});

		$.extend($.fn.validatebox.defaults.rules, {
			eqPassword : {
				validator : function(value, param) {

					return value == $(param[0]).val();
				},
				message : '密码不一致'
			}
		});
	});
</script>

</head>
<body scroll=no>
	<div id="img">
		<img src="image/bg/bg2.jpg" style="width: 100%; height: 99%">
		<div id="top"></div>
		<div id="loginAndRegDialog" title="用户登录"
			style="width: 250px; height: 180px;">
			<form id="loginInputForm" method="post"
				style="margin-bottom: 18px; margin-top: 35px;">
				<table style="margin: 0px auto;">
					<tr>
						<th>用户名:</th>
						<td><input style="width: 145px;" class="easyui-validatebox"
							required="true" type="text" name="name" size="19"
							missingMessage="请输入用户名" /></td>
						<td></td>
					</tr>
					<tr>
						<th>密 码:</th>
						<td><input style="width: 147px;" class="easyui-validatebox"
							required="true" type="password" name="password"
							missingMessage="请输入密码" /></td>
						<td></td>
					</tr>
					<!-- <tr>
						<th align="right">验证码</th>
						<td><input class="easyui-validatebox" required="true"
							type="text" name="yanzhengma" size="19" missingMessage="请输入验证码" /></td>
						<td><img src="admin/SecurityCode/code" id="random"
							style="cursor: pointer;" title="点击刷新验证码" onclick="randoms()" /></td>
					</tr> -->
				</table>
			</form>
		</div>
	</div>
</body>
</html>
