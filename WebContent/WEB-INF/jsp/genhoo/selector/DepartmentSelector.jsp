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
		url:"Department/list",
		rownumbers:true,
		fitColumns:true,
		pagination:true,
		height:308,
		columns:[[
 			{checkbox:true},
			{field:'departmentID',title:'ID',hidden:true},
			{field:'name',title:'名称',width:2},          
			{field:'type',title:'类型',width:2,hidden:true},
			{field:'parent',title:'父级',width:2,hidden:true},
			{field:'phone',title:'电话',width:2,hidden:true},
			{field:'code',title:'编码',width:2,hidden:true},
			{field:'remark',title:'备注',width:3,hidden:true},
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"goNext('"+row.departmentID+"','"+row.name+"')\">下一级</a>";
			}}
		]],
		loadFilter:function(data){
			//console.info(data);
			return data;
		},
		onDblClickRow:function(rowIndex, rowData){
			//alert("rowData");
			var data = [];
			data.push(rowData);
			art.dialog.data("departmentSelector", data);
			art.dialog.close();
		}
		
	});
	$(window).resize(function(){
		$('#dataTable').datagrid("resize");
	});
	
	console.info("content init");
	
});


DialogInterface = {
		validate:function(){
			var rows = $('#dataTable').datagrid("getSelections");
			if(rows.length==0){
				alert("你没有选择数据");
				return false;
			}else {
				return true;
			}
		},
		getData:function(){
			//art.dialog.data("departmentSelector", $('#dataTable').datagrid("getSelections"));
			return null;
		},
		setData:function(){
			art.dialog.data("departmentSelector", $('#dataTable').datagrid("getSelections"));
		},
		init:function(options){
			$('#dataTable').datagrid("options").singleSelect = options.singleSelect;
		}
	};

</script>
</body>
</html>
