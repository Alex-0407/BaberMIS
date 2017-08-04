<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScriptLoader loader = new ScriptLoader(new String[]{"base","easyui","artDialog","childPage"});
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑管理员</title>
<link rel="stylesheet" href="css/form.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>

<body>
    
	<form  method="post" class="baseform" id="addForm">
		<input type="hidden" value="${baber.baberId}" name="baberId">
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>真实姓名</span></td>
				<td class="formField">
					<input disabled="disabled" type="text" name="realName" value="${baber.realName}" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
					<span class="errorInfo"></span>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField help">该项不能修改</td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>用户名</span></td>
				<td class="formField">
					<input name="baberName" value="${baber.baberName}" type="text" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
					<span class="errorInfo"></span>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>电话</span></td>
				<td class="formField">
					<input type="text" name="baberTel" value="${baber.baberTel}" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
					<span class="errorInfo"></span>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>地址</span></td>
				<td class="formField">
					<input type="text" name="baberAddress" value="${baber.baberAddress}" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
					<span class="errorInfo"></span>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
	</form>

<script>
DialogInterface = {
	validate:function(){
		return $('#addForm').form("validate");
	},
	getData:function(){
		return $('#addForm').serializeArray();
	}
};

</script>
</body>
