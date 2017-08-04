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
           <a href="Module/index" style="color:#333; font-size:12px;">模块管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;">删除</a>
        </div>
    </div>
    
    
    <div class="content">
    	<div class="left">
    		<ul id="tree" class="easyui-tree"></ul>
    	</div>	
    	<div class="right">
    		<table id="dataTable" width="100%"></table>
    	</div>	
    </div>
    
	
<script>

var curentNodeID = null;

$(document).ready(function(){
	
	//初始化树控件
	$('#tree').tree({
		url:"Module/treeData",animate:true,
		loadFilter:function(data){
			return data;
		},
		onClick:function(node){
			curentNodeID = node.id;
			//在点击的时候把该节点下面的子节点加载在右边的表格里面
			loadChildToDataGride(curentNodeID);
		},
		loadFilter:function(data){
			return data;
		},
		onDblClick:function(node){
			$('#tree').tree('reload',node.target);
		}
	});
	
	//绑定新增按钮的事件
	$("#addBtn").click(function(){
		var node = $('#tree').tree("getSelected");
		if(node!=null){
			add(node.id);
		}else {
			alert("请选择");
		}
	});
	
	//初始化表格控件
	$('#dataTable').datagrid({
		url:"admin/Module/list",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
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
			//console.info(data);
			return data;
		}
	});
	
	$(window).resize(function(){
		$('#dataTable').datagrid("resize");
	});
	
});

function editFun(moduleID){
	art.dialog.open("admin/Module/initEdit?moduleID="+moduleID,{
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
				$.post("admin/Module/edit",data,function(data){
		 			if(data.OK){
		 				//刷新表格
		 				$('#dataTable').datagrid("reload");
		 				//刷新选中的节点
		 				reloadSeletedNode();
		 				
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

function deleteRow(parent,moduleID,code){
	$.post("admin/Module/delete",{parent:parent,moduleID:moduleID,code:code},function(data){
		$('#dataTable').datagrid("reload");
		
		//刷新树节点
		var node = $('#tree').tree("getSelected");
		
		if(node){
			if(data.hasChild){
				$('#tree').tree('reload',node.target);
			}else{
				var parentNode = $("#tree").tree("getParent",node.target);
				//如果是顶层根节点就刷新根节点
				if(parentNode){
					$('#tree').tree('reload',parentNode.target);
					$('#tree').tree('expand',node.target);	
				} else {
					$('#tree').tree('reload',node.target);
				}
				
			}
			$("#tree").tree("select",node.target);
		}
		
	});
}

function add(parent){
	art.dialog.open('admin/Module/initAdd', {
		title: '新增',
		lock: true,
		init: function(){
			var iframe = this.iframe.contentWindow;
			if (!iframe.document.body) {
	        	alert('iframe还没加载完毕呢');
	        	return false;
	        }
		},
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
			
				data.push({
					name: "parent",
					value: curentNodeID
				});
				
				$.post("admin/Module/add",data,function(data){
		 			if(data.OK){
		 				// 刷新表格
		 				$('#dataTable').datagrid("reload");
		 				
		 				//刷新选中节点
		 				reloadSeletedNode();
		 				
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

//加载数据
function loadChildToDataGride(curentNodeID){
	$('#dataTable').datagrid('load',{
		moduleID: curentNodeID
	});
}

//刷新选中的节点
function reloadSeletedNode(){
	var node = $('#tree').tree("getSelected");
	var isLeaf = $("#tree").tree("isLeaf",node.target);
	if(isLeaf){
		
		var parentNode = $("#tree").tree("getParent",node.target);
		if(parentNode){
			$('#tree').tree('reload',parentNode.target);
			$('#tree').tree('expand',node.target);
		}else{
			$('#tree').tree('reload',node.target);
			$('#tree').tree('expand',node.target);
		}
	}else{
		$('#tree').tree('reload',node.target);
	}
	
	$("#tree").tree("select",node.target);
}

</script>
</body>
</html>
