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
<title>理发师管理</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/Baber/index" style="color:#333; font-size:12px;">理发师管理基本信息管理</a>
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
					 name:$('input[name="name"]').val()
					 }
				 );
			},
	};

	$('#dataTable').datagrid({
		url:"admin/Baber/ajaxSearch",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'baberId',title:'ID',hidden:true},          
			{field:'baberPhotoUrl',title:'个人头像',width:4},
			{field:'baberName',title:'姓名',width:2},
			{field:'realName',title:'真实名字 ',width:2}, 
			{field:'baberSex',title:'性别',width:2},
			{field:'baberAge',title:'年龄',width:2},
			{field:'baberIDCard',title:'身份证号',width:6}, 
			{field:'baberTel',title:'手机',width:4},          
			/* {field:'baberAddress',title:'地址',width:4}, 
			{field:'baberWorkTime',title:'工作年限',width:2}, 
			{field:'baberWorkExperience',title:'工作经历',width:4}, 
			{field:'baberPlace',title:'服务地址',width:4},   
			{field:'baberService',title:'服务',width:4},   
			{field:'baberAccountName',title:'生日',width:4},  
			{field:'baberAccountAmount',title:'地址',width:4},  
			{field:'baberSelfIntroduce',title:'自我简介',width:4}, */
			{field:'status',title:'注册状态',width:2},   
			{field:'baberStatus',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.baberId+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.baberId+")'>删除</a>" ;
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
		art.dialog.open('admin/Baber/initAdd', {
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
					$.post("admin/Baber/ajaxAdd",data,function(data){
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
function deleteRow(baberId){
	//alert(baberId);
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Baber/delete",{baberId:baberId},function(data){
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
	Ids[i]=rows[i].baberId;//假设有id这个字段
	}
	Ids=usrIds.toString();
	if(r){
		$.post("admin/Baber/deleteAll",{Ids:Ids},function(data){
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

//CodeType/initEdit?baberId="+row.baberId+"
function editFun(baberId){
	art.dialog.open("admin/Baber/initEdit?baberId="+baberId, {
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
				$.post("admin/Baber/ajaxEdit",data,function(data){
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
