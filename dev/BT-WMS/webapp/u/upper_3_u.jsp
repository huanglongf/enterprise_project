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
	function upper_next(){
		var sku = $("#sku").val();
		if (sku == null || sku == undefined || sku == ''){
	    	$("#msg").html("库位号为空!");
	    	document.getElementById("sku").focus();
	    }else{
			window.location.href="${root}control/mainController/scanning_sku.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val()+"&sku="+$("#sku").val();
	    }
	};
	function back(){
		window.location.href="${root}control/mainController/setContainer.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val();
	}
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#sku").keydown(function(e){
		    if(e.keyCode==13) {
		    	upper_next();
		    }
		});
	});
</script>
<body>
	<div data-role="page" id="pageone">
		<div data-role="header" data-position="fixed">
			<h1>WMS|上架(逐件)</h1>
		</div>
		<br>
		<br>
		<div align="center"><span id="msg" style="color: red;">${message}</span></div>
		<div style="width: 400px;">
			<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
			<input type="hidden" id="container_code" name="container_code" value="${container_code}" />
			<input type="hidden" id="localhost_code" name="localhost_code" value="${localhost_code}" />
			
			<div style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容器号:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -20px;">[${container_code}]</div>
			<div style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库位号:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -20px;">[${localhost_code}]</div>
			<br>
			<div style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已上架 (${upper_number}) -容器内 (${container_number})</div>
		</div>
		<div style="width: 400px;">
			<div style="margin-left: 10px;margin-top: 25px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SKU :</div>
			<div style="margin-left: 140px;width: 200px;margin-top: -35px;"><input type="text" id="sku" name="sku" value="" /></div>
		</div>
		<div data-role="footer" style="text-align: center;" data-position="fixed">
			<div data-role="footer">
			  <button data-theme="a" onclick="upper_next();"><span style="font-size: 20px;">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</span></button>&nbsp;&nbsp;&nbsp;&nbsp;
			  <button data-theme="a" onclick="back();"><span style="font-size: 20px;">&nbsp;&nbsp;结&nbsp;&nbsp;束&nbsp;&nbsp;</span></button>&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
</body>
</html>
