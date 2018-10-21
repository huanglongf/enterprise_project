<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>

<title><s:text name="title.warehouse.execute.cancelsta"></s:text></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sales-release-inventory.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.executeResult{
		display: block;
		background-color: white;
		height: 200px;
	}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

	<form id="query-form">
		<table>
			<tr>
				<td width="25%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
				<td width="25%"><input loxiaType="date" name="createTime" showTime="true" mandatory="true"  id = "ksTime"></input></td>
				<td width="25%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
				<td width="25%"><input loxiaType="date" name="endCreateTime" showTime="true" mandatory="true"  id = "jsTime"/></td>
			</tr>
			<tr>
				<td class="label" width="25%"><s:text name="label.warehouse.pl.sta"></s:text></td>
				<td width="25%"><input loxiaType="input" name="sta.code" trim="true"/></td>
				<td width="25%" class="label"><s:text name="label.warehouse.pl.batchno"></s:text></td>
				<td width="25%"><input loxiaType="input" name="sta.pickingList.code" trim="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text></td>
				<td><input loxiaType="input" name="sta.refSlipCode" trim="true"/></td>
				<td class="label"><s:text name="label.warehouse.sta.receiver"></s:text></td>
				<td><input loxiaType="input" name="sta.staDeliveryInfo.receiver" trim="true"/></td>
			</tr>
			<tr>
				<!--<td class="label"><s:text name="label.warehouse.sta.shop"></s:text></td>
				<td>
					<%--<input loxiaType="input" name="shopId" trim="true"/> --%>
					<select id="shopId" name="shopId" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>-->
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text></td>
				<td><input loxiaType="input" name="sta.staDeliveryInfo.trackingNo" trim="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text></td>
				<td>
					<select id="selLpcode" name="sta.staDeliveryInfo.lpCode" loxiatype="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</td>
				<td class="label" style="color: red;" width="13%" id="wnames" hidden>仓库</td>
					<td width="20%">
						<select id="wselTrans" loxiaType="select" loxiaType="select" hidden>
							<option value="">--请选择仓库--</option>
						</select>
					</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search"><s:text name="button.search"></s:text></button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text></button>
	</div>
	<div class="buttonlist"  style="border-width: 3px; margin-top: 24px;">
		<tr>
			<td class="label">指定库位&nbsp;</td>
			<td><input loxiaType="input" id="appointStorage" name="appointStorage" trim="true" style="width: 11%; margin-top: 20px; margin-bottom: 15px;" placeholder="不指定库位，库存返回原始库位" /></td>
		<tr/>
	</div>
	
	<table id="tbl-Cancelled-list"></table>
	<div id="pager_query"></div>
	<br />
	<table>
		<tr>
			<td><b>批量释放</b>（输入单据号，以英文逗号分隔）：</td>
		</tr>
		<tr>
			<td>
				<textarea trim="true" name="staList" id = "staList"  rows="5" cols="100"></textarea>
			</td>
		</tr>
	</table>
	<button id="cancelLocation" loxiaType="button" class="confirm"><s:text name="button.inventory.release"></s:text></button>
	&nbsp; &nbsp; <button id="suggestion-view" loxiaType="button"><s:text name="button.suggest"></s:text></button>
	<br /><br />
	<span class="label"><s:text name="label.warehouse.inventory.suggest"></s:text></span>
	<table id="tbl-suggestion"></table>
</body>
</html>