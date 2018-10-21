<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>

</style>
<%@include file="../../common/meta.jsp"%>
<title><s:text name="SKU扫码记录"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/operationcenter/scan-sku-record.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="ssrList">
<form action="" method="post" id="form">
		<table id="filterTable" width="95%">
				<tr>
					<td class="label" width="13%"><s:text name="批次号"></s:text> </td>
					<td width="20%"><input name="ssrCmd.batchCode" loxiaType="input" trim="true"/></td>
					<td class="label" width="13%"><s:text name="记录时间"></s:text> </td>
					<td width="20%"><input loxiaType="date" name="ssrCmd.fromTime" showtime="true"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="20%"><input loxiaType="date" name="ssrCmd.toTime" showtime="true"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="SKU条形码"></s:text> </td>
					<td><input name="ssrCmd.skuBarcode" loxiaType="input" trim="true"/></td>
					<td class="label"><s:text name="SKU编码"></s:text> </td>
					<td><input name="ssrCmd.skuCode" loxiaType="input" trim="true"/></td>
					<td class="label"><s:text name="商品名称"></s:text> </td>
					<td><input name="ssrCmd.skuName" loxiaType="input" trim="true"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="库位"></s:text> </td>
					<td><input name="ssrCmd.locationCode" loxiaType="input" trim="true"/></td>
					<td class="label"><s:text name="操作人"></s:text> </td>
					<td><input name="ssrCmd.operatorName" loxiaType="input" trim="true"/></td>
					<td></td>
					<td></td>
				</tr>
		</table>
	</form>
	<div class="buttonlist" style="padding-left:80px;">
			<button id="query" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="scanOpt" loxiaType="button" class="confirm"><s:text name="开始扫描"/></button>
	</div>
	<table id="tbl_scan_sku_record_list"></table>
	<div id="pager"></div>
</div>
<div id="ssrOperate" class="hidden">
	<input type="hidden" id="batchCode" name="batchCode"/>
	<table id="filterTable" width="800px;">
		<tr>
			<td class="label" width="10%"><s:text name="库位条码"></s:text> </td>
			<td width="25%"><input id="locationCode" name="locationCode" style="width: 200px;height: 25px" loxiaType="input" trim="true"/></td>
			<td class="label" width="10%"><s:text name="商品条码"></s:text> </td>
			<td width="25%"><input id="skuBarcode" name="skuBarcode" style="width: 200px;height: 25px" loxiaType="input" trim="true"/></td>
		</tr>
	</table>
	<div class="buttonlist" style="padding-left:60px;">
			<button id="back" loxiaType="button" class=""><s:text name="返回"/></button>
	</div>
	<div style="height:82px;width:800px;padding-left:400px;text-align:center;">
		<div id="colorTipSuccess" style="height:80px;width:80px;margin-bottom:1px;background-color:green;text-align:center;vertical-align:middle;" class="hidden">
		<span style="padding-top:30px;display:inline-block;">Success！</span>
		</div>
		<div id="colorTipFail" style="height:80px;width:80px;margin-bottom:1px;background-color:red;text-align:center;vertical-align:middle;" class="hidden">
		<span style="padding-top:30px;display:inline-block;">该商品系统无记录！</span>
		</div>
	</div>
	<table id="tbl_scan_sku_record_detail"></table>
	<div class="buttonlist">
			<button id="confirm" loxiaType="button" class="confirm"><s:text name="确认本次扫描"/></button>&nbsp;&nbsp;
			<button id="clear" loxiaType="button" class=""><s:text name="清除重扫"/></button>
	</div>
</div>
</body>
</html>