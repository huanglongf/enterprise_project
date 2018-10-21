<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.executeResult{
		background-color: white;
		border: 1px gray solid;	
	}
	
	.table{
		border:1px gray solid;	
		margin: 0px;
		padding: 0px
	}
	.inputWidth{
		width: 120px;
	}

	
</style>
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inventory.query"></s:text> </title>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-log-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="" contextpath="<%=request.getContextPath() %>">
<div id="divHandOverList">
<form id="query_form">
<table width="95%">
	<tr>
		<td class="label" width="10%"><s:text name="label.wahrhouse.sta.start.time"></s:text> </td>
		<td width="15%">
			<input loxiaType="date" name="stock.stockStartTime1" value="" showTime="true"/>
		</td>
		<td class="label" width="10%"><s:text name="label.wahrhouse.sta.end.time"></s:text> </td>
		<td width="15%">
			<input loxiaType="date" name="stock.stockEndTime1" value="" showTime="true"/>
		</td>
		<td class="label" width="10%"><s:text name="label.warehouse.inventory.check.code"></s:text></td>
		<td width="25%"><input loxiaType="input" name="stock.inventoryCheckCode" trim="true" /></td>
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
		<td>
			<select id="transType" name="stock.transactionTypeid" loxiaType="select">
				<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
			</select>			 
		</td>
		<td class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
		<td><input loxiaType="input" name="stock.staCode" trim="true"/></td>
		<td class="label"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
		<td><input loxiaType="input" name="stock.operator" trim="true"/></td>
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.location.code"></s:text> </td>
		<td><input name="stock.districtCode" loxiaType="input" trim="true" /></td>
		<td class="label" ><s:text name="label.warehouse.whlocation.code"></s:text> </td>
		<td><input name="stock.locationCode" loxiaType="input" trim="true" /></td>
		<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
		<td>
			<div style="float: left">
				<select id="companyshop" name="stock.owner" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select>
			</div>
			<div style="float: left">
				<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
			</div>
		</td>
		
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text> </td>
		<td><input loxiaType="input" name="stock.barCode" trim="true"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.jmcode"></s:text> </td>
		<td><input loxiaType="input" name="stock.jmCode" trim="true"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.name"></s:text> </td>
		<td><input loxiaType="input" name="stock.skuName" trim="true"/></td>
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text> </td>
		<td><input loxiaType="input" trim="true" name="stock.skuCode" /></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text></td>
		<td><input loxiaType="input" trim="true" name="stock.refSlipCode"/></td>
		<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text></td>
		<td><input loxiaType="input" trim="true" name="stock.supplierCode"/></td>
	</tr>
	<tr>

		<td class="label">库存状态 </td>
                                <td>
					<div style="float: left">
						<select id="inventoryStatusId" name="stock.invStatus" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
						</select>
					</div>
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
</form>
	<div class="buttonlist">
		<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
	</div>
	
	<table id="tbl-inv-log-list"></table>
	<div id="pager"></div>
</div>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>