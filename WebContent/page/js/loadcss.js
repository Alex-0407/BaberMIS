// 动态加载外部css样式 
// 动态加载外部css样式 
//if( flag ){ 
//loadCss( "css/base.css" ); 
//}; 
function loadCss(url){ 
var link = document.createElement( "link" ); 
link.type = "text/css"; 
link.rel = "stylesheet"; 
link.href = url; 
document.getElementsByTagName( "head" )[0].appendChild(link); 
}; 