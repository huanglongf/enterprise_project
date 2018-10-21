<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="label.auth.user.title"/></title> 
<script type="text/javascript" src="<s:url value='/scripts/frame/user-batch-manage.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>">	
<form name="userBatchForm" id="userBatchForm">
	<table>
	  <tr>
		<td width="20%"><span class="label"><s:text name="label.auth.target.usergroup"/></span></td>
		<td width="20%">
			<s:select id="userGroupId"  list="userGroupList" mandatory="true" loxiaType="select" listKey="id" listValue="name" key="name" headerKey="0" headerValue="请选择">
			</s:select>
		</td>
		<td width="20%"><span class="label"><s:text name="label.auth.batch.change.userEnabled"/></span></td>
		<td width="20%">
			<s:select id="isEnabled" loxiaType="select" name="addList(#).isAvailable" class="isAvailable" list="#request.statusOptionList" listKey="optionKey" listValue="optionValue"></s:select>
		</td>	
		<td width="20%" align="right">
			<div class="buttonDivWidth">
				<button type="button" loxiaType="button" class="confirm" id="changeBatch"><s:text name="button.batch.change"/></button>
			</div>
		</td>
	  </tr>
   </table>	
	<p>&nbsp;</p>
	<table>
	  <tr>
		<td width="20%"><span class="label"><s:text name="label.auth.user.operationunit.type"/></span></td>
		<td width="30%">
			<s:select id="OperationUnitTypeId" onchange="return selectOperationUnitType(this);" list="operationUnitTypeList" mandatory="true" loxiaType="select" listKey="id" listValue="displayName" key="addOuType" headerKey="0" headerValue="请选择">
			</s:select>
		</td>
		<td width="20%"></td>
		<td width="30%"></td>		
	  </tr>
	  <tr>
		<td><span class="label"><s:text name="label.auth.user.operationunit"/></span></td>
		<td>
			<select id="OperationUnitId" loxiaType="select" mandatory="true">
			</select>
		</td>
		<td><span class="label"><s:text name="label.auth.user.role"/></span></td>
		<td>
			<select id="roleId" loxiaType="select" mandatory="true">
			</select>
		</td>		
	  </tr>
   </table>	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm"  refattribute="add" id="addRole" ><s:text name="button.batch.grant"/></button>
		<button type="button" loxiaType="button" class="confirm"  refattribute="remove" id="removeRole"><s:text name="button.batch.remove"/></button>
	</div>
</form>	
	<table width="50%">
	  <thead>
		  <tr>
			<td class="label" width="5%"><s:text name="label.user.batch.operation" /></td>
			<th class="label" width="8%"><s:text name="label.user.usergroup.name" /></td>
			<th class="label" width="5%"><s:text name="label.auth.user.operationunit" /></td>
			<th class="label" width="5%"><s:text name="label.auth.user.role" /></td>
			<th class="label" width="8%"><s:text name="label.user.operation.description" /></td>
		  </tr>
	  </thead>
	  <tbody id="rolesInfoTable">
		  <tr>
		  	<td></td>
		  	<td></td>
		  	<td></td>
		  	<td></td>
		  	<td></td>
		  </tr>
	  </tbody>
   </table>	
</body>
</html>
