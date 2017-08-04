function GetBaberInfo_Male() {
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var json = {};

	var request = $
			.ajax({
				type : "POST",
				url : baseUrl + "/admin/Baber/GetBaberInfo_Male",
				data : json,
				dataType : "json",
				success : function(data) {
					var arr = data.test;
//				    alert(arr);
					$(".bottom").html("");
					
					for (var i = 0; i < arr.length; i++) {
					   //alert(arr[i].baberId);
//						 alert(arr[i].photo);
						var $div = $('<div class="barber"><a href="Detail.html?baberId='+arr[i].baberId
								+'"><div class="img"><img src="'
								+ arr[i].baberPhotoUrl
								+ '"/></div><div class="text"><span>昵称：'
								+ arr[i].baberName
								+ '</span><br /><span>价格：￥'
								+ arr[i].servicePrice
								+'</span><br />'
								/*+'<span>口碑：</span><span><br />预约次数：</span>'*/
								+'</div></a></div>');
						$(".bottom").append($div);
					}
				},
				error : function() {
					alert("error");
				}
			})
}

function GetBaberInfo_Male_ByPrice() {
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var json = {};

	var request = $
			.ajax({
				type : "POST",
				url : baseUrl + "/admin/Baber/GetBaberInfo_Male_ByPrice",
				data : json,
				dataType : "json",
				success : function(data) {
//					var arr = data.baber;
					var arr = data.test;
//				     alert(arr);
					$(".bottom").html("");
					for (var i = 0; i < arr.length; i++) {
//						 alert(arr[i].photo);
						var $div = $('<div class="barber"><a href="Detail.html?baberId='+arr[i].baberId
								+ '"><div class="img"><img src="'
								+ arr[i].baberPhotoUrl
								+ '"/></div><div class="text"><span>昵称：'
								+ arr[i].baberName
								+ '</span><br /><span>价格：￥'
								+ arr[i].servicePrice
								+'</span><br />'
								/*+'<span>口碑：</span><span><br />预约次数：</span>'*/
								+'</div></a></div>');
						$(".bottom").append($div);
					}
				},
				error : function() {
					alert("error");
				}
			})
}

function loadServiceInfo() {
	var baseUrl = "http://localhost:8080/BaberMIS/";
	var json = {};
	var request1 = $.ajax({
		type : "POST",
		url : baseUrl + "/admin/Service/GetServiceInfo_Male",
		data : json,
		dataType : "json",
		success : function(data) {
			var arr = data.t_service;
//			 alert(arr1.length);
		
			
			$("#img").html("<img src='"+arr[0].serviceImgUrl+"' />");
			$("#nameIntro").html("<td class='intro'>"+" "+arr[0].nameIntro+"</td>");
			$("#timeIntro").html("<td class='intro'>"+arr[0].timeIntro+"</td>");
			$("#hairStyle").html("<td class='intro'>"+arr[0].hairStyle+"</td>");
			$("#extraService").html("<td class='intro'>"+arr[0].extraService+"</td>");
		},
		error : function() {
			alert("error");
		}
	})
}