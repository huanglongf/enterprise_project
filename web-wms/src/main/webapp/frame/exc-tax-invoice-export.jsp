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

<script type="text/javascript" src="../scripts/frame/exc-tax-invoice-export.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="query-order-list">
		<form name="queryForm" id="queryForm">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="15%"><input loxiaType="date" trim="true" name="createTime" showTime="true"/></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" trim="true" name="endCreateTime" showTime="true"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.operat"></s:text> </td>
					<td width="15%"><input loxiaType="date" trim="true" name="finishTime" showTime="true"/></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" trim="true" name="endFinishTime" showTime="true"/></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.code"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.refSlipCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.owner"></s:text> </td>
					<td width="15%">
						<%--<input loxiaType="input" trim="true" name="staCmd.owner"/> --%>
						<select id="companyshop" name="staCmd.owner" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
		
					</td>
					<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.stastatus"></s:text> </td>
					<td width="15%">
						<select  loxiaType="select" name="staCmd.status1" id="staStatus">
							<option></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text></option>
							<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text></option>
							<option value="3"><s:text name="label.warehouse.sta.status.checked"></s:text></option>
							<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text></option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text></option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.lpcode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.trackingNo"/></td>
					<td width="10%" class="label"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.operator"/></td>
					<td colspan="2"></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text></button>
			<button type="button" loxiaType="button" id="reset" ><s:text name="button.reset"></s:text></button>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<input type="hidden" id="sta_id">
		<table>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td width="15%"><span id="createTime"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.operat"></s:text></td>
				<td width="15%"><span id="finishTime"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.owner"></s:text> </td>
				<td width="15%"><span id="owner"></td>
			</tr>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
				<td width="15%"><span id="code"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
				<td width="15%"><span id="refSlipCode"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.stastatus"></s:text> </td>
				<td width="15%"><span id="status"></td>
			</tr>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
				<td width="15%"><span id="lpcode"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
				<td width="15%"><span id="trackingNo"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.other.operator"></s:text> </td>
				<td width="15%"><span id="operator"></td>
			</tr>
		</table>
		<br />
		<table id="tbl-order-detail"></table>
		<!-- <table id="tbl-order-detail"></table>-->
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="export" class="confirm" ><s:text name="button.export.invoice"></s:text> </button>
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text> </button>
		</div>
	</div>
	<iframe id="frmSoInvoice" class="hidden"></iframe>
</body>
</html>