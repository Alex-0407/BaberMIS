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
<script type="text/javascript" src="js/selector.js"> </script>
<script type="text/javascript" src="js/validate.js"> </script>
<style type="text/css">

</style>
</head>
<body>
	<br>
	<form class="demo form-horizontal" id="addForm" method="post">
		<div class="form-item">
            <label for="code">用户名：</label>
            <div class="form-field">
            	<input type="text" name="code" id="code" class="form-text easyui-validatebox" placeholder="1-30个字符" data-options="required:true,validType:['length[1,30]']">
            	<p class="form-des">用户登录的时的用户名</p>
            </div>
        </div>
        
        <div class="form-item">
            <label for="cardNo">身份证：</label>
            <div class="form-field">
            	<input type="text" name="cardNo" id="cardNo" class="form-text easyui-validatebox" data-options="required:true,validType:['idCard']">
            	<p class="form-des">第一代或第二代身份证</p>
            </div>
        </div>
        
        <div class="form-item">
            <label for="name">真实名字：</label>
            <div class="form-field">
            	<input type="text" name="name" id="name" class="form-text easyui-validatebox" placeholder="1-30个字符" data-options="required:true,validType:['length[1,30]']">
            	<p class="form-des">请填写身份证上面的名字</p>
            </div>
        </div>
    
    	<div class="form-item">
            <label for="password">密码：</label>
            <div class="form-field">
            	<input type="password" name="password" id="password" class="form-text easyui-validatebox" placeholder="6-20个字符" data-options="validType:['passwordWithDefault']">
            	<p class="form-des">如果不填写，默认取身份证的后六位</p>
            </div>
        </div>
        
        <div class="form-item form-radio-checkbox-wrap">
            <label>性别：</label>
            <div class="form-field">
                <label><input type="radio" name="gender" class="form-radio" value="男" checked="checked"><span>男</span></label>
                <label><input type="radio" name="gender" class="form-radio" value="女"><span>女</span></label>
            </div>
        </div>
        
        <div class="form-item">
            <label for="phone">电话：</label>
            <div class="form-field">
            	<input type="text" name="phone" id="phone" class="form-text easyui-validatebox" placeholder="8位或者11位数字" data-options="required:true,validType:['phone']">
            	<p class="form-des">如:55667788 或 023-55667788 或 13372662525</p>
            </div>
        </div>
        
        
        <div class="form-item">
            <label for="email">邮箱：</label>
            <div class="form-field">
            	<input type="text" name="email" id="email" class="form-text easyui-validatebox" data-options="required:true,validType:['email','length[1,30]']">
            	<p class="form-des">格式为: example@126.com</p>
            </div>
        </div>
        
        <div class="form-item">
            <label for="politicStatus">政治面貌：</label>
            <div class="form-field">
            	<input type="text" name="politicStatus" id="politicStatus" readonly="readonly" class="form-text easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
            </div>
        </div>
        
        <div class="form-item">
            <label for="departmentName">单位/部门：</label>
            <div class="form-field">
            	<input type="text" name="departmentName" id="departmentName" readonly="readonly" class="form-text easyui-validatebox genhoo-Components" ComponentConfig="" data-options="validType:['length[1,30]']">
            	<input type="hidden" name="department" id="department">
            </div>
        </div>
        
        <div class="form-item">
            <label for="position">职位：</label>
            <div class="form-field">
            	<input type="text" name="position" id="position" class="form-text easyui-validatebox" data-options="validType:['length[1,30]']">
            </div>
        </div>
        
	</form>
<script>

$(document).ready(function(){
	
	$("#departmentName").departmentSelector({
		singleSelect:true,//是否多选
		dataHander:function(data){//返回的是一个数组
			//console.info(data);
			if(data){
				$("#departmentName").val(data[0].name);
				$("#departmentName").focus();
				$("#department").val(data[0].departmentID);
			}
		}
	});
	
	$("#politicStatus").codeTableSelector({
		singleSelect:true,
		condition:GenooSelector.politicStatus,
		title:"选择政治面貌",
		dataHander:function(data){
			if(data){
				$("#politicStatus").val(data[0].name);
				//
				$("#politicStatus").focus();
			}
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
