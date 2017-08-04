function displaySubMenu(li) {
			var subMenu = li.getElementsByTagName("ul")[0];
			subMenu.style.display = "block";
			//alert(subMenu);
	}

	function hideSubMenu(li) {
			var subMenu = li.getElementsByTagName("ul")[0];
			subMenu.style.display = "none";
	}

function exit() {
	Alert('您已成功退出账号！');
	delCookie('userId');
	loadCss("css/modefy1.css"); 
	// alert(getCookie('userId'));
	$("#li_login").html('<a style="cursor:pointer" href="Login.html">登录</a>');
	$("#li_reg").html('<a style="cursor:pointer" href="Register.html">注册</a>');
	$("#li_exit").html();
	
	window.location.href = baseUrl + "page/Home.html";
}

function modefy() {
	var c_userId = getCookie('userId');
	if (c_userId) {
		// alert(3333);
		loadCss("css/modefy.css"); 
		$("#li_login").html('<a style="cursor:pointer" href="MyInfo.html">个人中心</a><ul>'
				/*+'<li><a style="cursor:pointer" href="Mybaber.html">我的预约</a></li>'*/
				/*+'<li><a style="cursor:pointer" href="Focus.html">我的关注</a></li>'*/
				+'<li><a style="cursor:pointer" href="ModefyPwd.html">修改密码</a></li>'
				+'<li><a style="cursor:pointer" href="#" onclick="exit();">退出账号</a></li>'
				+'</ul>');
		$("#li_reg").html('<a style="cursor:pointer" href="Focus.html">我的关注</a>');
		
		var $li = $('<li id="li_exit"><a style="cursor:pointer" href="#" onclick="exit();">退出</a>'
				+'</li>');
		$("#navigation").append($li);
		
	}

	if (!c_userId) {
		// alert(3333);
		loadCss("css/modefy1.css"); 
		$("#li_login").html('<a style="cursor:pointer" href="Login.html">登录</a>');
		$("#li_reg").html('<a style="cursor:pointer" href="Register.html">注册</a>');
		$("#li_exit").html();
		
	}
}
