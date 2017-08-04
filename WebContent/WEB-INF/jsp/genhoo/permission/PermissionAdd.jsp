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
<title>新增权限</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<script type="text/javascript" src="js/selector.js"> </script>
<script type="text/javascript" src="js/validate.js"> </script>
<style type="text/css">

</style>
</head>
<body>
	<br>
	<form class="demo form-horizontal" id="addForm" method="post">
		
        <div class="form-item">
            <label for="Permission-name">权限点名称：</label>
            <div class="form-field">
            	<input type="text" name="name" id="Permission-name" class="form-text easyui-validatebox" placeholder="1-100个字符" data-options="required:true,validType:['length[1,100]']">
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Permission-key">权限标识：</label>
            <div class="form-field">
            	<input type="text" name="key" id="Permission-key" class="form-text easyui-validatebox" placeholder="1-30个字符" data-options="required:true,validType:['length[1,30]']">
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Permission-module">模块：</label>
            <div class="form-field">
            	<input type="text" name="module" id="Permission-module" class="form-text easyui-validatebox" placeholder="1-20个字符" data-options="required:true,validType:['length[1,20]']">
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Permission-remark">备注：</label>
            <div class="form-field">
            	<input type="text" name="remark" id="Permission-remark" class="form-text easyui-validatebox" placeholder="1-255个字符" data-options="required:false,validType:['length[1,255]']">
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        
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
</html>
