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
           <a href="admin/CodeType/index" style="color:#333; font-size:12px;">码表类型管理</a> > <a href="javascript:void(0);" onclick="pathHander(0,1)" style="color:#333; font-size:12px;">码表管理</a>
        </div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;">删除</a>
        </div>
    </div>
    <div class="queryArea">
      <span>名称</span>
      <input type="text" value=""  id="hello">
      <input type="button" value="查询"/>
    </div>
    <div class="path" style="height:30px; line-height: 30px; overflow: auto;padding-left: 12px;">
    	<span>路径:</span>
    	<div id="path" style="display: inline;">
    		<span>码表类型&gt;&nbsp;</span><span>学历/</span><span>脚步/</span>
    		<span></span>
    	</div>
    	
    </div>
	<div class="content">
		<table id="dataTable" width="100%"></table>	
	</div>
<script>

var codeTypeID = "<%=request.getAttribute("codeTypeID")%>";
var name = "<%=request.getAttribute("name")%>";
var codeTypeCode = "<%=request.getAttribute("codeTypeCode")%>";
//alert(codeTypeID);

var parentID = 0;  

var pathContainer = [];

function pathHander(parent,index){
	parentID = parent;
	$("#path").html(pathContainer[index-1].pathHtml);
	pathContainer = pathContainer.slice(0, index);
	$('#dataTable').datagrid("load",{
		parent:parentID,
		codeTypeID:codeTypeID
	});
}

function goNext(codeTableID,name){
	//alert(codeTableID);
	parentID = codeTableID;
	$('#dataTable').datagrid("load",{
		parent:parentID,
		codeTypeID:codeTypeID
	});
	
	var length = pathContainer.length;  
	
	$("#path").html(pathContainer[length-1].pathHtml+createPath(codeTableID,length+1,name));
	
	pathContainer.push({
		pathHtml:$("#path").html()
	});
	
}

function createPath(parent,index,name){
	return "<a href='javascript:void(0);' onclick='pathHander("+parent+","+index+")'>&nbsp;&nbsp;"+name+"/</a>";
}

//程序入口
$(document).ready(function(){
	$("#path").html(createPath(0,1,name));
	
	pathContainer.push({
		pathHtml:$("#path").html()
	});
	
	$('#dataTable').datagrid({
		url:"admin/CodeTable/list",
		queryParams:{
			codeTypeID:codeTypeID
		},
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'codeTableID',title:'名称',hidden:true},      
			{field:'name',title:'名称',width:2},          
			{field:'code',title:'值',width:2},   
			{field:'remark',title:'备注',width:3},
			{field:'level',title:'等级',width:2},
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.codeTableID+")\">编辑</a> | " +
				"<a href='javascript:void(0);' onclick=\"deleteRow('"+row.codeTableID+"','"+row.code+"')\">删除</a> | " +
				"<a href='javascript:void(0);' onclick=\"goNext('"+row.codeTableID+"','"+row.name+"')\">下一级</a>";
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
		art.dialog.open('admin/CodeTable/initAdd', {
			title: '新增',
			init: function(){
				var iframe = this.iframe.contentWindow;
				if (!iframe.document.body) {
		        	alert('iframe还没加载完毕呢');
		        	return false;
		        }
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
					data.push({
						name: "codeType",
						value: codeTypeID
					});
					data.push({
						name: "parent",
						value: parentID
					});
					data.push({
						name: "codeTypeCode",
						value: codeTypeCode
					});
					$.post("admin/CodeTable/add",data,function(data){
			 			if(data.OK){
			 				$('#dataTable').datagrid("reload");
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
	});
	
});

//删除 
function deleteRow(codeTableID,code){
	//alert(codeTypeID);
	$.post("admin/CodeTable/delete",{codeTypeID:codeTableID,code:code},function(data){
		$('#dataTable').datagrid("reload");
	});
}
//CodeType/initEdit?codeTypeID="+row.codeTypeID+"
function editFun(codeTableID){
	art.dialog.open("admin/CodeTable/initEdit?codeTableID="+codeTableID,{
		title: '编辑',
		init: function(){
			var iframe = this.iframe.contentWindow;
			if (!iframe.document.body) {
	        	alert('iframe还没加载完毕呢');
	        	return false;
	        }
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
				$.post("admin/CodeTable/edit",data,function(data){
		 			if(data.OK){
		 				$('#dataTable').datagrid("reload");
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

function codeTableManage(codeTypeID){
	alert(codeTypeID);
	//console.info($('#dataTable').datagrid("getPanel"));
	//$('#dataTable').datagrid("getPanel").remove();
	
}



</script>
</body>
