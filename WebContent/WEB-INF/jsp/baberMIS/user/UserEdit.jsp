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
<script type="text/javascript" src="page/js/jquery.js"></script>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑用户</title>
<link rel="stylesheet" href="css/form.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">
</style>
</head>

<body>

	<form method="post" class="baseform" id="addForm">
		<input type="hidden" value="${user.userId}" name="userId">
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>名称</span></td>
				<td class="formField"><input name="userName" value="${user.userName}"
					type="text" class="easyui-validatebox"
					data-options="required:true,validType:['length[1,30]']"> <span
					class="errorInfo"></span></td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>

		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>性别</span></td>
				<td class="formField"><input type="hidden" value="${user.userSex}"
					id="sexMW" />
					<div id="radioCheck">
						<input type="radio" name="userSex" value="男" id="sexM"/> 男 <input
							type="radio" name="userSex" value="女" id="sexW" /> 女
					</div></td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>

		<script type="text/javascript">
			$(function() {
				var yourVal = document.getElementById('sexMW').value;
				$("input[name='userSex']").each(function(index) {
				    if ($("input[name='userSex']").get(index).value == yourVal) {
				        $("input[name='userSex']").get(index).checked = true;
				    }
				});
			});
		</script>

		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>手机号</span></td>
				<td class="formField"><input type="text" name="userTel"
					value="${user.userTel}" class="easyui-validatebox"
					data-options="required:true,validType:['length[1,30]']"> <span
					class="errorInfo"></span></td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>

	</form>

	<script>
		DialogInterface = {
			validate : function() {
				return $('#addForm').form("validate");
			},
			getData : function() {
				return $('#addForm').serializeArray();
			}
		};
	</script>
</body>