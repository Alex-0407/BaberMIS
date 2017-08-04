function deposit(_orderId,_i) {
	var orderId=_orderId;
	var i=_i;

	//var baseUrl = "http://localhost:8080/BaberMIS/";
	var json = {
		'orderId' : orderId,
		'orderStatus' :"现金已托管！"
	};

	// alert(json);
	    var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/Order/deposit",
		data : json,
		dataType : "json",
		success : function(data) {
			var returnString = data.result;	
			//alert(order);
			//alert(orderStatus);
		     if (returnString>0) {
				Alert("现金托管成功！");
				$("#orderStatus"+i+"").html("现金已托管！");
				//document.getElementById('pay'+i).value="评价";
				//document.getElementById('cancel'+i).value="取消支付";
				window.location.href = baseUrl
				+ "page/Mybaber.html"
			}
			else {
				Alert("现金不足，托管失败！");
			}

		},
		error : function(XMLHttpRequest, textStatus) {
			alert("服务器异常");
		}
	})
}

function cancelDeposit(_orderId,_i) {
	var orderId=_orderId;
	var i=_i;

	//var baseUrl = "http://localhost:8080/BaberMIS/";
	var json = {
		'orderId' : orderId,
		'orderStatus' :"已取消现金托管！"
	};

	// alert(json);
	    var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/Order/cancelDeposit",
		data : json,
		dataType : "json",
		success : function(data) {
			var returnString = data.result;	
			//alert(order);
			//alert(orderStatus);
            if (returnString>0) {
				Alert("取消现金托管成功！");
				$("#orderStatus"+i+"").html("已取消现金托管！");
				//document.getElementById('pay'+i).value="评价";
				//document.getElementById('cancel'+i).value="取消支付";
				window.location.href = baseUrl
				+ "page/Mybaber.html";
			}
			else {
				Alert("取消现金托管失败！");
			}

		},
		error : function(XMLHttpRequest, textStatus) {
			alert("服务器异常");
		}
	})
}