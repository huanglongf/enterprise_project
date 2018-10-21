<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<%@ include file="yuriy.jsp" %>
	<link rel="stylesheet" href="${root}css/jquery.mobile-1.3.2.min.css">
	<script src="<%=basePath %>js/jquery-1.8.3.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
	<script type="text/javascript" src="${root}js/jquery.qrcode.min.js"></script> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>宝尊电商</title>
		<script type="text/javascript">
			function sign() {
				window.location.href="${root}control/mainController/sign.do";
			}
			function sign_out() {
				window.location.href="${root}control/mainController/sign_out.do";
			}
			function detailed_list() {
				window.location.href="${root}control/mainController/detailed_list.do";
			}
			function main_list() {
				window.location.href="${root}control/mainController/main_list.do";
			}
			function out_login() {
				window.location.href="${root}control/userController/outLogin.do";
			}
			function address() {
				window.location.href="${root}control/addressControl/addressList.do";
			}
			$(document).ready(function(){
				document.getElementById("butA").focus();
			});
		</script>
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
			a:link{
				text-decoration:none;
			}
		</style>
	</head>

	<body>
		<div data-role="page" id="pageone" style="vertical-align:middle;" align="center">
			<div data-role="header" data-theme="b">
				<div style="height: 60px;">
					<div style="display: inline;float: left;"><h3>&nbsp;&nbsp;[Visitor information management system]访客信息管理系统</h3></div>
					<div style="display: inline;float: right;margin-right: 10px;"><img alt="log" src="${root}img/baozun.png" style="height: 60px;width: 160px;"></div>
					<div style="display: inline;float: right;margin-right: 10px;margin-top: 25px;">欢迎,${session_user.name}｜<a href="javascript.void();" onclick="out_login();">[退出]</a></div>
				</div>
			</div>
			<div class="is-Transformed" style="width: 1200px;">
				<div style="display: inline;float: left;margin-left: 120px;">
					<a id="butA" name="butA" href="javascript:void(0);" onclick="address();">
						<img alt="log" src="${root}img/qd_2.jpg" style="height: 200px;width: 180px;"><br>ADDRESS
					</a>
				</div>
				<div style="display: inline;float: left;margin-left: 130px;">
					<a  href="javascript:void(0);" onclick="sign();">
						<img alt="log" src="${root}img/qd_1.jpg" style="height: 200px;width: 180px;"><br>Check In
					</a>
				</div>
				<div style="display: inline;float: left;margin-left: 115px;">
					<a href="" onclick="sign_out();">
						<img alt="log" src="${root}img/zx.jpg" style="height: 200px;width: 200px;"><br>Check Out
					</a>
				</div>
				<div style="display: inline;float: right;">
				<a href="" onclick="detailed_list();"> 
					<img alt="log" src="${root}img/xx.jpg" style="height: 200px;width: 170px;"><br>History
				</a>
				</div>
			</div>
			<div data-role="footer"  data-theme="b" data-position="fixed">
				<h5>Copyright © Baozun Inc.</h5>
			</div>
		</div>
	</body>
</html>