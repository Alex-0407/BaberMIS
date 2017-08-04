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
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/Role/index" style="color:#333; font-size:12px;">角色管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;">删除</a>
        </div>
    </div>
    
    <div class="content">
    	<table id="dataTable" width="100%"></table>	
    </div>
	
<script>
$(document).ready(function(){
	
	$('#dataTable').datagrid({
		url:"admin/Role/list",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'roleID',title:'ID',hidden:true},          
			{field:'name',title:'名称',width:2},          
			{field:'remark',title:'备注',width:4},      
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.roleID+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.roleID+")'>删除</a>";
			}}
		]],
		loadFilter:function(data){
			//console.info(data);
			return data;
		}
		
	});
	$(window).resize(function(){
		$('#dataTable').datagrid("resize");
	});
	
	$("#addBtn").click(function(){
		art.dialog.open('admin/Role/initAdd', {
			width:600,
			title: '新增',
			lock: true,
			ok:function(){
				var validateResult = this.iframe.contentWindow.DialogInterface.validate();
				if(!validateResult){
					return false;
				}else{
					var data = this.iframe.contentWindow.DialogInterface.getData();
					$.post("admin/Role/add",data,function(callBack){
			 			if(callBack.OK){
			 				$('#dataTable').datagrid("reload");
			 				art.dialog.tips('<span style="font-size:18px;color:green;">'+callBack.message+'</span>', 1.5);
			 			}else{
			 				art.dialog.tips('<span style="font-size:18px;color:red;">'+callBack.message+'</span>', 1.5);
			 			}
			 		});
				}
			}
		});
	});
	
});	

//删除 
function deleteRow(roleID){
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Role/delete",{roleID:roleID},function(data){
			$('#dataTable').datagrid("reload");
		});
	}
}

function editFun(roleID){
	art.dialog.open("admin/Role/initEdit?roleID="+roleID, {
		width:600,
		title: '编辑',
		lock: true,
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
				$.post("admin/Role/edit",data,function(data){
		 			if(data.OK){
		 				$('#dataTable').datagrid("reload");
		 				art.dialog.tips('<span style="font-size:18px;color:green;">'+data.message+'</span>', 1.5);
		 			}else{
		 				art.dialog.tips('<span style="font-size:18px;color:red;">'+data.message+'</span>', 1.5);
		 			}
		 		});
			}
		}
	});
}

</script>
</body>
</html>
