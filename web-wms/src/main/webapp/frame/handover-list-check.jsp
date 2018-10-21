<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/handover-list-check.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id='list'>
		<table id="tbl-query-list"></table>
		<div id="pager"></div>
	</div>
	<div id='detailed' class="hidden">
		<table>
			<tr>
				<td width="10%" class="label">交情清单</td>
				<td width="40%" colspan="3"><span id="code"></span></td>
				<td width="10%" class="label">物流商</td>
				<td width="40%" colspan="3"><span id="expName"></span></td>
			</tr>
			<tr>
				<td width="10%" class="label">单据数量 </td>
				<td width="15%"><span id="billCount"></span></td>
				<td width="10%" class="label">发货方 </td>
				<td width="15%"><span id="sender"></span></td>
				<td width="10%" class="label">宝尊交接人 </td>
				<td width="15%"><span id="partyAOperator"></span></td>
				<td width="10%" class="label">创建人</td>
				<td width="15%"><span id="userName"></span></td>
			</tr>
		</table>
		<br/>
		<div class="hidden" id='hoListId'></div>
		<div><span class="label">条码扫描：</span><input id="billsId" style="width: 200px" loxiaType="input" mandatory="true" trim="true"/></div>
		<table id="tbl-query-detail"></table>
		<br />
		<div>
			<button id="submit" loxiaType="button" class="confirm">提交</button> 
			<button id="back" loxiaType="button">返回</button>
		</div>
	</div>
</body>
</html>