<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/work-line-no-create.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<font class="label">流水号维护</font>
	<form action="">
		<div loxiaType="edittable" id="tab_info">
			<table operation="add" id="barCode_tab" >
				<thead>
					<tr>
						<th width="10"><input type="checkbox"/></th>
						<th width="150">编码</th>
						<th width="200">开票机编码</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<tbody id="tab_tbody">
				</tbody>
				<tbody >
					<tr class="add" index="(#)" id="dateTr(#)">
						<td><input type="checkbox" id='chock(#)'/></td>
						<td><input loxiatype="number" mandatory="true" name="code" class="code"/></td>
						<td><input loxiatype="input" mandatory="true" name="lineNo" class="name"/></td>
						<td><button type="button" loxiaType="button" id="delete(#)" onclick="deleteTr(#)">删除</button></td>
					</tr>
				</tbody>
				<tfoot></tfoot>
			</table>
		</div>
	</form>
	<br />
	<button type="button" loxiaType="button" id='save' id="pdaExecuteInventory" class="confirm">保存更新</button>
</body>
</html>