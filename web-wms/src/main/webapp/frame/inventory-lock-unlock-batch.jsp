<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-lock-unlock-batch.js' includeParams='none' encode='false'/>"></script>
<div id="invLockBatchDialog">
		<table width="95%">
			<tr>
				<td class="label">
					店铺:
				</td>
				<td colspan="8">
					<select id="shopShow" name="shopShow"  loxiaType="select" style="width: 220px">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
					</select>&nbsp;<b style="color: red;">*</b>
				</td>
			</tr>
			<tr>
				<td class="label">
					SKU编码:
				</td>
				<td>
					<input loxiaType="input" id="skuCodeBatch" name="skuCodeBatch" />&nbsp;<b style="color: red;">*</b>
				</td>
				<td class="label">
					库位编码:
				</td>
				<td>
					<input loxiaType="input" id="locationCodeBatch" name="locationCodeBatch" />&nbsp;<b style="color: red;">*</b>
				</td>
			</tr>
			<tr>
				<td class="label">
					生产日期:
				</td>
				<td>
					<input loxiaType="date" id="productionDate" name="productionDate" showTime="true"/>
				</td>
				<td class="label">
					失效日期:
				</td>
				<td>
					<input loxiaType="date" id="expireDate" name="expireDate" showTime="true"/>
				</td>
				<td class="label">
					库存状态:
				</td>
				<td>
						<select id="inventoryStatusId"  loxiaType="select" style="width: 160px">
							<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option>
						</select>
				</td>
			</tr>
			<tr>
				<td><button id="batchQuery" loxiaType="button" class="confirm"><s:text name="button.search"/></button></td>
			</tr>
		</table>
	<br/>
	<table id = "tbl_inventory_lock_batch_list"></table>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="addInvBatch" >确认添加</button>
	<button type="button" loxiaType="button" id="cancel" >取消</button>
	<input type="hidden" id="idsList" />
</div>

