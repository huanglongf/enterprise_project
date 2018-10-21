<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.outof"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/VMI-flitting-out.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
	.showborder {
		border: thin;
	}
	
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
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
		<td class="label"></td>
		<td ></td>
	</tr>
	<tr>
		<td class="label">库存状态</td>
		<td id="status"></td>
		<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
		<td id="addiName"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td class="label">源店铺</td>
		<td id="ownerOuName"></td>
		<td class="label">目标店铺</td>
		<td id="addiOwnerOuName"></td>
		<td></td>
		<td></td>
	</tr>
</table>
<br />
</div>

<div id="div-sta-detail" class="hidden">
<table id="tbl-orderNumber"></table>
<br />
<div class="buttonlist">
<table>
	<tr>
		<td>
			<form id="importOcpForm"  method="post" target="upload" enctype="multipart/form-data">
				请选择文件：<input type="file" accept="application/msexcel" name="file"  id="importFileOcp"/>
			</form>
		</td>
		<td>
			<button id="importOccupancy" loxiaType="button" class="confirm">手动占用</button>
			<button loxiaType="button" id="outputTmpt">导出模版</button>
		</td>
	</tr>
	<tr>
		<td>
			<button id="occupancy" type="button" loxiaType="button" class="confirm">自动占用</button>
		</td>
		<td>
			<button type="button" loxiaType="button" id="backto"><s:text name="button.toback"/></button>
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
				<span class="label"><s:text name="label.warehouse.choose.file"></s:text></span>
			</div>
			<div class="divFloat">
				<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">	
					<input type="hidden" name="sta.id" id="imp_staId" />
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
		<iframe id="upload" name="upload" class="hidden"></iframe>
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
				<button type="button" loxiaType="button" class="confirm" id="output">SN导出</button>
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
				<td class="label"></td>
				<td ></td>
			</tr>
			<tr>
				<td class="label">库存状态</td>
				<td id="status2"></td>
				<td class="label"><s:text name="betweenlibary.addi.warehouse"/></td>
				<td id="addiName1"></td>
				<td class="label"></td>
				<td ></td>
			</tr>
			<tr>
				<td class="label">源店铺</td>
				<td id="ownerOuName1"></td>
				<td class="label">目标店铺</td>
				<td id="addiOwnerOuName1"></td>
				<td class="label"></td>
				<td></td>
			</tr>
		</table>
	</div>
<div id="download"></div>
<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>