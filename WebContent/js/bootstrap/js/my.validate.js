/*
 * Translated default messages for the jQuery validation plugin.
 * 在这里设置 jquery validate 插件的默认配置
 * 
 */
(function($) {
	$.validator.setDefaults({
		errorClass :"help-inline",  							/*这里使用了bootstrap的样式*/
		errorElement:"span",        							/*设置错误显示的显示的标签名字*/
		highlight:function(element, errorClass, validClass){
			$(element).parent().parent().addClass("error");		/*当产生错误的时候，高亮显示该表单项，这里的class 也是依赖于bootstrap的*/
		},
		unhighlight:function(element, errorClass, validClass){
			$(element).parent().parent().removeClass("error");	/*当验证正确的时候移除 错误的时候添加的 error class*/
		}
	});
}(jQuery));
