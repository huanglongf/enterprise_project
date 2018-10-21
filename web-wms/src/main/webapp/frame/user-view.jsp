<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.user.view"/></title>
<style>
	body, .ui-widget { font:9pt Verdana, Arail,"Trebuchet MS", sans-serif;}		
	td.decimal { text-align: right;	padding-right: 4px;}
	/* div.ui-datepicker{ font-size: 11px;} */
	
	.label { text-align: right; padding-right: 4px; font-weight: bold; background-color: #efefef;}
	select.ui-loxia-default, input.ui-loxia-default, textarea.ui-loxia-default { width: 95%; }
</style>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-view.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
   <s:text id="pselect" name="label.please.select"/>
	<form id="myform" name="myform" method="post" >
	<table>
		<tr>
			<td class="label" ><s:text name="label.user.loginname"/></td>
			<td width="130"><input loxiaType="input" name="user.loginName" id="loginName" /></td>
			<td class="label" ><s:text name="label.user.username"/></td>
			<td width="130"><input loxiaType="input" name="user.userName" id="userName" /></td>
		</tr>
		<tr>
	        <td class="label" ><s:text name="label.user.email"/></td>
			<td width="130"><input loxiaType="input" name="user.email" id="email"  /></td>
			<td class="label"><s:text name="label.user.iseffective"/></td>
			<td width="130">
			<s:select list="statusOptionList" loxiaType="select" listKey="optionKey" listValue="optionValue"
			 id="isEnabled" name="user.memo" headerKey="" headerValue="%{pselect}"></s:select>
			</td>			
		</tr>
	
		<tr>
		   <td class="label" ><s:text name="label.user.phone"/></td>
			<td width="130"><input loxiaType="input"  name="user.phone" id="phone" /></td>
		  <td class="label" ><s:text name="label.user.jobnumber"/></td>
			<td width="130"><input loxiaType="input"  name="user.jobNumber" id="jobNumber" /></td>
				</tr>
			<tr>
		 <td class="label" ><s:text name="label.user.usergroup"/></td>
			<td width="130"><s:select list="userGroupList" loxiaType="select" listKey="id" 
			listValue="name" id="groupId" name="userGroup.id" headerKey="-1" headerValue="%{pselect}"></s:select>
	              </td>
		</tr>
		
	</table>
	</form>
	<p></p>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="create" onclick="searchUser()"><s:text name="button.query"/></button>
	</div>
	<div id="tbl-opb-line" >
	<table id="tbl-userlist"></table>
	<div id="pager9"></div> 
	
	</div>
	<p></p>
	<div class="buttonlist">
		<button type="button" id="button1" onclick="showInfoUser()" loxiaType="button" class="confirm"  targetId="frame-container" ><s:text name="button.view"/></button>
	</div>

</body>
</html>