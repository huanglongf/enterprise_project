<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>

<style>
#div-inventory-detail {background-color: #fff;  color: #2E6E9E;}
#div-inventory-detail table {border: 1px solid #2E6E9E;}
#div-inventory-detail tr {border: 1px solid #BECEEB;}
#div-inventory-detail th, #div-inventory-detail td { padding: 3px; border-right-color: inherit; border-right-style: solid; border-right-width: 1px;
		border-bottom-color: inherit; border-bottom-style: solid; border-bottom-width: 1px;
		}
#div-inventory-detail thead{background-color: #EFEFEF;}
</style>

<script type="text/javascript" src="../scripts/frame/inventory-query.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->

<p>
	<span class="label"><s:text name="label.inventory.query.isShare.warehouse"/></span>
	<s:if test="warehouse.isShare"><s:text name="label.inventory.query.share.wh"/></s:if>
	<s:else><s:text name="label.inventory.query.unshare.wh"/></s:else>
	<input type="hidden" id="whOuId" value="<s:property value="warehouse.ou.id" />" />
</p>
<span class="label"><s:text name="label.inventory.query.condition"/></span>
<br />
<br />
<form id="quert-form" method="post">
		<table width="90%">
			<tr>
				<td class="label" width="13%"><s:text name="label.warehouse.inpurchase.jmcode"></s:text> </td>
				<td width="20%"><input loxiaType="input" trim="true" name="inventoryCommand.jmCode" id="jmCode" /></td>
				<td class="label" width="13%"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text> </td>
				<td width="20%"><input loxiaType="input" trim="true" name="inventoryCommand.barCode" id="barCode"/></td>
				<td class="label" width="13%"><s:text name="label.warehouse.inpurchase.name"></s:text> </td>
				<td width="20%"><input loxiaType="input" trim="true" name="inventoryCommand.skuName" id="skuName"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text> </td>
				<td><input loxiaType="input" trim="true" name="inventoryCommand.supplierSkuCode" id="supplierSkuCode"/></td>
				<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
				<td>
					<div style="float: left">
						<select id="invOwner"  name="inventoryCommand.invOwner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
					</div>
					<div style="float: left">
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					</div>
				</td>
				
				<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
				<td><input loxiaType="input" trim="true" name="inventoryCommand.skuCode" id="skuCode"/></td>
			</tr>
			<tr>
				<td class="label">品牌: </td>
				<td>
					<input loxiaType="input" trim="true" name="inventoryCommand.brandName" id="brandName" />
				</td>
				<td class="label">品牌对接编码: </td>
				<td>
					<input loxiaType="input" trim="true" name="inventoryCommand.extCode2" id="brandName" />
				</td>
				<td class="label">平台对接编码: </td>
				<td>
					<input loxiaType="input" trim="true" name="inventoryCommand.extCode1" id="extCode1" />
				</td>
			</tr>
			<tr style="height:46px;">
				<td class="label">数量起: </td>
				<td style="width: 1px;">
					<input loxiaType="input" trim="true" name="inventoryCommand.numberUp" id="numberUp" />
				</td>
				<td class="label">数量至: </td>
				<td>
					<input loxiaType="input" trim="true" name="inventoryCommand.AmountTo" id="AmountTo" />
				</td>
			</tr>
			<tr>
				<td class="label" colspan="6"><input type="checkbox" id="showZero" /><s:text name="label.inventory.show.zero"></s:text></td>
			</tr>			
		
	</table>
</form>
<div class="buttonlist">
<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
<br /><br />
<table id="tbl-inventory-query"></table>
<div id="pager2"></div>
</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>