<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/store-house-cancel.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-poOrder-list">
		<form id="queryForm" name="queryForm">
			<table>
			<tr>	<td width="200" class="label"><s:text name="作业单号"></s:text> </td>
					<td width="200"><input loxiaType="input" trim="true" name="sta.code"/></td>
					<td width="200" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="200"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
					<td width="10%" class="label">作业单状态:</td>
					<td width="15%">
						<select loxiaType="select" id="statusList" name="sta.intStaStatus" width="200">
							<option value="20" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text> </option>
							<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text> </option>
							<option value="3"><s:text name="label.warehouse.sta.status.checked"></s:text> </option>
							<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text> </option>
							<option value="5"><s:text name="label.warehouse.sta.status.partyReturned"></s:text> </option>
							<option value="8">装箱中</option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text> </option>
							<option value="15"><s:text name="label.warehouse.sta.status.cancelUndo"></s:text> </option>
							<option value="17"><s:text name="label.warehouse.sta.status.canceled"></s:text> </option>
							<option value="20"><s:text name="label.warehouse.sta.status.failed"></s:text> </option>
							<option value="25"><s:text name="label.warehouse.sta.status.frozen"></s:text> </option>
						</select>
					</td>
			</tr>
			<tr>
					<td width="200" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="200"><input loxiaType="date" id="c1" name="createTime" showTime="true"></input></td>
					<td width="200" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="200"><input loxiaType="date" id="e1" name="endCreateTime" showTime="true"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="15%">
						<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<font class="label" style="color:red">　　默认查询近三个月的单据，否则请加上时间条件！</font>
		</div>
		<table id="tbl-sta-cancel-list"></table>
		<div id="pager"></div>
	</div>
	
	<div id="detail_tabs" class="hidden">
			<div id="m2_tabs-1">
			<br>
				<table>
					<tr>
						<td width="10%" class="label">作业单号:</td>
						<td><span id="staCode" class="label"></span></td>
						<td width="10%" class="label">相关单据号:</td>
						<td><span id="slipCode" class="label"></span></td>
						<td width="10%" class="label">作业单状态:</td>
						<td><span id="staStatus" class="label"></span></td>
						<td width="10%" class="label">计划量:</td>
						<td><span id="planQty" class="label"></span></td>
						<td class="hidden"><span id="intStaStatus" class="label"></span></td>
					</tr>
				</table>
			</div>
				<div id="m2_tabs-2">
				<table id="tbl-sta-cancel-detail" ></table>
				<div id="pagerDetail"></div>
				</div>
				<div id="m2_tabs-3">
				<table id="tbl-sta-cancel-detail1" ></table>
				<div id="pagerDetail1"></div>
				</div>
			
		<div class="buttonlist1">
			<button type="button" loxiaType="button" class="confirm" id="save" ><s:text name="保存"></s:text> </button>
			<button type="button" loxiaType="button" class="confirm" id="saveAndPush"><s:text name="保存并推送"></s:text> </button>
			<button loxiaType="button" id="back"><s:text name="button.back"></s:text></button>
		</div>
		</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>