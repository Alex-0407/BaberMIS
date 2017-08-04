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
           <a href="admin/Department/index" style="color:#333; font-size:12px;">单位部门管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;">删除</a>
        </div>
    </div>
    
    <div class="queryArea">
      <span>名称</span>
      <input type="text" value=""  id="hello">
      <img src="image/icon/8.png" id="check" style="position:relative;top:2px;"/>
      <input type="button" value="查询"/>
    </div>
    
    <div class="path" style="height:30px; line-height: 30px; overflow: auto;padding-left: 12px;">
    	<span>路径:</span>
    	<div id="path" style="display: inline;">
    		
    	</div>
    </div>
    
    <div class="content">
    	<table id="dataTable" width="100%"></table>	
    </div>
	
<script>

var parentID = 0;

var pathContainer = [];

function pathHander(parent,index){
	parentID = parent;
	$("#path").html(pathContainer[index-1].pathHtml);
	pathContainer = pathContainer.slice(0, index);
	$('#dataTable').datagrid("load",{
		parent:parentID
	});
}

function createPath(parent,index,name){
	return "<a href='javascript:void(0);' onclick='pathHander("+parent+","+index+")'>&nbsp;&nbsp;"+name+"/</a>";
}

//进入下一级别
function goNext(departmentID,name){
	//alert(codeTableID);
	parentID = departmentID;
	$('#dataTable').datagrid("load",{
		parent:parentID
	});
	
	var length = pathContainer.length;
	
	$("#path").html(pathContainer[length-1].pathHtml+createPath(departmentID,length+1,name));
	
	pathContainer.push({
		pathHtml:$("#path").html()
	});
	
}

$(document).ready(function(){
	$("#path").html(createPath(0,1,"根节点"));
	pathContainer.push({
		pathHtml:$("#path").html()
	});
	
	$('#dataTable').datagrid({
		url:"admin/Department/list",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'departmentID',title:'ID',hidden:true},
			{field:'name',title:'名称',width:2},          
			{field:'type',title:'类型',width:2},
			{field:'parent',title:'父级',width:2,hidden:true},
			{field:'phone',title:'电话',width:2},
			{field:'code',title:'编码',width:2},
			{field:'remark',title:'备注',width:3},
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.departmentID+")\">编辑</a> | " +
				"<a href='javascript:void(0);' onclick=\"deleteRow('"+row.code+"')\">删除</a> | " +
				"<a href='javascript:void(0);' onclick=\"goNext('"+row.departmentID+"','"+row.name+"')\">下一级</a>";
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
		art.dialog.open('admin/Department/initAdd', {
			title: '新增',
			width:450,
			height:240,
			lock: true,
			ok:function(){
				var validateResult = this.iframe.contentWindow.DialogInterface.validate();
				if(!validateResult){
					return false;
				}else{
					var data = this.iframe.contentWindow.DialogInterface.getData();
					data.push({
						name:"parentID",
						value:parentID
					});
					
					$.post("admin/Department/add",data,function(data){
			 			if(data.OK){
			 				$('#dataTable').datagrid("reload");
			 				art.dialog.tips('<span style="font-size:18px;color:green;">'+data.message+'</span>', 1.5);
			 			}else{
			 				art.dialog.tips('<span style="font-size:18px;color:red;">'+data.message+'</span>', 1.5);
			 			}
			 		});
				}
			},
			cancelVal: '关闭',
		    cancel: true //为true等价于function(){}
			
		});
	});
	
});

//删除 
function deleteRow(code){
	//alert(codeTypeID);
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Department/delete",{code:code},function(data){
			$('#dataTable').datagrid("reload");
		});
	}
}

function editFun(departmentID){
	art.dialog.open("admin/Department/initEdit?departmentID="+departmentID, {
		width:450,
		height:240,
		title: '新增',
		lock: true,
		ok:function(){
			var validateResult = this.iframe.contentWindow.DialogInterface.validate();
			if(!validateResult){
				return false;
			}else{
				var data = this.iframe.contentWindow.DialogInterface.getData();
				$.post("admin/Department/edit",data,function(data){
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
