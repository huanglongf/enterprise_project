<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><s:text name="title.warehouse.transtype.maintain"/></title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="../scripts/frame/transtype-maintain.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
  <s:text id="pselect" name="label.please.select"/>
	<!-- 这里是页面内容区 -->
	<table id="tbl-systranstypelist"></table><br/>
	<table id="tbl-transtypelist"></table>
	<div id="editdiv" class="hidden">
	<h3><s:text name="label.warehouse.modifyTrType"/></h3>
	<form id="editForm" name="editForm">
	<table>			
	<input type="hidden" name="transactionType.id" id="id" value=""/>
		<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.code"/>：</td>
			<td width="30%"><input name="transactionType.code" id="code" loxiaType="input" mandatory="true" value="" maxlength="100" /></td>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.name"/>：</td>
			<td width="30%"><input name="transactionType.name" id="name" loxiaType="input" mandatory="true" value="" maxlength="100"/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.direction"/>：</td>
			<td width="30%"><s:select list="directionList" mandatory="true" loxiaType="select" listKey="optionKey" listValue="optionValue" name="transactionType.intDirection" id="intDirection" headerKey="" headerValue="%{pselect}">			
			</s:select>	</td>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.control"/>：</td>
			<td width="30%"><s:select list="controlList" mandatory="true" loxiaType="select" listKey="optionKey" listValue="optionValue" name="transactionType.intControl" id="intControl" headerKey="" headerValue="%{pselect}">	
			</s:select></td>
		</tr>	
		<tr>
			<td colspan="2" class="label"><input type="checkbox" name="transactionType.isInCost" id="isInCost" value="true"/><s:text name="label.warehouse.transactiontype.incost"/></td>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.status"/>：</td>
			<td width="30%"><s:select list="statusList" mandatory="true" loxiaType="select" listKey="optionKey" listValue="optionValue" name="transactionType.isAvailable" id="isAvailable" >	
			</s:select>
            </td>
		</tr>
		<tr>
            <td colspan="2" class="label"><input type="checkbox" name="transactionType.isSynchTaobao" id="isSynchTaobao" value="true"/><s:text name="label.warehouse.transactiontype.issynchtaobao"/></td>  
            <td colspan="2" class="label">&nbsp;</td>  
        </tr>
		<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.description"/>：</td>
			<td colspan="3"><textarea loxiaType="input" name="transactionType.description" id="description" checkmaster="checkDescribe"></textarea></td>			
		</tr>	
	</table>	
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="modifyTransactionType"><s:text name="button.save"/></button>
		<button type="button" loxiaType="button" id="btn-edit-cancel"><s:text name="button.cancel"/></button>
	</div>
	</div>
	<div id="newdiv">
	<h3><s:text name="label.warehouse.createTrType"/></h3>
	<form id="addForm" name="addForm">
	<table>			
			<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.code"/>：</td>
			<td width="30%"><input name="transactionType.code" id="code" loxiaType="input" mandatory="true" value=""/></td>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.name"/>：</td>
			<td width="30%"><input name="transactionType.name" id="name" loxiaType="input" mandatory="true" value=""/></td>
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.direction"/>：</td>
			<td width="30%"><s:select list="directionList" mandatory="true" loxiaType="select" listKey="optionKey" listValue="optionValue" name="transactionType.intDirection" id="intDirection" headerKey="" headerValue="%{pselect}" >			
			</s:select>	</td>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.control"/>：</td>
			<td width="30%"><s:select list="controlList" mandatory="true" loxiaType="select" listKey="optionKey" listValue="optionValue" name="transactionType.intControl" id="intControl" headerKey="" headerValue="%{pselect}">	
			</s:select></td>
		</tr>	
		</tr>	
		<tr>
			<td colspan="2" class="label"><input type="checkbox" name="transactionType.isInCost" id="isInCost" value="true"/><s:text name="label.warehouse.transactiontype.incost"/></td>
			<td colspan="2" class="label"><input type="checkbox" name="transactionType.isSynchTaobao" id="isSynchTaobao" value="true"/><s:text name="label.warehouse.transactiontype.issynchtaobao"/></td>	
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.transactiontype.description"/>：</td>
			<td colspan="3"><textarea loxiaType="input" name="transactionType.description" id="description"></textarea></td>			
		</tr>		
	</table>	
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="addTransactionType"  ><s:text name="button.add"/></button>
	</div>
	</div>
</body>
</html>