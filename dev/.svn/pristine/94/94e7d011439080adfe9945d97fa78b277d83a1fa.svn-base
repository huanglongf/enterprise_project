<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="https://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<script type="text/javascript">
function sub(){
	$("#msg").html("");
	var username = $("#username").val();
	var password = $("#password").val();
	if (username == null || username == undefined || username == ''){
    	$("#msg").html("用户名为空!");
    	document.getElementById("username").focus();
    }else if (password == null || password == undefined || password == ''){
    	$("#msg").html("密码名为空!");
    	document.getElementById("password").focus();
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
</script>
<body>
	<div data-role="page" id="pageone">
		<div data-role="header" data-position="fixed" align="center">
			<span style="font-size: 30px;">[WMS货物转移]</span>
		</div>
		<br>
		<div align="center"><span id="msg" style="color: red;">${message}</span></div>
		<br>
		<div style="width: 400px;">
			<div style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -35px;"><input type="text" id="username" name="username" /></div><br>
			<div style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -35px;"><input id="password" name="password" type="password" /></div>
		</div>
		<div data-role="footer" style="text-align: center;" data-position="fixed">
			<div data-role="footer">
			  <button data-theme="a" onclick="sub();"><span style="font-size: 30px;">&nbsp;&nbsp;登&nbsp;&nbsp;录&nbsp;&nbsp;</span></button>&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
</body>
</html>
