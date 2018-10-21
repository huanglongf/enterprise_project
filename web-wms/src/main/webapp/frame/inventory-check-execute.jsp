<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-check-execute.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.inventory.check.handel"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	
<div id="divPd">
	<table id="tbl_pd_list"></table>
</div>

<div id="invinfo" class="hidden">
	<table>
		<tr>
			<td class="label" width="25%">
				<s:text name="label.warehouse.inventory.check.code"/>
			</td>
			<td width="25%" id="code">
			</td>
			<td class="label" width="25%">
				<s:text name="label.warehouse.pl.createtime"/>
			</td>
			<td width="25%" id="createTime">
			</td>
		</tr>
		<tr>
			<td class="label">
				<s:text name="label.wahrhouse.sta.creater"/>
			</td>
			<td id="creator">
			</td>
			<td class="label">
				<s:text name="label.warehouse.pl.status"/>
			</td>
			<td id="status">
			</td>
		</tr>
	</table>
</div>
<div id="canelStatusDiv" class="hidden"><!-- 取消未处理 -->
	<div id="divImport" class="hidden">
		<div id="dtl_tabs">
			<ul>
				<li><a href="#dtl_tabs1">库存盘点差异</a></li>
				<li><a href="#dtl_tabs2">库存盘点调整数据</a></li>
			</ul>
			<div id="dtl_tabs1">
				<table id="tbl_import_detial"></table>
			</div>
				<div id="dtl_tabs2">
				<table id="tbl_import_check"></table>
			</div>
		</div>
		<div id="pagerEx"></div>
		<br/>
		<div class="buttonlist">
			<button id="confirmType" loxiaType="button" class="confirm">确认</button>
			<button id="toBackDetial" loxiaType="button"><s:text name="button.back"/></button>
		</div>
	</div>
</div>
<iframe id="exportInventoryCheck" name="upload" class="hidden"></iframe>
<div id="downloadOverage"></div>
</body>
</html>