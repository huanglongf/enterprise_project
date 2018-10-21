<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>库内补货建议</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sales-report-form.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
 
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<table>
			<tr>
				<td width="10%" class="label">出库时间</td>
				<td width="15%"><input loxiaType="date" showtime="true" mandatory="true" id="outboundTime"></input></td>
				<td width="10%" class="label" style="text-align: center;">到 </td>
				<td width="15%"><input loxiaType="date" showtime="true" mandatory="true" id="endOutboundTime"/></td>
			</tr>
		</table>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="expSummary">报表导出</button>
		<button loxiaType="button" id="reset">重置</button>
	</div>
	<div id="download"></div>
</body>
</html>