var baseUrl="http://localhost:8080/BaberMIS/";
function gettype()
{
	  var json={};
	  var request = $.ajax({
			type: "GET",  
			url:baseUrl+"admin/Type/GetTypeInfo", 
		 	data:json,
		 	dataType: "json",
		 	success :function(data)
		 	{
		 		 var type=data.type;
		 		 alert("type"+type);
		 	},
		 	error:function()
		 	{
		 		alert("error");
		 	}
	  })
}