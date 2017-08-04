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
<title>新增日志</title>
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
		<input type="hidden" name="logID" value="${log.logID}"/>
	        <div class="form-item">
	            <label for="Log-ip">访问IP：</label>
	            <div class="form-field">
	            	<input type="text" name="ip" id="Log-ip" class="form-text easyui-validatebox" placeholder="1-15个字符" data-options="required:false,validType:['length[1,15]']" 
	            	value="${log.ip}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-type">日志类型：</label>
	            <div class="form-field">
	            	<input type="text" name="type" id="Log-type" class="form-text easyui-validatebox" placeholder="1-255个字符" data-options="required:true,validType:['length[1,255]']" 
	            	value="${log.type}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-name">日志名称：</label>
	            <div class="form-field">
	            	<input type="text" name="name" id="Log-name" class="form-text easyui-validatebox" placeholder="1-30个字符" data-options="required:false,validType:['length[1,30]']" 
	            	value="${log.name}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-content">日志内容：</label>
	            <div class="form-field">
	            	<input type="text" name="content" id="Log-content" class="form-text easyui-validatebox"  data-options="required:false" 
	            	value="${log.content}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-data">操作数据：</label>
	            <div class="form-field">
	            	<input type="text" name="data" id="Log-data" class="form-text easyui-validatebox"  data-options="required:false" 
	            	value="${log.data}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-operatorID">操作员：</label>
	            <div class="form-field">
	            	<input type="text" name="operatorID" id="Log-operatorID" class="form-text easyui-validatebox" placeholder="1-20个字符" data-options="required:false,validType:['length[1,20]']" 
	            	value="${log.operatorID}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-operatorCode">操作员代号：</label>
	            <div class="form-field">
	            	<input type="text" name="operatorCode" id="Log-operatorCode" class="form-text easyui-validatebox" placeholder="1-50个字符" data-options="required:false,validType:['length[1,50]']" 
	            	value="${log.operatorCode}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-operatorName">操作员姓名：</label>
	            <div class="form-field">
	            	<input type="text" name="operatorName" id="Log-operatorName" class="form-text easyui-validatebox" placeholder="1-50个字符" data-options="required:false,validType:['length[1,50]']" 
	            	value="${log.operatorName}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-logDate">日志日期：</label>
	            <div class="form-field">
	            	<input type="text" name="logDate" id="Log-logDate" class="form-text easyui-validatebox"  data-options="required:false" 
	            	value="${log.logDate}">
	            	<!-- <p class="form-des">描述信息</p> -->
	            </div>
	        </div>
	        <div class="form-item">
	            <label for="Log-remark">备注：</label>
	            <div class="form-field">
	            	<input type="text" name="remark" id="Log-remark" class="form-text easyui-validatebox" placeholder="1-255个字符" data-options="required:false,validType:['length[1,255]']" 
	            	value="${log.remark}">
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
