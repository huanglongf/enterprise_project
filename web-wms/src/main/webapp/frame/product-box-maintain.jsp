<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="product.box.maintain.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/product-box-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>">	
	<form id="formQuery" name="formQuery">
			<table width="80%">
				<tr>
					<td class="label" width="10%">
						商品编码:
					</td>
					<td width="20%">
						<input loxiaType="input" trim="true" name="product.code" id=""/>
					</td>
					<td class="label"  width="10%">
						货号:
					</td>
					<td width="20%">
						<input loxiaType="input" trim="true" name="product.supplierSkuCode" id=""/>
					</td>
					<td width="13%" class="label">
						商品名称:
					</td>
					<td width="20%">
						<input loxiaType="input" trim="true" name="product.name" id=""/>
					</td>
				</tr>
			</table>
	</form>
	
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search" >查询</button>
		<button type="button" loxiaType="button" id="reset">重置</button>
	</div>
	<br/>
	<table id="tbl_query"></table>
	<div id="tbl_query_page"></div> 
	
	<br/>	
	<table>
		<tr>
			<td class="label">请选择导入文件:</td>
			<td>
				<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				</form>
			</td>
			<td> 
	            <button type="button" loxiaType="button" class="confirm" id="import" >导入</button>
	        </td>
	        <td>
	        	<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_product_count_box"></s:text>.xls&inputPath=tplt_import_product_count_box.xls" role="button">
					<span class="ui-button-text">模版文件下载</span>
				</a>
	        </td>
	 	</tr>
	</table>
	
	<iframe id="upload" name="upload" class="hidden"></iframe>		
</body>
</html>

 