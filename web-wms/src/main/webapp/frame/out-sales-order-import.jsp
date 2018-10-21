<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	.divFloat{
		float: left;
		margin-right: 10px;
	}
	
	.clear{
		clear:both;
		height:0;
	    line-height:0;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/out-sales-order-import.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.outSalesOrderImport"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<iframe class="hidden" id="upload" name="upload"></iframe>
	<div id="showList">
		<div>
			<div class="divFloat">
				<span class="label"><s:text name="label.warehouse.choose.file"></s:text></span>
			</div>
			<div class="divFloat">
				<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				</form>
			</div>
			<div class="divFloat">
				<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
			</div>
			<div class="divFloat">
				<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_out_sales_order_import"></s:text>.xls&inputPath=tplt_out_sales_order_import.xls"><s:text name="download.excel.template"></s:text></a>
			</div>
			<div class="clear"></div>
				
			<div class="buttonlist">
				<h3><s:text name="label.wharehouse.impory.result.error"></s:text> </h3>
			</div>
		</div>
			<div >
				<textarea id="errorMsg" style="width: 500px" loxiaType="input" rows="5"></textarea>
			</div>
			<div>
				<table id="tbl_sta"></table>
			</div>
		</div>

		<div id="showDetail" class="hidden">
			<table width="80%">
				<tr>
					<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.createTime"></s:text></td>
					<td width="15%">
						<input loxiaType="input" id="input_createTime_exp" readonly style="background-color: #f2f2f2; border: 0" />
					</td>
					<td class="label" width="15%"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
					<td width="15%">
						<input loxiaType="input" id="input_staCode_exp" readonly style="background-color: #f2f2f2; border: 0"  />
					</td>
					<td class="label" width="15%"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
					<td width="15%">
						<input loxiaType="input" id="input_creater_exp"  readonly style="background-color: #f2f2f2; border: 0" />
					</td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.transactiontype"></s:text> </td>
					<td>
						<input loxiaType="input" id="input_staType_exp" readonly style="background-color: #f2f2f2; border: 0"  />
					</td>
					<td class="label"><s:text name="label.wahrhouse.inner.num"></s:text> </td>
					<td>
						<input loxiaType="input" id="input_totalSkuQty_exp" readonly style="background-color: #f2f2f2; border: 0" />
					</td>
					<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td>
						<input loxiaType="input" id="input_lpcode"  readonly style="background-color: #f2f2f2; border: 0" />
					</td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.comment"></s:text> </td>
					<td colspan="5" id="input_memo_exp"></td>
				</tr>
			</table>
			<table id="tbl_showDetail"></table>
			<div class="buttonlist">
				<button id="toBack" loxiaType="button" class="confirm"><s:text name="button.back"></s:text> </button>
			</div>
		</div>
</body>
</html>