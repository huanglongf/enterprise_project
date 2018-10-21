<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
	<head>
	<% String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"; %>
	<script src="<%=basePath %>jquery/jquery-2.0.3.min.js" type="text/javascript"></script>
		<style>
		.max{width:100%;height:auto;}
		.min{width:100px;height:auto;}
		</style>
		
	<script>
		$(function(){
		$('#img').click(function(){
		
		$(this).toggleClass('min');
		$(this).toggleClass('max');
		});
		});
	</script>
</head>
	<body style="margin:0px;text-align: center">
		<img id="img" alt="" src="<%=basePath %>file/newer.png" >
	</body>
</html>
