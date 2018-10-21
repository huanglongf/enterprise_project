<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>特殊商品作废</title>
<script type="text/javascript"
	src="<s:url value='/scripts/frame/order-outbound-import.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}

.divFloat {
	float: left;
	margin-right: 10px;
}
</style>
</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->
	<div id="div-sta-detail">
		<br />
		<div class="district">
			<table id="tbl-orderNumber"></table>
			<br />
			<table id="tbl-select" cellspacing="0" cellpadding="0"
				style="font-size: 22">
				<tr>
					<td width="200px" class="label">出库单同步LMIS导入:</td>
					<td align="center">
						<form method="post" enctype="multipart/form-data" id="importForm"
							name="importForm" target="upload">
							<input type="file" name="file" loxiaType="input" id="file"
								style="width: 150px" />
						</form>
					</td>
					<td><a loxiaType="button"
						href="<%=request.getContextPath()%>/warehouse/excelDownload.do?fileName=出库单同步LMIS导入.xls&inputPath=tpl_import_order_outbound.xls">模板文件下载</a>
						<button loxiaType="button" class="confirm" id='import'>导入</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>