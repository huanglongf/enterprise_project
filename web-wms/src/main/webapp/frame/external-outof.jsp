<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.outof"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/external-outof.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
	.showborder {
		border: thin;
	}
	
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
 <s:text id="warstaselect" name="label.warehouse.choose.inventory.state"/>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
<input type="hidden" name="staId" id="staId"/>
<div id="div-sta-list" >
<table id="tbl-outof-order"></table>
</div>

<!-- 占用库存 -->
<div id="div-sta-detailform" class="hidden">
<table>
	<tr>
		<td class="label" ><s:text name="label.warehouse.inpurchase.createTime"/></td>
		<td id="createTime"></td>
		<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
		<td id="staCode"></td>
		<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
		<td id="addiName"></td>
	</tr>
	<tr>
		<td class="label"><s:text name="label.warehouse.inpurchase.status"/></td>
		<td id="status"></td>
		<td class="label"><s:text name="betweenlibary.plan.exec.count"/></td>
		<td id="planCount"></td>
		<td></td>
		<td></td>
	</tr>
</table>
<br />
</div>

<div id="div-sta-detail" class="hidden">
<table id="tbl-orderNumber"></table>
<br />
<form id="addForm" name="addForm">
<table id="tbl-select" cellspacing="0" cellpadding="0">
	
	<tr id="code-select" style="">
		<td class="label"><s:text name="label.warehouse.inventory.state"/></td>
		<td width='130'><s:select name="betwenMoveCmd.invStatusId" headerValue="%{warstaselect}" headerKey="" list="invStatusList" loxiaType="select" listKey="id" listValue="name" ></s:select></td>
		<td ></td>
		<td ></td>
		<td ></td>
		<td ></td>
		<td ></td>
	</tr>
</table>
</form>
<div class="buttonlist">
<table>
	<tr>
		<td>
		<button id="occupancy" type="button" loxiaType="button" class="confirm"><s:text name="betweenlibary.stock.occupy"/></button>
		<button type="button" loxiaType="button" class="confirm" id="backto"><s:text name="button.toback"/></button>
		</td>
	</tr>
</table>
</div>
</div>

<!-- 移库或释放占用库存 -->
	<div id="div-sta-outof" class="hidden">
		<table id="tbl-orderoutof"></table>
		<br />
		<div id="divSnImport" class="hidden">
			<div class="divFloat">
				<span class="label">SN号导入</span>
			</div>
			<div class="divFloat">
				<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					<input type="hidden" name="staId" id="imp_staId" />
					<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
				</form>
			</div>
			<div class="divFloat">
				<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
			</div>
			<div class="divFloat">
				<a loxiaType="button" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=<s:text name="excel.tplt_import_warehouse_sn_import"></s:text>.xls&inputPath=tplt_import_warehouse_sn_import.xls"><s:text name="download.excel.template"></s:text></a>
			</div>
			<div class="clear"></div>
		</div>
		
		<div id="packaging_materials" class="hidden">
			<div class="buttonlist"></div>
			<form id="importPMForm" method="post" enctype="multipart/form-data" name="importPMForm" target="upload" >
				<span class="label">包材导入</span>
				<input type="file" accept="application/msexcel" name="file" id="pmFile" />
				<button loxiaType="button" class="confirm" id="inputPM">包材导入</button>
				<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=包材条码.xls&inputPath=tplt_import_packaging_materials.xls" role="button">
					<span class="ui-button-text">模版文件下载</span>
				</a>
			</form>
		</div>
		<div class="buttonlist">
		<table>
			<tr>
				<td>
				<button id="confirm" type="button" loxiaType="button" class="confirm"><s:text name="betweenlibary.confirm.outofwarehouse"/></button>
				<button id="release" type="button" loxiaType="button"><s:text name="betweenlibary.release.stock.occupy"/></button>
				<button type="button" loxiaType="button" class="confirm" id="print"><s:text name="button.print"/></button>
				<button loxiaType="button" class="confirm" id="printSendInfo"><s:text name="button.print.send.info"/></button>
				<button type="button" loxiaType="button" class="confirm" id="backto1"><s:text name="button.toback"/></button>
				</td>
			</tr>
		</table>
		</div>
	</div>
	<!-- stv bottom -->
	<div id="div-sta-detailform1" class="hidden">
		<table>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.createTime"/></td>
				<td id="createTime2"></td>
				<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
				<td id="staCode1"></td>
				<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
				<td id="addiName1"></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.inpurchase.status"/></td>
				<td id="status2"></td>
				<td class="label"><s:text name="betweenlibary.plan.exec.count"/></td>
				<td id="planCount1"></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
<iframe id="upload" name="upload" class="hidden"></iframe>
</body>


</html>