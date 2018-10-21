<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>快递单号修改</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/express-order-edit.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
</style>
 <s:text id="pselect" name="label.please.select"/>
 
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<input type="hidden" name="lpcode" id="lpcode"/>
	<input type="hidden" name="staId" id="staId"/>
	<div id="staInfo">
		<div>
			 <form id="staForm" name="staForm">
				<table id="filterTable">
					<tr>
						<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
						<td colspan="3">
							<input loxiaType="date" style="width: 130px" name="expressOrderCmd.formCrtime1" id="formCrtime" showTime="true"/>
							<s:text name="label.warehouse.pl.to"/>
							<input loxiaType="date" style="width: 130px" name="expressOrderCmd.toCrtime1" id="toCrtime" showTime="true"/>
						</td>
						<td class="label"><s:text name="label.warehouse.pl.sta"/></td>
						<td style="width: 130px"><input loxiaType="input" trim="true" style="width: 130px"  name="expressOrderCmd.code" id="staCode"/></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.inpurchase.refcode"/></td>
						<td style="width: 130px"><input loxiaType="input" style="width: 130px" name="expressOrderCmd.refSlipCode" id="refSlipCode"/></td>
						<td class="label"><s:text name="label.warehouse.pl.sta.delivery"/></td>
						<td style="width: 130px">
						<select loxiaType="select" name="expressOrderCmd.lpcode" id="selTrans">
							<option value=""><s:text name="label.please.select"></s:text> </option>
						</select></td>
						<td class="label"><s:text name="label.warehouse.pl.sta.delivery.code"/></td>
						<td style="width: 130px"><input loxiaType="input" style="width: 130px" name="expressOrderCmd.trackingNo"/></td>
					</tr>
					<tr>
            <!--	<td class="label"><s:text name="label.warehouse.inpurchase.shop"/></td>
						<td style="width: 110px">
						<%--<s:select id="selShopId" headerKey="" headerValue="%{pselect}" name="expressOrderCmd.owner" list="shops" loxiaType="select" listKey="innerShopCode" listValue="innerShopCode" ></s:select> --%>
							<select id="selShopId" name="expressOrderCmd.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</td>-->
						<td class="label"><s:text name="label.wahrhouse.sta.creater"/></td>
						<td style="width: 110px"><input loxiaType="input" style="width: 130px" name="expressOrderCmd.crUser"/></td>
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
			<div class="clear"></div>
		</div>
		<br />
		<div>
			<table id="tbl-staList-query"></table>
			<div id="pager"></div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	<div id="staLineInfo" class="hidden">
		<table id="filterTable">
			<tr>
				<td class="label" width="15%"><s:text name="label.warehouse.pl.sta"></s:text> </td>
				<td width="15%"><span id="s_code"></span></td>
				<td class="label" width="15%"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
				<td width="20%" ><span id="s_createTime"></span></td>
				<td width="15%" class="label"><s:text name="label.warehouse.pl.status"></s:text> </td>
				<td width="15%"><span id="s_status"></span></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
				<td><span id="s_slipCode"></span></td>
				<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
				<td ><span id="s_owner"></span></td>
				<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
				<td width="15%"><span id="s_trans"></span></td>
			</tr>
		</table>
		<table id="tbl_detail_list"></table>
		<p><span class="label">作业单关联快递单号：</span><span id="trackingNoList"></span></p>
		<div id="stvlineListtable"></div>
		<div id="errBtnlist" class="buttonlist">
			<button loxiaType="button" class="confirm" id="edit">修改</button>
			<button type="button" loxiaType="button" id="back" class="confirm"><s:text name="button.back"></s:text> </button>
		</div>
	</div>
</body>
</html>