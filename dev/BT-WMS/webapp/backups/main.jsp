<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
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
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#menuid").keydown(function(e){
		    if(e.keyCode==13) {
		    	var menuid=$("#menuid").val();
		    	if(menuid==1){
			    	lower_next();
		    	}else if(menuid=2){
		    		upper_next();
		    	}
		    }
		});
	});
</script>
<body>
	<div align="center">
		<table style="width: 280px;">
			<tr align="center">
				<td width="40%">WMS|菜单</td>
				<td><span style="font-size: 12px;">WelCome,[${session_employee.username }]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${root}control/userController/outLogin.do">注销</a></span></td>
			</tr>
			<tr align="center">
				<td colspan="2">菜单列表</td>
			</tr>
			<tr align="center">
				<td colspan="2"><a href="${root}control/mainController/toSetContainer.do?opertion_type=1" data-icon="search">1.下架(逐件)</a></td>
			</tr>
			<tr align="center">
				<td colspan="2"><a href="${root}control/mainController/toSetContainer.do?opertion_type=2" data-icon="search">2.上架(逐件)</a></td>
			</tr>
			<tr align="center">
				<td colspan="2">输入菜单编号:<input id="menuid" name="menuid" type="text" style="width: 25px;"/></td>
			</tr>
		</table>
	</div>
</body>
</html>
