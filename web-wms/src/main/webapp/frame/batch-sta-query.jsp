<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/batch-sta-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>批次查询</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="query-batch-list">
		<form id="queryForm" name="queryForm">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text></td>
					<td width="15%"><input loxiaType="date" name="comd.formCrtime1" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" name="comd.toCrtime1" showTime="true"/></td>
				</tr>
				<tr>
					<td width="10%" class="label">批次号</td>
					<td width="15%"><input loxiaType="input" trim="true" name="comd.code"/></td>
					<td width="10%" class="label">状态</td>
					<td width="15%">
						<select id="selMainShopId" name="comd.intStatus" loxiaType="select">
							<option value=""></option>
							<option value="2">配货中</option>
							<option value="8">部分完成</option>
							<option value="10">全部完成</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="batch-list"></table>
		<div id="pager"></div>
	</div>
	<div id="order-List" class="hidden">
		<div id="pickingListId" class="hidden"></div>
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
			<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
				<div>
					<table>
					<tr style="height: 40px">
						<td class="label">导入文件</td>
						<td >
							<input type="file" loxiaType="input" id="file" name="file" style="width: 200px;"/> <button type="button" loxiaType="button" class="confirm" id="save" >导入</button>
						</td>
						<td >
							<a class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="<%=request.getContextPath() %>/warehouse/excelDownload.do?fileName=WMS刷成在途的导入格式.xls&inputPath=tplt_import_refresh_picking_list.xls" role="button">
								<span class="ui-button-text">模版文件下载</span>
							</a>
						</td>
						</tr>
					</table>
				</div>
			</form>
			<br/>
			<button loxiaType="button" id="export">配货清单导出 </button>
		</div>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text> </button>
		</div>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>