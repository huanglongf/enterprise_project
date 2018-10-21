<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>库内补货建议</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sku_move_suggest.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
 
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="queryForm" name="queryForm">
		<table width="90%">
			<tr>
				<td class="label" width="13%">商品SKU编码</td>
				<td width="20%"><input loxiaType="input" trim="true" name='comm.skuCode' id='skuCode' /></td>
				<td class="label" width="13%">商品编码</td>
				<td width="20%"><input loxiaType="input" trim="true" name='comm.jmCode' id='jmCode'  /></td>
				<td class="label" width="13%">条码</td>
				<td width="20%"><input loxiaType="input" trim="true" name='comm.barCode' id='barCode'  /></td>
			</tr>
			<tr>
				<td class="label" width="13%">库区编码</td>
				<td width="20%">
					<select loxiaType="select" name="comm.districtCode" id='districtCode' >
						<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select" ></s:text></option>
					</select>
				</td>
				<td class="label">安全警戒百分比(1-100)</td>
				<td><input loxiaType="number" trim="true" max="100" min="0" name='comm.warningPre' id='warningPre' /></td>
				<td class="label">货号</td>
				<td><input loxiaType="input" trim="true" name='comm.supplierCode' id='supplierCode'  /></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="expSummary">导出补货数据</button>
		<button loxiaType="button" id="reset">重置</button>
	</div>
	<div id="download"></div>
</body>
</html>