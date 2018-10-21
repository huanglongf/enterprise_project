<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.move.query"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-return-create2.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<table width="60%">
		<tr>
			<td class="label" >esprit店铺:</td>
			<td >
				      <select loxiaType="select" id="selectStore"  style="width: 160px;">
				      </select>
			</td>
		</tr>
		<tr id="selectTd">
			<td class="label" >物流商:</td>
			<td >
				      <select loxiaType="select" id="select"  style="width: 160px;">
					       <option value="">请选择...</option>
					       <option value="STO">申通快递</option>
					       <option value="ZTO">中通快递</option>
					       <option value="EMS">EMS速递</option>
					       <option value="SF">顺丰快递</option>
					       <option value="TTKDEX">天天快递</option>
					       <option value="YTO">圆通快递</option>
					       <option value="SFDSTH">顺丰电商特惠</option>
				      </select>
			</td>
			<td>
				<div class="buttonlist">
					<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
						<span class="label"><s:text name="label.warehouse.choose.file"></s:text>:</span>
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</div>
			</td>
			</tr>
			<tr>
			<td class="label" >&nbsp;&nbsp;&nbsp;</td>
		  	<td > 
				<button loxiaType="button" class="confirm" id="import">导入</button>
			</td>
			<td > 
	            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=ESPRIT_VMI_退仓.xls&inputPath=tplt_import_vmi_return_esprit.xls">模板文件下载</a>
	        </td>
		  </tr>
	</table>
	<br/> 
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>