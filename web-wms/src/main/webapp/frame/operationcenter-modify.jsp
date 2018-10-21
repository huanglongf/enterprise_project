<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.operationcenter.modify.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/baseinfo.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>">
<form id="operationcenterForm" name="operationcenterForm">	
	 <table>
		<tr>
			<td width="20%" class="label"><s:text name="operationcenter.ou.node.code"/>:</td>
			<td width="30%"><s:property value="ou.code"/></td>
			<td width="20%" class="label"><s:text name="operationcenter.ou.node.type"/>:</td>
			<td width="30%"><s:property value="ou.ouType.displayName"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="operationcenter.ou.name" />:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='ou.name'/>" name="ou.name"/></td>
			<td width="20%" class="label"><s:text name="operationcenter.principal.name"/>:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="50" mandatory="true" value="<s:property value='operationCenter.pic'/>" name="operationCenter.pic"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="operationcenter.principal.contact"/>:</td>
			<td colspan="3"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='operationCenter.picContact'/>" name="operationCenter.picContact"/></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="operationcenter.description"/>:</td>
			<td colspan="3"><textarea loxiaType="input" trim="true" maxlength="255" name="ou.comment"><s:property value="ou.comment"/></textarea></td>			
		</tr>
	</table>
</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="OCsaveBtn"><s:text name="button.save"/></button>
	</div>

</body>
</html>
