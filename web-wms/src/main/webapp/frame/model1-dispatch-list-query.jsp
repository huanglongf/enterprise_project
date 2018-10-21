<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="label.warehouse.pl.dispatch1.query"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/model1-dispatch-list-query.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<!-- 这里是页面内容区 -->
	<s:text id="pselect" name="label.please.select"/>
		<div id="showList">
			 <form id="plForm" name="plForm">
				<table>
					<tr>
						<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
						<td colspan="3">
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.formCrtime1" id="formCrtime" showTime="true"/>
							<s:text name="label.warehouse.pl.to"/>
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.toCrtime1" id="toCrtime" showTime="true"/>
						</td>
					
						<td class="label"><s:text name="label.warehouse.pl.formPickingTime"/></td>
						<td colspan="3">
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.formPickingTime1" id="formPickingTime" showTime="true"/>
							<s:text name="label.warehouse.pl.to"/>
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.toPickingTime1" id="toPickingTime" showTime="true"/>
						</td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.pl.formOutBoundTime"/></td>
						<td colspan="3">
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.formOutBoundTime1" id="formOutBoundTime" showTime="true"/>
							<s:text name="label.warehouse.pl.to"/>
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.toOutBoundTime1" id="toOutBoundTime" showTime="true"/>
						</td>
					
						<td class="label"><s:text name="label.warehouse.pl.formCheckedTime"/></td>
						<td colspan="3">
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.formCheckedTime1" id="formCheckedTime" showTime="true"/>
							<s:text name="label.warehouse.pl.to"/>
							<input loxiaType="date" style="width: 130px" name="allocateCargoCmd.toCheckedTime1" id="toCheckedTime" showTime="true"/>
						</td>
					</tr>
					<tr>
					    <td class="label"><s:text name="label.warehouse.pl.batchno"/></td>
						<td><input loxiaType="input" style="width: 130px" trim="true" name="allocateCargoCmd.code" id="code"/></td>
						<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
						<td style="width: 110px">
						<input loxiaType="input" trim="true" style="width: 130px"  name="allocateCargoCmd.staCode" id="staCode"/></td>
						<td class="label"><s:text name="label.warehouse.inpurchase.refcode"/></td>
						<td style="width: 110px"><input loxiaType="input" style="width: 130px" name="allocateCargoCmd.refSlipCode" id="refSlipCode"/></td>
						<td class="label"><s:text name="label.warehouse.pl.status"/></td>
						<td style="width: 100px">
							 <s:select list="plStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="allocateCargoCmd.intStatus" id="status" headerKey="" headerValue="%{pselect}"/>
						</td>
					</tr>
					<tr>
					<td class="label" width="13%" id="wnames" hidden>仓库</td>
					<td width="20%">
						<select id="wselTrans" loxiaType="select" loxiaType="select" hidden>
							<option value="">--请选择仓库--</option>
						</select>
					</td>
					</tr>
				</table>
			</form>
			<div class="buttonlist">
				<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
				<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
			</div>
			<div id="pager"></div>

	<div id="div-waittingList"  class="hidden">
		<table id="tbl-waittingList"></table>
	</div>
</div>
<div id="div2" class="hidden">

	<table width="90%">
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.batchno"></s:text> </td>
			<td>
				<label id="dphCode"></label>
			</td>
			<td class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
			<td id="dphStatus"></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
			<td width="20%" id="creator" ></td>
			<td class="label" class="15%"><s:text name="label.wahrhouse.sta.operator"></s:text> </td>
			<td id="operator"></td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.pltotal"></s:text> </td>
			<td width="15%" id="planBillCount">0</td>
<%-- 			<td class="label" width="15%"><s:text name="label.warehouse.pl.plcomplete"></s:text> </td>
			<td width="15%" id="checkedBillCount">0</td>
			<td class="label" width="15%"><s:text name="label.warehouse.pl.plship"></s:text> </td>
			<td width="15%" id="sendStaQty"></td> --%>
			<td class="label"><s:text name="label.warehouse.pl.prototal"></s:text> </td>
			<td id="planSkuQty"></td>
		</tr>
<%-- 		<tr>

			<td class="label"><s:text name="label.warehouse.pl.procomplete"></s:text> </td>
			<td id="checkedSkuQty">0</td>
			<td class="label"><s:text name="label.warehouse.pl.proship"></s:text> </td>
			<td id="sendSkuQty">0</td>
		</tr> --%>
		<tr>
			<td class="label"><s:text name="label.wahrhouse.pl.picking.time"></s:text> </td>
			<td id="pickingTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.checked.time"></s:text> </td>
			<td id="checkedTime"></td>
			<td class="label"><s:text name="label.wahrhouse.pl.last.send.time"></s:text> </td>
			<td id="lastSendTime"></td>
		</tr>
		<tr><td colspan="6"><input type="hidden" id="pickinglistid" value=""></input></td></tr>
	</table>
	<br /><br />
		<div id="divTbDetial"><table id="tbl-orderDetail"></table></div>
		<div id="divTbslipCode"><table id="tbl-slipCode"></table></div>
		<div id="btnlist" class="buttonlist" >
			<button loxiaType="button" class="confirm" id="finish"><s:text name="label.wahrhouse.handoverlist.status.finish"></s:text> </button>
			<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text> </button>
		</div>
	 <div id="btnlist1" class="buttonlist" >
		<button loxiaType="button" class="confirm" id="back1"><s:text name="button.back"></s:text> </button>
	 </div> 
</div>

	</body>
</html>