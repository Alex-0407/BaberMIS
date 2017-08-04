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
<title>新增作品</title>
<link rel="stylesheet" href="css/form.css">
<%=loader.cssFile()%>
<%=loader.scriptFile()%>
<script>
function uploadPdf2() {
    //判断是否有选择上传文件
    var imgPath4 = $("#hairPhotoUrl").val();
    if (imgPath4 == "") {
        alert("请选择上传文件！");
        return;
    }
    //判断上传文件的后缀名  
    var strExtension = imgPath4.substr(imgPath4.lastIndexOf('.') + 1);
    if (strExtension != 'jpg'||strExtension != 'png') {
        alert("请选择jpg或png格式的图片！");
        return;
    }
    $$.ajax({
        type: "POST",
        url: "/ashx/ZongHengHandler.ashx?type=upP",
        data: { imgPath4: $$("#uploadFile4").val() },
        cache: false,
        success: function(data) {
            alert("上传成功");
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("上传失败，请检查网络后重试");
        }
    });
} 
</script>
</head>
<link rel="stylesheet" href="css/form.css">
<body>
	<form method="post" class="baseform" id="addForm">
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>选择作品</span></td>
				<td class="formField">
					<input id="hairPhotoUrl" name="hairPhotoUrl"  type="file" class="easyui-validatebox" data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>作品名称</span></td>
				<td class="formField">
					<input type="text" name="hairPhotoName" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
		<table class="fromGroup">
			<tr>
				<td class="formLabel w1"><span class="needInput">*</span><span>电话</span></td>
				<td class="formField">
					<input type="text" name="hairPhotoDescribe" class="easyui-validatebox" data-options="required:true,validType:['length[1,30]']">
				</td>
			</tr>
			<tr>
				<td class="formLabel"></td>
				<td class="formField"></td>
			</tr>
		</table>
		
	</form>
<script>

$(document).ready(function(){
	
});

DialogInterface = {
	validate:function(){
		return $('#addForm').form("validate");
	},
	getData:function(){
		return $('#addForm').serializeArray();
	}
};


</script>
</body>
