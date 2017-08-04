//该类里面定义一些常用的选择器
(function(window,undefined){
	
	//选择器控件
	var GenooSelector = {
		
		//码表的选择器的选择条件
		//政治面貌选择器条件
		politicStatus:"codeTypeID=5&parent=0",
		
		//部门选择器
		deparmentSelector:function(options){
			art.dialog.open('admin/Department/selectorIndex', {
				width: 500,
				height: 340,
			    fixed: true,
			    resize: true,
			    drag: true,
				title: '选择部门',
				lock: true,
				init:function(){
					//console.info("dialog init ok");
					//这个方法在iframe页面里面的类容加载完成后被调用
					this.iframe.contentWindow.DialogInterface.init(options);
				},
				ok:function(){
					var validateResult = this.iframe.contentWindow.DialogInterface.validate();
					if(!validateResult){
						return false;
					}else{
						this.iframe.contentWindow.DialogInterface.setData();
					}
				},
				cancelVal: '关闭',
			    cancel: true, //为true等价于function(){}
				close:function(){
					var data = art.dialog.data("departmentSelector");
					options.dataHander(data);
				}
			});/***************/
		},
		
		//码表选择器
		codeTableSelector:function(options){
			art.dialog.open('admin/CodeTable/selectorIndex'+"?"+options.condition,{
				width: 500,
				height: 340,
			    fixed: true,
			    resize: true,
			    drag: true,
				title: options.title || "码表选择",
				lock: true,
				init:function(){
					//console.info("dialog init ok");
					//这个方法在iframe页面里面的类容加载完成后被调用
					this.iframe.contentWindow.DialogInterface.init(options);
				},
				ok:function(){
					var validateResult = this.iframe.contentWindow.DialogInterface.validate();
					if(!validateResult){
						return false;
					}else{
						this.iframe.contentWindow.DialogInterface.setData();
					}
				},
				cancelVal: '关闭',
			    cancel: true, //为true等价于function(){}
				close:function(){
					var data = art.dialog.data("codeTableSelector");
					options.dataHander(data);
				}
			});
		},
		
		recruitTypeSelector:function(options){
			art.dialog.open('recruitAdmin/RecruitType/recruitTypeSelector'+"?"+options.condition,{
				width: 500,
				height: 340,
			    fixed: true,
			    resize: true,
			    drag: true,
				title: options.title || "选择招考类型",
				lock: true,
				init:function(){
					//console.info("dialog init ok");
					//这个方法在iframe页面里面的类容加载完成后被调用
					this.iframe.contentWindow.DialogInterface.init(options);
				},
				ok:function(){
					var validateResult = this.iframe.contentWindow.DialogInterface.validate();
					if(!validateResult){
						return false;
					}else{
						this.iframe.contentWindow.DialogInterface.setData();
					}
				},
				cancelVal: '关闭',
			    cancel: true, //为true等价于function(){}
				close:function(){
					var data = art.dialog.data("recruitTypeSelector");
					options.dataHander(data);
				}
			});
		}
		
	};
	
	var selectIconHtml = "<img src='image/icon/8.png' style='position: relative;top:3px;left:6px;'/>";
	
	window.GenooSelector = GenooSelector;
	
	//部门选择器
	$.fn.departmentSelector = function(options){
		var img = $(selectIconHtml);
		$(this).after(img);
		img.click(function(){
			GenooSelector.deparmentSelector(options);
		});
	};
	
	//码表选择器
	$.fn.codeTableSelector = function(options){
		var img = $(selectIconHtml);
		$(this).after(img);
		img.click(function(){
			GenooSelector.codeTableSelector(options);
		});
	};
	
	//招考类型选择器
	$.fn.recruitTypeSelector = function(options){
		//var selectIconHtml = "<img src='image/icon/8.png' style='position: absolute;top:8px;right:22px;'/>";
		var img = $(selectIconHtml);
		$(this).after(img);
		img.click(function(){
			GenooSelector.recruitTypeSelector(options);
		});
	};
	
	
})(window);