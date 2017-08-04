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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>角色模块管理</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">
.content{
	position: absolute;
	top:41px;
	bottom: 0;
	left:0;
	right:0;
	
}
.m-left{
	position: absolute;
	top:0;
	bottom: 0;
	width: 150px;
	border-top: 1px solid #eee;
	border-right: 1px solid #eee;
}

ul.role-wrap li{
	padding:8px 4px;
	border-bottom:1px solid #eee;
	
}

ul.role-wrap li.active{
	background-color: #4F94CD;
	color:#fff;
}

.m-right{
	position: absolute;
	top:0;
	bottom: 0;
	right:0;
	left:153px;
}
</style>
</head>
<body>
	<div class="topbar">
        <div class="leftrace">
           <span class="location">当前位置：</span>
           <a href="admin/RoleModule/index" style="color:#333; font-size:12px;">角色模块</a>
        </div>
        <div class="arrow"></div>
        <div class="btnbar">
        	<a id="js-savebtn" href="javascript:;">保存</a>
        </div>
    </div>
    
    <div class="content">
    	<table id="dataTable" width="100%"></table>	
    	<div class="m-left">
    		<ul class="role-wrap" id="js-role"></ul>
    	</div>
    	<div class="m-right">
    		<ul id="tree" class="ztree"></ul>
    	</div>
    </div>
	
<script>
$(document).ready(function(){

	var roleArea = $("#js-role");
	//ztree控件
	var zTreeObj = null;
	//当前被选中的 角色ID
	var roleID = 0;
	/*对树的处理*/
	
	var setting = {
		check: {
			enable: true
		},
		async:{
			enable: true,
			url: "admin/RoleModule/zTree",
			autoParam: ["moduleID"],
			otherParam:{"roleID":1},
			dataFilter:function(treeId, parentNode, responseData){
				if(responseData.ok){
					return responseData.data;
				}else {
					alert(responseData.message);
				}
			}
		},
		callback:{
			onClick: zTreeOnClick
		}
	};
	
	function zTreeOnClick(event, treeId, treeNode) {
	   //console.info(treeNode);
	}
	
	/*加载role */
	$.post("admin/RoleModule/getAllRole",{},function(data){
		if(data.ok){
			for(var i=0; i<data.data.length;i++){
				if(i==0){
					roleArea.append("<li class='active' data-id=\""+data.data[i].roleID+"\">"+data.data[i].name+"</li>");
					/*初始化右边的树控件*/
					//console.info("this:"+data.data[i].roleID);
					setting.async.otherParam = {"roleID":data.data[i].roleID};
					zTreeObj = $.fn.zTree.init($("#tree"), setting, null);
				}else{
					roleArea.append("<li data-id=\""+data.data[i].roleID+"\">"+data.data[i].name+"</li>");
				}
				
			}
		}else {
			alert(data.message);
		}																
			
	});
	
	$("#js-savebtn").click(function(){
		var nodes = zTreeObj.getCheckedNodes(true);
		//console.info("被选中的节点");
		//console.table(nodes);
		/*加载role */
		var roleModules = [];
		for(var i=0;i<nodes.length;i++){
			roleModules.push(nodes[i].moduleID+","+nodes[i].parent);
		}
		//console.info("id:"+roleID);
		$.post("admin/RoleModule/saveRoleModule",{"roleID":roleID,"roleModules":roleModules.join(";")},function(data){
			alert("保存成功");																
		});
		
	});
	
	
	/*处理 roleArea 下面的 li 标签的单击事件*/
	roleArea.delegate("li","click",function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		roleID = $(this).attr("data-id");
		zTreeObj.setting.async.otherParam = {"roleID":roleID};
		//重新加载异步树
		zTreeObj.reAsyncChildNodes(null, "refresh");
	});
	
	
});

</script>
</body>
</html>
