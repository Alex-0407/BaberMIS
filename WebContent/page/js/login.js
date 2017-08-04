function login() {
	
	$("#login").click(function(){
		/*var userNameElement = document.getElementById('userName');
		var pwdElement = document.getElementById('pwd');
		var userName = userNameElement.value;
		var pwd = pwdElement.value;*/
		var data1=$("#loginform").serialize();
	    $.ajax({
	        url: baseUrl + "admin/User/login",
	        type:"POST",
	        data: data1,
	        dataType : "json",
	        success: function(data) {
	        	var returnString = data.success;
	        	var user = data.user;
	        	
				if (returnString) {				
					var userId = user[0].userId;
					var userName = user[0].userName;
				    //Alert("登录成功！");
			        /*if (getCookie('userId')) {
						window.location.href = baseUrl
								+ "page/Mybaber.html?userId=" + userId + "";
						window.location.href = baseUrl
						+ "page/Home.html";
					} else {*/
						Alert("登录成功！");
						setCookie('userId',userId,1000);
						setCookie('userName',userName,1000);
						/*window.location.href = baseUrl
								+ "page/Mybaber.html?userId=" + userId + "";*/
						window.location.href = baseUrl
						+ "page/Home.html";
					//}
				}
							
				if (!returnString) {
					Alert("用户名或密码错误！");
				}

			},
			error : function(XMLHttpRequest, textStatus) {
				Alert("服务器异常");
			}
	    });
	});
}

function ajaxUserCall(){
var request = $.ajax({
	type : "POST",
	url : baseUrl + "admin/User/checkInfo",
	data : json,
	dataType : "json",
	success : function(data) {
		if(data.info)
			{
    alertText: "* 此名称已被其他人使用";
    alertTextLoad: "* 正在确认名称是否有其他人使用，请稍等。";
			}
	}
});
}

/*
function login1() {

	var telElement = document.getElementById('userName');
	var pwdElement = document.getElementById('pwd');
	var userName = telElement.value;
	var pwd = pwdElement.value;

	if (userName == "") {
		Alert('请输入用户名');
		telElement.focus();
		return false;
	}

	
	if (pwd == "" || pwd.length != 6) {
		alert('请输入6位正确密码');
		pwdElement.focus();
		return false;
	}
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var json = {
		'userName' : userName,
		'pwd' : pwd
	};
	
	// window.location.href=baseUrl+"admin/User/login.html";
	// alert(tel+pwd);

	// alert(json);

	var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/User/login",
		data : json,
		dataType : "json",
		success : function(data) {
			var returnString = data.success;
			var user = data.user;		
// alert(userId);
			if (returnString) {
				var userId = user[0].userId;
				var userName = user[0].userName;
				// alert("登录成功！");
				if (getCookie('userId')) {
					window.location.href = baseUrl
							+ "page/Mybaber.html?userId=" + userId + "";
					window.location.href = baseUrl
					+ "page/Home.html";
				} else {
					setCookie('userId',userId);
					setCookie('userName',userName);
					window.location.href = baseUrl
							+ "page/Mybaber.html?userId=" + userId + "";
					window.location.href = baseUrl
					+ "page/Home.html";
				}
			}
			if (!returnString) {
				Alert("用户名或密码错误！");
			}

		},
		error : function(XMLHttpRequest, textStatus) {
			alert("服务器异常");
		}
	})
}*/
		