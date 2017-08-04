var focusId = null;
function getFocusInfo() {
	var userId = getCookie('userId');
	var baseUrl = "http://localhost:8080/BaberMIS/";

	// alert(baberId);
	var json = {
		'userId' : userId
	};

	var request = $
			.ajax({
				type : "POST",
				url : baseUrl + "admin/Focus/getFocusInfo",
				data : json,
				dataType : "json",
				success : function(data) {
					var focus = data.focus;

					var i;
					var count = 0;
					for (i = 0; i < focus.length; i++) {
						var $div = $('<div class="list">'
								+ '<div class="left">' + '<div class="img">'
								+ '<img src="'
								+ focus[i].baberPhotoUrl
								+ '" /> </div>'
								+ '<div class="center">'
								+ ' <span class="key">理发师姓名：</span><span class="value">'
								+ focus[i].baberName
								+ '</span><br />'
								+ ' <span class="key">性别：</span><span class="value">'
								+ focus[i].baberSex
								+ '</span><br />'
								+ ' <span class="key">年龄：</span><span class="value">'
								+ focus[i].baberAge
								+ '</span><br />'
								+ ' <span class="key">工作年限：</span><span class="value">'
								+ focus[i].baberWorkTime
								+ '</span><br />'
								+ '<span><input id="detail'
								+ i
								+ '" type="button" class="detail" onclick="detail(&quot;'
								+ focus[i].baberId
								+ '&quot;,&quot;'
								+ i
								+ '&quot;)" style="cursor: pointer" value="查看详情"></input>'
								+ '<input id="cancelFocus'
								+ i
								+ '" type="button" class="cancelFocus" onclick="cancelFocus1(&quot;'
								+ focus[i].focusId
								+ '&quot;,&quot;'
								+ i
								+ '&quot;)" style="cursor: pointer" value="取消关注"></input>'
								+ ' </span></div>');
						$("#focuslist").append($div);

					}
				},
				error : function(XMLHttpRequest, textStatus) {
					alert("error");
				}
			})
}

function getFocusStatusInfo() {
	var baberId = $_GET['baberId'];
	var userId = getCookie('userId');
	var baseUrl = "http://localhost:8080/BaberMIS/";
	if(!getCookie('userId'))
	{
		document.getElementById('focus').style.display = 'none';
		return false;
	}
	// alert(baberId);
	var json = {
		'baberId' : baberId,
		'userId' : userId
	};

	var request = $.ajax({
		type : "POST",
		url : baseUrl + "admin/Focus/getFocusStatusInfo",
		data : json,
		dataType : "json",
		success : function(data) {
			var status = data.focusStatus;

			if (status == "已关注！") {
				$("#focus").html('已关注');
				document.getElementById('cancelfocus').style.display = 'block';
				// $("#cancelfocus").display="block";
				var focus = data.focus;
				window.focusId = focus[0].focusId;
			} else {
				$("#focus").html('加关注');
			}
			// alert(focus);
			// alert(window.focusId);
		},
		error : function(XMLHttpRequest, textStatus) {
			alert("error");
		}
	})
}
function focus() {

	$("#focus")
			.click(
					function() {
						var baberId = $_GET['baberId'];
						var userId = getCookie('userId');
						// alert(evaluateContent);

						var date = new Date();
						var today = date.toLocaleString();
						var json = {
							'userId' : userId,
							'baberId' : baberId,
							'focusTime' : today,
							'focusStatus' : "已关注！"
						};

						// alert(json);
						var baseUrl = "http://localhost:8080/BaberMIS/";
						var request = $
								.ajax({
									type : "POST",
									url : baseUrl + "admin/Focus/focus",
									data : json,
									dataType : "json",
									success : function(data) {

										var returnString = data.OK;

										if (returnString) {
											Alert("关注成功！");
											/*
											 * window.location.href = baseUrl +
											 * "page/Detail.html?baberId="+baberId;
											 */
											$("#focus").html('已关注');
											document
													.getElementById('cancelfocus').style.display = 'block';
											window.location.href = baseUrl
													+ "page/Detail.html?baberId="
													+ baberId;
										} else {
											Alert("关注失败！");
										}

									},
									error : function(XMLHttpRequest, textStatus) {
										alert("服务器异常");
									}
								});
					});
}

function cancelFocus() {
	var r = confirm("确定要取消关注吗？");
	if (r) {
		$.post(baseUrl + "admin/Focus/delete", {
			focusId : focusId
		}, function(data) {
			var returnString = data.OK;
			if (returnString) {
				Alert("取消关注成功！");
				/*
				 * window.location.href = baseUrl +
				 * "page/Detail.html?baberId="+baberId;
				 */
				$("#focus").html('加关注');
				document.getElementById('cancelfocus').style.display = 'none';
			} else {
				Alert("取消关注成功！");
				/*
				 * window.location.href = baseUrl +
				 * "page/Detail.html?baberId="+baberId;
				 */
				$("#focus").html('加关注');
				document.getElementById('cancelfocus').style.display = 'none';
			}
		});
	}
}

function detail(_baberId,_i) {
	var baberId = _baberId;
	window.location.href = baseUrl + "page/Detail.html?baberId=" + baberId;
}

function cancelFocus1(_focusId,_i) {
	var focusId = _focusId;
	var r = confirm("确定要取消关注吗？");
	if (r) {
		$.post(baseUrl + "admin/Focus/delete", {
			focusId : focusId
		}, function(data) {
			var returnString = data.OK;
			if (returnString) {
				Alert("取消关注成功！");
				window.location.href = baseUrl + "page/Focus.html";
			}
			else
				{
				Alert("取消关注成功！");
				window.location.href = baseUrl + "page/Focus.html";
				}
	    });
    }
}