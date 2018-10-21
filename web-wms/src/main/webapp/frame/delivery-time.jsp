<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/delivery-time.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="staInfo">
	<form action="/generationPendingList.json" method="post" id="query-form">
		<span class="label" style="font-size: 15px;" >基础条件</span>
		<table id="filterTable" width="95%">
				<tr>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.createtime"></s:text> </td>
					<td width="20%"><input loxiaType="date" name="fromDate" showtime="true"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="20%"><input loxiaType="date" name="toDate" showtime="true"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="20%"><input name="sta.code" loxiaType="input" trim="true"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td><input name="sta.refSlipCode" loxiaType="input" trim="true"/></td>
					<td width="10%" class="label">相关单据号1</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode1"/></td>
					<td width="10%" class="label">相关单据号2</td>
					<td width="15%"><input loxiaType="input" trim="true" name="sta.slipCode2"/></td>
				</tr>
				
				<tr>
					<td class="label" style="color:blue">运单时限类型</td>
					<td>
						<select name="transTimeType" id="transTimeType" loxiaType="select" >
							<option value="">--请选择运单时限类型--</option>
							<option value="1">普通</option>
							<option value="3">及时达</option>
							<option value="5">当日</option>
							<option value="6">次日</option>
							<option value="7">次晨</option>
							<option value="8">云仓专配次日</option>
							<option value="9">云仓专配隔日</option>
						</select>
					</td>
				</tr>
			</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		<br /><br />
		<table id="tbl-staList-query"></table>
		<div id="pager_query"></div>
		<br/>
		<button type="button" loxiaType="button" id="confirm" class="confirm"><s:text name="升单"></s:text> </button>
		<button type="button" loxiaType="button" id="outconfirm" class="confirm"><s:text name="不升单"></s:text> </button>
		<br/><br/>
	</div>
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
			<td colspan="3"><span id="s_owner"></span></td>
		</tr>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
			<td><span id="s_type"></span></td>
			<td class="label"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
			<td width="15%"><span id="s_trans"></span></td>
			<td class="label"><s:text name="label.warehouse.sta.isneedinvoice"></s:text> </td>
			<td><span id="s_inv"></span></td>
		</tr>
	</table>
	<table id="tbl_detail_list"></table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" id="back" class="confirm"><s:text name="升单"></s:text> </button>
		<button type="button" loxiaType="button" id="back1" class="outconfirm"><s:text name="不升单"></s:text> </button>
	</div>
	<jsp:include page="/common/include/include-shop-query-multi-invoice.jsp"></jsp:include>
</div>
		
</body>
</html>