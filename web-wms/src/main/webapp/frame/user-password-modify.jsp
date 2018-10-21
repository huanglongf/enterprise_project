<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.user.maintain"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-password-modify.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 	<span class="label">用户密码修改</span>
 	
	<form id="passwordform" name="passwordform" method="post" >
		<table width="50%">
			<tbody>
				<tr>
					<td class="label">原始密码：</td>
					<td><input name="usercmd.password" type="password" size="35"/></td>
				</tr>
				<tr>
					<td class="label">新密码：</td>
					<td><input name="usercmd.newPassword" type="password" size="35"/></td>
				</tr>
				<tr>
					<td class="label">确认密码：</td>
					<td><input name="usercmd.confirmPassword" type="password" size="35"/></td>
				</tr>
			</tbody>
		</table>
	</form>
	<button type="button" loxiaType="button" class="confirm" id="passwordmodify" style="margin: 20px 180px;"><s:text name="button.password.modify"/></button>
</body>
</html>