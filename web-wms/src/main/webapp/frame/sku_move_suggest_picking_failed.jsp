<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>配货失败建议</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sku_move_suggest_picking_failed.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
 
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="queryForm" name="queryForm">
		<table width="65%">
			<tr>
				<td class="label" width="17%">作业单号</td>
				<td width="30%"><input loxiaType="input" trim="true" name='comm.staCode' id='staCode' /></td>
				<td class="label" width="17%">相关单据号</td>
				<td width="30%"><input loxiaType="input" trim="true" name='comm.staSlipCode' id='staSlipCode'  /></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="expSummary">导出配货失败数据</button>
		<button loxiaType="button" id="reset">重置</button>
	</div>
	<div id="download"></div>
</body>
</html>