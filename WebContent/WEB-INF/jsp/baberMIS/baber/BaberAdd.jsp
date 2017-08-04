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
<title>新增理发师</title>
<link rel="stylesheet" href="css/form.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<link rel="stylesheet" href="css/form.css">
<body>
	<form method="post" class="baseform" id="addForm">
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>真实姓名</span></td>
				<td class="formField">
					<input name="realName" type="text" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>性别</span></td>
				<td class="formField">
					<input type="radio" name="baberSex" value="男" checked="checked"/> 男
                    <input type="radio" name="baberSex" value="女"/> 女
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>年龄</span></td>
				<td class="formField">
					<input type="text" name="baberAge" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
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
					<input type="text" name="baberTel" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
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
					<input type="text" name="baberAddress" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>身份证号</span></td>
				<td class="formField">
					<input type="text" name="baberIDCard" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>

		
	</form>
<script>

$(document).ready(function(){
	
});

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
