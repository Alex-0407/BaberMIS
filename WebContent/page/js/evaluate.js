function evaluateP(_i, _orderId) {
	// alert(_orderId);
	orderId = _orderId;
	i = _i;
	// window.location.href = baseUrl + "page/EvaluatePart.html";
	window.location.href = baseUrl + "page/EvaluatePart.html?orderId='"
			+ orderId + "'";
	// alert(222);
}

function evaluate() {
	// var orderCode=_orderCode;
	// var i=_i;

	$("#evaluate").click(function() {

		var orderId = unescape($_GET['orderId']).replace(/[^0-9]/ig, "");
		;

		// alert(orderId);
		var proElement = document.getElementById('professional');
		// alert(proElement.rating());
		var attElement = document.getElementById('attitude');
		var ontElement = document.getElementById('ontime');
		var contentElement = document.getElementById('content');
		var professionalSkillScore = proElement.value;
		var serviceAttitudeScore = attElement.value;
		var onTimeScore = ontElement.value;
		var evaluateContent = contentElement.value;

		
		//alert(evaluateContent);
		
		var date = new Date();
		var today = date.toLocaleString();
		var json = {
			'orderId' : orderId,
			'professionalSkillScore' : professionalSkillScore,
			'serviceAttitudeScore' : serviceAttitudeScore,
			'onTimeScore' : onTimeScore,
			'evaluateContent' : evaluateContent,
			'evaluateTime' : today,
			'orderStatus' : "已评价！"
		};
		var data1 = $("#evaluateform").serialize();
		// alert(json);
		var baseUrl = "http://localhost:8080/BaberMIS/";
		var request = $.ajax({
			type : "POST",
			url : baseUrl + "admin/Evaluate/evaluate",
			data : json,
			dataType : "json",
			success : function(data) {
				// var orderStatus = order[0].orderStatus;
				var returnString = data.OK;
				//var returnData = data.result;
				// alert(order);
				// alert(orderStatus);
				if (returnString) {
					Alert("评价成功！");
					// $("#orderStatus" + i + "").html("评价成功！");
					// document.getElementById('pay' + i).value = "评价";
					// document.getElementById('cancel' + i).value = "取消支付";
					 window.location.href = baseUrl
					 + "page/MybaberNeedEvaluatePart.html"
				} else {
					Alert("评价失败！");
				}

			},
			error : function(XMLHttpRequest, textStatus) {
				alert("服务器异常");
			}
		});
	});
}

function cancelEvaluate() {

	var baseUrl = "http://localhost:8080/BaberMIS/";
	window.location.href = baseUrl + "page/MybaberNeedEvaluatePart.html";
}

function ignoreEvaluate(_orderId) {

	var baseUrl = "http://localhost:8080/BaberMIS/";
	orderId = _orderId;
	
	var json = {
			'orderId' : orderId,
			'orderStatus' : "已完成，未评价！"
		};
	
	var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/Order/ignoreEvaluate",
		data : json,
		dataType : "json",
		success : function(data) {
			var returnString = data.OK;
			// alert(returnString);
			if (returnString) {
				Alert("您已忽略该订单评价！");
				 window.location.href = baseUrl
				 + "page/MybaberNeedEvaluatePart.html"
			} else {
				Alert("操作失败！");
			}

		},
		error : function(XMLHttpRequest, textStatus) {
			alert("服务器异常");
		}
	});

}