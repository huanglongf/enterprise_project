<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sta-info-input-export.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>作业单信息导入导出</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">配货清单导出 </a></li>
			<li><a href="#tabs-2" >其他导入导出 </a></li>
		</ul>
		<div id="tabs-1">
			<div id=query-batch-list>
				<div style="width:100%;overflow:hidden">
					<button type="button" loxiaType="button" class="confirm" id="createBatch" style="float:left">创建配货批 </button>
					<form method="post" enctype="multipart/form-data" id="importForm1" name="importForm" target="upload" style="float:right;">
						<input type="file" loxiaType="input" id="file1" name="file" style="width: 200px;"/> <button type="button" loxiaType="button" class="confirm" id="save1" >作业单批量出库导入</button>
						<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=WMS刷成在途的导入模板.xls&inputPath=tplt_import_refresh_picking_list_sn.xls" role="button">
							<span class="ui-button-text">作业单模版文件下载</span>
						</a>
					</form>
				</div>
				<div class="buttonlist" style="width:100%">
					<form id="queryForm" name="queryForm" style="width:100%">
						<table>
							<tr>
								<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
								<td colspan="3">
									<input loxiaType="date" style="width: 130px" name="comd.formCrtime1" id="formCrtime" showTime="true"/>
									<s:text name="label.warehouse.pl.to"/>
									<input loxiaType="date" style="width: 130px" name="comd.toCrtime1" id="toCrtime" showTime="true"/>
								</td>
							
								<td class="label"><s:text name="label.warehouse.pl.formPickingTime"/></td>
								<td colspan="3">
									<input loxiaType="date" style="width: 130px" name="comd.formPickingTime" id="formPickingTime" showTime="true"/>
									<s:text name="label.warehouse.pl.to"/>
									<input loxiaType="date" style="width: 130px" name="comd.toPickingTime" id="toPickingTime" showTime="true"/>
								</td>
							</tr>
							<tr>
								<td class="label"><s:text name="label.warehouse.pl.formCheckedTime"/></td>
								<td colspan="3">
									<input loxiaType="date" style="width: 130px" name="comd.formCheckedTime" id="formCheckedTime" showTime="true"/>
									<s:text name="label.warehouse.pl.to"/>
									<input loxiaType="date" style="width: 130px" name="comd.toCheckedTime" id="toCheckedTime" showTime="true"/>
								</td>
								<td colspan="4"></td>
							</tr>
							<tr>
							    <td class="label"><s:text name="label.warehouse.pl.batchno"/></td>
								<td><input loxiaType="input" style="width: 130px" trim="true" name="comd.code" id="code"/></td>
								<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
								<td style="width: 110px">
								<input loxiaType="input" trim="true" style="width: 130px"  name="comd.staCode" id="staCode"/></td>
								<td class="label"><s:text name="label.warehouse.inpurchase.refcode"/></td>
								<td style="width: 110px"><input loxiaType="input" style="width: 130px" name="comd.refSlipCode" id="refSlipCode"/></td>
								<td class="label"><s:text name="label.warehouse.pl.status"/></td>
								<td style="width: 100px">
									 <s:select list="plStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="comd.intStatus" id="status" headerKey="" headerValue="%{pselect}"/>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
					<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
				</div>
				<table id="batch-list"></table>
				<div id="pager"></div>
			</div>
			<div id="order-List" class="hidden">
				<input type="hidden" id='pickingListId' />
				<input type="hidden" id='invPKStatus' />
				<table >
					<tr>
						<td width="100px" class="label"><s:text name="label.warehouse.pl.batchno"></s:text> </td>
						<td width="100px" id="dphCode"></td>
						<td width="100px" class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
						<td width="100px" id="dphStatus"></td>
						<td width="200px" colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
						<td id="creator" ></td>
						<td class="label" class="15%"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
						<td id="operator"></td>
						<td class="label">配货单模式</td>
						<td id="dphMode"></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.pl.pltotal"></s:text> </td>
						<td id="planBillCount">0</td>
						<td class="label" ><s:text name="label.warehouse.pl.plcomplete"></s:text> </td>
						<td id="checkedBillCount">0</td>
						<td class="label" ><s:text name="label.warehouse.pl.plship"></s:text> </td>
						<td id="sendStaQty"></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.pl.prototal"></s:text> </td>
						<td id="planSkuQty"></td>
						<td class="label"><s:text name="label.warehouse.pl.procomplete"></s:text> </td>
						<td id="checkedSkuQty">0</td>
						<td class="label"><s:text name="label.warehouse.pl.proship"></s:text> </td>
						<td id="sendSkuQty">0</td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
						<td id="pickingTime"></td>
						<td class="label"><s:text name="label.wahrhouse.pl.last.checked.time"></s:text> </td>
						<td id="checkedTime"></td>
						<td class="label"><s:text name="label.wahrhouse.pl.last.send.time"></s:text> </td>
						<td id="lastSendTime"></td>
					</tr>
				</table>
				<br /><br />
				<div id="divTbDetial">
					<table id="sta-list"></table>
					<div id="pager1"></div>
				</div>
				<div class="buttonlist">
					<button type="button" loxiaType="button" class="confirm" id="saleSendOutInfoExport" >销售（换货）发货表导出 </button>
					<button type="button" loxiaType="button" class="confirm" id="printTrunkPackingList" >打印装箱清单 </button>
					<button type="button" loxiaType="button" class="confirm" id="trunkPackingListExport" >装箱清单导出 </button>
				</div>
				<div>
					<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text></button>
				</div>
			</div>
		</div>
		<div id="tabs-2">
			<div>
				<button type="button" loxiaType="button" class="confirm" id="returnRegisterInfoExport" >退货登记表导出 </button>
				<form method="post" enctype="multipart/form-data" id="returnRequestInboundImportForm" name="returnRequestInboundImportForm" target="upload" style="float:right;">
					<input type="file" loxiaType="input" id="fileInput" name="file" style="width: 200px;"/> 
					<button type="button" loxiaType="button" class="confirm" id="returnRequestInboundImport" >退货入库导入 </button>
				</form>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="returnReportingStockExport" >库存报表导出 </button>
			</div>
		</div>
	</div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
	<div id="showErrorDialog">
		<div id="errorMsg" class="ui-loxia-default ui-corner-all" style="margin:10;height:250px;overflow-y:scroll"></div>
	</div>
</body>
</html>