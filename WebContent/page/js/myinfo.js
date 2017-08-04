$(document).ready(function(){
$(".level1 > a").click(function(){
$(this).addClass("current") //给当前元素添加"current"样式
.next().show() //下一个元素显示
.parent().siblings().children("a").removeClass("current") //父元素的兄弟元素的子元素<a>移除"current"样式
.next().hide(); //它们的下一个元素隐藏
return false;
}); 
});

$(document).ready(function(){ 
$(".level2 a").each(function(){ 
$this = $(this); 
if($this[0].href==String(window.location)){ 
$this.addClass("hover"); 
} 
}); 
}); 


function GetUserInfo() {
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var userId=getCookie('userId');
	var json = {'userId':userId};

	var request = $
			.ajax({
				type : "POST",
				url : baseUrl + "/admin/User/GetUserInfo",
				data : json,
				dataType : "json",
				success : function(data) {
					var arr = data.user;
//				    alert(arr);
					$("#img").html("<img src='"+arr[0].userPhotoUrl+"' />");
					$("#userName").html("<td class='intro'>"+" "+arr[0].userName+"</td>");
					$("#realName").html("<td class='intro'>"+arr[0].userRealName+"</td>");
					$("#gender").html("<td class='intro'>"+arr[0].userSex+"</td>");
					$("#birthday").html("<td class='intro'>"+arr[0].userBirthday+"</td>");
					$("#tel").html("<td class='intro'>"+arr[0].userTel+"</td>");
					$("#addr").html("<td class='intro'>"+arr[0].userAddress+"</td>");
					$("#num").html("<td class='intro'>"+arr[0].userAccountAmount+"</td>");
				},
				error : function() {
					alert("error");
				}
			})
}

function LoadUserInfo() {
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var userId=getCookie('userId');
	var json = {'userId':userId};

	var request = $
			.ajax({
				type : "POST",
				url : baseUrl + "/admin/User/GetUserInfo",
				data : json,
				dataType : "json",
				success : function(data) {
					var arr = data.user;
					document.getElementById('userName').value=arr[0].userName;
					document.getElementById('realName').value=arr[0].userRealName;
					document.getElementById('gender').value=arr[0].userSex;
					document.getElementById('birthday').value=arr[0].userBirthday;
					document.getElementById('tel').value=arr[0].userTel;
					document.getElementById('addr').value=arr[0].userAddress;
					document.getElementById('num').value=arr[0].userAccountAmount;
				},
				error : function() {
					alert("error");
				}
			})
}


function modefyMyInfo() {
	
	$("#modefymyinfo").click(function() {
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var userId=getCookie('userId');
	
	var userNameElement = document.getElementById('userName');
	var realNameElement = document.getElementById('realName');
    var genderElement=document.getElementById('gender');
	var birthdayElement = document.getElementById('birthday');
    var telElement=document.getElementById('tel');
    var addrElement=document.getElementById('addr');
	var userName = userNameElement.value;
	var realName = realNameElement.value;
	var gender=genderElement.value;
	var birthday = birthdayElement.value;
    var tel=telElement.value;
    var addr=addrElement.value;

	//alert("name"+name+"tel"+tel+"pwd"+pwd);
	
	var json = {
			'userId' : userId,
			'userName' : userName,
			'realName' : realName,
			'sex' : gender,
			'birthday' : birthday,
			'tel' : tel,
			'address' : addr
		};


	if ($("#modefymyinfoform").validationEngine("validate")) {
		//alert("00000");
		$.ajax({
			url : baseUrl + "admin/User/modefyMyInfo",
			type : "POST",
			data : json,
			dataType : "json",
			success : function(data) {
				var returnString = data.OK;
				if (returnString) {
					//alert("1111"+returnString+"data.OK"+data.OK);
					Alert("修改个人信息成功！");
					window.location.href = baseUrl + "page/MyInfoPart.html"
				}
				// alert(data.OK);
				// alert("注册成功！");
				else {
					Alert("修改个人信息失败！");
				}
			},
			error : function(XMLHttpRequest, textStatus) {
				Alert("error");
			}
		});
	}
});
}

function cancel() {
	Alert('您已成功退出账号！');
	delCookie('userId');
	window.location.href = baseUrl + "page/Home.html";
}