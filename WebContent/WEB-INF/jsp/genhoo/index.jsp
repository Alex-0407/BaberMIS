<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cqut.genhoo.ScriptLoader" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScriptLoader loader = new ScriptLoader(new String[]{"base","easyui","artDialog","jqueryui"});
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title></title>
<link rel="stylesheet" type="text/css" href="css/frame.css">
<link rel="stylesheet" href="css/form.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style type="text/css">
.logo{
	float: left;
	margin: 10px;
}

.logo-link {
	display: inline-block;
	padding: 0px 10px 0px 0px;
	border-right: 1px solid #e5e5e5;
	float: left;
}

.welcome {
	font-size: 24px;
	font-family: 'Microsoft Yahei';
	color: #fff;
	vertical-align: middle;
	margin-left: 10px;
	line-height: 70px;
}

/***/
.first-nav-wrap {
	position: absolute;
	left:270px;
	top:0px;
	right:200px;
	height:90px;
	overflow: auto;
	zoom:1;
	z-index: 21;
	/* border-bottom: 8px solid rgb(240, 249, 253); */
}

.first-nav-wrap ul{
	list-style: none;
	font-size: 14px;
	line-height: 1.666;
	border: 0;
}

.first-nav-wrap ul li{
	padding: 0px 20px;
	float: left;
	height:90px;
	line-height:90px;
	z-index: 3;
	color:#333;
	outline: 0;
	cursor: pointer;
	color: #fff;
}

.first-nav-wrap ul li.active{
	background-color:#3EA38F;
	/* background-color:rgb(240, 249, 253); */
}

.first-nav-wrap ul li.nav-after{
	border-left: none;
}


/*覆盖jqueryui的样式*/
.ui-accordion .ui-accordion-content{
	padding: 0;
}

.secondnav ul li{
	padding:5px 1em 5px 2.2em;
	border-bottom: 1px dashed #a6c9e2;
	cursor: pointer;
}

.secondnav ul li.active{
	color:#e17009;
}

.secondnav ul li.last-item{
	border-bottom: none;
}
</style>

</head>
<body>
    <div class="head">
         
         <div class="logo">
			<a href="index.jsp" class="logo-link">
				<img alt="" src="image/icon/logo-new.png">
			</a>
			<span class="welcome" id="js-system-name">人事局子系统</span>
		</div>
        
        <div class="first-nav-wrap">
            <ul id="js-first-nav">
            	<li data-moduleid="2" class="">系统设置</li>
            	<li data-moduleid="3" class="" id="js-first-selected" >人事局子系统</li>
            </ul>
        </div>
        
        <div class="headOperate">
            <div id="line1"></div>
            <div id="home"></div>
            <div id="line2"></div>
            <div id="exit"></div>
        </div>

    </div>

    <div class="content">
        <div class="left secondnav" id="js-secondnav">
       		<!-- <h3>系统管理</h3>
            <div>
            	<ul>
	                <li class="active" data-url="admin/CodeType/index">码表管理</li>
	                <li data-url="admin/Role/index">角色管理</li>
	                <li data-url="admin/Module/indexZree">模块管理</li>
	                <li data-url="admin/Department/index">单位部门管理</li>
	                <li data-url="admin/Operator/index">用户管理</li>
	                <li data-url="admin/UserRole/index">人员角色分配管理</li>
	                <li class="last-item" data-url="admin/RoleModule/index">角色模块分配管理</li>
	            </ul>
            </div>
            
           	<h3>基础设置</h3>
           	<div>
           		<ul>
	                <li class="last-item">招考类型管理</li>
	            </ul>
           	</div> -->
        </div>
        <div class="right">
            <iframe name="innerFrame" class="innerFrame" id="js-innerFrame" src="Admin/iframeIndex" frameborder="0" scrolling="auto" style="margin: 0px auto; height: 100%; -ms-overflow-x: hidden;"></iframe>
        </div>
    </div>

    <div class="footer">
        <span class="copyright">国家工作人员招考系统版权所有</span>
    </div>
    
<script type="text/javascript">
$(document).ready(function(){
	var firstNav = $("#js-first-nav");
	var secondNav = $("#js-secondnav");
	var innerFrame = $("#js-innerFrame");
	//当前被选中的菜单项
	var selectedItem = $(".active",secondNav);
	
	 /*异步加载第一级菜单*/
	/*$.post("Admin/loadModule",{"moduleID":1},function(data){
		//alert("保存成功");	
		if(data.ok){
			for(var i=0;i<data.data.length;i++){
				firstNav.append("<li data-moduleID='"+data.data[i].moduleID+"'>"+data.data[i].name+"</li>");	
			}
		}else {
			alert(data.message);
		}
		
	}); */
	
	
	
	/*第一级菜单的 click 事件*/
	firstNav.delegate("li","click",function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		var moduleID = $(this).attr("data-moduleID");
		$.post("Admin/loadModule",{"moduleID":moduleID},function(data){
			if(data.ok){
				/*先销毁原来的*/
				secondNav.accordion( "destroy" );
				secondNav.html("");
				for(var i=0;i<data.data.length;i++){
					secondNav.append("<h3 data-moduleID='"+data.data[i].moduleID+"'>"+data.data[i].name+"</h3>"+
									 "<div></div>");	
				}
				/*二级菜单的手风琴效果，调用jqueryui的组件*/
				secondNav.accordion({
					collapsible: true,
					heightStyle: "content",
					active:false,
					/*在展开的时候判断是否异步加载了第三级菜单，如果没有加载就显示‘菊花’*/
					beforeActivate: function( event, ui ) {
						
						if(ui.newPanel.attr("data-loaded")!="true"){
							ui.newPanel.html("<ul><li class='last-item'><img src='image/backstage/body/loading2.gif'/></li></ul></p>");
						}
					},
					/*加载完后*/
					activate: function( event, ui ) {
						/*如果还没有加载第三级菜单*/
						if(ui.newPanel.attr("data-loaded")!="true"){
							var moduleID = ui.newHeader.attr("data-moduleID");
							/*异步加载第三级菜单*/
							$.post("Admin/loadModule",{"moduleID":moduleID},function(data){
								if(data.ok){
									var ulHtml = $("<ul></ul>");//
									for(var i=0;i<data.data.length;i++){
										ulHtml.append("<li "+(i==data.data.length-1?'class="last-item" ':' ') + "data-url='"+data.data[i].url+"' data-moduleID='"+data.data[i].moduleID+"'>"+data.data[i].name+"</li>");
									}
									ui.newPanel.html(ulHtml);
									/*标识第三级菜单已经加载完毕*/
									ui.newPanel.attr("data-loaded","true");
								}else {
									alert(data.message);
								}
							});
						}
					}
				
				});
			}else {
				alert(data.message);
			}
			
		});
		
	});
	
	secondNav.accordion({
		collapsible: true,
		heightStyle: "content"
	});
	
	secondNav.delegate("li","click",function(){
		innerFrame.attr("src",$(this).attr("data-url"));
		selectedItem.removeClass("active");
		selectedItem = $(this);
		selectedItem.addClass("active");
	});
	
	
	//触发第一个菜单的click事件
	$("#js-first-selected").click();
	
	
});  
</script>
    
</body>
</html>
