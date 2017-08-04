<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScriptLoader loader = new ScriptLoader(new String[]{"base","easyui","artDialog","childPage","ztree"});
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>XX</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">
.content{
	position: absolute;
	left: 0;
	right:0;
	bottom: 0;
	top:41px;
}

.content .left{
	position: absolute;
	left: 0;
	top:0;
	width: 200px;
	bottom: 0;
	overflow: auto;
	border: 1px solid rgb(195, 223, 243);
}

.content .right{
	position: absolute;
	left: 204px;
	top:0;
	right:0;
	bottom: 0;
	overflow: auto;
}


</style>
</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/Module/indexZree" style="color:#333; font-size:12px;">模块管理</a>
        </div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;">删除</a>
        </div>
    </div>
    
    <div class="content">
    	<div class="left">
    		<ul id="tree" class="ztree"></ul>
    	</div>	
    	<div class="right">
    		<table id="dataTable" width="100%"></table>
    	</div>	
    </div>
	
<script>

var zTreeObj= null;
var setting = {
	async:{
		enable: true,
		url: "admin/Module/zTree",
		autoParam: ["id"]
	},
	callback:{
		onClick: zTreeOnClick
	}
};

function zTreeOnClick(event, treeId, treeNode) {
    $('#dataTable').datagrid('load',{
		moduleID: treeNode.id
	});
}


$(document).ready(function(){
	//初始化树
	zTreeObj = $.fn.zTree.init($("#tree"), setting, null);
	//初始化表格控件
	$('#dataTable').datagrid({
		url:"admin/Module/list",
		rownumbers:true,fitColumns:true,pagination:true,
		//height:308,
		columns:[[
			{checkbox:true},
			
			{field:'moduleID',title:'ID',hidden:true},
			{field:'name',title:'名称',width:1},
			{field:'url',title:'路径',width:2},
			{field:'code',title:'树形编码',width:1},
			{field:'remark',title:'备注',width:1},
			{field:'parent',title:'父亲',width:1},
			{field:'operator',title:'操作',width:1,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.moduleID+")\">编辑</a> | " +
				"<a href='javascript:void(0);' onclick=\"deleteRow('"+row.parent+"','"+row.moduleID+"','"+row.code+"')\">删除</a> | "+
				"<a href='javascript:void(0);' onclick=\"PermissionsPoint('"+row.moduleID+"','"+row.code+"')\">权限点</a>";
			}}
		]],
		loadFilter:function(data){
			return data;
		}
	});
	
	$(window).resize(function(){
		$('#dataTable').datagrid("resize");
	});
	
	//绑定新增按钮的事件
	$("#addBtn").click(function(){
		var nodes = zTreeObj.getSelectedNodes();
		
		if(nodes.length==1){
			add(nodes[0].id);
		}else{
			alert("请先选择一个目录");
		}
		
	});
	
});

function add(parent){
	art.dialog.open('admin/Module/initAdd', {
		title: '新增',
		lock: true,
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
			
				data.push({
					name: "parent",
					value: parent
				});
				
				$.post("admin/Module/add",data,function(data){
		 			if(data.OK){
		 				// 刷新表格
		 				$('#dataTable').datagrid("reload");
		 				zTreeObj.getSelectedNodes()[0].isParent = true;
		 				zTreeObj.reAsyncChildNodes(zTreeObj.getSelectedNodes()[0],"refresh");
		 				
		 				art.dialog.tips('<span style="font-size:18px;color:green;">提交成功</span>', 1.5);
		 			}else{
		 				art.dialog.tips('<span style="font-size:18px;color:red;">新增失败</span>', 1.5);
		 			}
		 		});
			}
		},
		cancelVal: '关闭',
	    cancel: true //为true等价于function(){}
	});
}

function deleteRow(parent,moduleID,code){
	$.post("admin/Module/delete",{parent:parent,moduleID:moduleID,code:code},function(data){
		$('#dataTable').datagrid("reload");
		
		zTreeObj.reAsyncChildNodes(zTreeObj.getSelectedNodes()[0],"refresh");
		if(!data.hasChild){
			zTreeObj.getSelectedNodes()[0].isParent = false;
		}
	});
}

function editFun(moduleID){
	art.dialog.open("admin/Module/initEdit?moduleID="+moduleID,{
		title: '编辑',
		lock: true,
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
				$.post("admin/Module/edit",data,function(data){
		 			if(data.OK){
		 				//刷新表格
		 				$('#dataTable').datagrid("reload");
		 				//刷新选中的节点
		 				zTreeObj.reAsyncChildNodes(zTreeObj.getSelectedNodes()[0],"refresh");
		 				art.dialog.tips('<span style="font-size:18px;color:green;">提交成功</span>', 1.5);
		 			}else{
		 				art.dialog.tips('<span style="font-size:18px;color:red;">提交失败</span>', 1.5);
		 			}
		 		});
			}
		},
		cancelVal: '关闭',
	    cancel: true //为true等价于function(){}
	});
}


function PermissionsPoint(moduleID,code){
	
}

</script>
</body>
</html>
