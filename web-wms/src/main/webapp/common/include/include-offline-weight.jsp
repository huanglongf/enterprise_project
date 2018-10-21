<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/common/include/include-offline-weight.js' includeParams='none' encode='false'/>"></script>
<div id="shopQueryDialog2" style="display:none;">
<input type="hidden" id="nowNum"/>
<input type="hidden" id="tNum"/>
<input type="hidden" id="orderId"/>
	<!-- <form id="shopQueryDialogForm" name="shopQueryDialogForm"> -->
			<table width="95%">
			<tr id="delNo">
				<td class="label">
					扫描快递单号
				</td>
				<td>
					<input id="transNo" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:200px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					扫描耗材
				</td>
				<td>
					<input id="skuId" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:200px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					包裹重量
				</td>
				<td>
					<input id="autoWeigth" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;" readonly="readonly"/>kg<button id="restWeight" loxiaType="button" class="confirm">重启称</button>
				</td>
			</tr>
			<tr>
				<td class="label">
					手工录入包裹重量
				</td>
				<td>
					<input id="packageWeight" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;"/>kg <div id="button2" style="display: inline;"><!-- <button type="button" loxiaType="button" class="confirm" >打印电子面单</button> --></div>
				</td>
			</tr>
			<tr>
				<td class="label">
					立方信息
				</td>
				<td>
					<input id="volume" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;" readonly="readonly"/>m³<button type="button" loxiaType="button" id="btnShopQueryClose2" class="confirm">关闭</button>
				</td>
			</tr>
			<tr>
				<td class="label">
					称重包裹数量
				</td>
				<td>
					<input id="yNum" loxiaType="input" trim="true" name="" style="height: 25px; line-height: 25px;width:100px;"readonly="readonly"/>
				</td>
			</tr>
		</table>
		<button type="button" loxiaType="button" id="zhiSubmit" class="confirm" >打印电子面单</button>
		<span class="label">确认条码：</span><input id="okSubmit2" loxiaType="input" trim="true" style="width: 200px; height: 30px; line-height: 30px;"/>
	<!-- </form> -->
	<!-- <div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="btnShopQuery" >查询</button>
		<button type="button" loxiaType="button" id="btnShopFormRest" >重置</button>
	</div>
	<table id="tbl_shop_query_dialog"></table>
	<div id="tbl_shop_query_dialog_pager"></div>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnShopQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnShopQueryClose" >取消</button> -->
</div>

