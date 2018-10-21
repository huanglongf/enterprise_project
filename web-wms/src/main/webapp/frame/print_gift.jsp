<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/print_gift.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-order-list">
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="finishTime" showTime="true"/></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text></td>
					<td width="15%"><input loxiaType="date" name="endFinishTime" showTime="true"/></td>
					
				</tr>
				<tr>
					
					<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text></td>
					<td width="15%">
						<select loxiaType="select" id="statusList" name="sta.intStaStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text> </option>
							<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text> </option>
							<option value="3"><s:text name="label.warehouse.sta.status.checked"></s:text> </option>
							<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text> </option>
							<option value="5"><s:text name="label.warehouse.sta.status.partyReturned"></s:text> </option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text> </option>
							<option value="15"><s:text name="label.warehouse.sta.status.cancelUndo"></s:text> </option>
							<option value="17"><s:text name="label.warehouse.sta.status.canceled"></s:text> </option>
							<option value="20"><s:text name="label.warehouse.sta.status.failed"></s:text> </option>
							<option value="25"><s:text name="label.warehouse.sta.status.frozen"></s:text> </option>
						</select>
					</td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode1"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode2"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code" /></td>
				</tr>
				
				
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<font class="label" style="color:red">　　默认查询近三个月的单据，否则请加上时间条件！</font>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<table>
			<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="40%" colspan="3"><span id="createTime"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.finish"></s:text> </td>
					<td width="40%" colspan="3"><span id="finishTime"></span></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><span id="code"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><span id="refSlipCode"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
					<td width="15%"><span id="type"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
					<td width="15%"><span id="status"></span></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.address"></s:text> </td>
					<td width="15%" colspan="5"><span id="country"></span>
					<span id="city"></span>
					<span id="district"></span>
					<span id="address"></span>
					<span id="receiver"></span>
					<span id="telephone"></span>
					</td>
				</tr>
		</table>
		<br />
		
		<div id="detail_tabs">
			<ul>
				<li><a href="#m2_tabs-1"><s:text name="label.warehouse.sta.operate_detail"></s:text></a></li>
			</ul>
			<div id="m2_tabs-1">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
				<span style="text-align: right; padding-right: 4px; font-weight: bold; background-color: #ffffff;"><s:text name="label.warehouse.location.comment"/></span><br />
				<div style="display:block; background-color: #efefef; width: 560px; height: 80px;" id="sta_memo"></div>
			</div>
			
			<div id="m2_tabs-2" class="hidden">
				<form id="query_detail_form" name="query_detail_form">
					
				</form>
				
				<table id="tbl-order-detail_operate"></table>
			</div>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
	</div>
	
	<jsp:include page="../common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>