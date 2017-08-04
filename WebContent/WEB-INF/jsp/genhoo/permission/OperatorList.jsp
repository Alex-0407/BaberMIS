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
           <a href="admin/Operator/index" style="color:#333; font-size:12px;">用户管理</a>
        </div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;" id="deleteBtn">删除</a>
        </div>
    </div>
    
    <div class="queryArea">
      <span>名称</span>
      <input type="text" value=""  id="hello">
      <img src="image/icon/8.png" id="check" style="position:relative;top:2px;"/>
      <input type="button" value="查询"/>
    </div>
    
    <div class="content">
    	<table id="dataTable" width="100%"></table>	
    </div>
	
<script>
$(document).ready(function(){
	
	$('#dataTable').datagrid({
		url:"admin/Operator/list",
		rownumbers:true,
		fitColumns:true,
		pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'operatorID',title:'ID',hidden:true},          
			{field:'name',title:'真实姓名',width:2},          
			{field:'code',title:'用户名',width:2},          
			{field:'phone',title:'电话',width:2,hidden:true},          
			{field:'email',title:'邮箱',width:2,hidden:true},          
			{field:'department',title:'单位/部门',width:2,hidden:true},          
			{field:'departmentName',title:'单位/部门',width:2},          
			{field:'position',title:'职位',width:2,hidden:true},          
			{field:'gender',title:'性别',width:1},          
			{field:'cardNo',title:'身份证',width:2},          
			{field:'politicStatus',title:'政治面貌',width:2,hidden:true},      
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.operatorID+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.operatorID+")'>删除</a>" ;
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
		art.dialog.open('admin/Operator/initAdd', {
			width: 500,
			height: 500,
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
					$.post("admin/Operator/add",data,function(data){
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
	
	$("#deleteBtn").click(function(){
		CQUTDialog.showSuccess({
			message:"success"
		});
	});
	
	
});

//删除 
function deleteRow(operatorID){
	//alert(codeTypeID);
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Operator/delete",{operatorID:operatorID},function(data){
			$('#dataTable').datagrid("reload");
		});
	}
}
//CodeType/initEdit?codeTypeID="+row.codeTypeID+"
function editFun(operatorID){
	art.dialog.open("admin/Operator/initEdit?operatorID="+operatorID, {
		width: 500,
		height: 500,
		title: '编辑',
		init: function(){
			
		},
		close:function(){
			
		},
		lock: true,
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
				$.post("admin/Operator/edit",data,function(data){
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
