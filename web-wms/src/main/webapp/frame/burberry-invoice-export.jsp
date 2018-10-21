<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>

<style>
#div-inventory-detail {background-color: #fff;  color: #2E6E9E;}
#div-inventory-detail table {border: 1px solid #2E6E9E;}
#div-inventory-detail tr {border: 1px solid #BECEEB;}
#div-inventory-detail th, #div-inventory-detail td { padding: 3px; border-right-color: inherit; border-right-style: solid; border-right-width: 1px;
		border-bottom-color: inherit; border-bottom-style: solid; border-bottom-width: 1px;
		}
#div-inventory-detail thead{background-color: #EFEFEF;}
</style>

<script type="text/javascript" src="../scripts/frame/burberry-invoice-export.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">销售-发票导出</a></li>
			<li><a href="#tabs-2">退换货-发票导出</a></li>
			<li><a href="#tabs-3">配货批-发票导出</a></li>
		</ul>
		<div id="tabs-1">
			<div id="query-order-list">
				<form name="queryForm" id="queryForm">
				<table>
					<tr>
						<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
						<td width="15%"><input loxiaType="date" trim="true" name="createTime" showTime="true"/></td>
						<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
						<td width="15%"><input loxiaType="date" trim="true" name="endCreateTime" showTime="true"/></td>
						<td colspan="4"><input type="hidden" name="staCmd.owner" value='1博柏利官方旗舰店' /></td>
					</tr>
					<tr>
						<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
						<td width="15%"><input loxiaType="input" trim="true" name="staCmd.code"/></td>
						<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
						<td width="15%"><input loxiaType="input" trim="true" name="staCmd.refSlipCode"/></td>
						<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.stastatus"></s:text> </td>
						<td width="15%">
							<select  loxiaType="select" name="staCmd.status1" id="staStatus">
								<option></option>
								<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text></option>
								<option value="3"><s:text name="label.warehouse.sta.status.checked"></s:text></option>
								<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text></option>
								<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text></option>
							</select>
						</td>
						<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
						<td width="15%"><input loxiaType="input" trim="true" name="staCmd.trackingNo"/></td>
					</tr>
				</table>
			</form>
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text></button>
				<button type="button" loxiaType="button" id="reset" ><s:text name="button.reset"></s:text></button>
			</div>
			<table id="tbl-orderList"></table>
			<div id="pager"></div>
		</div>
		<div id="order-detail" class="hidden">
			<input type="hidden" id="sta_id">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="15%"><span id="createTime"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.operat"></s:text></td>
					<td width="15%"><span id="finishTime"></span></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.owner"></s:text> </td>
					<td width="15%"><span id="owner"></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><span id="code"></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
					<td width="15%"><span id="refSlipCode"></td>
					<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.stastatus"></s:text> </td>
					<td width="15%"><span id="status"></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td width="15%"><span id="lpcode"></td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
					<td width="15%"><span id="trackingNo"></td>
					<td width="10%" class="label"><s:text name="label.warehouse.other.operator"></s:text> </td>
					<td width="15%"><span id="operator"></td>
				</tr>
			</table>
			<br />
			<table id="tbl-order-detail"></table>
			<!-- <table id="tbl-order-detail"></table>-->
			<div class="buttonlist">
				<button type="button" loxiaType="button" id="export" class="confirm" ><s:text name="button.export.invoice"></s:text> </button>
				<button loxiaType="button" class="confirm" id="back"><s:text name="button.back"></s:text> </button>
			</div>
		</div>
		<iframe id="frmSoInvoice" class="hidden"></iframe>
	</div>
	<div id="tabs-2">
	<div id="query-order-list2">
		<form name="queryForm2" id="queryForm2">
			<table>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="15%"><input loxiaType="date" trim="true" name="createTime" showTime="true"/></td>
					<td width="10%" class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="15%"><input loxiaType="date" trim="true" name="endCreateTime" showTime="true"/></td>
					<td colspan="4"><input type="hidden" name="staCmd.owner" value='1博柏利官方旗舰店' /></td>
				</tr>
				<tr>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.code"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.refSlipCode"/></td>
					<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.stastatus"></s:text> </td>
					<td width="15%">
						<select  loxiaType="select" name="staCmd.status1" id="staStatus2">
							<option></option>
							<option value="1"><s:text name="label.warehouse.sta.status.created"></s:text></option>
							<option value="2"><s:text name="label.warehouse.sta.status.occupied"></s:text></option>
							<option value="3"><s:text name="label.warehouse.sta.status.checked"></s:text></option>
							<option value="4"><s:text name="label.warehouse.sta.status.intransit"></s:text></option>
							<option value="10"><s:text name="label.warehouse.sta.status.finished"></s:text></option>
						</select>
					</td>
					<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
					<td width="15%"><input loxiaType="input" trim="true" name="staCmd.trackingNo"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search2" ><s:text name="button.search"></s:text></button>
			<button type="button" loxiaType="button" id="reset2" ><s:text name="button.reset"></s:text></button>
		</div>
		<table id="tbl-orderList2"></table>
		<div id="pager2"></div>
	</div>
	<div id="order-detail2" class="hidden">
		<input type="hidden" id="sta_id2">
		<table>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td width="15%"><span id="createTime2"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.operat"></s:text></td>
				<td width="15%"><span id="finishTime2"></span></td>
				<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.owner"></s:text> </td>
				<td width="15%"><span id="owner2"></td>
			</tr>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta"></s:text> </td>
				<td width="15%"><span id="code2"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.inpurchase.refcode"></s:text> </td>
				<td width="15%"><span id="refSlipCode2"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.tax.invoice.stastatus"></s:text> </td>
				<td width="15%"><span id="status2"></td>
			</tr>
			<tr>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
				<td width="15%"><span id="lpcode2"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
				<td width="15%"><span id="trackingNo2"></td>
				<td width="10%" class="label"><s:text name="label.warehouse.other.operator"></s:text> </td>
				<td width="15%"><span id="operator2"></td>
			</tr>
		</table>
		<br />
		<table id="tbl-order-detail2"></table>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="export2" class="confirm" ><s:text name="button.export.invoice"></s:text> </button>
			<button loxiaType="button" class="confirm" id="back2"><s:text name="button.back"></s:text> </button>
		</div>
	</div>
</div>
<div id="tabs-3">
	<s:text id="pselect" name="label.please.select"/>
		<div id="showList">
			 <form id="plForm" name="plForm">
				<table>
					<tr>
					    <td class="label"><s:text name="label.warehouse.pl.batchno"/></td>
						<td><input loxiaType="input" style="width: 130px" trim="true" name="allocateCargoCmd.code" id="code"/></td>
						<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
						<td style="width: 110px"><input loxiaType="input" trim="true" style="width: 130px"  name="allocateCargoCmd.staCode" id="staCode"/></td>
						<td class="label"><s:text name="label.warehouse.inpurchase.refcode"/></td>
						<td style="width: 110px"><input loxiaType="input" style="width: 130px" name="allocateCargoCmd.refSlipCode" id="refSlipCode"/></td>
						<td class="label"><s:text name="label.warehouse.pl.status"/></td>
						<td style="width: 100px">
							<select  loxiaType="select" name="allocateCargoCmd.intStatus" id="allocateCargoCmd_intStatus">
								<option></option>
								<option value="0"> 取消</option>
								<option value="1"> 等待配货</option>
								<option value="2"> 配货中</option>
								<option value="8"> 部分完成</option>
								<option value="10"> 全部完成</option>
								<option value="19"> 无法送达</option>
								<option value="20"> 配货失败</option>
							</select>
						</td>
					</tr>
					<tr><td colspan="8"><input type="hidden" name="allocateCargoCmd.owner" value='1博柏利官方旗舰店' /></td></tr>
				</table>
			</form>
			<div class="buttonlist">
				<button id="search3" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
				<button id="reset3" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
			</div>
			<div>
				<table id="tbl-dispatch-list"></table>
			</div>
			<div id="pager3"></div>
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
				<td id="operator3"></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="label" width="15%"><s:text name="label.warehouse.pl.pltotal"></s:text> </td>
				<td width="15%" id="planBillCount">0</td>
				<td class="label" width="15%"><s:text name="label.warehouse.pl.plcomplete"></s:text> </td>
				<td width="15%" id="checkedBillCount">0</td>
				<td class="label" width="15%"><s:text name="label.warehouse.pl.plship"></s:text> </td>
				<td width="15%" id="sendStaQty"></td>
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
			<tr><td colspan="6"><input type="hidden" id="pickinglistid" value=""></input></td></tr>
		</table>
		<br /><br />
		<div id="divTbDetial"><table id="tbl-orderDetail"></table></div>
		<div id="btnlist" class="buttonlist" >
			<button loxiaType="button" class="confirm" id="exportByPickingList" title="税控发票导出">税控发票导出</button>	
			<button loxiaType="button" class="confirm" id="back3"><s:text name="button.back"></s:text> </button>
		</div>
	</div>
	</div>
	</div>
</body>
</html>