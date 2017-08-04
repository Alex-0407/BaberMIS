
 /*--------------------实现2(返回 $_GET 对象, 仿PHP模式)----------------------*/
 var $_GET = (function(){
     var url = window.document.location.href.toString();
     var u = url.split("?");
     if(typeof(u[1]) == "string"){
         u = u[1].split("&");
         var get = {};
         for(var i in u){
             var j = u[i].split("=");
             get[j[0]] = j[1];
         }
         return get;
     } else {
         return {};
     }
 })();
  
 /*第2种方式, 使用时, 可以直接 $_GET['get参数'], 就直接获得GET参数的值*/