<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.SnapShot.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-Snapshot-inquires.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<div id="typeTwo">
<form id="queryInv">
	<table width="60%">
		<tr>
			<td class="label" width="10%"><s:text name="label.warehouse.starttime"></s:text> </td>
			<td width="15%"><input id="startDate" name="startDate" loxiaType="date" showtime="true" mandatory="true" checkmaster="checkStartDate"/></td>
			<td width="10%" class="label"><s:text name="label.warehouse.endtime"></s:text> </td>
			<td width="15%"><input id="endDate" name="endDate" loxiaType="date" showtime="true" mandatory="true" checkmaster="checkEndDate"/></td>
			<td width="10%" class="label"><s:text name="label.warehouse.wh"></s:text> </td>
			<td width="15%"><select id="selWhOuId" name="inventory.whOuId" loxiaType="select" >
			<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
		</select></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text> </td>
			<td><input loxiaType="input" name="inventory.barCode" trim="true"/></td>
			<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"></s:text> </td>
			<td><input  loxiaType="input" name="inventory.jmCode"  trim="true"/></td>
			<td class="label"><s:text name="label.warehouse.inpurchase.brand"></s:text> </td>
			<td><input loxiaType="input" name="inventory.brandName"  trim="true"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.inpurchase.name"></s:text> </td>
			<td><input loxiaType="input" name="inventory.skuName"  trim="true"/></td>
			<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
			<td>
				<!--<input loxiaType="input" name="inventory.invOwner"  trim="true"/>
				-->
				<select id="companyshop" name="inventory.invOwner" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select>
			</td>
			<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text> </td>
			<td><input loxiaType="input" name="inventory.supplierSkuCode" trim="true"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text> </td>
			<td><input loxiaType="input" trim="true" name="inventory.skuCode" /></td>
			<td colspan="4"></td>
		</tr>
	</table>
</form>
	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
		<button type="reset" loxiaType="button"  id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
		<table id="tbl-inventory-list" ></table>
		<div id="pager"></div>
		<BR /><BR />
		<div id="detail" class="hidden">
			<table id="tbl-details"></table>
			<div id="pater-details"></div>
		</div>
</div>
</body>
</html>