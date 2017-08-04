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
           <a href="admin/CodeType/index" style="color:#333; font-size:12px;">码表管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<!-- <a href="CodeType/initAdd">新增</a>&nbsp; | &nbsp;  -->
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;">删除</a>
        </div>
    </div>
    <div class="queryArea">
      <span>名称</span>
      <input type="text" value=""  id="hello">
      <input type="button" value="查询"/>
    </div>
    
	<div class="content">
		<table id="dataTable" width="100%"></table>	
	</div>
<script>

$(document).ready(function(){
	
	$('#dataTable').datagrid({
		url:"admin/CodeType/list",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'codeTypeID',title:'名称',hidden:true},          
			{field:'name',title:'名称',width:2},          
			{field:'code',title:'编码',width:2},          
			{field:'remark',title:'备注',width:4},      
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.codeTypeID+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.codeTypeID+")'>删除</a> | <a href='admin/CodeTable/index?codeTypeID="+row.codeTypeID+"'>码表管理</a>" ;
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
		art.dialog.open('admin/CodeType/initAdd1', {
			width:600,
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
					$.post("admin/CodeType/ajaxAdd",data,function(data){
			 			if(data.OK){
			 				$('#dataTable').datagrid("reload");
			 				art.dialog.tips('<span style="font-size:18px;color:green;">提交成功</span>', 1.5);
			 			}else{
			 				art.dialog.tips('<span style="font-size:18px;color:red;">新增失败</span>', 1.5);
			 			}
			 		});
				}
			}
		});
	});
	
});

//删除 
function deleteRow(codeTypeID){
	//alert(codeTypeID);
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/CodeType/delete",{codeTypeID:codeTypeID},function(data){
			$('#dataTable').datagrid("reload");
		});
	}
}
//CodeType/initEdit?codeTypeID="+row.codeTypeID+"
function editFun(codeTypeID){
	art.dialog.open("admin/CodeType/initEdit1?codeTypeID="+codeTypeID, {
		width:600,
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
				
				console.info(data);
				$.post("admin/CodeType/ajaxEdit",data,function(data){
		 			if(data.OK){
		 				$('#dataTable').datagrid("reload");
		 				art.dialog.tips('<span style="font-size:18px;color:green;">提交成功</span>', 1.5);
		 			}else{
		 				art.dialog.tips('<span style="font-size:18px;color:red;">提交失败</span>', 1.5);
		 			}
		 		});
			}
		}
	});
}


</script>
</body>
