<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta-query-printNew.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-order-list">
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td class="label">快递单号:</td>
					<td><input loxiaType="input" trim="true" id="trackingNo" name="trackingNo"></input></td>
					<td class="label">物流商:</td>
					<td><select loxiaType="select" id="select" name="lpCode">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
	</div>
	
	<div class="buttonlist">
			<button type="button" loxiaType="button" id="printAll">批量打印 </button>
	</div>
</body>
</html>