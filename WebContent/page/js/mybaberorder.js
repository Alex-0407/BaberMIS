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
				if (test[i].orderStatus == "理发师已接单，请准时来店理发！"
					|| test[i].orderStatus == "理发师申请支付！"
					|| test[i].orderStatus == "用户申请退款！"
					) 
					{
				if(test[i].orderStatus=="理发师申请支付！")
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
				           /* +' <span id="baberId'+i+'"  style=" visibility:hidden;">'
				            +test[i].baberId*/
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
				            /*+' <span id="userId'+i+'"  style=" visibility:hidden;">'
				            +test[i].userId*/
				            +'</span>'
				            +' </div>'
				            +'<div class="right">'
				            +' <div class="price"><span id="price">订单价格：</span><span class="orderPrice" id="orderPrice">'
				            +test[i].orderPrice
				            +'元</span></div>'
				            +'<div class="oprate"><span id="state">订单状态：</span><span class="orderStatus" id="orderStatus'+i+'" class="orderStatus">'
				            +test[i].orderStatus
				            +'</span><br/><br/><span>'
				            +'<button id="pay'+i+'" class="pay" onclick="agreepay(&quot;'+test[i].orderId+'&quot;,&quot;'+i+'&quot;)" style="cursor: pointer">立即支付</button>'
				            +'</span><span>'
				            +'<button id="cancel'+i+'" class="cancel" onclick="cancelPay(&quot;'+test[i].orderId+'&quot;,&quot;'+i+'&quot;)" style="cursor: pointer">申请退款</button>'
				            +'</span></div>'
				            +'</div>'
				            +' </div>');
					
					$("#orderlist").append($div);	
					}
				else if(test[i].orderStatus=="理发师已接单，请准时来店理发！"|| test[i].orderStatus == "用户申请退款！")
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
				           /* +' <span id="baberId'+i+'"  style=" visibility:hidden;">'
				            +test[i].baberId*/
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
				            /*+' <span id="userId'+i+'"  style=" visibility:hidden;">'
				            +test[i].userId*/
				            +'</span>'
				            +' </div>'
				            +'<div class="right">'
				            +' <div class="price"><span id="price">订单价格：</span><span class="orderPrice" id="orderPrice">'
				            +test[i].orderPrice
				            +'元</span></div>'
				            +'<div class="oprate"><span id="state">订单状态：</span><span class="orderStatus" id="orderStatus'+i+'" class="orderStatus">'
				            +test[i].orderStatus
				            +'</span><br/><br/><span>'
				            +'</span></div>'
				            +'</div>'
				            +' </div>');
					
					$("#orderlist").append($div);	
						}	
				}
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			alert("error");
		}
	})

}
