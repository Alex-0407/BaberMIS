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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>日志管理</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/Log/index" style="color:#333; font-size:12px;">日志</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;" id="deleteBtn">删除</a>
        </div>
    </div>
    
    <div class="content">
    	<table id="dataTable" width="100%"></table>	
    </div>
	
<script>
$(document).ready(function(){
	
	$('#dataTable').datagrid({
		url:"admin/Log/list",
		rownumbers:true,
		fitColumns:true,
		pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'logID',title:'primary key',width:1},
			{field:'ip',title:'访问IP',width:1},
			{field:'type',title:'日志类型',width:1},
			{field:'name',title:'日志名称',width:1},
			{field:'content',title:'日志内容',width:1},
			{field:'data',title:'操作数据',width:1},
			{field:'operatorID',title:'操作员',width:1},
			{field:'operatorCode',title:'操作员代号',width:1},
			{field:'operatorName',title:'操作员姓名',width:1},
			{field:'logDate',title:'日志日期',width:1},
			{field:'remark',title:'备注',width:1},
			{field:'operator',title:'操作',width:1,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.logID+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.logID+")'>删除</a>" ;
			}}
		]],
		loadFilter:function(data){
			//console.info(data);
			if(data.ok){
				return data.grid;
			}else{
				alert(data.message);
				return false;
			}
		},
		onOpen:function(){
			//在面板第一加载成功的时候重新调整表格的宽度，让每一列更好的适应表格
			$('#dataTable').datagrid("resize");
		}
		
	});
	$(window).resize(function(){
		$('#dataTable').datagrid("resize");
	});
	
	$("#addBtn").click(function(){
		art.dialog.open('admin/Log/initAdd', {
			width: 500,
			//height: 500,
		    fixed: true,
		    resize: true,
		    drag: true,
			title: '新增',
			lock: true,
			ok:function(){
				var validateResult = this.iframe.contentWindow.DialogInterface.validate();
				if(!validateResult){
					return false;
				}else{
					var data = this.iframe.contentWindow.DialogInterface.getData();
					$.post("admin/Log/add",data,function(data){
			 			if(data.OK){
			 				$('#dataTable').datagrid("reload");
			 				CQUTDialog.showSuccess({
			 					message:data.message
			 				});
			 			}else{
			 				CQUTDialog.showError({
			 					message:data.message
			 				});
			 			}
			 		});
				}
			},
			cancelVal: '关闭',
		    cancel: true //为true等价于function(){}
		});
	});
	
	
	//删除 选中的行
	$("#deleteBtn").click(function(){
		//得到所有被选中的行
		var selections = $('#dataTable').datagrid("getSelections");

		var ids = [];
		for(var i=0;i<selections.length;i++){
			ids.push(selections[i].logID);
		}
		
		if(ids.length==0){
			alert("请选择需要删除的行。");
		}else{
			var r=confirm("确定要删除选中的数据吗");
			if(r){
				$.post("admin/Log/deleteSelections",{selections:ids.join(",")},function(data){
					$('#dataTable').datagrid("reload");
				});
			}
		}
		
		
	});
	
});

//删除 
function deleteRow(logID){
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Log/delete",{logID:logID},function(data){
			$('#dataTable').datagrid("reload");
		});
	}
}

//编辑
function editFun(logID){
	art.dialog.open("admin/Log/initEdit?logID="+logID, {
		width: 500,
		//height: 500,
		title: '编辑',
		lock: true,
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
				$.post("admin/Log/edit",data,function(data){
		 			if(data.OK){
		 				$('#dataTable').datagrid("reload");
		 				CQUTDialog.showSuccess({
		 					message:data.message
		 				});
		 			}else{
		 				CQUTDialog.showError({
		 					message:data.message
		 				});
		 			}
		 		});
			}
		},
		cancelVal: '关闭',
	    cancel: true
	});
}	

</script>
</body>
</html>
