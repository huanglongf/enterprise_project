<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货库位状态看板</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript"
	src="<s:url value='/scripts/frame/automaticEquipment/shipping-collection-see-board.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath()%>">
	<table>
				<tr>
				    <td class="label">集满超时预警时效(小时)：</td>
					<td><input loxiaType="input" name="fullTime"  id="fullTime"  trim="true"/></td>
					<td class="label">未集满超时预警时效(小时)：</td>
					<td width="160px"><input loxiaType="input" name="notFullTime"  id="notFullTime"  trim="true"/></td>
				</tr>
	</table>
		<div class="buttonlist">
			<table>
				<tr>
					<td><button type="button" loxiaType="button" class="confirm" id="search">
						刷新
					</button></td>
				</tr>
		</table>
		<div style="float:center;width:600px">
			   <table   border="1"  id="tbl-detail-list" style="border-bottom:solid ;border:1px;font-size: 20px; border-color: red;">
			    
			   </table>
		</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>