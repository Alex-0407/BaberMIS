
function charge() {
	//$("#register").attr("disabled", "disabled");
	// alert("1111"+$("#regform").validationEngine("validate"));
	//$("#register").removeAttr("disabled");
	$("#charge").click(function() {
		
		var nameElement = document.getElementById('name');
		var numElement = document.getElementById('num');
		var pwdElement = document.getElementById('pwd');
		
		var name = nameElement.value;
		var num = numElement.value;
		var pwd = pwdElement.value;
		

		//alert("name"+name+"num"+num+"pwd"+pwd);
		
		//var data1 = $("#chargeform").serialize();
		var json = {
				'name' : name,
				'num' : num,
				'pwd' : pwd
			};
		
		if ($("#chargeform").validationEngine("validate")) {
			//alert("00000");
			$.ajax({
				url : baseUrl + "admin/User/charge",
				type : "POST",
				data : json,
				dataType : "json",
				success : function(data) {
					var returnString = data.OK;
					if (returnString) {
						Alert("充值成功！");
						window.location.href = baseUrl + "page/MyInfoPart.html";
					}
					// alert(data.OK);
					// alert("注册成功！");
					else {
						Alert("账户名或密码错误！");
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					Alert("error");
				}
			});
			}
	});
}

function cancelCharge()
{
	window.location.href = baseUrl + "page/MyInfoPart.html";
	}