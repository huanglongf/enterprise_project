<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/vmi-outbound-adjust-import.js"></script>
<style type="text/css">
.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<div id="tab_2">
			<table width="80%">
				<tbody>
					<tr>
						<td class="label" width="15%">选择店铺:</td>
						<td width="20%">
							<select id="owner" name="owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>
						<td class="label" width="15%">相关单据号:</td>
						<td width="20%">
							<input loxiaType="input" trim="true" name="slipCode" id="slipCode"/>
						</td>
						<td width="30%">
							<button id="createsku" loxiaType="button" class="confirm"><s:text name="button.vmi.inv.check.create.sku"/></button>
						</td>
					</tr>
					<tr>
						<td class="label" width="15%">调整原因:</td>
						<td width="25%">
							<select loxiaType="select" name="reasonCode" id="reasonCode">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>
						<td colspan="3"><font color="red">*CONVERSE、PUMA、STARBUCKS 必填</font></td>
					</tr>
				</tbody>
			</table>
			<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
			<p>&nbsp;</p>
			<div id="searchFile">
				<table><tr>
				<td><span class="label"><s:text name="label.warehouse.choose.file"></s:text> </span></td>
				<td>
					<form method="POST" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
						<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
					</form>
				</td>
				<td> 
					<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
		            <a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_vmi_outbound_adjust"></s:text>.xls&inputPath=tplt_import_vmi_outbound-adjust.xls"><s:text name="download.excel.template"></s:text></a>
		        </td>
				</tr></table>
			</div>
			<iframe id="upload" name="upload" class="hidden"></iframe>
			<div class="clear"></div>
		</div>
</body>
</html>