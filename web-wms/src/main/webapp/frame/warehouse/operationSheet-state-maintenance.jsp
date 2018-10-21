<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/operationSheet-state-maintenance.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code" /></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td width="15%">
						<select loxiaType="select" id="select" name="sta.lpcode">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<!-- <option value="STO">申通快递</option>
							<option value="EMS">EMS速递</option>
							<option value="SF">顺丰快递</option>
							<option value="EMSCOD">EMS-COD</option>
							<option value="SF-COD">顺丰-COD</option> -->
						</select></td>
					</td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.status"></s:text></td>
					<td width="15%">
						<select loxiaType="select" id="statusList" name="sta.intStaStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text> </option>
							<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text> </option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<font class="label" style="color:red">　　默认查询近一个月的单据，否则请加上时间条件！</font>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<br />
		<div id="detail_tabs">
			<ul>
				<li><a href="#m2_tabs-1"><s:text name="label.warehouse.sta.create_detail"></s:text></a></li>
				<li><a href="#m2_tabs-2"><s:text name="label.warehouse.sta.operate_detail"></s:text></a></li>
				<li><a href="#m2_tabs-3"><s:text name="label.warehouse.sta.sn_detail"></s:text></a></li>
				<li><a href="#m2_tabs-4"><s:text name="label.warehouse.sta.operate_log"></s:text></a></li>
			</ul>
			<div id="m2_tabs-1">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
				<span style="text-align: right; padding-right: 4px; font-weight: bold; background-color: #ffffff;"><s:text name="label.warehouse.location.comment"/></span><br />
				<div style="display:block; background-color: #efefef; width: 560px; height: 80px;" id="sta_memo"></div>
			</div>
			
			<div id="m2_tabs-2">
				<form id="query_detail_form" name="query_detail_form">
					<table width="80%">
						<tr>
							<td width="10%" align="right"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td width="15%"><input loxiaType="date" name="createTime" showTime="true"></input></td>
							<td width="10%" align="right" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
							<td width="15%"><input loxiaType="date" name="endCreateTime" showTime="true"/></td>
							<td width="10%" align="right"><s:text name="label.warehouse.sta.finish"></s:text> </td>
							<td width="15%"><input loxiaType="date" name="finishTime" showTime="true"/></td>
							<td width="10%" align="right" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td width="15%"><input loxiaType="date" name="endFinishTime" showTime="true"/></td>
						</tr>
						<tr>
							<td align="right" ><s:text name="label.warehouse.inpurchase.skucode"></s:text></td>
							<td >
								<input loxiaType="input" trim="true" name="stvl.skuCode"/>
							</td>
							<td align="right" ><s:text name="label.warehouse.other.transaction"></s:text></td>
							<td >
								 <select loxiaType="select" name="stvl.directionInt">
									<option value=""><s:text name="label.warehouse.choose.transactiontype"></s:text></option>
									<option value="1"><s:text name="label.warehouse.other.transactionin"></s:text></option>
									<option value="2"><s:text name="label.warehouse.other.transactionout"></s:text></option>
								</select>
							</td>
							<td align="right" ><s:text name="label.wahrhouse.sta.creater"></s:text></td>
							<td >
								<input loxiaType="input" trim="true" name="stvl.creater"/>
							</td>
						</tr>
						<tr>
							<td align="right"><s:text name="label.warehouse.whlocation.code"></s:text></td>
							<td>
								 <input loxiaType="input" trim="true" name="stvl.locationCode"/>
							</td>
							<td align="right"><s:text name="label.warehouse.inventory.state"></s:text></td>
							<td>
								<s:select name="stvl.intInvstatus" headerKey="" headerValue="请选择库存状态" list="statusList" loxiaType="select" listKey="id" listValue="name" ></s:select>
							</td>
							<td colspan="4"></td>   
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button id="searchDetail" loxiaType="button" class="confirm" staId=""><s:text name="button.search" /></button>
					<button type="button" loxiaType="button" id="resetDetail"><s:text name="button.reset"></s:text> </button>
				</div>
				<table id="tbl-order-detail_operate"></table>
				<div id="pagerDetail_operate"></div>
			</div>
			
			
		</div>
		
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text></button>
		</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>