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
<title>新增附件</title>
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
            <label for="Attachment-name">附件名称：</label>
            <div class="form-field">
            	<input type="text" name="name" id="Attachment-name" class="form-text easyui-validatebox" placeholder="1-255个字符" data-options="required:true,validType:['length[1,255]']" 
            	value="${attachment.name}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Attachment-url">附件地址：</label>
            <div class="form-field">
            	<input type="text" name="url" id="Attachment-url" class="form-text easyui-validatebox" placeholder="1-255个字符" data-options="required:true,validType:['length[1,255]']" 
            	value="${attachment.url}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Attachment-type">附件类型：</label>
            <div class="form-field">
            	<input type="text" name="type" id="Attachment-type" class="form-text easyui-validatebox" placeholder="1-100个字符" data-options="required:true,validType:['length[1,100]']" 
            	value="${attachment.type}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Attachment-owner">所有者：</label>
            <div class="form-field">
            	<input type="text" name="owner" id="Attachment-owner" class="form-text easyui-validatebox" placeholder="1-20个字符" data-options="required:true,validType:['length[1,20]']" 
            	value="${attachment.owner}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Attachment-downloadCount">下载次数：</label>
            <div class="form-field">
            	<input type="text" name="downloadCount" id="Attachment-downloadCount" class="form-text easyui-validatebox" placeholder="1-11个字符" data-options="required:false,validType:['length[1,11]']" 
            	value="${attachment.downloadCount}" disabled>
            	<!-- <p class="form-des">描述信息</p> -->
            </div>
        </div>
        <div class="form-item">
            <label for="Attachment-createDate">创建时间：</label>
            <div class="form-field">
            	<input type="text" name="createDate" id="Attachment-createDate" class="form-text easyui-validatebox"  data-options="required:true" 
            	value="${attachment.createDate}" disabled>
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
