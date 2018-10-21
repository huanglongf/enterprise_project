<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.warehouse.inv.detail.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-details-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="typeTwo">
	<form id="form_query">
		<table width="95%">
			<tr>
				<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.jmcode"/>：</td>
				<td width="12%"><input loxiaType="input" trim="true" name="inventoryCommand.jmCode"/></td>
				<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.barcode"/>：</td>
				<td width="25%"><input loxiaType="input" trim="true" name="inventoryCommand.barCode"/></td>
				<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.name"/>：</td>
				<td width="12%"><input loxiaType="input" trim="true" name="inventoryCommand.skuName"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"/>：</td>
				<td><input loxiaType="input" trim="true" name="inventoryCommand.supplierSkuCode"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.shop"/>：</td>
				<td>
					<div style="float: left">
						<select id="companyshop" name="inventoryCommand.invOwner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
						</select>
					</div>
					<div style="float: left">
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					</div>
				</td>
				<td class="label"><s:text name="label.warehouse.whlocation.code"/>：</td>
				<td><input loxiaType="input" trim="true" name="inventoryCommand.locationCode"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text>：</td>
				<td><input loxiaType="input" trim="true" name="inventoryCommand.skuCode" /></td>
				<td class="label">库存状态：</td>
				<td>
					<div style="float: left">
						<select id="inventoryStatusId" name="inventoryCommand.inventoryStatusId" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
						</select>
					</div>
				</td>
				<td class="label">品牌对接编码：</td>
				<td><input loxiaType="input" trim="true" name="inventoryCommand.extCode2" /></td>
			</tr>
			<tr>
			<td class="label" >保质期选项：</td>
				<td>
						<select loxiaType="select" name="inventoryCommand.shelfLife" id = "shelfLife">
							<option value="">请选择</option>
							<option value="0">正常</option>
							<option value="1">预警过期</option>
							<option value="2">已过期</option>
						</select>
				</td>
			</tr>
			<tr class="hidden" id="wdDiv">
					<td class="label">预警天数：</td>
					<td>
						<input loxiaType="input" trim="true" name="inventoryCommand.warningDate" id="warningDate"/>
					</td>	
			</tr>
			<tr>
				<td class="label" style="font-size: 13px">过期时间段查询：</td>
			</tr>
			<tr>
				<td class="label" ><s:text name="label.warehouse.starttime"></s:text>：</td>
				<td><input id="startDate" name="inventoryCommand.startDate" loxiaType="date" showtime="true" style="width: 160px"/></td>
				<td class="label"><s:text name="label.warehouse.endtime"></s:text>：</td>
				<td><input id="endDate" name="inventoryCommand.endDate" loxiaType="date" showtime="true" style="width: 160px"/></td>
				<td class="label" align="left" colspan="2" ><input type="checkbox" trim="true" id="isZeroInventory"/>显示库存为0记录</td>
			</tr>
		</table>
	</form>
	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"/></button>
		<button type="reset" loxiaType="button" id="reset" ><s:text name="button.reset"/></button>
	</div>
		<div>符合您的查询结果的统计如下：SKU个数：<span id="sku" class="">0</span>个;
		库位：<span class="blue" id="loc"></span>
		实际库存量：<span id="invavail">0</span>个;
		占用库存量：<span id="invsales">0</span>个;
		可用库存量：<span id="invoccupy">0</span>个;
		</div>
		<table id="tbl-inventory-list"></table>
		<div id="pager"></div>
</div>
<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>