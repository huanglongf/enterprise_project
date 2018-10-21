<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.company.modify.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/report-export-for-levis-current-inventory.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>														

<body contextpath="<%=request.getContextPath() %>">	 
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div>
		<form method="post" id="exportform" name="exportform" target="upload">
			<table width="600px">
				<tr>
					<td class="label" width="25%">款号</td>
					<td width="25%">
						<input loxiaType="input" name="cmd.supplierSkuCode" />
					</td>
					<td class="label" width="25%">
						性别/Consumer Group
					</td>
					<td width="25%">
						<select loxiaType="select" name="cmd.consumerGroup" id="consumerGroup">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">大类/Product Category</td>
					<td>
						<select loxiaType="select" name="cmd.productCategory" id="productCategory">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label">
						小类/Product Line
					</td>
					<td>
						<select loxiaType="select" name="cmd.productLine" id="productLine">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="buttonlist">
		<button type="button" loxiaType="button" id="export" class="confirm">当前库存明细导出</button>
	</div>
</body>
</html>
