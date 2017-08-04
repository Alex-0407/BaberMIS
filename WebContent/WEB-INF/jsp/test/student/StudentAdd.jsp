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
<title>这里是标题</title>
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
				<td class="formLabel w1"><span class="needInput">*</span><span>名称</span></td>
				<td class="formField">
					<input name="name" type="text" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>编码</span></td>
				<td class="formField">
					<input type="text" name="code" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField help">该项不能重复，如果不输，将由于系统自动生成</td>
			</tr>
		</table>

		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span>备注</span></td>
				<td class="formField">
					<textarea rows="5" class="easyui-validatebox w4" name="remark" data-options="validType:['length[0,255]']"></textarea>
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
