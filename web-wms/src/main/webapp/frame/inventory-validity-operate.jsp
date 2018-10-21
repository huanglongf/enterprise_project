<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="label.wahrhouse.inner.title"></s:text></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-validity-operate.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="inventory_lock_div">
			<div id="div-lock-detail" >
				<form id="form_query" name="form_query">
					<table width="100%">
						<tr>
							<td class="label" width="8%"><s:text name="label.warehouse.inpurchase.code"></s:text></td>
							<td width="12%">
								<input name="inventoryCommand.barCode" loxiaType="input" trim="true" />
							</td>
							<td class="label" width="8%"><s:text name="label.warehouse.inpurchase.jmcode"></s:text></td>
							<td width="12%">
								<input name="inventoryCommand.skuCode" loxiaType="input" trim="true" />
							</td>
							<td class="label" width="10%"><s:text name="label.warehouse.sku.name"></s:text></td>
							<td width="15%">
								<input name="inventoryCommand.skuName" loxiaType="input" trim="true"/>
							</td>
							<td class="label" width="10%"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text></td>
							<td width="15%">
								<input name="inventoryCommand.supplierSkuCode" loxiaType="input" trim="true"/>
							</td>
						</tr>
						<tr>
							<td class="label" width="10%"><s:text name="label.warehouse.inpurchase.shop"></s:text></td>
							<td width="15%">
								<select id="companyshop" name="inventoryCommand.invOwner" loxiaType="select" style="width: 220px; height: 24px;">
									<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
							</td>
							<div style="float: left;padding-left: 391px;padding-bottom: -38px;margin-bottom: -90px;">
								<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" style="margin-top: 27px; margin-left: -5px;">查询店铺</button>
							</div>
							
							<td class="label" width="10%"><s:text name="label.warehouse.inpurchase.locationCode"></s:text></td>
							<td width="15%">
								<input name="inventoryCommand.locationCode" loxiaType="input" trim="true"/>
							</td>
							<td class="label" width="10%"><s:text name="库存状态"></s:text></td>
							<td width="15%">
								<div style="float: left; width: 230px;">
									<select id="inventoryStatusId" name="inventoryCommand.inventoryStatusId" loxiaType="select">
										<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
									</select>
								</div>
							</td>
							<td class="label" width="10%"><s:text name="品牌对接编码"></s:text></td>
							<td width="15%">
								<input loxiaType="input" trim="true" name="inventoryCommand.extCode2" />
							</td>
						</tr>
						<tr>
							<td class="label" width="10%"><s:text name="生产日期范围"></s:text></td>
							<td width="15%">
								<input loxiaType="date" id="minPDate" name="inventoryCommand.minPDate" showTime="true"/>
							</td>
							<td class="label" width="10%" style="text-align: center;">至</td>
							<td width="15%">
								<input loxiaType="date" id="maxPDate" name="inventoryCommand.maxPDate" showTime="true"/>
							</td>
							<td class="label" width="10%"><s:text name="失效日期范围"></s:text></td>
							<td width="15%">
								<input loxiaType="date" id="minExpDate" name="inventoryCommand.minExpDate" showTime="true"/>
							</td>
							<td class="label" width="10%" style="text-align: center;">至</td>
							<td width="15%">
								<input loxiaType="date" id="maxExpDate" name="inventoryCommand.maxExpDate" showTime="true"/>
							</td>
						</tr>
						
						
					</table>
				</form>			
				<div class="buttonlist">
					<button id="query" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
					<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>
				</div>
				<table id="tbl_inventory_adjust_list"></table>
				<div id="pager2"></div>
				<br/>
				<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
				<jsp:include page="/common/include/include-validity-operate.jsp"></jsp:include>
</body>
</html>