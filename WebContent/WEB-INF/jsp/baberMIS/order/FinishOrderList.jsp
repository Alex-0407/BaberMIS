<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	ScriptLoader loader = new ScriptLoader(new String[]{"base",
			"easyui", "artDialog", "childPage"});
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已完成订单</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">
</style>
</head>
<body>
	<div class="topbar">
		<div class="leftrace">
			<span class="location">当前位置：</span> <a href="admin/Order/FinishOrderIndex"
				style="color: #333; font-size: 12px;">已完成订单</a>
		</div>
		<div class="arrow"></div>
		<div class="btnbar">
		</div>
	</div>
	<div class="queryArea">
		<span>订单状态</span> <input type="text" value="" id="name"> <input
			id="searchBtn" type="button" value="查询" onclick="obj.search();" />
	</div>

	<div class="content">
		<table id="dataTable" width="100%"></table>
	</div>
	<script>
$(document).ready(function(){
	
	obj={
			search:function(){
				
				 $('#dataTable').datagrid('load',{
					 orderStatus:$("#name").val()
					 }
				 );
			},
	};

	$('#dataTable').datagrid({
		url:"admin/Order/ajaxSearchFinishOrder",
		rownumbers:true,fitColumns:true,pagination:true,
		height:308,
		columns:[[
			{checkbox:true},
			{field:'orderId',title:'ID',hidden:true},          
			{field:'baberName',title:'理发师',width:1},  
			{field:'userName',title:'用户',width:1},   
			{field:'orderCode',title:'订单编号',width:4},          
			{field:'appointmentTime',title:'预约时间 ',width:3},  
			{field:'orderStartTime',title:'下单时间',width:3},  
			/* {field:'orderEndTime',title:'订单失效日期',width:1},  
			{field:'reservateServe',title:'订单服务',width:1},   */
			{field:'orderPrice',title:'订单价格',width:1},  
			{field:'orderStatus',title:'状态',width:1},  
			{field:'operator',title:'操作',width:2,formatter:function(value,row,index){
				return "<a href='javascript:void(0);' onclick=\"deleteRow("+row.orderId+")\">删除</a> " ;
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

/*

//CodeType/checkRow?orderId="+row.orderId+"
function detailRow(orderId){
	$.post("admin/Order/detailRow",{orderId:orderId},function(data){
		$('#dataTable').datagrid("reload");
		$.messager.show({
			title:'提示',
			msg:'详细订单！',
			timeout:3000,
			showType:'slide'
		});

	});
}
 
//CodeType/checkRow?orderId="+row.orderId+"
function uncheckRow(orderId){
	$.post("admin/Order/uncheckRow",{orderId:orderId},function(data){
		$('#dataTable').datagrid("reload");
		$.messager.show({
			title:'提示',
			msg:'您已取消该预约单！',
			timeout:3000,
			showType:'slide'
		});

	});
}

*/

//删除 
function deleteRow(orderId){
	//alert(studentID);
	var r=confirm("确定要删除吗");
	if(r){
		$.post("admin/Order/deleteOrder",{orderId:orderId},function(data){
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
	Ids[i]=rows[i].orderId;//假设有id这个字段
	}
	Ids=Ids.toString();
	if(r){
		$.post("admin/Order/deleteAll",{Ids:Ids},function(data){
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

</script>
</body>