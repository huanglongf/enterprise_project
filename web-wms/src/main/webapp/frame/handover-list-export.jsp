<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.executeResult{
		background-color: white;
		border: 1px gray solid;	
	}
	
	.table{
		border:1px gray solid;	
		margin: 0px;
		padding: 0px
	}

	
</style>
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/handover-list-export.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="tabs">
	<form id="plForm" name="plForm"  enctype="multipart/form-data"  target="upload">
		<table>
			<tr>
					<td class="label" width="20%">开始时间</td>
					<td width="30%"><input id="fromDate" name="fromDate" loxiaType="date" showtime="true" mandatory="true"/></td>
					<td class="label" width="20%">结束时间</td>
					<td width="30%"><input id="endDate" name="endDate" loxiaType="date" showtime="true" mandatory="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
				<td><select id="selTrans" name="lpCode" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select></td>
				<td class="hidden" id="warehouselable" style="font-weight:bold" align="right">仓库</td>
					<td class="hidden" id="warehouse" width="20%">
						<select id="selTransOpc" name="whId" loxiaType="select" loxiaType="select">
							<option value="">--请选择仓库--</option>
						</select>
					</td>
			</tr>
			<tr>
				<td><button id="btnExport" loxiaType="button" class="confirm" ><s:text name="导出"></s:text></button></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
	<iframe id="frmTrans" name="upload" class="hidden"></iframe>
</body>
</html>