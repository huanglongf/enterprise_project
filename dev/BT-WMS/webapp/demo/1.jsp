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
	function ma(){
		window.location.href="${root}2.jsp"
	};
	function ret(){
		window.location.href="${root}main.jsp"
	}
</script>
<body>
	<div data-role="page" id="pageone">
		<div data-role="header" data-position="fixed">
			<h1>WMS|上架(逐件)</h1>
		</div>
		<br>
		<br>
		<div style="width: 400px;">
			<div style="margin-left: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扫描容器号:</div>
			<div style="margin-left: 150px;width: 200px;margin-top: -35px;"><input type="text" id="rqh" name="rqh" /></div>
		</div>
		<div data-role="footer" style="text-align: center;" data-position="fixed">
			<div data-role="footer">
			  <button data-theme="a" onclick="ma();"><span style="font-size: 30px;">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</span></button>&nbsp;&nbsp;&nbsp;&nbsp;
			  <button data-theme="a" onclick="ret();"><span style="font-size: 30px;">&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;</span></button>
			</div>
		</div>
	</div>
</body>
</html>
