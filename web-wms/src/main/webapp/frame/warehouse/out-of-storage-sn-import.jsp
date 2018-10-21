<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>SN号编辑</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/out-of-storage-sn-import.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="staInfo">
<div id="tabs">
<ul>
	<li>
		<a id="exp_li" href="#tabs-2">出入库SN号导入</a>
	</li>
</ul>

	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="tabs-2" >
	<span class="label" style="font-size: 15px;" >出入库SN号导入</span>
	<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
	<table id="filterTable" width="95%">
			<tr >
				<td class="label" width="13%">SN号导入</td>
				<td width="20%">
				<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
                </td>
			    <td></td>
			    <td><a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=出入库SN号-编辑模板.xls&inputPath=tplt_import_out_of_storage_sn.xls">
			    <span class="ui-button-text">模版文件下载</span>
			    </a>
			    <button id="import" class="confirm ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" type="button" role="button" aria-disabled="false" style="right: 215px;">
			    <span class="ui-button-text">导入</span>
			    </button>
			    </tr>
		</table>
		</form>
</div>
</div>
<jsp:include page="/common/include/include-shop-query-multi.jsp"></jsp:include>
</div>
</div>
</body>
</html>