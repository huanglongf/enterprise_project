<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:property value="user.userName"/></title>
<style>
	body, .ui-widget { font:9pt Verdana, Arail,"Trebuchet MS", sans-serif;}		
	td.decimal { text-align: right;	padding-right: 4px;}
	/* div.ui-datepicker{ font-size: 11px;} */
	
	.label { text-align: right; padding-right: 4px; font-weight: bold; background-color: #efefef;}
	select.ui-loxia-default, input.ui-loxia-default, textarea.ui-loxia-default { width: 95%; }
</style>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-viewinfo.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="myform" name="myform" method="post" >
	<table>
	<input type="hidden" name="user.id" id="userid" value="<s:property value="user.id"/>"/>
		<tr>
			<td class="label" width="20%"><s:text name="label.user.loginname"/></td>
			<td width="30%"><s:property value="user.loginName"/></td>
			<td class="label" width="20%"><s:text name="label.user.iseffective"/></td>
			<td width="30%">
			<s:if test="%{user.isEnabled}">
			<s:text name="label.status.true"/>
			</s:if>
			<s:else>
			<s:text name="label.status.false"/>
			</s:else>
			</td>
		</tr>
		<tr>
			<td class="label" width="20%"><s:text name="label.user.username"/></td>
			<td width="30%"><s:property value="user.userName"/></td>
			<td class="label" width="20%"><s:text name="label.user.email"/></td>
			<td width="30%"><s:property value="user.email"/></td>
		</tr>
		
		<tr>
			<td class="label" width="20%"><s:text name="label.user.phone"/></td>
			<td width="30%"><s:property value="user.phone"/></td>
			<td class="label" width="20%"><s:text name="label.user.jobnumber"/></td>
			<td width="30%"><s:property value="user.jobNumber"/></td>
			
		</tr>
		
	</table>
	</form>
	<p></p>
	
	<table id="tbl-userlist"></table>
	<div id="pager9"></div> 
	

	<p></p>
	
</body>
</html>