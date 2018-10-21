<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/product-info-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="f1">
		<form id="form_query" name="form_query">
		<input type="hidden" id="nikeFlag" name="flag" value="false"/>
		<table>
			<tr>
				<td class="label">商品编码:</td>
				<td><input loxiaType="input" name="proCmd.code" id="code" trim="true"/></td>
				<td class="label">商品条码:</td>
				<td><input loxiaType="input" name="proCmd.barCode" id="barCode" trim="true"/></td>
				<td class="label">品牌名称:</td>
				<td>
				<!-- 	<select loxiaType="select" id="brandName" name="proCmd.brandName" trim="true">
					</select> -->
					
					<select loxiaType="select" id="brandName" trim="true" name="proCmd.brandName" data-placeholder="请选择品牌"></select>
					
					<!-- <div style="float: left">
						<button type="button" loxiaType="button" class="confirm" id="btnSearchBrandName" >查询品牌</button>
					</div>-->
					
				</td>
				<td class="label">上架类型:</td>
				<td><input loxiaType="input" name="proCmd.skuTypeName" id="skuTypeName" trim="true"/></td>
			</tr>
			<tr>
				<td class="label">货号:</td>
				<td><input loxiaType="input" name="proCmd.supplierCode" id="supplierCode" trim="true"/></td>
				<td class="label">商品名称:</td>
				<td><input loxiaType="input" name="proCmd.name" id="name" trim="true"/></td>
				<td class="label">箱型条码:</td>
				<td><input loxiaType="input" name="proCmd.packageBarCode" trim="true"/></td>
				<td class="label"><s:text name="label.warehouse.inpurchase.consumable"></s:text></td>
				<td>
					<select id="isConsumable" name="proCmd.isConsumable" loxiaType="select">
						<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
		</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="query"><s:text name="button.query"></s:text></button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text></button><br/>
		</div>
		<table id="tbl-product">
		</table>
		<div id="pager"></div>
		<div class="buttonlist">
			<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="pdaLogFrame">	
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="import" >基础信息导入</button>
	        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_pro_info_maintain"></s:text>.xls&inputPath=tplt_import_pro_info_maintain.xls">
					<span class="ui-button-text">基础信息模版文件下载</span>
				</a>
			</form>
		   <form method="post" enctype="multipart/form-data" id="skuTypeImportForm" name="skuTypeImportForm" target="pdaLogFrame">	
				<input type="file" name="skuTypeImportFile" loxiaType="input" id="skuTypeImportFile" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="skuTypeImport" >商品上架类型批量导入</button>
	        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=商品上架类型维护模板.xls&inputPath=tplt_sku_type.xls">
					<span class="ui-button-text">商品上架类型模版下载</span>
				</a>
			</form> 
			
			
			<form method="post" enctype="multipart/form-data" id="skuSpTypeImportForm" name="skuSpTypeImportForm" target="pdaLogFrame">	
				<input type="file" name="skuSpTypeImportFile" loxiaType="input" id="skuSpTypeImportFile" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="skuSpTypeImport" >SKU编码与耗材绑定</button>
	        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=SKU编码与耗材绑定模板.xls&inputPath=tplt_sku_Supplies.xls">
					<span class="ui-button-text">SKU编码与耗材绑定模版下载</span>
				</a>
			</form>
			
			<form method="post" enctype="multipart/form-data" id="channelSkuSpTypeImportForm" name="channelSkuSpTypeImportForm" target="pdaLogFrame">	
				<input type="file" name="channelSkuSpTypeImportFile" loxiaType="input" id="channelSkuSpTypeImportFile" style="width:200px"/>
				<button type="button" loxiaType="button" class="confirm" id="channelSkuSpTypeImport" >店铺SKU编码与耗材绑定</button>
	        	<a id="downloadTemplate" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=>店铺SKU编码与耗材绑定模板.xls&inputPath=tplt_channel_sku_Supplies.xls">
					<span class="ui-button-text">>店铺SKU编码与耗材绑定</span>
				</a>
			</form>
			<iframe id="pdaLogFrame" name="pdaLogFrame" class="hidden"></iframe>
		</div>
	</div>
	<div id="f2" class="hidden">
		<form id="editForm">
			<table>
				<tr>
					<td style="color:#888" class="label">商品编码:</td>
					<td><input loxiaType="input" readonly="true" style="color:#888" id="code1" name="proCmd.code"/></td>
					<td colspan="6"></td>
				</tr>
				<tr>
					<td class="label" width="100px" style="color:#888">商品名称:</td>
					<td width="120px"><input loxiaType="input" readonly="true" style="color:#888" id="name1"/></td>
					<td class="label" width="100px" style="color:#888">货号:</td>
					<td width="120px"><input loxiaType="input" readonly="true" style="color:#888" id="supplierCode1"/></td>
					<td class="label" width="100px" style="color:#888">品牌名称:</td>
					<td width="120px"><input loxiaType="input" readonly="true" style="color:#888" id="brandName1"/></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td class="label">长(单位/mm):</td>
					<td><input loxiaType="input" id="length" name="proCmd.length"/></td>
					<td class="label">宽(单位/mm):</td>
					<td><input loxiaType="input" id="width" name="proCmd.width"/></td>
					<td class="label">高(单位/mm):</td>
					<td><input loxiaType="input" id="height" name="proCmd.height"/></td>
					<td class="label">净重(单位/KG):</td>
					<td><input loxiaType="input" id="grossWeight" name="proCmd.grossWeight"/></td>
				</tr>
				<tr>
					<td class="label">是否陆运:</td>
					<td>
						<select loxiaType="select" id="railwayStr" name="proCmd.railwayStr">
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</td>
					<td class="label">商品分类:</td>
					<td><input loxiaType="input" id="skuCategoriesName" name="proCmd.skuCategoriesName"/></td>
					<td class="label">箱型条码:</td>
					<td><input loxiaType="input" id="packageBarCode" name="proCmd.packageBarCode"/></td>
					<td class="label">商品上架类型</td>
					<td>
						<select id="skuType" loxiaType="select" name="proCmd.skuTypeId">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
			  <tr><td colspan="8"><span style="color:red;margin-left:50px;">*注意是否陆运的选择</span></td></tr>
			  <tr>
			  <td class="label">原产地</td>
			  <td><input loxiaType="input" id="countryOfOrigin" name="proCmd.countryOfOrigin"/></td>
			  <td class="label">报关编码</td>
			  <td><input loxiaType="input" id="htsCode" name="proCmd.htsCode"/></td>
			  <td class="label">单位名称</td>
			  <td><input loxiaType="input" id="unitName" name="proCmd.unitName"/></td>
			  <td class="label"><s:text name="label.warehouse.inpurchase.consumable"></s:text></td>
			  <td>
				<select id="consumable" name="proCmd.isConsumable" loxiaType="select">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
		     </td>
			 </tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="edit">修改</button>
			<button type="button" loxiaType="button" id="back">返回</button>
		</div>
	</div>
	<!--<jsp:include page="include-brandname-query.jsp"></jsp:include>-->
</body>
</html>