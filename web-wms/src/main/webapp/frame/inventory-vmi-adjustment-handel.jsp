<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-vmi-adjustment-handel.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/esp-inv-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.inventory.check.handel"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	
<div id="divPd">
	<table id="tbl_pd_list"></table>
</div>

<div id="invinfo" class="hidden">
	<table>
		<tr>
			<td class="label" width="25%">
				<s:text name="label.vmi.inventory.check.code"/>
			</td>
			<td width="25%" id="code">
			</td>
			<td class="label" width="25%">
				<s:text name="label.warehouse.pl.createtime"/>
			</td>
			<td width="25%" id="createTime">
			</td>
		</tr>
		<tr>
			<td class="label">
				<s:text name="label.warehouse.pl.status"/>
			</td>
			<td id="status">
			</td>
			<td class="label" width="25%">
				<s:text name="label.warehouse.pl.slipCode"/>
			</td>
			<td id="slipCode">
			</td>
		</tr>
	</table>
</div>
<div id="newStatusDiv" class="hidden"><!-- 新建 -->
	<div id="divDetialList">
		<table id="tbl_detial"></table>
		<div id="pager1"></div>
		<div class="buttonlist">
			<button id="export" loxiaType="button" class="confirm"><s:text name="button.export.vmi.inv.check.list"/></button>
			<button id="cancel" loxiaType="button" class="confirm"><s:text name="button.vmi.inv.check.cancel"/></button>
			<button id="toBack" loxiaType="button"><s:text name="button.back"/></button>
		</div>
		<br/>
	</div>
</div> <!--  -->

<div id="import" class="hidden">
	<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
		<span class="label"><s:text name="label.vmi.inventory.check.import"/></span>
		<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/> &nbsp;&nbsp;&nbsp;
		<input type="hidden" id="invcheckid" value="" />
 		<button id="importFile" loxiaType="button" class="confirm"><s:text name="button.import"/></button>
	</form>
</div>

<div id="canelStatusDiv" class="hidden"><!-- 取消未处理 -->
	<div id="divImport" class="hidden">
		<div id="dtl_tabs">
			<ul>
				<li><a href="#dtl_tabs1"><s:text name="excel.tplt_export_vmi_adjustment_invcheck"></s:text> </a></li>
			</ul>
			<div id="dtl_tabs1">
				<table id="tbl_import_detial"></table>
			</div>
		</div>
		
		<div id="pagerEx"></div>
		<br/>
		<div id='invoice' class='hidden'>
			<table width="70%">
				<tr>
					<td width="15%" class="label">
						发票号
					</td>
					<td  width="30%">
						<input loxiaType="input" id="invoice_invoiceNumber" readonly="readonly" />
					</td>
					<td colspan="2" id="invoice_select2TD"  class='hidden'>
						<select  loxiaType="select" name="numberSelect" id="invoice_selectInv">
							<option value="">请选择发票号</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="label">
						税收系数
					</td>
					<td  width="30%">
						<input loxiaType="number" mandatory="true" decimal="2"  readonly="readonly" id="invoice_dutyPercentage" trim="true" />
					</td>
					<td width="15%" class="label">
						其他系数
					</td>
					<td  width="30%">
						<input loxiaType="number" mandatory="true" decimal="2" readonly="readonly" id="invoice_miscFeePercentage" trim="true"  />
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<div class="buttonlist">
			<button id="confirm" loxiaType="button" class="confirm"><s:text name="button.confirm.difference"/></button>
			<button id="exportinvcheck" loxiaType="button" class="confirm"><s:text name="button.export.vmi.inv.check.list"/></button>
			<button id="cancelInv" loxiaType="button" class="confirm"><s:text name="button.vmi.inv.check.cancel"/></button>
			<button id="toBackDetial" loxiaType="button"><s:text name="button.back"/></button>
		</div>
	</div>
</div>
<iframe id="exportInventoryCheck" name="upload" class="hidden"></iframe>
</body>
</html>