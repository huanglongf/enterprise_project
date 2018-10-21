<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/show-rtw-dieking-line.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>退仓拣货执行明细（含短拣）</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divMain">
		<form id="formQuery" name="formQuery" >
			<table width="70%">
				<tr>
					<td width="13%" class="label">创建时间</td>
					<td width="18%"><input id="startDate" name="rtwDiekingCommend.startCreateTime1" loxiaType="date" showTime="true"/></td>
					<td width="13%" class="label">到</td>
					<td width="18%"><input id="endDate" name="rtwDiekingCommend.endCreateTime1" loxiaType="date" showTime="true"/></td>
					<td class="label">拣货批次编码</td>
					<td><input loxiaType="input"  trim="true" id="staCode" name="rtwDiekingCommend.batchCode"/></td>
					
				</tr>
				<tr>
				    <td width="13%" class="label">作业单类型</td>
					<td width="18%">
						<select loxiaType="select" id="staTypeList" name="rtwDiekingCommend.staType">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="61"><s:text name="采购退货出库"></s:text> </option>
							<option value="101"><s:text name="VMI退仓出库"></s:text> </option>
							<option value="102" ><s:text name="VMI退仓转店出库"></s:text> </option>
							<option value="64"><s:text name="代销出库"></s:text> </option>
							<option value="62"><s:text name="结算经销出库"></s:text> </option>
						</select>
					</td>
					<td class="label">作业单号</td>
					<td><input loxiaType="input"  trim="true" id="staCode" name="rtwDiekingCommend.staCode"/></td>
					<td class="label">相关单据号</td>
					<td><input loxiaType="input"  trim="true" id="staSlipCode" name="rtwDiekingCommend.staRefSlipCode"/></td>
				</tr>
				<tr>
				    <td width="13%" class="label">短拣标识</td>
					<td width="18%">
						<select loxiaType="select" id="statusList" name="rtwDiekingCommend.status">
							<option value="1" selected="selected"><s:text name="短拣"></s:text> </option>
							<option value="2"><s:text name="非短拣"></s:text> </option>
							<option value="3"><s:text name="未拣货"></s:text> </option>
							<option value="6"><s:text name="全部"></s:text> </option>
						</select>
					</td>
					<td class="label">SKU编码</td>
					<td><input loxiaType="input"  trim="true" id="staCode" name="rtwDiekingCommend.skuCode"/></td>
				</tr>
				<tr>
				    <td class="label"  style="color: red;" width="13%"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="20%">
						<select  class="special-flexselect" id="shopLikeQuery" name="shopLikeQuery" data-placeholder="*请输入店铺名称">
						</select>
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					</td>
					<td colspan="2">
						<div id="showShopList">
						</div>
						<input type="hidden" value="" id="postshopInput" />
						<input type="hidden" value="" id="postshopInputName" />
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button name="query" id="query" type="button" loxiaType="button" class="confirm">查询</button>
			<button id="btnReset" type="button" loxiaType="button" >重置</button>
		</div>
		<table id="tbl_sta"></table>
		<div id="tbl_sta_page"></div>
	</div>
	<jsp:include page="/common/include/include-shop-query-multi-invoice.jsp"></jsp:include>
</body>
</html>