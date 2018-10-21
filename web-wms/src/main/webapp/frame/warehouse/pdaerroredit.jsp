<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pdaerroredit.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div1">
		<form id="queryForm">
			<table>
				<tr>
					<td class="label">创建时间：</td>
					<td><input loxiaType="date" showtime="true" name="pc.beginTime"/></td>
					<td class="label">到</td>
					<td><input loxiaType="date" showtime="true" name="pc.beginTime1"/></td>
				</tr>
				<tr>
					<td class="label">单据号：</td>
					<td colspan="3"><input loxiaType="input" style="width:120px" name="pc.orderCode"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>
		<table id="staList"></table>
		<div id="staList_pager"></div>
	</div>
	<div id="div2" class="hidden">
		<table>
			<tr>
				<td class="label" style="width:100px;">单据号:</td>
				<td id="pcode"></td>
				<td class="label" style="width:100px;">创建时间:</td>
				<td id="ptime"></td>
			</tr>
			<tr>
				<td class="label">失败原因:</td>
				<td colspan="3" id="pmemo"></td>
			</tr>
		</table>
		<table id="detailList"></table>
		<div class="buttonlist">
			<button loxiaType="button" id="back">返回</button>
		</div>
	</div>
</body>
</html>