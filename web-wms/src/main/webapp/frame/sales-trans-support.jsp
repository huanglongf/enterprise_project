<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title>销售出库推荐物流</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sales-trans-support.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body contextpath="<%=request.getContextPath() %>">	
<div id="staInfo">
	<form action="/generationPendingList.json" method="post" id="query-form">
		<table id="filterTable" width="80%">
				<tr>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="20%"><input loxiaType="date" showtime="true" name="fromDate"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="20%"><input loxiaType="date" showtime="true" name="toDate"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="20%"><input name="sta.code" loxiaType="input" trim="true"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td><input name="sta.refSlipCode" loxiaType="input" trim="true" /></td>
					<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td>
						<select id="shopId" name="shopId" loxiaType="select">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						</select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
					<td>
						<select name="sta.type" loxiaType="select">
							<option value=""></option>
							<option value="OUTBOUND_SALES"><s:text name="label.wahrhouse.sta.type.salesoutbound"></s:text> </option>
							<option value="OUTBOUND_RETURN_REQUEST"><s:text name="label.wahrhouse.sta.type.changeoutbound"></s:text> </option>
							<option value="OUT_SALES_ORDER_OUTBOUND_SALES">外部销售出库</option>
						</select>
					</td>
					<td  class="label">
						省
					</td>
					<td>
						<input loxiaType="input" name="province" trim="true" />
					</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
		</table>
	</form>
	
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<table id="tbl-staList-query"></table>
	<div id="pager_query"></div>
	
</div>
<div id="staLineInfo" class="hidden">
	<table id="filterTable">
		<tr>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.sta"></s:text> </td>
			<td width="15%"><span id="s_code"></span></td>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
			<td width="20%" ><span id="s_createTime"></span></td>
			<td width="15%" class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
			<td width="15%"><span id="s_status"></span></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
			<td><span id="s_slipCode"></span></td>
			<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
			<td colspan="3"><span id="s_owner"></span></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
			<td><span id="s_type"></span></td>
			<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
			<td width="15%"><span id="s_trans"></span></td>
			<td class="label"><s:text name="label.warehouse.sta.isneedinvoice"></s:text> </td>
			<td><span id="s_inv"></span></td>
		</tr>
	</table>
	<table id="tbl_detail_list"></table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" id="back" class="confirm"><s:text name="button.back"></s:text> </button>
	</div>
</div>
</body>
</html>
