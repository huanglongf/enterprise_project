<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sta_restore.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>作业单备份还原</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div >
			<table>
				<tr>
					<td width="80px" class="label">作业单号</td>
					<td width="200px"><input loxiaType="input" name="staCode" id="staCode"></input></td>
				</tr>
			</table>
			<div><font color="red">此功能是把备份的作业单还原回来</font></div>
		<div class="buttonlist">
			<button type="button" id="resetStatus" loxiaType="button">还原</button>
		</div>
	</div>
</body>
</html>