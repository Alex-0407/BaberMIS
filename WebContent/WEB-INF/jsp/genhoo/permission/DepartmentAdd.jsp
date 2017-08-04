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
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<body>
	<br>
	<form class="demo form-horizontal" id="addForm" method="post">
		<div class="form-item">
            <label for="name">名称：</label>
            <div class="form-field">
            	<input type="text" name="name" id="name" class="form-text easyui-validatebox" placeholder="1-30个字符" data-options="required:true,validType:['length[1,30]']">
            </div>
        </div>
        
        <div class="form-item">
            <label for="phone">电话：</label>
            <div class="form-field">
            	<input type="text" name="phone" id="phone" class="form-text easyui-validatebox" placeholder="8位或者11位数字" data-options="validType:['phone']">
            </div>
        </div>
        
        <div class="form-item form-radio-checkbox-wrap">
            <label>类型：</label>
            <div class="form-field">
                <label><input type="radio" name="type" class="form-radio" value="单位" checked="checked"><span>单位</span></label>
                <label><input type="radio" name="type" class="form-radio" value="部门"><span>部门</span></label>
            </div>
        </div>
        
        <div class="form-item">
            <label>备注：</label>
            <div class="form-field">
            	<textarea name="remark" id="intro" maxlength="255"></textarea>
            </div>
        </div>
        
	</form>
	
<script>
$(document).ready(function(){
	$.extend($.fn.validatebox.defaults.rules, {
	    phone: {
	        validator: function(value, param){
	        	var reg = /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9})|(\d{7,8}))$/;
	        	return reg.test(value);
	        },
	        message: '请输入正确的电话号码'
	    }
	});
	
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
