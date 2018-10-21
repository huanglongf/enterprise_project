<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/delivery-change-log.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
	<form name="searchForm" id="searchForm">
		<table>
			<tr>
				<td class="label"><s:text name="店铺:"></s:text></td> 
				<td>
					<div style="float: left">
					<select id="companyshop" name="deliveryLog.channel" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
					</div>
					<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					 </div>
				</td> 
				<td class="label"><s:text name="作业单号:"></s:text></td>
				<td width="230"><input loxiaType="input" trim="true" id="staCode" name="deliveryLog.staCode" width="200"/></td>
				<td class="label"><s:text name="相关单据号:"></s:text></td> 
				<td width="230"><input loxiaType="input" trim="true" id="slipCode" name="deliveryLog.slipCode"/></td> 
				<td class="label"><s:text name="平台订单号:"></s:text></td>
				<td width="230"><input loxiaType="input" trim="true" id="slipCode1" name="deliveryLog.slipcode1"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="原物流商:"></s:text></td> 
				<td>
					<select loxiaType="select" id="lpCode" name="deliveryLog.lpcode">
						<option value="" selected="selected"><s:text name="请选择"></s:text></option>
					</select>
				</td>
				<td class="label"><s:text name="原快递单号:"></s:text></td>
				<td><input loxiaType="input" trim="true" id="trackingNo" name="deliveryLog.trackingNo"/></td>
				<td class="label"><s:text name="现物流商:"></s:text></td> 
				<td>
					<select loxiaType="select" id="newlpCode" name="deliveryLog.newLpcode">
						<option value="" selected="selected"><s:text name="请选择"></s:text></option>
					</select>
				</td> 
				<td class="label"><s:text name="现快递单号:"></s:text></td>
				<td><input loxiaType="input" trim="true" id="newTrackingNo" name="deliveryLog.newTrackingNo"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="更改时间:"></s:text></td> 
				<td><input loxiaType="date" id="c1" name="deliveryLog.startDate" showTime="true">
				</td> 
				<td class="label"><s:text name="到:"></s:text></td>
				<td><input loxiaType="date" id="e1" name="deliveryLog.endDate" showTime="true">
				</td>
			</tr>
		</table>
		</form>
	<br>
	<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
	<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button>
	<table id="tbl-delivery-change-list"></table>
	<div id="pager_query"></div>
	</div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>