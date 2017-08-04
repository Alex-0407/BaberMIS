/*--------------------实现2(返回 $_GET 对象, 仿PHP模式)----------------------*/
var $_GET = (function() {
	var url = window.document.location.href.toString();
	var u = url.split("?");
	if (typeof (u[1]) == "string") {
		u = u[1].split("&");
		var get = {};
		for ( var i in u) {
			var j = u[i].split("=");
			get[j[0]] = j[1];
		}
		return get;
	} else {
		return {};
	}
})();

/* 第2种方式, 使用时, 可以直接 $_GET['get参数'], 就直接获得GET参数的值 */

function GetOrderInfo_ByUserId() {

	var userId = getCookie('userId');
	// var userId = $_GET['userId'];

	// alert(userId);

	var json = {
		'userId' : userId
	};

	var request = $
			.ajax({
				type : "POST",
				url : baseUrl + "admin/Order/GetOrderInfo_ByUserId",
				data : json,
				dataType : "json",
				success : function(data) {
					var test = data.test;
					// alert(test[0].name);
					var i;
					var count = 0;
					for (i = 0; i < test.length; i++) {
						// alert(arr[i].baberId);
						// alert(arr[i].photo);

						if (test[i].orderStatus == "预约成功！"
								|| test[i].orderStatus == "现金已托管！"
								|| test[i].orderStatus == "已取消现金托管！"
								|| test[i].orderStatus == "预约未通过审核！"
								||test[i].orderStatus =="理发师很忙，请预约其他时段！系统已全额退款。") {
							if (test[i].orderStatus == "预约成功！"||test[i].orderStatus =="已取消现金托管！") {
								var $div = $('<div class="list">'
										+ '<div class="left">'
										+ '<div class="img">' + '<img src="'
										+ test[i].baberPhotoUrl
										+ '" /> </div>'
										+ '</div>'
										+ '<div class="center">'
										+ ' <span class="name">理发师预约服务'
										+ ' <span id="baberId'
										+ i
										+ '" style="visibility:hidden;">'
										+ test[i].orderId
										+ '</span><br/>'
										+ ' <span class="key">预约时间：</span><span class="value">'
										+ test[i].appointmentTime
										+ '</span><br />'
										+ ' <span class="key">理发师：</span><span class="value">'
										+ test[i].baberName
										+ '</span><br />'
										+ ' <span class="key" id="orderCode'
										+ i
										+ '">订单号：</span><span class="value">'
										+ test[i].orderCode
										+ '</span>'
										+ ' </div>'
										+ '<div class="right">'
										+ ' <div class="price"><span id="price">订单价格：</span><span class="orderPrice" id="orderPrice">'
										+ test[i].orderPrice
										+ '元</span></div>'
										+ '<div class="oprate"><span id="state">订单状态：</span><span class="orderStatus" id="orderStatus'
										+ i
										+ '" class="orderStatus">'
										+ test[i].orderStatus
										+ '</span><br/><br/><span>'
										+ '<input id="pay'
										+ i
										+ '" type="button" class="pay" onclick="deposit(&quot;'
										+ test[i].orderId
										+ '&quot;,&quot;'
										+ i
										+ '&quot;)" style="cursor: pointer" value="托管现金"></input>'
										+ '</span><span>'
										+ '<input id="cancel'
										+ i
										+ '" type="button" value="取消预约" class="cancel" onclick="cancelOrderPart(&quot;'
										+ test[i].orderId
										+ '&quot;)" style="cursor: pointer"></input>'
										+ '</span></div>'
										+ '</div>'
										+ ' </div>');
								$("#orderlist").append($div);

							} else if (test[i].orderStatus == "现金已托管！") {
								var $div1 = $('<div class="list">'
										+ '<div class="left">'
										+ '<div class="img">' + '<img src="'
										+ test[i].baberPhotoUrl
										+ '" /> </div>'
										+ '</div>'
										+ '<div class="center">'
										+ ' <span class="name">理发师预约服务'
										+ ' <span id="baberId'
										+ i
										+ '"  style=" visibility:hidden;">'
										+ test[i].baberId
										+ '</span><br/>'
										+ ' <span class="key">预约时间：</span><span class="value">'
										+ test[i].appointmentTime
										+ '</span><br />'
										+ ' <span class="key">理发师：</span><span class="value">'
										+ test[i].baberName
										+ '</span><br />'
										+ ' <span class="key" id="orderCode'
										+ i
										+ '">订单号：</span><span class="value">'
										+ test[i].orderCode
										+ ' <span id="userId'
										+ i
										+ '"  style=" visibility:hidden;">'
										+ test[i].userId
										+ '</span>'
										+ ' </div>'
										+ '<div class="right">'
										+ ' <div class="price"><span id="price">订单价格：</span><span class="orderPrice" id="orderPrice">'
										+ test[i].orderPrice
										+ '元</span></div>'
										+ '<div class="oprate"><span id="state">订单状态：</span><span class="orderStatus" id="orderStatus'
										+ i
										+ '" class="orderStatus">'
										+ test[i].orderStatus
										+ '</span><br/><br/><span>'
										+ '<input id="pay'
										+ i
										+ '" type="button" class="pay" onclick="cancelDeposit(&quot;'
										+ test[i].orderId
										+ '&quot;,&quot;'
										+ i
										+ '&quot;)" style="cursor: pointer" value="取消托管！"></input>'
										+ '</span><span>'
										+ '</span></div>'
										+ '</div>'
										+ ' </div>');

								$("#orderpayedlist").append($div1);
							} else if (test[i].orderStatus == "预约未通过审核！"||test[i].orderStatus == "理发师很忙，请预约其他时段！系统已全额退款。") {
								var $div1 = $('<div class="list">'
										+ '<div class="left">'
										+ '<div class="img">' + '<img src="'
										+ test[i].baberPhotoUrl
										+ '" /> </div>'
										+ '</div>'
										+ '<div class="center">'
										+ ' <span class="name">理发师预约服务'
										+ ' <span id="baberId'
										+ i
										+ '"  style=" visibility:hidden;">'
										+ test[i].baberId
										+ '</span><br/>'
										+ ' <span class="key">预约时间：</span><span class="value">'
										+ test[i].appointmentTime
										+ '</span><br />'
										+ ' <span class="key">理发师：</span><span class="value">'
										+ test[i].baberName
										+ '</span><br />'
										+ ' <span class="key" id="orderCode'
										+ i
										+ '">订单号：</span><span class="value">'
										+ test[i].orderCode
										+ ' <span id="userId'
										+ i
										+ '"  style=" visibility:hidden;">'
										+ test[i].userId
										+ '</span>'
										+ ' </div>'
										+ '<div class="right">'
										+ ' <div class="price"><span id="price">订单价格：</span><span class="orderPrice" id="orderPrice">'
										+ test[i].orderPrice
										+ '元</span></div>'
										+ '<div class="oprate"><span id="state">订单状态：</span><span class="orderStatus" id="orderStatus'
										+ i
										+ '" class="orderStatus">'
										+ test[i].orderStatus
										+ '</span><br/><br/><span>'
										+ '<input id="cancel'
										+ i
										+ '" type="button" value="移除" class="cancel" onclick="cancelOrderPart(&quot;'
										+ test[i].orderId
										+ '&quot;)" style="cursor: pointer"></input>'
										+ '</span></div>'
										+ '</div>'
										+ ' </div>');

								$("#orderpayedlist").append($div1);
							} else

							   {
								count++;
								if (count == test.length) {
									var $div2 = $('<span style="margin-left:50px">您还没有预约哦！<a href="Home.html"><span>立即去预约</span></a></span>');
									$("#orderlist").append($div2);
								}

							}

						}

					}

				},
				error : function(XMLHttpRequest, textStatus) {
					alert("error");
				}
			})

}
