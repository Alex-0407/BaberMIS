/**
 * CqutDialog
 * 用于将dialog放置在窗口的顶层。
 * 数据传递
 * 时间处理
 */
(function(window, undefined){
	function createDialog(options,parameters){
		var id ="dialog_" + new Date().getTime();
		$("body").append("<div id='"+id+"'></div>");
		window.CqutDialog.setParameter(id,parameters);
		
		
		
		$("#"+id).dialog(options);
		
	}
	window.CqutDialog = {};
	
	window.CqutDialog.createDialog = createDialog;
	
	//用户缓存页面的参数
	window.CqutDialog.parameters = {};
	
	//设置传递给dialog页面的参数
	window.CqutDialog.setParameter = function(parameterName,prarameterValue){
		window.CqutDialog.parameters[parameterName] = prarameterValue;
	};
	
	//取得传递给dialog的数据
	window.CqutDialog.getParameter = function(parameterName){
		return window.CqutDialog.parameters[parameterName];
	};
	
	//删除参数
	window.CqutDialog.removeParameter = function(parameterName){
		delete window.CqutDialog.parameters[parameterName];
	};	
	
})(window);