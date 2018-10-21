<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script type="text/javascript">
	function upper_next(){
		var localhost_code = $("#localhost_code").val();
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
			    	window.location.href="${root}control/newMainController/setLocalhost.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val();
			    }
			}else{
				$("#msg").html("长度不能超过20位!");
				playMusic();
			}
		}
	};
	function back(){
		var num = $("#num").val();
		if(num!=0){
			var a = confirm("还有未操作完的数据!");
			if(a){
				window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=2";
			}
		}
	}
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#localhost_code").keydown(function(e){
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
				<td width="40%"><span style="font-size: 12px;">WMS|上架(逐件)</span><div id="play-frame-2"></div></td>
				<td><span style="font-size: 11px;">WelCome,[${session_employee.username }]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${root}control/userController/outLogin.do">注销</a></span></td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="font-size: 13px;">容器号:[${container_code}]</span>
					<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
					<input type="hidden" id="container_code" name="container_code" value="${container_code}" />
					<input type="hidden" id="num" name="num" value="${num}" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="font-size: 13px;">库&nbsp;&nbsp;位</span><input type="text" id="localhost_code" name="localhost_code" value="" />
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
