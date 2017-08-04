function register() {
	
	$("#register").click(function() {
		
		var nameElement = document.getElementById('name');
		var telElement = document.getElementById('tel');	
		var pwdElement = document.getElementById('pwd');
		
		// var telcodeElement=document.getElementById('telcode');
		// var checkpwdElement=document.getElementById('checkpwd');
		
		var name = nameElement.value;
		var tel = telElement.value;
		var pwd = pwdElement.value;
		
		// var telcode=telcodeElement.value;
		// var checkpwd=checkpwdElement.value;
		//alert("name"+name+"tel"+tel+"pwd"+pwd);
		
		var data1 = $("#regform").serialize();
		var json = {
				'name' : name,
				'tel' : tel,
				'pwd' : pwd
			};
		
		if ($("#regform").validationEngine("validate")) {
			//alert("00000");
			$.ajax({
				url : baseUrl + "admin/User/register",
				type : "POST",
				data : json,
				dataType : "json",
				success : function(data) {
					var returnString = data.OK;
					var returnResult = data.result;
					if(returnResult=="已存在！")
						Alert("用户名已被抢先注册了！");
					else if (returnString) {
						Alert("注册成功！");
						window.location.href = baseUrl + "page/Login.html"
					}
					// alert(data.OK);
					// alert("注册成功！");
					else {
						Alert("注册失败！");
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					Alert("error");
				}
			});
		}
	});
}


/*function register1() {
	var nameElement = document.getElementById('name');
	var telElement = document.getElementById('tel');
	var telcodeElement = document.getElementById('telcode');
	var pwdElement = document.getElementById('pwd');
	var checkpwdElement = document.getElementById('checkpwd');
	var name = nameElement.value;
	var tel = telElement.value;
	var telcode = telcodeElement.value;
	var pwd = pwdElement.value;
	var checkpwd = checkpwdElement.value;

	if (name == "") {
		alert('请输入昵称');
		telElement.focus();
		return false;
	}
	if (tel == "") {
		alert('请输入手机号');
		telElement.focus();
		return false;
	}
	if (pwd == "" || pwd.length < 6) {
		alert('请输入6位正确密码');
		pwdElement.focus();
		return false;
	}
	if (pwd != checkpwd) {
		alert('两次输入密码不一致！');
		pwdElement.focus();
		return false;
	}

	// alert(tel+pwd);
	var json = {
		'name' : name,
		'tel' : tel,
		'pwd' : pwd
	};
	// alert(json);

	var baseUrl = "http://localhost:8080/BaberMIS/";

	var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/User/register",
		data : json,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			var returnString = data.OK;
			if (returnString) {
				window.location.href = baseUrl + "page/Login.html"
			}
			// alert(data.OK);
			// alert("注册成功！");
		},
		error : function(XMLHttpRequest, textStatus) {
			alert("error");
		}
	})
}
*/
