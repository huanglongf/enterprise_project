<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script type="text/javascript">
function sub(){
	$("#msg").html("");
	var username = $("#username").val();
	var password = $("#password").val();
	if (username == null || username == undefined || username == ''){
    	$("#msg").html("用户名为空!");
    	document.getElementById("username").focus();
    	playMusic();
    }else if (password == null || password == undefined || password == ''){
    	$("#msg").html("密码名为空!");
    	document.getElementById("password").focus();
    	playMusic();
    }else{
		$.ajax({  
			type: "POST",  
			url: root+"control/userController/login.do?",  
			//我们用text格式接收  
			//dataType: "text",   
			//json格式接收数据  
			dataType: "json",  
			data: "username="+$("#username").val()+"&password="+$("#password").val(),
			success: function (data) {  
				if(data.code==200){
					window.location.href="${root}control/mainController/main_list.do";
				}else{
					$("#msg").html(data.message);
					playMusic();
				}
			}  
		}); 
    }
}
$(function(){
	$("#username").keydown(function(e){
	    if(e.keyCode==13) {
	    	$("#msg").html("");
			var username = $("#username").val();
			if (username == null || username == undefined || username == ''){
		    	$("#msg").html("用户名为空!");
		    	document.getElementById("username").focus();
		    	playMusic();
		    }else{
		    	document.getElementById("password").focus();
		    }
	    }
	});
	$("#password").keydown(function(e){
	    if(e.keyCode==13) {
			sub();
	    }
	});
});
$(document).ready(function(){$('input[type=text]:first').focus();});
function playMusic(){
	var box = document.getElementById('play-frame-2');
	var str = "<embed allowFullScreen='true' id='embedid' hidden='true' quality='high'  src='<%=basePath %>2.wav' ></embed>";
	box.innerHTML = str;
};
</script>
<body>
	<div align="center">
		<span style="font-size: 20px;">[WMS货物转移]</span>
		<br>
		<div align="center"><span id="msg" style="color: red;">${message}</span><div id="play-frame-2"></div></div>
		<br>
		<div>
			<div >用户名:<input type="text" id="username" name="username" /></div><br>
			<div>密&nbsp;&nbsp;&nbsp;码:<input id="password" name="password" type="password" /></div>
			<br>
			<button data-theme="a" onclick="sub();">&nbsp;&nbsp;登&nbsp;&nbsp;录&nbsp;&nbsp;</button>
		</div>
	</div>
</body>
</html>
