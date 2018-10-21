<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="label.wahrhouse.inner.title"></s:text></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-lock-unlock.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="inventory_lock_div">
			<div id="div-lock-detail" >
				<form id="form_query" name="form_query">
					<table width="100%">
						<tr>
							<td class="label" width="8%"><s:text name="label.warehouse.pl.createtime"></s:text></td>
							<td width="12%">
								<input loxiaType="date" name="staCmd.createTime1" showTime="true"/>
							</td>
							<td class="label" width="5%" align="center"><s:text name="label.warehouse.pl.to"></s:text></td>
							<td width="12%">
								<input loxiaType="date" name="staCmd.finishTime1" showTime="true"/>
							</td>
							<td class="label" width="10%"><s:text name="label.warehouse.pl.sta"></s:text></td>
							<td width="15%">
								<input name="staCmd.code" loxiaType="input" trim="true"/>
							</td>
							<td class="label" width="10%"><s:text name="label.wahrhouse.sta.creater"></s:text></td>
							<td width="15%">
								<input name="staCmd.creater" loxiaType="input" trim="true"/>
							</td>
						</tr>
						<tr>
							<td class="label" width="8%"><s:text name="锁定原因"></s:text></td>
							<td>
							<select name="lockType" style="12%" loxiaType="select" id="lockReason">
									<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
									<option value="1"><s:text name="店铺暂不销售"></s:text> </option>
									<option value="2"><s:text name="残次待核实"></s:text> </option>
									<option value="3"><s:text name="库存差异待核实"></s:text> </option>
									<option value="10"><s:text name="其他"></s:text> </option>
							</select>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</form>			
				<div class="buttonlist">
					<button id="query" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
					<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>
				</div>
				<table id="tbl_inventory_lock_list"></table>
				<div id="pager2"></div>
				<br/>
				<div class="buttonlist"></div>
				<div>
					<font class="label">创建锁定作业单</font>
				</div>
				<div id="text"></div>
				<br />
				<div >
					<table width="100%">
						<tr>
							<td class="label" width="50px"><s:text name="label.warehouse.inpurchase.shop"></s:text> </td>
							<td colspan="8">
								<select id="shopS" name="" loxiaType="select" style="width: 150px">
									<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<%--
								 <s:select id="shopS" style="width: 150px" headerKey="" headerValue="" list="shops" loxiaType="select" listKey="id" listValue="innerShopCode" ></s:select> 
								--%>
							</td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.inner.one"></s:text></td>
							<td class="label"><s:text name="label.warehouse.whlocation.code"></s:text> </td>
							<td  style="width: 180px"><input loxiaType="input" trim="true" checkmaster="findSku" aclist="<%=request.getContextPath() %>/findAvailLocation.json" name="locationCode" id="locationCode" mandatory="true" /></td>
							<td class="label"><s:text name="label.wahrhouse.inner.sku"></s:text> </td>
							<td>
								<select style="width: 150px" loxiaType="select" id="selectSku">
									<option><s:text name="label.wahrhouse.sta.select"></s:text></option>
								</select>
								<div id="selectLocationSkuNum" class="hidden"></div>
							</td>
							<td class="label"><s:text name="label.warehouse.inventory.state"></s:text> </td>
							<td>
								<select style="width: 150px" loxiaType="select" id="selectStatus">
									<option><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<div id="statusSkuNum" class="hidden"></div>
							</td>
							
							<td class="label" >生产日期：</td>
							<td><input id="startDate1" name="startDate1" loxiaType="date" showtime="true" style="width: 160px" /></td>
							<td class="label">失效日期：</td>
							<td><input id="endDate1" name="endDate1" loxiaType="date" showtime="true" style="width: 160px" /></td>
							
							
							<td class="label"><s:text name="label.warehouse.inpurchase.count"></s:text> </td>
							<td style="width: 180px"><input loxiaType="number" min="1" trim="true" id="skuNum" /></td>
							<td><button loxiaType="button" class="confirm" id="searchOne"><s:text name="添加"></s:text></button></td>
						</tr>
						<tr>
							<td class="label"><s:text name="label.wahrhouse.inner.two"></s:text> </td>
							<td class="label"><s:text name="label.warehouse.inpurchase.skucode"></s:text> </td>
							<td><input loxiaType="input" trim="true" mandatory="true" checkmaster="findLocationCode" aclist="<%=request.getContextPath() %>/findAvailSku.json" name="skuCode" id="skuCode" /></td>
							<td class="label"><s:text name="label.wahrhouse.inner.loc"></s:text></td>
							<td>
								<select  style="width: 150px" loxiaType="select" id="selectLocation">
									<option><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<div id="selectLocationSkuNum1" class="hidden"></div>
							</td>
							<td class="label"><s:text name="label.warehouse.inventory.state"></s:text> </td>
							<td>
								<select  style="width: 150px" loxiaType="select" id="selectStatus1">
									<option><s:text name="label.wahrhouse.sta.select"></s:text> </option>
								</select>
								<div id="statusSkuNum1" class="hidden"></div>
							</td>							
							<td class="label" >生产日期：</td>
							<td><input id="startDate2" name="startDate2" loxiaType="date" showtime="true" style="width: 160px"/></td>
							<td class="label">失效日期：</td>
							<td><input id="endDate2" name="endDate2" loxiaType="date" showtime="true" style="width: 160px"/></td>
							<td class="label"><s:text name="label.warehouse.inpurchase.count"></s:text> </td>
							<td><input trim="true" loxiaType="number" min="1" id="skuNum1" /></td>
							<td><button loxiaType="button" class="confirm" id="searchTwo"><s:text name="添加"></s:text></button></td>
						</tr>
					</table>
				</div>
				<br />
				<button loxiaType="button" id="inventoryLockBatch" class="confirm" style="width: 130px"><s:text name="批量添加"></s:text> </button>
				<br />
				<br />
				<table id="tbl_temp_list"></table>
				<br/>
				<table width="100%">
					<tr>
						<td align="left" style="text-align:right;width: 70px;"><font class="label" ><s:text name="锁定原因："></s:text></font></td>
						<td>
							<select  style="width: 210px" loxiaType="select" id="txtLockReason">
									<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
									<option value="1"><s:text name="店铺暂不销售"></s:text> </option>
									<option value="2"><s:text name="残次待核实"></s:text> </option>
									<option value="3"><s:text name="库存差异待核实"></s:text> </option>
									<option value="10"><s:text name="其他（此时，用户需填写备注说明）"></s:text> </option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" style="text-align:right;width: 70px;"><font class="label" ><s:text name="备注："></s:text></font></td>
						<td>
							<textarea id="txtRmk" name="remork" loxiaType="input" rows="3"></textarea>
						</td>
					</tr>
				</table>
				<div class="buttonlist">
					<button loxiaType="button" id="deleteSku"><s:text name="button.wahrhouse.inner.delete"></s:text> </button>
					<button loxiaType="button" id="inventoryLock" class="confirm"><s:text name="确认锁定"></s:text> </button>
				</div>
			</div>
		</div>
		<div id="inventory_unlock_div" class="hidden">
			<div id="div-sta">
				<input type="hidden" id="input_staId" />
				<table>
					<tr>
						<td class="label"><s:text name="作业单号"></s:text> </td>
						<td><input loxiaType="input" id="input_staCode" readonly
							style="background-color: #f2f2f2; border: 0"  /></td>
						<td class="label"><s:text name="label.wahrhouse.sta.creater"></s:text> </td>
						<td><input loxiaType="input" id="input_creater"  readonly
							style="background-color: #f2f2f2; border: 0" /></td>
						<td class="label"><s:text name="锁定量"></s:text> </td>
						<td><input loxiaType="input" id="input_totalSkuQty" readonly
							style="background-color: #f2f2f2; border: 0" value="123" /></td>
					</tr>
					<tr>
						<td class="label"><s:text name="label.warehouse.transactiontype"></s:text> </td>
						<td><input loxiaType="input" id="input_staType" readonly
							style="background-color: #f2f2f2; border: 0" value="库存锁定" /></td>
						<td class="label"><s:text name="状态"></s:text> </td>
						<td><input loxiaType="input" id="input_staStatus" readonly
							style="background-color: #f2f2f2; border: 0" value="库存锁定" /></td>
						<td class="label"><s:text name="label.warehouse.inpurchase.createTime"></s:text> </td>
						<td><input loxiaType="input" id="input_createTime" readonly
							style="background-color: #f2f2f2; border: 0"/></td>
					</tr>
					<tr>
						<td class="label"><s:text name="备注"></s:text> </td>
						<td colspan="5" id="input_memo"></td>
					</tr>
				</table>
				<table id="tbl_unlock_list"></table>
				<div class="buttonlist">
					<button type="button" loxiaType="button" id="inventoryUnclock" class="confirm"><s:text name="解锁"></s:text> </button>
					<button type="button" loxiaType="button" id="back" ><s:text name="button.back"></s:text> </button>
				</div>
			</div>
		<jsp:include page="/frame/inventory-lock-unlock-batch.jsp"></jsp:include>
		</div>
</body>
</html>