<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/nike-import-print.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<span class="label" style="font-size: 15px;" >导入文件打印</span><br/><br/>
<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
	<span class="label">文件导入 </span>
	<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
	<button id="import" class="confirm ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" type="button" role="button" aria-disabled="false">
	<span class="ui-button-text">导入打印</span>
	</button>
	<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=导入打印模板.xls&inputPath=tplt_import_print.xls">
	<span class="ui-button-text">模版文件下载</span>
	</a>
</form>
<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>