<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/specialsku/special-sku-maintain.js"' includeParams='none' encode='false'/>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form method="post" enctype="multipart/form-data" id="importForm"	name="importForm" target="upload">
		<table>
			<tr>
				<td class="label">供应商编码导入：</td>
				<td align="center">
					<input type="file" name="file" loxiaType="input" id="file" style="width: 200px" />
				</td>
				<td width="200px"><a loxiaType="button"  href="<%=request.getContextPath()%>/warehouse/excelDownload.do?fileName=供应商编码导入.xls&inputPath=tpl_import_supplier_code.xls">模板下载</a>
					<button loxiaType="button" class="confirm" id='sn_import'>导入</button></td>
			</tr>
		</table>
	</form>
	<form id="addform">
		<table>
			<tr>
				<td class="label">供应商编码：</td>
				<td width="200px"><input id="supplierCode" loxiaType="input" trim="true" name="supplierCode"/></td>
				<td width="400px"><button type="button" loxiaType="button" id="add" class="confirm">添加</button><span style="color:#f00">(将添加该供应商编码对应的所有SKU)</span></td>
			</tr>
		</table>
	</form>
		<form id="searchform">
		<table>
			<tr>
				<td class="label">供应商编码：</td>
				<td width="200px"><input id="supplierCode1" loxiaType="input" trim="true" name="supplierCode"/></td>
				<td ><button type="button" loxiaType="button" id="search" class="confirm">查询</button></td>
				<td ><button type="button" loxiaType="button" id="reset" class="confirm">重置</button></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<table id="have-special-sku">
		</table>
		<div id="pager"></div>
	</div>
	<button type="button" loxiaType="button"  class="confirm" id="batchRemove">
					删除选中项
				</button>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>