<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta-lpcode-update.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-order-list">
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td width="10%" class="label" ><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%" ><input loxiaType="date" showtime="true" mandatory="true" name="createTime"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" showtime="true" mandatory="true" name="endCreateTime"/></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.code" /></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.refSlipCode"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text></td>
					<td>
						<select loxiaType="select" id="staLpcode" name="sta.lpcode" style="width: 150px">
							<option value="">--无--</option>
						</select></td>
					<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td>
						<div style="float: left">
							<select id="companyshop" name="sta.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>						
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
				</tr>
				<tr>
				<td class="label" id="selTransname" style="color: red;" width="5%" hidden>仓库</td>
					<td width="10%">
						<select id="selTrans" loxiaType="select" loxiaType="select" name="ou.id" hidden>
							<option value="">--请选择仓库--</option>
						</select>
					</td>
				<tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
		<div class="buttonlist">
			<font class="label">物流商：</font>
				<select loxiaType="select" id="select"  style="width: 150px">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select>
			<font class="label"><button type="button" loxiaType="button" id="updateSelect" >勾选修改</button></font>
			<font class="label"><button type="button" loxiaType="button" class="confirm" id="updateAll">查询结果修改</button></font>
		</div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>