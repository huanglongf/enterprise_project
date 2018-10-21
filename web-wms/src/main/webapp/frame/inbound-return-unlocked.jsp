<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.return.unlocked"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/inbound-return-unlocked.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
.a{
	font-size: 20px;
	font-weight:bold;
}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->

<div id="div-sta-list" >

<form id="form_query">
	<table>
		<tr>
			<td class="label" width="20%"><s:text name="label.warehouse.inpurchase.shop"/></td>
			<td width="30%">
				<div style="float: left">
					<select id="companyshop" name="sta.owner" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>
				</div>
				<div style="float: left">
					<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
				</div>
			</td>					
			<td class="label"><s:text name="label.warehouse.inpurchase.code"/></td>
			<td><input loxiaType="input" id = "barCode" trim="true" name="barCode" /></td>
		</tr>
		<tr>
			<td class="label">作业单号</td>
			<td><input loxiaType="input" id = "staCodeId" trim="true" name="sta.code" /></td>
			<td class="label" style="color:blue">相关单据号（申请单号）</td>
			<td><input loxiaType="input" id = "slipcodeId" trim="true" name="sta.refSlipCode" /></td>
		</tr>
		<tr>
			<td class="label" width="20%">订单号（相关单据1）</td>
			<td width="30%"><input loxiaType="input" id = "slipcode1Id"  trim="true" name="orderCode" /></td>
			<td class="label" width="20%" style="color:blue">平台单据号（相关单据2）</td>
			<td width="30%"><input loxiaType="input" trim="true" name="taobaoOrderCode" /></td>
		</tr>
		<tr>
			<td class="label" width="20%" style="color:blue">商城自主退单号（相关单据3）</td>
			<td width="30%"><input loxiaType="input" trim="true" name="sta.slipCode3" /></td>
			<td class="label" width="20%" >快递单号</td>
			<td width="30%"><input loxiaType="input" id = "trackingNoId" trim="true" name="trackingNo" /></td>
		</tr>
		<tr>
			<td class="label">创建时间</td>
			<td>
				<input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
			<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
			<td><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
		</tr>
		<tr>
			<td class="label" width="20%"><s:text name="label.warehouse.pl.sta.delivery"></s:text></td>
			<td width="30%">
				<select name="lpcode" id="selLpcode" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label" width="20%">原订单收货人</td>
			<td width="30%"><input loxiaType="input" trim="true" name="receiver" /></td>
			<td class="label" width="20%">原订单收货人联系电话</td>
			<td width="30%"><input loxiaType="input" trim="true" name="receiverPhone" /></td>
		</tr>
		 
		</table>
	</form>
	<div class="buttonlist">
		<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
		<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
	</div>
	
	<table id="tbl-inbound-purchase"></table>
	<div id="pager"></div>
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>

</div>

<div id="div-sta-detail" class="hidden">
	<input type="hidden" name="staId" id="staId"/>
	<input type="hidden" name="stvId" id="stvId"/>
	<table>
		<tr>
			<td class="label"><s:text name="label.warehouse.pl.sta"/>：</td>
			<td id="staCode"></td>
			<td class="label"><s:text name="label.warehouse.inpurchase.refcode"/>：</td>
			<td id="refSlipCode"></td>
		</tr>
		<tr>
			<td class="label">店铺：</td>
			<td id="ownerId"></td>
			<td class="label">创建时间：</td>
			<td id="createTime"></td>
		</tr>
	</table>
	<br/>
	<div id="div-sta-detail1" class="hidden">
	<table>
		<tr>
			<td class="label">快递单号</td>
			<td>
				<input loxiaType="input" id="shortcut_code" trim="true" />
			</td>
			<td class="label" width="20%"><s:text name="label.warehouse.pl.sta.delivery"></s:text>： </td>
			<td width="30%">
				<select name="lpcode" id="lpcode" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option> 
				</select>
			</td>
		</tr>
		<tr>
			<td class="label" width="20%">退货原因： </td>
			<td width="30%" >
				<select name="returnReasonType" id="returnReasonType" loxiaType="select">
					<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option> 
					<option value="1">7天无理由退/换货</option>
					<option value="2">商品质量问题</option>
					<option value="3">实物与订购商品不符</option>
					<option value="4">非COD订单客户拒收</option>
					<option value="5">COD订单客户拒收</option>
					<option value="6">三十天无理由退货</option>
					<option value="7">COD正常退货</option>
					<option value="99">其他</option>
				</select>
			</td>
			<td colspan = "2"></td>
		</tr>
		<tr>
			<td class="label"  width="20%">备注：</td>
			<td colspan = "3">
				<textarea loxiaType="input" trim="true" maxlength="500" id = "returnReasonMemo" name="returnReasonMemo"></textarea>
			</td>
		</tr>
		<table id="tbl-commodity-detail"></table>
		<div id="pager1"></div>	
		
		<tr>
			<td>
				<button loxiaType="button" class="confirm" id="unlockd_btn">解锁</button>
			</td>
			<td>
				<button loxiaType="button"  id="backto"><s:text name="button.toback"/></button>
			</td>
			<td colspan = "2">
				<span class="label">确认条码：</span>
				<input id="confirmWeightInfoValue" loxiaType="input" trim="true" style="width: 100px;"/>
			</td>
		</tr>
	</table>
	</div>
	<div id="div-sta-detail2" class="hidden">
	<table><tr><td id="owner" class="a"></td><td><font color="red" size="30px">作废</font></td></tr>
	<tr><td><button loxiaType="button"  id="backto1"><s:text name="button.toback"/></button></td></tr></table>
	</div>
	

</div>
