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
<title>平台端首页</title>
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<style>
a, a:link, a:visited {
	/*color:#ccc;text-decoration:none;*/
	text-decoration: none;
}

.u_list {
	text-decoration: none;
	list-style-type: none;
	width: 100%;
	height: auto;
	padding: 0px;
	outline-offset: 0px;
}

.u_list li:hover {
	background: #c1f3f0;
}

.list {
	width: 100%;
	height: 20px;
	background: #eee;
	border: 3px solid #eee;
	margin-bottom: 3px;
	padding-bottom: 1px;
	text-align: center;
}

.u_list .list a {
	display: block;
	color: #888;
}

.head {
	width: 100%;
	height：60px;
	/* background-image: url(images/backload1.png); */
	font-size: 36px;
	font-family: "幼圆";
	font-weight: bolder;
	color: #0CF;
}

.logo img {
	float: left;
}

.title_1 {
	font-size: 36px;
	height: 20px;
	line-height: 20px;
	font-weight: 600;
	text-align:center;
	margin-top: 20px;
}
</style>

<script type="text/javascript">
	$(function() {
		 //动态菜单数据
	    var firstTreeData = [{
	            text : "基本信息",
	            children : [{
	            	    "id":1,
	                    text : "管理员",
	                    attributes : {
	                        url : "admin/Admin/index"
	                    }
	                }, {
	                	"id":2,
	                    text : "理发师",
	                    attributes : {
	                        url : "admin/Baber/index"
	                    }
	                }, {
	                	"id":3,
	                    text : "用户",
	                    attributes : {
	                        url : "admin/User/index"
	                    }
	                },
	            ]
	        }
	    ];
		 
	    var secondTreeData = [{
            text : "理发师管理",
            children : [{
            	    "id":4,
                    text : "审核理发师",
                    attributes : {
                        url : ""
                    }
                }, {
                	"id":5,
                    text : "一级菜单22",
                    attributes : {
                        url : ""
                    }
                }, {
                	"id":6,
                    text : "一级菜单23",
                    attributes : {
                        url : ""
                    }
                },
            ]
        }
    ];
	    var thirdTreeData = [{
            text : "订单",
            children : [{
            	    "id":7,
                    text : "预约订单",
                    attributes : {
                        url : "admin/Order/CheckOrderIndex"
                    }
                }, {
                	"id":8,
                    text : "申请退款订单",
                    attributes : {
                        url : "admin/Order/ApplyDrawbackOrderIndex"
                    }
                }, {
                	"id":9,
                    text : "作废订单",
                    attributes : {
                        url : ""
                    }
                },
            ]
        }
    ];
	    var forthTreeData = [{
            text : "个人中心",
            children : [{
            	    "id":10,
                    text : "我的资料",
                    attributes : {
                        url : ""
                    }
                }, {
                	"id":11,
                    text : "修改密码",
                    attributes : {
                        url : ""
                    }
                }, {
                	"id":12,
                    text : "退出系统",
                    attributes : {
                        url : ""
                    }
                },
            ]
        }
    ];
		 
		//实例化树形菜单
		$("#firstTree").tree({
			data : firstTreeData,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});
		//实例化树形菜单
		$("#secondTree").tree({
			data : secondTreeData,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});
		//实例化树形菜单
		$("#thirdTree").tree({
			data : thirdTreeData,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});
		//实例化树形菜单
		$("#forthTree").tree({
			data : forthTreeData,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});
		//在右边center区域打开菜单，新增tab
		function Open(text, url) {
			if ($("#tabs").tabs('exists', text)) {
				$('#tabs').tabs('select', text);
			} else {
				var href = url;
				var content = "<iframe src="+url+" width='1150px' height='401px' frameborder='no' border='0' marginwidth='0' marginheight='0' scrolling='yes' allowtransparency='yes'></iframe>";
				$('#tabs').tabs('add', {
					title : text,
					closable : true,
					content : content,
				//  href:url
				});
			}
		}
		//绑定tabs的右键菜单
		$("#tabs").tabs({
			onContextMenu : function(e, title) {
				e.preventDefault();
				$('#tabsMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data("tabTitle", title);
			}
		});

		//实例化menu的onClick事件
		$("#tabsMenu").menu({
			onClick : function(item) {
				CloseTab(this, item.name);
			}
		});

		//几个关闭事件的实现
		function CloseTab(menu, type) {
			var curTabTitle = $(menu).data("tabTitle");
			var tabs = $("#tabs");

			if (type === "close") {
				tabs.tabs("close", curTabTitle);
				return;
			}

			var allTabs = tabs.tabs("tabs");
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel("options");
				if (opt.closable && opt.title != curTabTitle
						&& type === "Other") {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === "All") {
					closeTabsTitle.push(opt.title);
				}
			});

			for (var i = 0; i < closeTabsTitle.length; i++) {
				tabs.tabs("close", closeTabsTitle[i]);
			}
		}
	});
</script>
</head>
<body class="easyui-layout">

 	<div data-options="region:'north',collapsible:false"
		style="height:60px;">
		<div class="head">
		    <div class="logo">
				<!-- <img
					style="width: 350px; height: 80px; margin-left: 130px; margin-top: 20px;"
					src="images/logo1.jpg" /> -->
			</div> 
			<div class="title_1">好时理发后台管理系统</div>
		</div>

	</div>
	
	<!-- <div data-options="region:'north',title:'好时理发后台管理系统'" style="height:25px;">
	</div>  -->
	
	<div data-options="region:'south',split:'true',collapsible:false"
		style="height: 44px;">
		<p align="center" style="align-content: center">Copyright
			2015-2016 重庆理工信管36-1班</p>
	</div>

	<div id="accodion"
		data-options="region:'west',split:'true',title:'导航菜单'"
		class="easyui-accordion" style="height: 100%; width: 200px">
		<div title="基本信息管理" iconcls="icon-computer" selected="true"
			style="overflow: auto; padding: 10px;">
			<ul id="firstTree" class="easyui-tree">				
			</ul>
		</div>
		<div title="理发师管理" iconcls="icon-reload" style="padding: 10px;">
			<ul id="secondTree" class="easyui-tree"></ul>
		</div>
		<div title="订单管理" iconcls="icon-user" style="padding: 10px;">
			<ul id="thirdTree" class="easyui-tree"></ul>
		</div>
		<div title="系统管理" iconcls="icon-key" style="padding: 10px;">
			<ul id="forthTree" class="easyui-tree"></ul>
		</div>
	</div>
	<div data-options="region:'center',split:'true'"
		style="padding: 5px; background: #eee;">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页" style="padding: 20px;">
				<div style="height: 320px;font-size: 25px;color:purple;text-align:center;padding-top: 150px;background:url('./image/bg/bg10.jpg') no-repeat;">
					<%-- <%= session.getAttribute("loginName")%>,欢迎使用好时理发管理系统！ --%>
				    ${adminLoginName},欢迎使用好时理发管理系统
				</div>
			</div>
		</div>
	</div>
	<div id="tabsMenu" class="easyui-menu" style="width: 120px;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>
</body>