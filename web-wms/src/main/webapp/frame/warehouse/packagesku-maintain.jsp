<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/packagesku-maintain.js"' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="addform">
		<table>
			<tr>
				<td class="label">商品条码：</td>
				<td width="200px"><input id="barcode" loxiaType="input" trim="true" name="barCode"/></td>
				<td class="label">商品件数：</td>
				<td width="200px"><input id="skuqty" loxiaType="number" name="skuQty"  mandatory="true" trim="true"/></td>
				<td><button type="button" loxiaType="button" id="add" class="confirm">添加</button><span style="color:#f00">(最多添加3个商品)</span></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<table id="new-package-sku">
		</table>
	</div>
	<div>
		<table>
			<tr>
				<td class="label">商品种类数:</td>
				<td><input loxiaType="input" id="skuQty" readonly/></td>
				<td class="label">商品总件数:</td>
				<td><input loxiaType="input" id="staTotalSkuQty" readonly/></td>
				<td class="label">过期时间:</td>
				<td><input loxiaType="date" showTime="true" min="today" id="expireDate"/></td>
				<td><button loxiaType="button" type="button" id="save" class="confirm">保存</button></td>
			</tr>
			<tr>
				<td colspan="7" style="color:red">过期时间不填写，默认为当前时间加24小时</td>
			</tr>
		</table>
	</div>
	<div class="buttonlist">
		<table id="have-package-sku">
		</table>
	</div>
</body>
</html>