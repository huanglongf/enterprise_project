<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/receving-move-suggest.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>收货暂存区移动建议</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="suggestForm" name="suggestForm">
		<table width="80%">
			<tr>
				<td width="13%" class="label">
					条码
				</td>
				<td width="20%"><input loxiaType="input" trim="true" name="cmd.barCode" /></td>
				<td width="13%" class="label">
					商品SKU编码
				</td>
				<td width="20%"><input loxiaType="input" trim="true" name="cmd.skuCode" /></td>
				<td width="13%" class="label">
					货号
				</td>
				<td width="20%"><input loxiaType="input" trim="true" name="cmd.supplierCode" /></td>
			</tr>
			<tr>
				<td class="label">
					收货暂存区编码
				</td>
				<td><input loxiaType="input" trim="true" name="cmd.districtCode" /></td>
				<td class="label">
					暂存区库位编码
				</td>
				<td><input loxiaType="input" trim="true" name="cmd.locationCode" /></td>
				<td class="label">
				</td>
				<td></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="suggest">导出移库建议</button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text></button>
	</div>
</body>
</html>