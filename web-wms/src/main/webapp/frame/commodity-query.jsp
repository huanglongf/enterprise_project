<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/commodity-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

<title><s:text name="label.warehouse.sku.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divList">
		<span class="label"><s:text name="label.warehouse.sku.term"></s:text></span>
		<br />
		<form id="form_query" name="form_query">
		<table width="80%">
			<thead>
				<tr>
					<th width="10%"><s:text name="label.warehouse.inpurchase.skucode"></s:text></th>
					<td width="20%"><input loxiaType="input" trim="true" name="skuCom.code" /></td>
					<th width="10%"><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text></th>
					<td width="20%"><input loxiaType="input" trim="true" name="skuCom.barCode" /></td>
					<th width="10%"><s:text name="label.warehouse.inpurchase.supplierCode"></s:text></th>
					<td width="20%"><input loxiaType="input" trim="true" name="skuCom.supplierCode" /></td>
					
				</tr>
				<tr>
					<th><s:text name="label.warehouse.inpurchase.jmcode"></s:text></th>
					<td><input loxiaType="input" trim="true" name="skuCom.jmCode" /></td>
					<th><s:text name="label.warehouse.inpurchase.extpro"></s:text></th>
					<td><input loxiaType="input" trim="true" name="skuCom.keyProperties" /></td>
					<th><s:text name="label.warehouse.sku.name"></s:text></th>
					<td><input loxiaType="input" trim="true" name="skuCom.name" /></td>
				</tr>
				<tr>
					<th><s:text name="label.warehouse.inpurchase.brand"></s:text></th>
					<td>
						<select class="special-flexselect" id="brandSelect" name="skuCom.brandName" data-placeholder="请选择品牌">
						</select>
					</td>
					<th><s:text name="label.warehouse.inpurchase.consumable"></s:text></th>
					<td>
						<select id="isConsumable" name="skuCom.isConsumable" loxiaType="select">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
					<th colspan="2"></th>
				</tr>
			</thead>
		</table>
		</form>
		<table>
			<tr>
				<td colspan="4">
					<div class="buttonlist">
						<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
							<span class="label">保质期商品导入：</span>
							<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
						</form>
					</div>
				</td>
				<td colspan="2"> 
							<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
					</td>
					    <td style="width: 200px;" colspan="2"> 
	            				<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=商品保质期.xls&inputPath=tplt_import_sku_shelf_life.xls"><s:text name="download.excel.template"></s:text></a>
	     	   </td>
			 </tr>
		</table>
		
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search"><s:text name="button.search"></s:text></button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text></button>
			
		</div>
		<table id="tbl-commodity-query"></table>
		<div id="pager"></div>
	</div>
	<div id="divDetial" class="hidden">
		<input type="hidden" id="detialSkuId" name="staId" />
		<table width="60%">
			<tr>
				<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
				<td width="25%" id="skuCode"></td>
				<td class="label" width="15%"><s:text name="label.warehouse.sku.name"></s:text></td>
				<td width="25%" id="skuName"></td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.warehouse.sku.barCode"></s:text>
				</td>
				<td colspan="3">
					<div id="divModify">
						<span id="spanBarcode"></span>
						<button id="modeify" type="button" loxiaType="button" class="hidden" ><s:text name="button.modeify"></s:text></button>
						<button id="printBarcode" type="button" loxiaType="button"  class="confirm"><s:text name="button.print"></s:text></button>
					</div>
					<div id="divSave" class="hidden">
						<input loxiaType="input" trim="true" id="barcode" style="width: 150PX;" />
						<button id="btnSaveMainBarcode" type="button" loxiaType="button" class="confirm" ><s:text name="button.save"></s:text></button>
						<button id="btnCancel" type="button" loxiaType="button"><s:text name="button.cancel"></s:text></button>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.warehouse.inpurchase.jmcode"></s:text>
				</td>
				<td id="skuJmCode"></td>
				<td class="label">
					<s:text name="label.warehouse.inpurchase.extpro"></s:text>
				</td >
				<td id="skuKeyProperties"></td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.warehouse.inpurchase.supplierCode"></s:text>
				</td>
				<td id="skuSupplierCode"></td>
				<td class="label">
					<s:text name="label.warehouse.inpurchase.brand"></s:text>
				</td>
				<td id="skuBrandName"></td>
			</tr>
		</table>
		<br/><br/>
		<form id="form_sku_barCode" name="form_sku_barCode">
		<div id="edittable" loxiaType="edittable" style="width:300px;">
			<table operation="add,delete" append="1" id="tbl_barcode_modify" width="100%">
				<thead>
					<tr>
						<th><input type="checkbox" /></th>
						<th><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tbody>
					<tr>
						<td><input type="checkbox"/></td>
						<td><input type="text" loxiaType="input" class="newExpress" mandatory="true" name="barcode" trim="true" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		</form>
		<div id="showDiv" loxiaType="edittable" style="width:300px;">
			<table append="0" id="tbl_barcode_show" width="100%">
				<thead>
					<tr>
						<th><s:text name="label.warehouse.pl.sta.sku.barcode"></s:text></th>
					</tr>
				</thead>
				<tbody id="showSkuBarCodeTb">
				</tbody>
				<tbody>
				</tbody>
			</table>
		</div>
		
		<div class="buttonlist">
			<button id="save" type="button" loxiaType="button" class="confirm" ><s:text name="button.save"></s:text></button>
			<button id="back" type="button" loxiaType="button" ><s:text name="button.back"></s:text></button>
		</div>
	</div>
	<div id="product_print_dialog" style="text-align: center; padding-top: 10px;">
		<span class="label">输入打印数量：</span>
		<input id="inputCount" loxiaType="input" checkmaster="validateInput" trim="true" style="width: 180px; height: 30px; line-height: 30px;"/><br/><br/>
		<button id="confirmPrint" loxiaType="button" class="confirm">打印</button>
		<button id="cancelPrint" loxiaType="button" class="confirm">取消</button>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<jsp:include page="include-brandname-query.jsp"></jsp:include>
</body>
</html>