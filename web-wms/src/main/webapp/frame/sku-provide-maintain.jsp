<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="sku.prodive.maintain.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sku-provide-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>">	
	<table>
		<tr>
			<td> 
	            <button type="button" loxiaType="button"  class="confirm" id="export_1" >导出所有拣货区商品</button>
	        </td>
	        <td>
	         	<button type="button" loxiaType="button" id="export_2" >导出所有未设置补货商品 </button>
	        </td>
	        <td>
	         	<button type="button" loxiaType="button" id="export_3">导出未完成作业单中未设置补货商品 </button>
	        </td>
	        <td></td>
	 	</tr>
	 	
	 	<tr>
			<td class="label">请选择导入文件:</td>
			<td>
				<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				</form>
			</td>
	        <td colspan="2">
	        	<button type="button" loxiaType="button" class="confirm" id="import" >导入</button>
	        	<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_sku_provide_max_count_maintain"></s:text>.xls&inputPath=tplt_import_sku_max_prodive_maintain.xls">
					<span class="ui-button-text">模版文件下载</span>
				</a>
	        </td>
	 	</tr>
	</table>
	<div id="download"></div>
	<iframe id="upload" name="upload" class="hidden"></iframe>		
</body>
</html>