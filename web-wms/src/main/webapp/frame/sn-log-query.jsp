<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sn-log-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="queryForm" name="queryForm">
		<table>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
				<td width="15%"><input loxiaType="input" trim="true" name="cmd.staCode" /></td>
				<td width="10%" class="label">SN号</td>
				<td width="15%"><input loxiaType="input" trim="true" name="cmd.sn" />
				<td width="10%" class="label">作业方向 </td>
				<td width="15%">
					<select loxiaType="select" id="typeList" name="cmd.directionInt">
						<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
						<option value="1">入库</option>
						<option value="2">出库</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<table id="tbl-snLogList"></table>
	<div id="pager"></div>
</body>
</html>