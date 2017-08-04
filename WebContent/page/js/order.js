function order() {
	var priceElement = document.getElementById('price').innerHTML;
	var hairdesignElement = document.getElementById('hairdesign');
	var selectdateElement = document.getElementById('selectdate');
	var timeElement = document.getElementById('time');

	var price = priceElement.substring(1);
	var hairdesign = hairdesignElement.value;
	var selectdate = selectdateElement.value;
	var time = timeElement.value;
	var userId=getCookie('userId');
	var baberId= $_GET['baberId'];
//	alert(userId);
//	alert(baberId);

	selectdate = selectdate.replace(/-/g, "/");

	// alert(price);
	// alert(hairdesign);
	// alert(selectdate);
	// alert(time);

	var date = new Date();
	var today = date.toLocaleDateString();

	function dateComp(d1, d2) {
		var date1 = new Date(Date.parse(d1.replace("-", "/")));
		var date2 = new Date(Date.parse(d2.replace("-", "/")));
		var r = (date1 - date2) / (24 * 60 * 60 * 1000);
		return r;
	}
	
	if (!userId) {
		Alert('已登录用户才能预约哦！');
		return false;
	}

	// alert(dateComp(selectdate,today));
	if (!selectdate) {
		Alert('你忘了选日期哦！');
		return false;
	}
	if (dateComp(selectdate, today) < 0) {
		Alert('日期不能小于今天');
		selectdateElement.focus();
		return false;
	}
	
	var json = {
	    'orderCode' :uuidFast()+" U"+userId+" D"+today+" B"+baberId,
		'orderPrice' : price,
		'hairdesign' : hairdesign,
		'orderStartTime' : date.toLocaleString(),
//		'selectdate' : selectdate,
		'orderStatus' : "预约成功！",
		'appointmentTime' : selectdate+" "+time,
		'userId' : userId,
		'baberId' : baberId
	};
	
	if(userId==getCookie('userId')&&baberId==getCookie('baberId')&&selectdate==getCookie('selectdate')&&time==getCookie('time'))
    {
		Alert('不能重复预约哦！');
		}
	else{

	var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/Order/order",
		data : json,
		dataType : "json",
		success : function(data) {
			var returnString = data.OK;
			if (returnString) {
				Alert("预约成功！");
				setCookie('baberId',baberId);
				setCookie('selectdate',selectdate);
				setCookie('time',time);
				window.location.href = baseUrl
				+ "page/MyInfo.html";
			}
			if (!returnString) {
				Alert("预约失败！");
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			Alert("服务器异常！");
		}
	})
	}

}

function cancelOrder(_orderId) {

	var orderId=parseInt(_orderId);

	var r=confirm("确定要取消预约吗？");
	if(r){
		$.post(baseUrl+"admin/Order/delete",{orderId:orderId},function(data){
			var returnString = data.OK;
			if (returnString) {
				Alert("取消预约成功！");
				window.location.href = baseUrl
				+ "page/MyInfo.html";
			}
			else {
				Alert("取消预约成功！");
				window.location.href = baseUrl
				+ "page/MyInfo.html";
			}
		});
	}
}

function cancelOrderPart(_orderId) {

	var orderId=parseInt(_orderId);

	var r=confirm("确定要取消预约吗？");
	if(r){
		$.post(baseUrl+"admin/Order/delete",{orderId:orderId},function(data){
			var returnString = data.OK;
			if (returnString) {
				Alert("取消预约成功！");
				window.location.href = baseUrl
				+ "page/MybaberPart.html";
			}
			else {
				Alert("取消预约成功！");
				window.location.href = baseUrl
				+ "page/MybaberPart.html";
			}
		});
	}
}