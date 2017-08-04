function exit() {
	Alert('您已成功退出账号！');
	delCookie('userId');
	/*loadCss("css/modefy1.css"); 
	// alert(getCookie('userId'));
	$("#li_login").html('<a style="cursor:pointer" href="Login.html">登录</a>');
	$("#li_reg").html('<a style="cursor:pointer" href="Register.html">注册</a>');
	$("#li_exit").html();*/
	
	window.location.href = baseUrl + "page/Login.html";
}

function modefypwd() {
	//$("#register").attr("disabled", "disabled");
	// alert("1111"+$("#regform").validationEngine("validate"));
	//$("#register").removeAttr("disabled");
	$("#modefypwd").click(function() {
		
		var pwdElement = document.getElementById('pwd');
		var newpwdElement = document.getElementById('newpwd');
		
		var name = getCookie('userName');
		
		var pwd = pwdElement.value;
		var newpwd = newpwdElement.value;

		//alert("name"+name+"tel"+tel+"pwd"+pwd);
		
		//var data1 = $("#modefypwdform").serialize();
		var json = {
				'name' : name,
				'pwd' : pwd,
				'newpwd' : newpwd
			};
		
		if ($("#modefypwdform").validationEngine("validate")) {
			//alert("00000");
			if(!name)
			{
			alert("您还未登录！");
			}
		else
			{
			$.ajax({
				url : baseUrl + "admin/User/modefyPwd",
				type : "POST",
				data : json,
				dataType : "json",
				success : function(data) {
					var returnString = data.OK;
					if (returnString) {
						Alert("修改密码成功，请重新登录！");
						//alert("1111"+returnString+"data.OK"+data.OK);
						exit();
					}
					// alert(data.OK);
					// alert("注册成功！");
					else {
						Alert("原密码错误！");
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					Alert("error");
				}
			});;
			}
		
		}
	});
}

function cancel()
{
	window.location.href = baseUrl + "page/Home.html";
	}