<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>SN号编辑</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/skuquery.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="staInfo">
<div id="tabs">
<ul>

<li>
	<a id="exp_li" href="#tabs-2">SKU批量导入获取库存  </a>
</li>
</ul>
<div id="tabs-1" class="hidden">
		<span class="label" style="font-size: 15px;" >TAB1-SN手工维护</span>
		<table id="filterTable" width="95%">
				<tr >
					<td class="label" width="13%">商品条码</td>
					<td width="20%"><input loxiaType="input" trim="true" id="pCode"/></td>
				</tr>
				<tr> 
					<td class="label">SKU商品编码 </td>
					<td width="20%" id="skuSupplierCode"><span id="skuSupplierCodes"></td>
					<td class="label" style="color: red;">货号</td>
					<td width="20%"  id="skuCode"><span id="skuCodes"></td>
					<td class="label" style="color: red;"> 商品名称  </td>
					<td width="20%" id="skuName"><span id="skuNames"></td>
				</tr>
				<tr>
					<td class="label"  style="color: red;">SN号</td>
					<td width="20%"><input id="snnumber" loxiaType="input" trim="true"/></td>
				</tr>
			</table>
			<br>
			<button type="button" id="btn-query" loxiaType="button" class="confirm">保存 </button>
			<div class="buttonlist">
			<table id="tbl-staList-query"></table>
			<div id="pager_query"></div>
		</div>
		<div id="staLineInfo" class="">
		<table id="filterTable">
		</table>
		<div class="buttonlist">
		</div>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="tabs-2" >
	<span class="label" style="font-size: 15px;" >SKU批量导入获取库存 </span>
	<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
	<table id="filterTable" width="95%">
			<tr >
			
			<td class="label" width="13%">平台店铺</td>
				<td>	<select id="invOwner"  name="inventoryCommand.invOwner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
				</td>
						</tr>
						<tr>
				<td class="label" width="13%">SKU导入</td>
				<td width="20%">
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
                </td>
			    <td><b>限制导入SKU数量在1000个以下</b><a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=SKU批量导入模板.xls&inputPath=tplt_import_sku_number.xls">
			    <span class="ui-button-text">模版文件下载</span>
			    </a>
			    <button id="import" class="confirm ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" type="button" role="button" aria-disabled="false">
			    <span class="ui-button-text">导入</span>
			    </button>
			    </tr>
		</table>
		</form>
</div>
</div>
<jsp:include page="/common/include/include-shop-query-multi.jsp"></jsp:include>
</div>
</div>
</body>
</html>