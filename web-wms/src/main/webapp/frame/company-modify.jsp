<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.company.modify.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/baseinfo.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>">	
<form id="companyForm" name="companyForm">
	 <table>
		<tr>
			<td width="20%" class="label"><s:text name="company.ou.node.code"/>:</td>
			<td width="30%"><s:property value="ou.code"/></td>
			<td width="20%" class="label"><s:text name="company.ou.node.type"/>:</td>
			<td width="30%"><s:property value="ou.ouType.displayName"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="company.ou.node.name"/>:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='ou.name'/>" name="ou.name"/></td>
			<td width="20%" class="label"><s:text name="company.taxno"/>:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="50" mandatory="true" value="<s:property value='company.taxNo'/>" name="company.taxNo" /></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="company.bankname"/>:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="255" mandatory="true" value="<s:property value='company.bankName'/>" name="company.bankName"/></td>
			<td width="20%" class="label"><s:text name="company.bankaccount"/>:</td>
			<td width="30%"><input loxiaType="input" trim="true" maxlength="50" mandatory="true" value="<s:property value='company.bankAccount'/>" name="company.bankAccount" /></td>
		</tr>
		
		<tr>
			<td width="20%" class="label"><s:text name="company.k3Code" />:</td>
			<td><input loxiaType="input" trim="true" value="<s:property value='company.k3Code'/>"   mandatory="true" name="company.k3Code"/></td>
			<td colspan="2"></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="company.legalrep" />:</td>
			<td colspan="3"><input loxiaType="input" trim="true" maxlength="50" value="<s:property value='company.legalRep'/>" name="company.legalRep"/></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="company.scopeofbusiness"/>:</td>
			<td colspan="3"><textarea loxiaType="input" trim="true" maxlength="1000" name="company.scopeOfBusiness"><s:property value='company.scopeOfBusiness'/></textarea></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="company.comment"/>:</td>
			<td colspan="3"><textarea loxiaType="input" trim="true" maxlength="50" name="ou.comment" ><s:property value="ou.comment"/></textarea></td>			
		</tr>
	</table>
</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="COsaveBtn"><s:text name="button.save"/></button>
	</div>
	  
</body>
</html>
