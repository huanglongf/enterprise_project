<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/picking-list-query-new.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
<div id="div1">
	<form id="quert-form" method="post">
		<table width="90%">
			<tr>
				<td class="label" width="13%">配货单编号 </td>
				<td width="20%"><input loxiaType="input" trim="true" name="comd.code" id="code" /></td>
				<td class="label" width="13%">创建时间 </td>
				<td width="20%"><input loxiaType="date" name="comd.formCrtime1" showTime="true"/></td>
				<td class="label" align="center" width="13%">到  </td>
				<td width="20%"><input loxiaType="date" name="comd.toCrtime1" showTime="true"/></td>
				
			</tr>
			<tr>
				<td class="label" width="13%">作业单编号 </td>
				<td><input loxiaType="input" trim="true" name="comd.staCode" id="staCode"/></td>
				
				<td class="label" width="13%">最新操作时间 </td>
				<td width="20%"><input loxiaType="date" name="comd.formPickingTime1" showTime="true"/></td>
				<td class="label" align="center"  width="13%">到 </td>
				<td width="20%"><input loxiaType="date" name="comd.toPickingTime1" showTime="true"/></td>
			</tr>
			<tr>
				<td class="label" width="13%">配货单状态 </td>
				<td>
					<select id="statusInt" name="comd.intStatus" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						<option value="0">取消 </option>
						<option value="1">等待配货 </option>
						<option value="2">配货中 </option>
						<option value="8">部分完成 </option>
						<option value="10">全部完成 </option>
						<option value="20">配货失败 </option>
					</select>
				</td>
				<td class="label">配货单模式 </td>
				<td>
					<select id="pkModeInt" name="comd.pkModeInt" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						<option value="1">模式一 </option>
						<option value="2">模式二 </option>
						<option value="3">模式三 </option>
					</select>
				</td>
				<td class="label">操作员 </td>
				<td>
					<input loxiaType="input" trim="true" name="comd.operUserName" id="operUserName"/>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
	</div>
	<br />
	<table id="pickingList"></table>
	<div id="pager"></div>
</div>

<div id="div2" class="hidden">
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
			<td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
			<td id="pickingTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.checked.time"></s:text> </td>
			<td id="checkedTime"></td>
<%-- 			<td class="label" ><s:text name="label.warehouse.pl.plcomplete"></s:text> </td>
			<td id="checkedBillCount">0</td>
			<td class="label" ><s:text name="label.warehouse.pl.plship"></s:text> </td>
			<td id="sendStaQty"></td> --%>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.prototal"></s:text> </td>
			<td id="planSkuQty"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.send.time"></s:text> </td>
			<td id="lastSendTime"></td>
<%-- 			<td class="label"><s:text name="label.warehouse.pl.procomplete"></s:text> </td>
			<td id="checkedSkuQty">0</td>
			<td class="label"><s:text name="label.warehouse.pl.proship"></s:text> </td>
			<td id="sendSkuQty">0</td> --%>
		</tr>
		<tr>
<%-- 			<td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
			<td id="pickingTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.checked.time"></s:text> </td>
			<td id="checkedTime"></td> --%>
<%-- 			<td class="label"><s:text name="label.wahrhouse.pl.last.send.time"></s:text> </td>
			<td id="lastSendTime"></td> --%>
		</tr>
	</table>
	<br /><br />
	<div id="divTbDetial">
		<table id="tbl-pickingListDetail"></table>
		<div id="pager1"></div>
	</div>
	<br/>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="printPickingList">打印新版装箱清单 </button>
		<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text> </button>
	</div>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
</body>
</html>