<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="title.warehouse.inventory.snapshot"></s:text> </title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-historical-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="typeTwo">
<form id="form_query">
	<table width="95%">
		<tr>
			<td class="label" width="10%"><s:text name="label.warehouse.time"></s:text> </td>
			<td width="15%"><input id="startDate" name="date" loxiaType="date" showtime="true" mandatory="true" checkmaster="checkDate"/></td>
			<td class="label" width="10%"><s:text name="label.warehouse.wh"></s:text> </td>
			<td width="15%">
				<select id="selTrans" name="inventory.whOuId" loxiaType="select" >
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
				</select>
			</td>
			<td class="label" width="10%"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text> </td>
			<td width="15%"><input loxiaType="input" name="inventory.barCode" trim="true"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"></s:text> </td>
			<td><input  loxiaType="input" name="inventory.jmCode"  trim="true"/></td>
			<td class="label"><s:text name="label.warehouse.inpurchase.brand"></s:text> </td>
			<td><input loxiaType="input" name="inventory.brandName"  trim="true"/></td>
			<td class="label"><s:text name="label.warehouse.inpurchase.name"></s:text> </td>
			<td><input loxiaType="input" name="inventory.skuName"  trim="true"/></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
			<td width=150>
				<div style="float: left;width: 260px">
					<select id="companyshop" name="inventory.invOwner" loxiaType="select" >
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</div>
				<div style="float: left">
				</div>

			</td>
			<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text> </td>
			<td><input loxiaType="input" name="inventory.supplierSkuCode" trim="true"/></td>
			<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
			<td><input loxiaType="input" trim="true" name="inventory.skuCode" /></td>
		</tr>
		<tr>
			<td class="label" colspan="6"><input type="checkbox" id="showZero" /><s:text name="label.inventory.show.zero"></s:text></td>
		</tr>
	</table>
</form>
	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
		<button type="reset" loxiaType="button" id="reset" ><s:text name="button.reset"></s:text> </button>
	</div>
		<table id="tbl-inventory-list"></table>
		<div id="pager"></div>
		<BR /><BR />
		<div id="detail" class="hidden">
			<br /><br />
			<table id="tbl-details"></table>
			<div id="pater-details"></div>
		</div>
</div>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>