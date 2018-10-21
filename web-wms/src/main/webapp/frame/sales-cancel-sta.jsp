<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.cancelSta.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sales-cancel-sta.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="list">
	<form id="query-form">
		<table>
			<tr>
				<td class="label" width="25%"><s:text name="label.warehouse.pl.sta"></s:text> </td>
				<td width="25%"><input loxiaType="input" name="sta.code" trim="true"/></td>
				<td width="25%" class="label"><s:text name="label.warehouse.pl.batchno"></s:text> </td>
				<td width="25%"><input loxiaType="input" name="sta.pickingList.code" trim="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
				<td><input loxiaType="input" name="sta.refSlipCode" trim="true"/></td>
				<td class="label"><s:text name="label.warehouse.sta.receiver"></s:text> </td>
				<td><input loxiaType="input" name="sta.staDeliveryInfo.receiver" trim="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
				<td>
					<%--<input loxiaType="input" name="shopId" trim="true"/> --%>
					<select id="companyshop" name="shopId" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
				<td><input loxiaType="input" name="sta.staDeliveryInfo.trackingNo" trim="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
				<td>
					<select id="selLpcode" name="sta.staDeliveryInfo.lpCode" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
				<td class="label">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</form>
	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm"  id="query"><s:text name="button.search"></s:text> </button>
		<button type="reset" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	
	<table id="tbl-order-cancel"></table>
	<div id="pager-cancel"></div>
	<br /><br />
	<button id="doCancel" loxiaType="button" class="confirm" id="cancelList"><s:text name="button.sta.cancel"></s:text> </button>

</div>

<div id="detail" class="hidden">
	<table width="600px">
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%" id="staCode"></td>
					<td class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="15%" id="staCreateTime"></td>
					<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td id="staTransCode"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
					<td id="staType"></td>
					<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td id="staReCode"></td>
					<td class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
					<td id="staStatus"></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.total"></s:text> </td>
					<td id="planCount"></td>
					<td class="label"><s:text name="label.warehouse.pl.sta.comment"></s:text> </td>
					<td id="staMemo" colspan="3"></td>
				</tr>
			</table>
			<br /><br />
			<table id="tbl-billView"></table>
			<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="cancelSta"><s:text name="button.sta.cancel"></s:text> </button>
				<button id="toBack" loxiaType="button"><s:text name="button.back"></s:text> </button>
			</div>
</div>

</body>
</html>