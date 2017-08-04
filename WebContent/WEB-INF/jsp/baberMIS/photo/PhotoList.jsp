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
<title>业务管理</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/Photo/index" style="color:#333; font-size:12px;">业务管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href='javascript:void(0);' onclick='deleteAllRow()'>删除</a>
        </div>
    </div>
    <div class="queryArea">
      <span>姓名</span>
      <input type="text" value=""  id="name">
      <input  id="searchBtn" type="button" value="查询" onclick="obj.search();"/>
    </div>
    
	<div class="content">
		<table id="dataTable" width="100%"></table>	
	</div>
<script>
$(document).ready(function(){
	
	obj={
			search:function(){
				
				 $('#dataTable').datagrid('load',{
					 Name:$('input[name="name"]').val()
					 }
				 );
			},
	};

	$('#dataTable').datagrid({
		url:"admin/Photo/ajaxSearch",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'photoId',title:'ID',hidden:true},          
			{field:'baberId',title:'理发师ID',width:2},  
			{field:'hairPhotoUrl',title:'作品图片地址',width:2},   
			{field:'hairPhotoName',title:'作品名称',width:4},          
			{field:'hairPhotoDescribe',title:'作品描述',width:4},
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.photoId+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.photoId+")'>删除</a>" ;
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
		art.dialog.open('admin/Photo/initAdd', {
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
					$.post("admin/Photo/ajaxAdd",data,function(data){
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
function deleteRow(photoId){
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Photo/delete",{photoId:photoId},function(data){
			$('#dataTable').datagrid("reload");
		});
	}
}

//删除 
function deleteAllRow(){
	//alert(studentID);
	var r=confirm("确定要删除吗");
	var rows = $("#dataTable").datagrid("getSelections"); 
	var Ids =new Array();
	for(var i=0;i<rows.length;i++)
	{
	//获取每一行的数据
	Ids[i]=rows[i].photoId;//假设有id这个字段
	}
	Ids=Ids.toString();
	if(r){
		$.post("admin/Photo/deleteAll",{Ids:Ids},function(data){
			$('#dataTable').datagrid("reload");
			$.messager.show({
				title:'提示',
				msg:'删除成功',
				timeout:3000,
				showType:'slide'
			});

		});
	}
}

//CodeType/initEdit?PhotoId="+row.PhotoId+"
function editFun(photoId){
	art.dialog.open("admin/Photo/initEdit?photoId="+photoId, {
		width:600,
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
				
				console.info(data);
				$.post("admin/Photo/ajaxEdit",data,function(data){
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
