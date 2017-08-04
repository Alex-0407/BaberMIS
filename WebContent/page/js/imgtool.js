function GetAbsPosition(obj) 
{ 
var curleft = 0, curtop = 0; 
do { 
curleft += obj.offsetLeft; 
curtop += obj.offsetTop; 
} while (obj = obj.offsetParent); 
return [curleft,curtop]; 
} 
function ShowFloatingImage(image, width, height) 
{ 
var id = "test"; 
var newdiv = document.getElementById(id); 
if(newdiv == null) 
{ 
	newdiv = document.createElement('div'); 
	newdiv.setAttribute('id',id); 
	newdiv.setAttribute('onmouseout', "HideElement('"+id+"');"); 
	document.getElementById("worksimg").appendChild(newdiv); 
} 
newdiv.innerHTML = '<img src='+image.src+ ' width='+(image.width + width) + ' height=' + (image.height + height) + ' />'; 
var absPos = GetAbsPosition(image); 
newdiv.style.position = "relative"; 
test.style.css = "top:100px;left:50px;float:center"; 
newdiv.style.posLeft = absPos[0]+image.width/2; 
newdiv.style.posTop = absPos[1]+image.height/2; 
newdiv.style.display="block";
} 
function HideElement(id) 
{ 
var elem = document.getElementById(id); 
elem.style.display="none"; 
} 