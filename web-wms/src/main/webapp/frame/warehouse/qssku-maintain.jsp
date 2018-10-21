<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/qssku-maintain.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->

		<div class="buttonlist">
			<table>
				<tr>
					<td class="label">
						QS商品导入:
					</td>
					<td>
						<form method="post" enctype="multipart/form-data" id="importForm2" name="importForm" target="upload">
							<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/>
							<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=QS商品导入.xls&inputPath=qssku.xls">模板文件下载</a>
							<button loxiaType="button" class="confirm" id="import">导入</button>
						</form>
					</td>
				</tr>
			</table>
			<iframe id="upload" name="upload" class="hidden"></iframe>
		</div>

<div id="div1">
		<table id="filterTable">
			<tr>
				<td class="label">
					SKU编码
				</td>
				<td>
					<input loxiaType="input" trim="true" id="skuCode"/>
				</td>
				<td>
					<button type="button"  class="confirm" loxiaType="button" id="search">查询</button><button  class="confirm" type="button" loxiaType="button" id="reset" >重置</button>
				</td>
			</tr>
		</table>
</div>

<div id="div-waittingList"  style="width: 1000px;">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
	<button type="button" class="confirm" loxiaType="button" id="del">删除选中项</button>
</body>
</html>