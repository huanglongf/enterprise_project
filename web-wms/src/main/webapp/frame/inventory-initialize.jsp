<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.inventory.initialize.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-initialize.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<div id="searchFile">
	<div class="divFloat">
		<span class="label"><s:text name="label.warehouse.choose.file"></s:text></span>
	</div>
	<div class="divFloat">
		<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
			<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
		</form>
	</div>
	<div class="divFloat">
		<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
	</div>
	<div class="divFloat">
		<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_inventory_initialize"></s:text>.xls&inputPath=tplt_import_inventory_initialize.xls"><s:text name="download.excel.template"></s:text></a>
	</div>
	<br/><br/>
	</div>
	<table id="tbl-invList"></table>
	<div id="pager"></div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>