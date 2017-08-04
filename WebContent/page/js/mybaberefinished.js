function GetOrderInfo_ByUserId() {

	var userId=getCookie('userId');
//	var userId = $_GET['userId'];
    
	//alert(userId);
	
	var json = {
		'userId' : userId
	};

	var request = $.ajax({
		type : "POST", 
		url : baseUrl + "admin/Order/GetOrderInfo_ByUserId",
		data : json,
		dataType : "json",
		success : function(data) {
			var test=data.test;
//			alert(test[0].name);
			var i;
			for (i = 0; i < test.length; i++) {
				   //alert(arr[i].baberId);
					// alert(test[i].orderStatus);
				if(test[i].orderStatus=="已评价！"|| test[i].orderStatus=="已完成，未评价！"|| test[i].orderStatus == "退款成功！退款金额为订单的90% 。")
					{
					var $div = $('<div class="list">'
				            +'<div class="left">'
				            +'<div class="img">'
				            +'<img src="'
		                    +test[i].baberPhotoUrl
				            +'" /> </div>'
				            +'</div>'
				            +'<div class="center">'
				            +' <span class="name">理发师预约服务'
                            +'</span><br/>'
				            +' <span class="key">预约理发时间：</span><span class="value">'
				            +test[i].appointmentTime
				            +'</span><br />'
				            +' <span class="key">预约时间：</span><span class="value">'
				            +test[i].orderStartTime
				            +'</span><br />'
				            +' <span class="key">理发师：</span><span class="value">'
				            +test[i].baberName
                            +'</span><br />'
				            +' <span class="key" id="orderCode'+i+'">订单号：</span><span class="value">'
				            +test[i].orderCode
				            +'</span>'
				            +' </div>'
				            +'<div class="right">'
				            +' <div class="price"><span id="price">订单价格：</span><span class="orderPrice" id="orderPrice">'
				            +test[i].orderPrice
				            +'元</span></div>'
				            +'<div class="oprate"><span id="state">订单状态：</span><span class="orderStatus" id="orderStatus'+i+'" class="orderStatus">'
				            +test[i].orderStatus
				            +'</span><br/><br/><span>'
				            +'</span><span>'
				            +'<button id="cancel'+i+'" class="cancel" onclick="removeOrder(&quot;'+test[i].orderId+'&quot;)" style="cursor: pointer">移除</button>'
				            +'</span></div>'
				            +'</div>'
				            +' </div>');
					}
					$("#orderlist").append($div);			
				}
		},
		error : function(XMLHttpRequest, textStatus) {
			alert("error");
		}
	})
}

function removeOrder(_orderId) {

	var baseUrl = "http://localhost:8080/BaberMIS/";
	orderId = _orderId;
	
	var json = {
			'orderId' : orderId,
			'orderStatus' : "用户已移除！"
		};
	
	var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/Order/removeOrder",
		data : json,
		dataType : "json",
		success : function(data) {
			var returnString = data.OK;
			// alert(returnString);
			if (returnString) {
				Alert("您已移除该订单！");
				 window.location.href = baseUrl
				 + "page/MybaberFinishedPart.html"
			} else {
				Alert("操作失败！");
			}

		},
		error : function(XMLHttpRequest, textStatus) {
			alert("服务器异常");
		}
	});

}
/*function removeOrder(_orderId) {

	var orderId=parseInt(_orderId);

	var r=confirm("确定要删除该订单及其评价吗？");
	if(r){
		$.post(baseUrl+"admin/Order/delete",{orderId:orderId},function(data){
			var returnString = data.OK;
			if (returnString) {
				Alert("删除订单成功！");
				window.location.href = baseUrl
				+ "page/MybaberFinishedPart.html";
			}
			else {
				Alert("删除订单成功！");
				window.location.href = baseUrl
				+ "page/MybaberFinishedPart.html";
			}
		});
	}
}*/
