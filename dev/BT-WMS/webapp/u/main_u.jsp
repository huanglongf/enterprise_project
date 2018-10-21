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
		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=1";
	};
	function upper_next(){
		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=2";
	};
	function out_login(){
		window.location.href="${root}control/userController/outLogin.do";
	}
</script>
<body>
	<div data-role="page" id="pageone">
		<div data-role="header" data-position="fixed">
		<h1>WMS货物转移</h1>
			<div data-role="navbar">
				<ul>
					<li><a href="javascript:void();" onclick="lower_next();" data-icon="search">1.下架(逐件)</a></li>
				</ul>
				<ul>
					<li><a href="javascript:void();" onclick="upper_next();" data-icon="search">2.上架(逐件)</a></li>
				</ul>
			</div>
		</div>
		
		<div data-role="footer" style="text-align: center;" data-position="fixed">
			<h3>WelCome,[Yuriy]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:void();" onclick="out_login();">注销</a></h3>
		</div>
	</div>
</body>
</html>
