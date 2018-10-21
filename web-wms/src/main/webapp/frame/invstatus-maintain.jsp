<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/invstatus-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.invstatus"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<table id="tbl-yes-invstatuslist"></table>
	<br />
	<table id="tbl-no-invstatuslist"></table>
	<div id="editdiv" class="hidden">
	<h3><s:text name="label.warehouse.invstatus.edit"/></h3>
	<form id="editForm" name="editForm">
		<table>			
			<tr>
				<td width="20%" class="label">&nbsp;</td>
				<td width="30%">&nbsp;</td>
				<td width="20%" class="label">&nbsp;</td>
				<td width="30%"><input type="hidden" name="inventoryStatus.id" id="invId"/></td>
			</tr>	
			<tr>
				<td width="20%" class="label"><s:text name="label.warehouse.invstatus.name"/></td>
				<td colspan="3"><input name="inventoryStatus.name" loxiaType="input" mandatory="true" id="invName" trim="true"  max="50" maxLength="50"/></td>			
			</tr>
			<tr>
				<td width="20%" class="label"><s:text name="label.describe"/></td>
				<td colspan="3"><textarea loxiaType="input" name="inventoryStatus.description" id="invDesc" trim="true"  max="255" maxLength="255"></textarea></td>			
			</tr>
			<tr>
				<td colspan="2" class="label">
					<input type="checkbox" name="inventoryStatus.isForSale" id="invIsForSale"/><s:text name="label.warehouse.invstatus.isforsale"/>&nbsp;&nbsp;
					<input type="checkbox" name="inventoryStatus.isInCost" id="invIsInCost"/><s:text name="label.warehouse.invstatus.isincost"/>
				</td>
				<td width="20%" class="label"><s:text name="label.status"/>：</td>
				<td width="30%">
					<s:select list="statusOptionList" loxiaType="select" listKey="optionKey"
						listValue="optionValue" id="invIsAvailable" name="inventoryStatus.isAvailable"  mandatory="true"></s:select>
				</td>
			</tr>
		</table>
	</form>	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="save"><s:text name="button.save"/></button>
		<button type="button" loxiaType="button" id="btn-edit-cancel"><s:text name="button.cancel"/></button>
	</div>
	</div>
	<div id="newdiv">
	<h3><s:text name="label.warehouse.invstatus.add"/></h3>
	<form id="addForm" name="addForm">
	<table>			
		<tr>
			<td width="20%" class="label">&nbsp;</td>
			<td width="30%">&nbsp;</td>
			<td width="20%" class="label">&nbsp;</td>
			<td width="30%">&nbsp;</td>
		</tr>		
		<tr>
			<td width="20%" class="label"><s:text name="label.warehouse.invstatus.name"/></td>
			<td colspan="3"><input name="inventoryStatus.name" loxiaType="input" mandatory="true" trim="true"  max="50" id="addName" maxLength="50"/></td>			
		</tr>
		<tr>
			<td width="20%" class="label"><s:text name="label.describe"/></td>
			<td colspan="3"><textarea loxiaType="input" name="inventoryStatus.description"  trim="true"  max="255" maxLength="255"></textarea></td>			
		</tr>
		<tr>
			<td colspan="2" class="label"><input type="checkbox" name="inventoryStatus.isForSale" value="false" id="isForSale" checked/><s:text name="label.warehouse.invstatus.isforsale"/>&nbsp;&nbsp;
			<input type="checkbox" name="inventoryStatus.isInCost" value="false" id="isInCost" checked/><s:text name="label.warehouse.invstatus.isincost"/></td>
			<td colspan="2" class="label">&nbsp;</td>			
		</tr>
	</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="add"><s:text name="button.add"/></button>
	</div>
	</div>
</body>
</html>