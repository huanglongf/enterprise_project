<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/barcode-scanning.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<div>条形码:<input loxiaType="input" trim="true" id="barcode" style="width: 150px" /></div>
	<br />
	<div loxiaType="edittable" >
		<table operation="add,delete" id="barCode_tab" >
			<thead>
				<tr>
					<th width="10"><input type="checkbox"/></th>
					<th width="200">条形码</th>
					<th width="100">数量</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tbody >
				<tr class="add" index="(#)">
					<td><input type="checkbox"/></td>
					<td><input loxiatype="input" mandatory="true" name="addList(#).code" class="code"/></td>
					<td><input loxiatype="input" mandatory="true" name="addList(#).name" class="name"/></td>
				</tr>
			</tbody>
			<tfoot></tfoot>
		</table>
	</div>
</body>
</html>