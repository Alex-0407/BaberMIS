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
<title>这里是标题</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<script type="text/javascript" src="js/selector.js"> </script>
<script type="text/javascript" src="js/validate.js"> </script>
<style type="text/css">

.content{
	position: absolute;
	top: 41px;
	bottom: 0px;
	left: 0px;
	right: 0px;
}
.departmentTreeArea{
	position: absolute;
	width: 200px;
	left: 0;
	top:0;
	bottom: 0px;
	overflow: auto;
}
.userTreeArea{
	position: absolute;
	width: 470px;
	left: 200px;
	top:0;
	bottom: 0px;
	border-left:1px solid #e5e5e5;
	border-right:1px solid #e5e5e5;
	overflow: auto;
}

.roleArea{
	position: absolute;
	left: 675px;
	top:0;
	right: 0px;
	bottom: 0px;
	overflow: auto;
}
#dataTable{
	width: 460px;
}

#queryForm{
	margin-bottom: 0px;
}

#userRoles{
	width:150px;
	float: left;
	min-height: 300px;
	max-height: 600px;
	overflow: auto;
}



#allRoles{
	width:150px;
	float: left;
	min-height: 300px;
	max-height: 600px;
	overflow: auto;
}

#operatorWrap{
	
	margin: 32px 20px 32px 20px;
	float: left;
	margin-top: 32px;
	zoom:1;
	overflow: auto;
}


</style>
</head>
<body>

	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/Operator/index" style="color:#333; font-size:12px;">用户管理</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="addBtn" href="javascript:;">添加</a> &nbsp; | &nbsp; <a href="javascript:;" id="deleteBtn">删除</a>
        </div>
    </div>
    
    <div class="content">
    	<div class="departmentTreeArea">
    		<ul id="tree" class="ztree"></ul>
    	</div>
    	
    	<div class="userTreeArea">
    		<form id="queryForm">
    		部门:<input name="departmentName" id="departmentName" disabled="disabled" style="width:90px;"/><input type="hidden" name = "departmentID" id="departmentID"/>
    		姓名:<input type="text" name="operatorName" id="operatorName"><input type="button" value="查询" id="queryBtn">
    		</form>
    		
    		<table id="dataTable"></table>
    	</div>
    	
    	<div class="roleArea">
    		<div id="userRoles">
    			<p style="line-height: 30px; height: 30px;">已分配的角色</p>
    			<form style="border:1px solid #eee; padding:5px;">
	    			<ul id="userRoleList"></ul>
	    		</form>
    		</div>
    		
    		<div id="operatorWrap">
    			<input type="button" value=">>" id="removeUserRoleBtn"><br>
    			<input type="button" value="<<" id="addUserRoleBtn">
    		</div>
    		
    		<div id="allRoles">
    			<p style="line-height: 30px; height: 30px;">可分配的角色</p>
    			<form style="border:1px solid #eee; padding:5px;">
	    			<ul id="aviliableRoleList"></ul>
	    		</form>
    		</div>
    		
    		
    	</div>
    </div>
    
<script type="text/javascript">

//设置树控件的配置信息
var zTreeObj= null;
var setting = {
	async:{
		enable: true,
		url: "admin/UserRole/departmentTree",
		autoParam: ["id"]
	},
	callback:{
		onClick: zTreeOnClick
	}
};

var departmentID = null,
	selectedOperatorID = null;//被选中的用户的ID
function zTreeOnClick(event, treeId, treeNode){
	
	//刷新中间的表格
	$('#dataTable').datagrid('load',{
		departmentID: treeNode.id
	});
	$("#departmentID").val(treeNode.id);
	$("#departmentName").val(treeNode.name);
}


$(document).ready(function(){
	zTreeObj = $.fn.zTree.init($("#tree"), setting, null);

	$('#dataTable').datagrid({
		url:"admin/UserRole/loadUserList",
		rownumbers:true,
		fitColumns:true,
		pagination:true,
		singleSelect:true,
		height:308,
		columns:[[
			/* {checkbox:false}, */
			{field:'operatorID',title:'ID',hidden:true},          
			{field:'name',title:'真实姓名',width:2},          
			{field:'code',title:'用户名',width:2},          
			{field:'operator',title:'操作',width:2,hidden:true, formatter:function(value,row,index){
				return "分配角色" ;
			}}
		]],
		loadFilter:function(data){
			//console.info(data);
			return data;
		},
		onSelect:function(rowIndex, rowData){
			selectedOperatorID = rowData.operatorID;
			$.post("admin/UserRole/loadUserRole",[{name:"operatorID",value:rowData.operatorID}],function(callBack){
				if(callBack.userRoles){
					$("#userRoleList").html("");
	 				for(var i=0;i<callBack.userRoles.length;i++){
	 					$("#userRoleList").append("<li><label><input type='checkbox' name='userRole' roleId='"+callBack.userRoles[i].roleID+"'>"+callBack.userRoles[i].name+"</label></li>");
	 				}
	 			}
				
				if(callBack.aviliableRoles){
					$("#aviliableRoleList").html("");
	 				for(var i=0;i<callBack.aviliableRoles.length;i++){
	 					$("#aviliableRoleList").append("<li><label><input type='checkbox' name='aviliableRole' roleId='"+callBack.aviliableRoles[i].roleID+"'>"+callBack.aviliableRoles[i].name+"</label></li>");
	 				}
	 			}
	 		});
		}
		
	});
	$(window).resize(function(){
		$('#dataTable').datagrid("resize");
	});
	
	$("#queryBtn").click(function(){
		$('#dataTable').datagrid('load',{
			departmentID: $("#departmentID").val(),
			operatorName: $("#operatorName").val()
		});
	});
	
	
	function updateUserRole(){
		var roleArr = [];
		$(":checkbox","#userRoleList").each(function(){
			roleArr.push($(this).attr("roleId"));
		});
		
		$.post("admin/UserRole/updateUserRole",[{name:"userRoleList",value:roleArr.join("_")},{name:"operatorID",value:selectedOperatorID}],function(data){
			if(data.OK){
 				//$('#dataTable').datagrid("reload");
 				/* CQUTDialog.showSuccess({
 					message:data.message
 				}); */
 			}else{
 				CQUTDialog.showError({
 					message:data.message
 				});
 			}
		});
	}
	
	/*remove user`s role*/
	$("#removeUserRoleBtn").click(function(){
		
		$(":checkbox[checked='checked']","#userRoleList").each(function(){
			$(this).removeAttr("checked");
			$(this).parent().parent().appendTo($("#aviliableRoleList"));
		});
		updateUserRole();
	});
	
	/*add user`s role*/
	$("#addUserRoleBtn").click(function(){
		
		$(":checkbox[checked='checked']","#aviliableRoleList").each(function(){
			$(this).removeAttr("checked");
			$(this).parent().parent().appendTo($("#userRoleList"));
		});
		updateUserRole();
	});
	
});
</script>
</body>
</html>
