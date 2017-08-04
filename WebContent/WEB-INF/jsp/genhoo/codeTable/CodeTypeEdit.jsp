<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>这里是标题</title>
<!--其他的外部文件-->
<link rel="stylesheet" href="css/reset.css"/>
<link rel="stylesheet" href="css/childPage.css">
<link rel="stylesheet" href="css/form.css">

</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="CodeType/index" class="traceTitle">码表类型管理</a>
           <span class="traceTitle">&gt;</span>
           <a href="CodeType/initEdit?codeTypeID=${codeType.codeTypeID}" class="traceTitle">编辑</a>
        </div>
        
        <div class="arrow"></div>
        
        <div class="btnbar"></div>
    </div>
    
    <div class="content">
		<form action="CodeType/edit" method="post" class="baseform" id="addForm">
			<input type="hidden" value="${codeType.codeTypeID}" name="codeTypeID">
			<table class="fromGroup">
				<tr>
					<td class="formLabel w1"><span class="needInput">*</span><span>名称</span></td>
					<td class="formField">
						<input name="name" value="${codeType.name}" type="text" label="名称" nullableMessage="名称不能为空" ltlength = "10" ltlengthMessage="名称的最大长度不超过10" gtlength="1" gtlengthMessage="长度不小于1">
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
					<td class="formLabel w1"><span class="needInput">*</span><span>编码</span></td>
					<td class="formField">
						<input type="text" name="code" value="${codeType.code}" ltlength = "10"  id="testInput" nullable="false">
						<span class="errorInfo"></span>
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
					<textarea rows="5" class="w4" name="remark" maxLength = "255">${codeType.remark}</textarea>
					<span class="errorInfo"></span></td>
				</tr>
				<tr>
					<td class="formLabel"></td>
					<td class="formField help"></td>
				</tr>
			</table>

			<table class="fromGroup">
				<tr>
					<td class="formLabel w1"></td>
					<td class="formField"><input type="submit" value="保存"><span class="errorInfo"></span></td>
				</tr>
			</table>
			
		</form>
	</div>


<script type="text/javascript" src="js/GH.js"></script>
<script>
GH.addLoadEvent(function(){
    var f = new GH.form("addForm");
    GH.addEvent(GH.$("serialize"),"click",function(){
       //console.info(f.serializeArray());
       console.info(f.serialize());
    });
},false);
</script>
</body>
