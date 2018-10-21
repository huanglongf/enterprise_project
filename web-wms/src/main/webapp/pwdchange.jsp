<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><s:text name="title.password.change"/></title>
<link rel="stylesheet" type="text/css" href="<s:url value='css/redmond/jquery-ui-1.8.7.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='css/redmond/loxia-ui-1.2.custom.css' includeParams='none' encode='false'/>"/>
<link rel="stylesheet" type="text/css" href="<s:url value='css/main.css' includeParams='none' encode='false'/>"/>
<style>
	html,body {height: 100%; overflow: hidden;}
	body{text-align: center;}
	.label{font-size:  15px;}
</style>
<script type="text/javascript" src="<s:url value='scripts/main/jquery-1.4.4.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='scripts/main/jquery-ui-1.8.7.custom.min.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='scripts/main/jquery.bgiframe.min.js' includeParams='none' encode='false'/>"></script>

<script type="text/javascript" src="<s:url value='scripts/main/jquery.loxiacore-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='scripts/main/jquery.loxiainput-1.2.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='scripts/main/jquery.loxia.locale-zh-CN.js' includeParams='none' encode='false'/>"></script>
	
<script type="text/javascript"><!--
var $j = jQuery.noConflict();

$j(document).ready(function (){	
	loxia.init({debug: true, region: 'zh-CN'});	
});
--></script>
<script type="text/javascript" src="<s:url value='/scripts/pwdchange.js' includeParams='none' encode='false'/>"></script>
</head>
<body contextpath="<%=request.getContextPath() %>">
<div id="loginContainer">
	<form action="<%=request.getContextPath() %>/root/pwdchange.do" method="post" id="loginForm" name="loginForm">
	<div class="title">
		<span><s:text name="system.name"/></span>
	</div>
	<div class="content">
		<table width="70%">
			<caption class="label" style="text-align: center;">宝尊电商WMS用户密码修改：</caption>
			<tbody>
				<tr>
					<td width="20%" class="label">用户名：</td>
					<td width="30%"><input id="username" name="user.loginName" loxiatype="input" mandatory="true" trim="true" maxLength="20" max="20" tabindex='0'/></td>
				</tr>
				<tr>
					<td class="label">原始密码：</td>
					<td><input id="pwd" name="user.password" loxiatype="input" mandatory="true" trim="true" maxLength="20" max="20" tabindex='1'/></td>
				</tr>
				<tr>
					<td class="label">新密码：</td>
					<td><input id="newPwd" name="user.newPassword" type="password" loxiatype="input" trim="true" mandatory="true" maxLength="20" max="20" tabindex='2'/></td>
				</tr>
				<tr>
					<td class="label">确认密码：</td>
					<td><input id="confirmPwd" name="user.confirmPassword" type="password" loxiatype="input" trim="true" mandatory="true" maxLength="20" max="20" tabindex='3'/></td>
				</tr>
				<tr>
					<td class="label">验证码：</td>
					<td>
						<input id="validateCode" name="validateCode" type="text" loxiatype="input" trim="true" max="6" mandatory="true" style="width: 80px;"/>
						<img src="<%=request.getContextPath() %>/imagecode.do" alt="" width="150" height="60" id="imageCode" style="cursor: pointer;" title="看不清，请猛戳"/>
						<a href="#" onclick="refreshCode()">看不清楚，刷新</a>
					</td>
				</tr>
			</tbody>
		</table>
		<button id="btn_login" type="button" tabindex='5' class="confirm" style="width: 400px;">确定</button>
	</div>
	</form>
</div>
</body>
</html>