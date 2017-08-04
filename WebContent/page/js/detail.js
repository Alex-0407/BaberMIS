function resize_img(pic,w,h){
    var re_new_size=function(r){    //根据比率重新计算宽度
        return {w:pic.width/r,h:pic.height/r};
        };
    var re_offset=function(n){  //根据新的宽高度返回偏移量
        return {off_l:(n.w-w)*0.5,off_t:(n.h-h)*0.5};
        };  
    var re_position=function(o,n){  //重新定位图片
        pic.style.cssText="position:absolute;top:"+-o.off_t+"px;left:"+-o.off_l+"px;width:"+n.w+"px;height:"+n.h+"px;";
        };  
    var execute=function(rate){
        var new_size=re_new_size(rate),
              offset_new=re_offset(new_size);
        re_position(offset_new,new_size);
        };
    var rate_of_w=pic.width/w,
          rate_of_h=pic.height/h,
          rate;
    if(rate_of_w>=1){                //图片宽度大于显示区域宽度
                if(rate_of_h>=1){            //且图片高度大于显示区域高度
                rate=Math.min(rate_of_w,rate_of_h);
                }else{                      //图片高度小于显示区域
                    rate=pic.height/h;
                    }
    }else{                              //图片宽度小于显示区域宽度
                if(rate_of_h>=1){            //且图片高度大于显示区域高度
                    rate=pic.width/w;
                }else{                          //图片高度小于显示区域高度
                    rate=Math.min(rate_of_w,rate_of_h);
                    }
            }     
    execute(rate);  //执行入口      
    }

function getBaberDetailInfo()
 {	 
	 var  baberId= $_GET['baberId'];
	 var baseUrl="http://localhost:8080/BaberMIS/";  
	 
	 //alert(baberId);
	 var json={'baberId':baberId};
	 
	 var request = $.ajax({
			type: "POST",  
			url:baseUrl+"admin/Baber/GetBaberDetailInfo_ById", 
		 	data:json,
		 	dataType: "json",
		 	success :function(data)
		 	{
		 		var arr = data.test;
		 		var evaluateData=data.evaluate;
		 		
		 		
		 		$(".baberimg").html('<img id="baberimg" src="'
                    +arr[0].baberPhotoUrl
                    +'" /></span>');
		 		
		 		$(".babername").html(arr[0].baberName);
		 		
		 		$(".add").html(arr[0].baberAddress);
		 		
		 		$("#num").html(arr[0].baberWorkExperience);
		 		
		 		
		 		
		 		$("#service_standard").html(arr[0].baberService);
		 		
		 		$("#service_intro").html(arr[0].baberSelfIntroduce);
		 		
		 		var professional=0;
		 		var serviceAttitude=0;
		 		var onTime=0;
		 		var star=0;
		 		for (var i = 0; i < evaluateData.length; i++)
		 			{
		 			professional+=evaluateData[i].professionalSkillScore;
		 			serviceAttitude+=evaluateData[i].serviceAttitudeScore;
		 			onTime+=evaluateData[i].onTimeScore;
		 			}
		 		professional=professional/evaluateData.length;
		 		serviceAttitude=serviceAttitude/evaluateData.length;
		 		onTime=onTime/evaluateData.length;
		 		star=Math.round((professional+serviceAttitude+onTime)/3.0);
		 		//alert(star);
		 		
		 		document.getElementById('star').value=star;
		 		$("#professional").html(professional);
		 		$("#attitude").html(serviceAttitude);
		 		$("#ontime").html(onTime);
		 		
		 		//$("#star").html(star);
		 		//$(".star").append($div3);
		 		//onmouseover="this.width=150;this.height=180;" onMouseOut="this.width=120; this.height=160"
		 		//onmouseover="ShowFloatingImage(this, 120, 180);"
		 		
				for (var i = 0; i < arr.length; i++) {
				var $div3 = $('<li>'
                        +'<img id="workimg" src="'
                        +arr[i].hairPhotoUrl
                        +'"/>'
                        +'</li>');
				$(".worksimg ul").append($div3);
				}
				
				for (var i = 0; i < evaluateData.length; i++) {
				var $div4 = $('<div class="list">'
                        +'<div class="customer">'
                        +'<div class="img">'
                        +'<img src="'
                        +evaluateData[i].userPhotoUrl
                        +'"/></div>'
                        +'<div class="listtxt">'
                        +'<p>'
                        +'<span class="name">'
                        +evaluateData[i].userName
                        +'</span><span class="appraisel">好评</span><span class="listtime">'
                        +evaluateData[i].evaluateTime
                        +'</span></p><p><span class="classdinfo">专业<span class="num">'
                        +evaluateData[i].professionalSkillScore
                        +'</span>|态度<span class="num">'
                        +evaluateData[i].serviceAttitudeScore
                        +'</span>|守时  <span class="num">'
                        +evaluateData[i].onTimeScore
                        +'</span>|</span></p>'
                        +'<p id="evaluateContent" class="txt">'
                        +evaluateData[i].evaluateContent
                        +' </p></div>'
                        +' </div>'
                        +' </div>');
				$("#appraisedlist").append($div4);
				}
				
				$(".price").html('￥'+arr[0].servicePrice);
				
				$("#hairdesign").html('<option value="发型设计" style="background:white">发型设计</option>'
						+'<option value="普通理发" style="background:white">普通理发</option>');
				
				$(".time").html('<option value="9:00-10:00" style="background:white">9:00-10:00</option>'
						+'<option value="10:00-11:00" style="background:white">10:00-11:00</option>'
						+'<option value="11:00-12:00" style="background:white">11:00-12:00</option>'
						+'<option value="14:00-15:00" style="background:white">14:00-15:00</option>'
						+'<option value="15:00-16:00" style="background:white">15:00-16:00</option>'
						+'<option value="16:00-17:00" style="background:white">16:00-17:00</option>');
		 	},
		 	error :function(XMLHttpRequest, textStatus)
		 	{
		 		alert("error");
		 	}
		 })	
 }

