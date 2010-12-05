window.addEventListener("load",function(){
	var color=[
			"#",
			("00"+(Math.random()*0x99|0).toString(16)).slice(-2),
			("00"+(Math.random()*0x99|0).toString(16)).slice(-2),
			("00"+(Math.random()*0x99|0).toString(16)).slice(-2),
			""].join("");
	var $=function(id){
		return document.getElementById(id);
	};
	var disp=function(msg,color){
		var parent=$("messages");
		var elem=document.createElement("div");
		elem.textContent=msg;
		if(parent.hasChildNodes()){
			parent.insertBefore(elem,parent.firstChild);
		}else{
			parent.appendChild(elem);
		}
		elem.style.color=(color||"#000000");
	};
	var sendMessage=function(){
		var message=$("message");
		if(message.value==""){return;}
		ws.send(JSON.stringify({"message":message.value,"color":color}));
		message.value="";
		message.focus();
	};
	var protocol=(location.protocol=="https:")?"wss":"ws";
	var host=/^https?:$/.test(location.protocol)?location.host:"localhost:40320";
	var url=protocol+"://"+host+"/";
	var ws=new WebSocket(url);
	ws.addEventListener("open",function(){
		$("send").addEventListener("click",sendMessage,false);
		disp("connect","#ff0000");
	},false);
	ws.addEventListener("close",function(){
		$("send").removeEventListener("click",sendMessage,false);
		disp("disconnect","#ff0000");
	},false);
	ws.addEventListener("message",function(evt){
		var data=JSON.parse(evt.data);
		disp("> "+(data.message||""),data.color);
	},false);
	window.addEventListener("unload",function(){
		ws.close();
	},false);
	$("message").focus();
},false);
