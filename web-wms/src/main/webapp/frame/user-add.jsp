

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.add.user"/></title>
<style>
	body, .ui-widget { font:9pt Verdana, Arail,"Trebuchet MS", sans-serif;}		
	td.decimal { text-align: right;	padding-right: 4px;}
	/* div.ui-datepicker{ font-size: 11px;} */
	
	.label { text-align: right; padding-right: 4px; font-weight: bold; background-color: #efefef;}
	select.ui-loxia-default, input.ui-loxia-default, textarea.ui-loxia-default { width: 95%; }
</style>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/user-add.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
   <s:text id="pselect" name="label.please.select"/>
	<form id="myform" name="myform" method="post" >
	<table>
		<tr>
			<td class="label"><s:text name="label.user.loginname"/></td>
			<td width="130"><input loxiaType="input" name="user.loginName" id="loginName" mandatory="true" checkmaster="checkLoginName" maxlength="50"/></td>
			<td class="label"><s:text name="label.user.userfirstpassword"/></td>
			<td width="130"><input type="input" name="user.password" loxiaType="input" id="password" maxlength="20" mandatory="true" checkmaster="checkPassword"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.user.username"/></td>
			<td width="130"><input loxiaType="input" name="user.userName" id="userName" mandatory="true" maxlength="50" checkmaster="checkUserName"/></td>
			<td class="label"><s:text name="label.user.jobnumber"/></td>
			<td width="130"><input loxiaType="input"  name="user.jobNumber" id="jobNumber" mandatory="true" maxlength="10" checkmaster="checkJobNumber"/></td>
			
		</tr>
		
		<tr>		  
			<td class="label"><s:text name="label.user.email"/></td>
			<td width="130"><input loxiaType="input" name="user.email" id="email" mandatory="true" maxlength="100" checkmaster="checkEmailisNullorRule" /></td>
			<td class="label"><s:text name="label.user.phone"/></td>
			<td width="130"><input loxiaType="input"  name="user.phone" id="phone" maxlength="100" checkmaster="checkPhone" /></td>	
		</tr>
		<tr>
			<td class="label"><s:text name="label.user.remark"/></td>
			<td width="130"><textarea loxiaType="input" id="memo" name="user.memo" checkmaster="checkRemark"></textarea></td>
		</tr>
	</table>
	<p></p>
	<table id="tbl-userlist"></table>
		<table>
			<tr>
			<td class="label"><s:text name="label.user.outype"/></td>
			<td width="130">
			<s:select onchange="setFAQTwo(this)" list="opeUnitType" loxiaType="select" listKey="id" listValue="displayName" key="addOuType" headerKey="" headerValue="%{pselect}">			
			</s:select>
						</td>
			<td class="label"></td>
			<td width="130"></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.user.ou"/></td>
			<td width="130"><select id="OperationUnitId" loxiaType="select" mandatory="true">
					
					</select></td>
			<td class="label"><s:text name="label.user.role"/></td>
			<td width="130"><select id="roleId" loxiaType="select" mandatory="true">
						
					</select></td>
		</tr>
		<tr>
			<td class="label"><s:text name="lable.user.isdefault"/></td><td width="30%"><input type="checkbox" name="isDefinexxx" id="isDefinexxx" value ="1" checked="checked"></td>		
			<td colspan="2"><button type="button" loxiaType="button" onclick="baddUserRole()" class="confirm" ><s:text name="button.addrole"/></button>
			<button id="button1" type="button" loxiaType="button" onclick="delUserRole('true')" ><s:text name="button.deleteselectrole"/></button></td>
		</tr>
		
	</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="create" onclick="addUserandRole()"><s:text name="button.createuser"/></button>
	</div>
	
	</form>

</body>
</html>