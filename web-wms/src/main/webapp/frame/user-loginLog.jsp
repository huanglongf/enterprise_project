<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="/common/meta.jsp"%>
	<title><s:text name="title.sys.loginlog"/></title>
	<script type="text/javascript" src="<s:url value='/scripts/frame/user-loginlog.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	<style type="text/css">
		.label{ text-align:left;}
	</style>
</head>

<body contextpath="<%=request.getContextPath() %>">	 
	<div class="label"><s:text name="label.search.param"/></div>
	<form id="sysLogCommandForm">
	<table>
	  <tr>
		<td class="label"><s:text name="label.sys.loginlog.loginname"/></td>
		<td width="150"><input id="loginName" name="sysLoginLogCommand.loginName" loxiaType="input" trim="true" /></td>
		<td class="label"><s:text name="label.sys.loginlog.username"/></td>
		<td width="150"><input id="userName" name="sysLoginLogCommand.userName" loxiaType="input" trim="true" /></td>
		<td class="label"><s:text name="label.sys.loginlog.jobnumber"/></td>
		<td width="150"><input id="jobNumber" name="sysLoginLogCommand.jobNumber" loxiaType="input" trim="true" /></td>
	  </tr>
	  
	  <tr>
	    <td class="label"><s:text name="label.sys.loginlog.isenabled"/></td>		
		<td>
		<s:select list="statusOptionList" loxiaType="select" listKey="optionKey" 
					listValue="optionValue" id="isEnabled" name="sysLoginLogCommand.isEnabled" headerKey="" headerValue="-ALL-"></s:select>
		</td>
		<td class="label"><s:text name="label.sys.loginlog.usergroup"/></td>
		<td>
			<s:select list="userGroup" loxiaType="select" listKey="id" 
			listValue="name" id="groupId" name="sysLoginLogCommand.groupId" headerKey="" headerValue="-ALL-"></s:select>
		</td>
		<td colspan="2"></td>
	  </tr>
	  
	   <tr>
	    <td class="label"><s:text name="label.sys.loginlog.logintime"/></td>
		
		<td>
			<input id="loginTimeFrom" loxiaType="date" name="sysLoginLogCommand.loginTimeFrom1" showTime="true"/>
		</td>		
		 
		<td align="center" class="label">到</td>
				
		<td>
			<input id="loginTimeTo" loxiaType="date" name="sysLoginLogCommand.loginTimeTo1" showTime="true"/>
		</td>				
		<td colspan="2"></td>
	  </tr>
	</table>
	</form>
	<br/><br/>
	<div class="buttonlist buttonDivWidth">
		<button type="button" loxiaType="button" class="confirm" title="tip：<s:text name='tip.sys.loginlog.search'/>" id="reload"><s:text name="button.query"/></button>
	</div>
	
	<!--<div class="label"><s:text name="label.sys.loginlog.result"/></div>
	
	--><table id="tbl-userloginlist"></table>
	<div id="pager"></div>
</body>
</html>
