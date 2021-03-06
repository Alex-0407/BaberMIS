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
<link rel="stylesheet" href="css/form1.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<body>
	<form method="post" id="editForm" class="myform form1">
		<input name="roleID" value="${roleID}" type="hidden"/>
		<div class="formItem">
			<div class="formItem_label">
				<label for="name">名称</label>
			</div>
			<div class="formItem_wrapper">
				<div class="formItem_field">
					<input value="${name}" id="name" name="name" type="text" class="easyui-validatebox w2" data-options="required:true,validType:['length[1,30]']"/>
				</div>
			</div>
		</div>
		
		<div class="formItem row">
			<div class="formItem_label">
				<label for="remark">备注</label>
			</div>
			<div class="formItem_wrapper">
				<div class="formItem_field">
					<textarea class="textArea1" name="remark">${remark}</textarea>
				</div>
			</div>
		</div>
	</form>
	
<script>
$(document).ready(function(){
	
});

DialogInterface = {
	validate:function(){
		return $('#editForm').form("validate");
	},
	getData:function(){
		return $('#editForm').serializeArray();
	}
};
</script>
</body>
</html>
