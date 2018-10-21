<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script type="text/javascript">
	function upper_next(){
		var container_code = $("#container_code").val();

    	$("#container_code").attr("disabled","disabled");
		if(container_code.length>25){
			$("#msg").html("容器号超长!");
			playMusic();
		}else{
			window.location.href="${root}control/mainController/setContainer.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val();
		}
	};
	function back(){
		window.location.href="${root}control/mainController/main_list.do"
	}
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#container_code").keydown(function(e){
		    if(e.keyCode==13) {
		    	upper_next();
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
				<td width="40%"><span style="font-size: 13px;">WMS|上架(逐件)</span><div id="play-frame-2"></div></td>
				<td><span style="font-size: 12px;">WelCome,[${session_employee.username }]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${root}control/userController/outLogin.do">注销</a></span></td>
			</tr>
			<tr>
				<td colspan="2">容器号:
				<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="text" id="container_code" name="container_code" />
					<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span id="msg" style="color: red;font-size: 10px;">${message}</span></td>
			</tr>
			<tr>
				<td colspan="2"><br>
					<button data-theme="a" onclick="upper_next();">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</button>&nbsp;&nbsp;&nbsp;&nbsp;
				  	<button data-theme="a" onclick="back();">&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
