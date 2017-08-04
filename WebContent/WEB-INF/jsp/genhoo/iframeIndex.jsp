<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScriptLoader loader = new ScriptLoader(new String[]{"base","easyui","artDialog","jqueryui"});
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title></title>
<link rel="stylesheet" type="text/css" href="css/frame.css">
<link rel="stylesheet" href="css/form.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">
.t-img{
	position: absolute;
	top:50%;
	left: 50%;
	margin-left: -200px;
	margin-top:  -150px;
}
</style>
</head>
<body>
    <img alt="" src="image/backstage/body/bd3eb13533fa828b99ead81bfc1f4134960a5a78.jpg" class="t-img">
</body>
</html>
