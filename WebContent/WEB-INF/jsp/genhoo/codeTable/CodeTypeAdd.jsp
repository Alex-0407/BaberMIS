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
<title>新增</title>
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
           <a href="genhoo/codeTable/CodeType?actionType=initAdd" class="traceTitle">新增码表类型</a>
        </div>
        
        <div class="arrow"></div>
        
        <div class="btnbar"></div>
    </div>
    
    <div class="content">
		<form action="CodeType/add" method="post" class="baseform" id="addForm">
			<table class="fromGroup">
				<tr>
					<td class="formLabel w1"><span class="needInput">*</span><span>名称</span></td>
					<td class="formField">
						<input name="name" type="text" label="名称" nullableMessage="名称不能为空" ltlength = "10" ltlengthMessage="名称的最大长度不超过10" gtlength="1" gtlengthMessage="长度不小于1"/>
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
						<input type="text" name="code" ltlength = "10"  id="testInput" nullable="false">
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
					<textarea rows="5" class="w4" name="remark" maxLength = "255"></textarea>
					<span class="errorInfo"></span></td>
				</tr>
				<tr>
					<td class="formLabel"></td>
					<td class="formField"></td>
				</tr>
			</table>

			<table class="fromGroup">
				<tr>
					<td class="formLabel w1"></td>
					<td class="formField"><input type="submit" value="添加"><span class="errorInfo"></span></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript" src="js/GH.js"></script>
<script>

//nextSibling 下一个兄弟节点 
//在ele节点的后面插入 
//ele.parentNode.insertBefore(errorNode,ele.nextSibling);
//nodeType == 3  文本节点 
//nodeType == 1  元素节点
//hasAttribute() 有兼容性问题  用 getAttribute("") 代替   如果 没有 返回null 
GH.addLoadEvent(function(){
     var f = new GH.form("addForm");
     GH.addEvent(GH.$("serialize"),"click",function(){
        //console.info(f.serializeArray());
        console.info(f.serialize());
     });
},false);
</script>
</body>
