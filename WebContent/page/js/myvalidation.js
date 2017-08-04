$(function() {
	$.validator
			.addMethod(
					"isMobile",
					function(value, element) {
						var length = value.length;
						var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
						return this.optional(element)
								|| (length == 11 && mobile.test(value));
					}, "请正确填写您的手机号码");

	$("#regform").validate({
		rules : {
			tel : {
				required : true,
				minlength : 11,
				// 自定义方法：校验手机号在数据库中是否存在
				// checkPhoneExist : true,
				isMobile : true
			},
			code : {
				digits : true,
				required : true
			}
		},
		messages : {
			tel : {
				required : "请输入手机号",
				minlength : "确认手机不能小于11个字符",
				isMobile : "请正确填写您的手机号码"
			},
			code : {
				required : "请输入验证码",
				digits : "验证码应该输入数字"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo(element.next().next());
		},
		ignore : ".codeCls"
	});
})