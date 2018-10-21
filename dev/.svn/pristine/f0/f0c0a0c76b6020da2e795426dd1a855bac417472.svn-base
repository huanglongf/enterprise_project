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
	function lower_next(){
		var localhost_code = $("#localhost_code").val();
		if (localhost_code == null || localhost_code == undefined || localhost_code == ''){
	    	$("#msg").html("库位号为空!");
	    	document.getElementById("localhost_code").focus();
	    }else{
	    	window.location.href="${root}control/mainController/setLocalhost.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val();
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
	});
</script>
<body>
	<div data-role="page" id="pageone">
		<div data-role="header" data-position="fixed">
			<h1>WMS|下架(逐件)</h1>
		</div>
		<br>
		<br>
		<div align="center"><span id="msg" style="color: red;">${message}</span></div>
		<div style="width: 400px;">
			<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
			<input type="hidden" id="container_code" name="container_code" value="${container_code}" />
			<div style="margin-left: 10px;margin-top: 30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容器号:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -20px;">[${container_code}]</div>
		</div>
		<div style="width: 400px;">
			<div style="margin-left: 10px;margin-top: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库&nbsp;&nbsp;位:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -35px;"><input type="text" id="localhost_code" name="localhost_code" /></div>
		</div>
		<div data-role="footer" style="text-align: center;" data-position="fixed">
			<div data-role="footer">
			  <button data-theme="a" onclick="lower_next();"><span style="font-size: 30px;">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</span></button>&nbsp;&nbsp;&nbsp;&nbsp;
			  <button data-theme="a" onclick="back();"><span style="font-size: 30px;">&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;</span></button>
			</div>
		</div>
	</div>
</body>
</html>
