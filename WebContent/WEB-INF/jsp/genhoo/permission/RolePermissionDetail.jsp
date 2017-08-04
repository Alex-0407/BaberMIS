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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>新增角色权限</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<script type="text/javascript" src="js/selector.js"> </script>
<script type="text/javascript" src="js/validate.js"> </script>
<style type="text/css">

</style>
</head>
<body>
	<br>
	<form class="demo form-horizontal" id="detailForm" method="post">
		
        <div class="form-item">
            <label for="RolePermission-role">角色：</label>
            <div class="form-field">
            	<input type="text" name="role" id="RolePermission-role" class="form-text easyui-validatebox" placeholder="1-20个字符" data-options="required:true,validType:['length[1,20]']" 
            	value="${rolePermission.role}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="RolePermission-module">模块：</label>
            <div class="form-field">
            	<input type="text" name="module" id="RolePermission-module" class="form-text easyui-validatebox" placeholder="1-20个字符" data-options="required:true,validType:['length[1,20]']" 
            	value="${rolePermission.module}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="RolePermission-permission">权限点：</label>
            <div class="form-field">
            	<input type="text" name="permission" id="RolePermission-permission" class="form-text easyui-validatebox" placeholder="1-20个字符" data-options="required:true,validType:['length[1,20]']" 
            	value="${rolePermission.permission}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        
	</form>
<script>

$(document).ready(function(){
	
});

</script>
</body>
</html>
