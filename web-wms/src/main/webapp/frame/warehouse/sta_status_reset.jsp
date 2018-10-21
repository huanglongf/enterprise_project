<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sta_status_reset.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>作业单状态重置</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div >
			<table>
				<tr>
					<td width="80px" class="label">作业单号</td>
					<td width="200px"><input loxiaType="input" name="staCode" id="staCode"></input></td>
				</tr>
			</table>
			<div><font color="red">此功能只能重置状态为配货中和已核对的销售出和换货出的作业单；重置合并订单的时候会删除主订单，并将主订单下面的所有子订单都还原为新建状态！慎用！！</font></div>
		<div class="buttonlist">
			<button type="button" id="resetStatus" loxiaType="button">重置为新建状态</button>
		</div>
	</div>
</body>
</html>