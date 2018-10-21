<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货管理</title>
<%@include file="/common/meta.jsp"%>	
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/handrefcarton.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<form id="form_query">
			<table>
				<tr>
					<td class="label"><font size = "4">货箱号：</font></td>
					<td width="160px" ><input loxiaType="input"  style="height:25px;" mandatory="true" name="code"  id="containerCode"/></td>
				</tr>
				<tr>
					<td class="label"><font size = "4">目的区域：</font></td>
					<td width="160px">
						<select  loxiaType="select" style="height:25px;" name="name"  id="popCode">
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<table>
				<tr>
					<button type="button" loxiaType="button" class="confirm" id="save">
						保存
					</button>
				</tr>
			</table>
		</div>
</body>
</html>