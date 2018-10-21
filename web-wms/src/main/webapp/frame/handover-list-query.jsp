<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.executeResult{
		background-color: white;
		border: 1px gray solid;	
	}
	
	.table{
		border:1px gray solid;	
		margin: 0px;
		padding: 0px
	}

	
</style>
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.outbound.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/handover-list-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:text name="label.wahrhouse.handoverlist.pickinglist"></s:text></a></li>
		<li><a href="#tabs-2"><s:text name="label.wahrhouse.handoverlist.current.package"></s:text></a></li>
	</ul>
	<div id="tabs-1">
		<div id="divHandOverList">
		<form id="query_form">
		<table>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.code"></s:text> </td>
				<td style="width: 140px"><input name="handOverList.code" loxiaType="input" style="width: 120px" trim="true" /></td>
				<td class="label" style="width: 100px;"><s:text name="label.warehouse.pl.status"></s:text> </td>
				<td><select name="handOverList.hoIntStatus" loxiaType="select" style="width: 80px">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					<option value="1"><s:text name="label.wahrhouse.handoverlist.status.created"></s:text> </option>
					<option value="10"><s:text name="label.wahrhouse.handoverlist.status.finish"></s:text> </option>
					<option value="0"><s:text name="label.wahrhouse.handoverlist.status.cancel"></s:text> </option>
				</select></td>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
				<td style="width: 150px"><input name="handOverList.trackingNo" loxiaType="input" style="width: 120px" trim="true" /></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
				<td><select id="selTrans" name="handOverList.lpcode" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
				</select></td>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text> </td>
				<td><input loxiaType="input" name="handOverList.partyBOperator" style="width: 120px" trim="true"/></td>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text> </td>
				<td><input loxiaType="input" name="handOverList.paytyBPassPort" style="width: 120px" trim="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text> </td>
				<td><input loxiaType="input" name="handOverList.partyAOperator" style="width: 120px" trim="true"/></td>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text> </td>
				<td><input loxiaType="input" name="handOverList.paytyAMobile" style="width: 120px" trim="true"/></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td colspan="5"><input loxiaType="date" name="handOverList.createStratTime1"  style="width: 120px" trim="true" value="" showTime="true"/><s:text name="label.warehouse.to"></s:text><input loxiaType="date" name="handOverList.createEndTime1" style="width: 120px" trim="true" value="" showTime="true"/></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.handovertime"></s:text> </td>
				<td colspan="5"><input loxiaType="date" name="handOverList.handOverStartTime1" style="width: 120px" trim="true" value="" showTime="true"/><s:text name="label.warehouse.to"></s:text><input loxiaType="date" name="handOverList.handOverEndTime1" style="width: 120px" trim="true" value="" showTime="true"/></td>
			</tr>
		</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
			<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
		</div>
		<table id="tbl-hand-over-list"></table>
		<div id="pager"></div>
		</div>
		<div id="allDelete" class="hidden">
			<div style="text-align:center;font-size:25px;color:red;font-weight:bold">交接单明细行已全部作废该交接单也被作废</div>
			<div class="buttonlist" >
				<button id="back3" loxiaType="button">返回</button>
			</div>
		</div>		
		<div id="divHandOverDeital" class="hidden">
		<table width="600px">
			<tr>
				<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.code"></s:text> </td>
				<td id="code" width="120px"></td>
				<td class="label" width="120px"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td id="createTime"></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.totalpgnum"></s:text> </td>
				<td><span id="packageCount"></span><s:text name="label.wahrhouse.handoverlist.count"></s:text> </td>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.totalweight"></s:text> </td>
				<td><span id="totalWeight"></span><s:text name="label.warehouse.package.weight.kg"></s:text> </td>
			</tr>
		</table>
		<table width="600px" id="tbl_hand_over_list_info"> <!-- 完成 -->
			<tr>
				<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text> </td>
				<td id="partyAOperator" width="120px"></td>
				<td class="label" width="120px"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text></td>
				<td id="partyAMobile"></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text></td>
				<td id="partyBOperator"></td>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBMobile"></s:text></td>
				<td id="partyBMobile"></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text></td>
				<td id="paytyBPassPort"></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<form id="handOver_form" class="hidden"><!-- 新建 handOverList   -->
			<table width="600px">
				<tr>
					<td class="label" width="160px"><s:text name="label.wahrhouse.handoverlist.partyAOperator"></s:text></td>
					<td width="120px">
						<input type="hidden" name="handOverList.id" id="handOverListid" value="" />
						<input loxiaType="input" style="width: 120px" name="handOverList.partyAOperator" id="partyAOperator1" trim="true"  mandatory="true"/>
					</td>
					<td class="label" width="120px"><s:text name="label.wahrhouse.handoverlist.paytyAMobile"></s:text></td>
					<td ><input loxiaType="input" style="width: 120px" name="handOverList.paytyAMobile" id="partyAMobile1" trim="true"  mandatory="true"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.wahrhouse.handoverlist.partyb"></s:text></td>
					<td ><input loxiaType="input" style="width: 120px" name="handOverList.partyBOperator" id="partyBOperator1" trim="true"  mandatory="true"/></td>
					<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBMobile"></s:text> </td>
					<td ><input loxiaType="input" style="width: 120px" name="handOverList.paytyBMobile" id="partyBMobile1" trim="true"  mandatory="true"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.wahrhouse.handoverlist.paytyBPassPort"></s:text> </td>
					<td ><input loxiaType="input" style="width: 120px" name="handOverList.paytyBPassPort" id="paytyBPassPort1" trim="true"  mandatory="true"/></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
		<br />
		<table id="tbl-detial"></table>
		<div class="buttonlist">
			<button id="brnDetialHo" loxiaType="button" class="confirm"><s:text name="button.handover"></s:text> </button>
			<button id="btnDetialSavePrint" loxiaType="button" class="confirm"><s:text name="button.saveAndPrint"></s:text> </button>
			<button id="btnDetialPrint" class="confirm hidden" loxiaType="button"><s:text name="button.print"></s:text> </button>
<%-- 			<button type="button" loxiaType="button" id="export" class="confirm hidden" ><s:text name="label.warehouse.export.trans"></s:text> </button>--%>			<button id="back" loxiaType="button"><s:text name="button.back"></s:text> </button>
		</div>
		</div>
	</div>
	
	<div id="tabs-2">
		<table>
			<tr>
				<td>
					<table id="tbl_handover_total"></table>
				</td>
				<td valign="top">
					<button id="flush" type="button" loxiaType="button"><s:text name="label.warehouse.flush"></s:text></button>
				</td>
			</tr>
		</table>
		
	</div>
</div>
<iframe id="frmTrans" class="hidden"></iframe>
</body>
</html>