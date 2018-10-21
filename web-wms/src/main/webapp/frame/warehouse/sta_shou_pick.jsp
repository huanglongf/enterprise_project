<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sta_shou_pick.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>作业单状态重置</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div >
			<table>
				<tr>
					<td width="80px" class="label">作业单号</td>
					<td width="200px"><!-- <input loxiaType='textarea' name="staCode" id="staCode"></input> -->
					<textarea  name="staCode" id="staCode" ></textarea>
					</td>
				</tr>
			</table>
			<div><font color="red">此功能只能重置状态为新建和配货失败的销售出和换货出的作业单；多个作业单中间用“,”隔开；上限20单</font></div>
		<div class="buttonlist">
			<button type="button" id="resetStatus" loxiaType="button">手动占用</button>
		</div>
	</div>
</body>
</html>