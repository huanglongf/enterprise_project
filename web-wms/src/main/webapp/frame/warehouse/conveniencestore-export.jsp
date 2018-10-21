<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title>便利店自提信息导出</title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/conveniencestore-export.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<p><span class="label">便利店自提信息导出：</span></p>
	<form id="form_query">
		<table width="60%">
			<tr>
				<td class="label" width="20%">开始时间</td>
				<td width="30%"><input id="fromDate"  loxiaType="date" showtime="true" mandatory="true"/></td>
				<td class="label" width="20%">结束时间</td>
				<td width="30%"><input id="endDate"  loxiaType="date" showtime="true" mandatory="true"/></td>
			</tr>
		</table>
	</form>
	<div id="btnlist" class="buttonlist" >
		<button loxiaType="button" class="confirm" id="export">便利店自提信息导出</button>	
		<button loxiaType="button" id="reset">重置</button>	
	</div> 
	
	<iframe id="frmExport" class="hidden"></iframe>
	<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>