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
<title>用户管理</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">

</style>
</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/User/index" style="color:#333; font-size:12px;">用户基本信息管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href='javascript:void(0);' onclick='deleteAllRow()'>删除</a>
        </div>
    </div>
    <div class="queryArea">
      <span>姓名</span>
      <input type="text" value=""  id="userName">
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
					 userName:$("#userName").val()
					 }
				 );
			},
	};

	$('#dataTable').datagrid({
		url:"admin/User/ajaxSearch",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'userId',title:'ID',hidden:true},          
			{field:'userName',title:'姓名',width:2},  
			{field:'userSex',title:'性别',width:2},
			{field:'userAge',title:'年龄',width:2},   
			{field:'userTel',title:'手机',width:4},          
			{field:'realName',title:'真实名字 ',width:4},  
			{field:'userBirthday',title:'生日',width:4},  
			{field:'userAddress',title:'地址',width:4},  
			{field:'userPhotoUrl',title:'个人头像',width:4},  
			{field:'userStatus',title:'状态',width:2},  
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"editFun("+row.userId+")\">编辑</a> | <a href='javascript:void(0);' onclick='deleteRow("+row.userId+")'>删除</a>" ;
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
	
	  
	/* }); */
	
	/* $("#check").click(function(){
	     alert($("#hello").val());
	     $('#dataTable').datagrid('reload',{
	        hello:$("#hello").val(),
	     });
	
	}); */
	
	$("#addBtn").click(function(){
		art.dialog.open('admin/User/initAdd', {
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
					$.post("admin/User/ajaxAdd",data,function(data){
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
function deleteRow(userId){
	//alert(studentID);
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/User/delete",{userId:userId},function(data){
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
	Ids[i]=rows[i].userId;//假设有id这个字段
	}
	Ids=Ids.toString();
	if(r){
		$.post("admin/User/deleteAll",{Ids:Ids},function(data){
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

//CodeType/initEdit?userId="+row.userId+"
function editFun(userId){
	art.dialog.open("admin/User/initEdit?userId="+userId, {
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
				$.post("admin/User/ajaxEdit",data,function(data){
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
