<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<%@ include file="yuriy.jsp" %>
	<link rel="stylesheet" href="${root}css/jquery.mobile-1.3.2.min.css">
	<link rel="stylesheet" href="${root}css/ui-dialog.css">
	<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
	<script src="${root}js/dialog.js"></script>
	<script src="${root}js/dialog-min.js"></script>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>宝尊电商</title>
		
		<style type="text/css">
		.is-Transformed {   
			  width: 50%;  
			  margin: auto;  
			  position: absolute;  
			  top: 50%; left: 50%;  
			  -webkit-transform: translate(-50%,-50%);  
			      -ms-transform: translate(-50%,-50%);  
			          transform: translate(-50%,-50%);  
			}
		</style>
	</head>

	<body>
		
		<form method="post" action="">
			<div  data-role="page" id="pageone" style="vertical-align:middle;" align="center">
				<div data-role="header" data-theme="b" align="center">
					<div style="margin-top: 10px;margin-bottom: 10px;"><img alt="log" src="${root}img/baozun.png" style="height: 60px;width: 160px;"></div>
				</div>
				<div class='is-Transformed'>
					<div style="width: 400px;">
						<h3>用户名:</h3><input type="text" name="username" id="username" style="width:60;overflow-x:visible;overflow-y:visible;" tabindex="1"/>
					</div>
					<br>
		   			<div style="width: 400px;">
			      		<h3>密码:</h3><input type="password" name="password" id="password" tabindex="1"/>
					</div>
					<div style="width: 400px;">
			      		<h3>地点:</h3>
			      		<select id="addressInfor">
			      			<option value = "">全部</option>
			      		</select>
					</div>
					<br>
					<span id="msg" name="msg" style="">${message}</span>
					<br>
					<div style="width: 360px;">
		     			<button type="button" onclick="sub();" data-theme="b" tabindex="3">登入</button> 
		     		</div>
				</div>
				<div data-role="footer"  data-theme="b" data-position="fixed">
					<h5>Copyright © Baozun Inc.</h5>
				</div>
			</div>
		</form>
	</body>
	<script type="text/javascript">
	$(function(){//获取登录地点和名称
			$.ajax({
				url : "${root}control/addressControl/findAllInfor.do",
				type : "POST",
				dataType : "json",
				success : function(data) {
					$("#addressInfor option:gt(0)").remove();
					var addressInfor = data.addressInforLists;
					for (var i = 0; i < addressInfor.length; i++) {
						var addressName = addressInfor[i].addressName;
						var addressCode = addressInfor[i].addressCode;
						var printerIp = addressInfor[i].printerIp;
						if(addressCode != "" && addressName != ""){
							$("#addressInfor").append("<option value='" + addressName + '——' + addressCode + '——' + printerIp +"'>" + addressName + "——" + addressCode + "——" + printerIp +"</option>")
						}
					}
				}
		});
	});
	
	
	function sub(){
		$("#msg").html("");
		var username = $("#username").val();
		var password = $("#password").val();
		if (username == null || username == undefined || username == ''){
			showModal01("用户名为空!");
	    	document.getElementById("username").focus();
	    	return;
	    }else if (password == null || password == undefined || password == ''){
	    	showModal01("密码名为空!");
	    	document.getElementById("password").focus();
	    	return;
	    }else{
		var addressInfor = $("#addressInfor option:selected").val();
		if(addressInfor == null || addressInfor == ""){
			showModal01("请选择地点后进入！")
			return;
		  }
	    }
			$.ajax({  
				type: "POST",  
				url: root+"control/userController/login.do?",  
				//我们用text格式接收  
				//dataType: "text",   
				//json格式接收数据  
				dataType: "json",  
				data: "username="+$("#username").val()+"&password="+$("#password").val()+"&addressInfor="+addressInfor,
				success: function (data) {  
					if(data.code==200){
						window.location.href="${root}control/mainController/main_list.do";
					}else{
						$("#msg").html(data.message);
					}
				}  
			}); 
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
	
	function showModal(content,ofunc) {
  		if(ofunc == null || ofunc == ""){
  			var ofunc = function(){};
  		}
  		var d = dialog({
  			title : '提示',
  			content : content,
  			lock : true,
  			ok : ofunc,
  			cancel : function () {}
  		});
  		d.showModal();
  	}
	function showModal01(content) {
  		var d = dialog({
  			title : '提示',
  			content : content,
  			lock : true,
  			ok : function () {}
  		});
  		d.showModal();
  	}
	</script>
</html>