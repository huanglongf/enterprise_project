<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script type="text/javascript">
	function lower_next(){
		var localhost_code = $("#localhost_code").val();
		if(localhost_code.indexOf("-") < 0 ){
			$("#msg").html("库位号错误!");
			playMusic();
		}else{
			$("#localhost_code").attr("disabled","disabled");
			if(localhost_code.length<20){
				if (localhost_code == null || localhost_code == undefined || localhost_code == ''){
			    	$("#msg").html("库位号为空!");
			    	$("#localhost_code").removeAttr("disabled");
			    	document.getElementById("localhost_code").focus();
			    	playMusic();
			    }else{
			    	window.location.href="${root}control/mainController/setLocalhost.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val();
			    }
			}else{
				$("#msg").html("长度不能超过20位!");
				playMusic();
			}
		}
	};
	function back(){
		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=1";
	}
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#localhost_code").keydown(function(e){
		    if(e.keyCode==13) {
		    	lower_next();
		    }
		});
		if($("#msg").html()!=''){
			playMusic();
		}
	});
	function playMusic(){
		var box = document.getElementById('play-frame-2');
		var str = "<embed allowFullScreen='true' id='embedid' hidden='true' quality='high'  src='<%=basePath %>2.wav' ></embed>";
		box.innerHTML = str;
	};
</script>
<body>
	<div align="center">
		<table style="width: 280px;">
			<tr>
				<td width="40%"><span style="font-size: 12px;">WMS|下架(逐件)</span><div id="play-frame-2"></div></td>
				<td><span style="font-size: 11px;">WelCome,[${session_employee.username }]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${root}control/userController/outLogin.do" >注销</a></span></td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="font-size: 12px;">容器号:[${container_code}]</span>
					<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
					<input type="hidden" id="container_code" name="container_code" value="${container_code}" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<h5>库&nbsp;&nbsp;位</h5><input type="text" id="localhost_code" name="localhost_code" value="" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span id="msg" style="color: red;font-size: 10px;">${message}</span></td>
			</tr>
			<tr>
				<td colspan="2">
					<button data-theme="a" onclick="lower_next();">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</button>&nbsp;&nbsp;&nbsp;&nbsp;
				  	<button data-theme="a" onclick="back();">&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
