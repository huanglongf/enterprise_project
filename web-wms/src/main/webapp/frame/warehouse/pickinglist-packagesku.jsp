<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-packagesku.js"' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/hight_province_config.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

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
					<td class="label" style="color: red;"><s:text name="label.warehouse.sta.isneedinvoice"></s:text> </td>
					<td>
						<select id="selIsNeedInvoice" name="isNeedInvoice" loxiaType="select">
							<option value="">--请选择是否需要发票--</option>
							<option value="1"><s:text name="label.warehouse.sta.isneedinvoice.true"></s:text> </option>
							<option value="0"><s:text name="label.warehouse.sta.isneedinvoice.false"></s:text></option>
						</select>
					</td>
					<td class="label">优先发货城市</td>
					<td>
						<select name="city" id="priorityCity" width="200px" multiple="multiple"></select>
					</td>
				</tr>
				
				<tr>
					<td class="label" style="color: red;"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td>
						<select name="sta.staDeliveryInfo.lpCode" id="selLpcode" loxiaType="select">
							<option value="">--请选择物流商--</option>
						</select>
					</td>
					<td class="label"  style="color: red;">是否特殊处理</td>
					<td>
						<select name="sta.isSpecialPackaging" id="selIsSpPg" loxiaType="select">
							<option value="false">否</option>
							<option value="true">是</option>
						</select>
					</td>
					<td class="label" style="color:blue;">是否COD订单</td>
					<td >
						<select name="isCod" id="isCod" loxiaType="select">
							<option value="">不限制</option>
							<option value="1">COD订单</option>
							<option value="0">非COD订单</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">状态</td>
					<td>
						<select name="sta.status" id="status" loxiaType="select">
							<option value="">--请选择状态--</option>
							<option value="CREATED">已创建</option>
							<option value="FAILED">配货失败</option>
						</select>
					</td>
					<td class="label">商品件数:</td>
					<td>
						<select name="sta.skuQty" id="skuQty" loxiaType="select">
							<option value="">--请选择--</option>
							<option value="2">2件</option>
							<option value="3">3件</option>
							<option value="4">多件</option>
						</select>
					</td>
					<td></td><td></td>
				</tr>
				<tr>
					<td class="label" style="color:black">是否澳门件</td>
					<td>
						<select name="sta.isMacaoOrder" id="isMacaoOrder" loxiaType="select" >
							<option value="false">否</option>
							<option value="true">是</option>
						</select>
					</td>
					<td class="label" style="color:black">是否打印海关单</td>
					<td>
						<select name="sta.isPrintMaCaoHGD" id="isPrintMaCaoHGD" loxiaType="select" >
							<option value="false">否</option>
							<option value="true">是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">商品列表</td>
					<td colspan="5">
						<input id="skuName" loxiaType="input" trim="true" readonly style="float:left;width:700px;"/>
						<input name="sta.packageSku" loxiaType="input" hidden id="packageSkuId" trim="true"/>
						<button type="button" loxiaType="button" class="confirm" id="skuSelect" style="float:left;width:75px;height:21px;">查询</button>
					</td>
				</tr>
				<tr>
					<td class="label"  style="color: red;" width="13%"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="20%">
						<select  class="special-flexselect" id="shopLikeQuery" name="shopLikeQuery" data-placeholder="*请输入店铺名称">
						</select>
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					</td>
					<td class="label">优先发货省份</td>
					<td>
						<select name="priority" id="priorityId" width="200px" multiple="multiple">
						</select>
					</td>
					<td colspan="2">
						<div id="showShopList">
						</div>
						<input type="hidden" value="" id="postshopInput" />
						<input type="hidden" value="" id="postshopInputName" />
					</td>
				</tr>
					<tr>
					<td class="label"  style="color: red;" width="13%">渠道组 </td>
					<td width="20%">
						<div>
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShopGroup" >查询渠道</button>
						</div>
					</td>
					<td colspan="4">
						<div id="showShopList1">
						</div>
						<input type="hidden" value="" id="postshopInput1" />
						<input type="hidden" value="" id="postshopInputName1" />
					</td>
				</tr>
				<tr>
					<td class="label"  style="color: red;" width="13%">拣货区域</td>
					<td width="20%">
						<div>
							<button type="button" loxiaType="button" class="confirm" id="btnSearchArea" >查询区域</button>
						</div>
					</td>
					
					<td class="label"  style="color: red;" width="13%">是否预售订单</td>
					<td width="20%">
						<div>
							<select name="sta.isPreSale" id="isPreSale" loxiaType="select">
							<option value="" selected="selected">非预售订单</option>
							<option value="1">预售订单</option>
						</select>
						</div>
					</td>
					
					<td colspan="4">
						<div id="AeraList1">
						</div>
						<input type="hidden" value="" id="AreaInput1" />
						<input type="hidden" value="" id="AreaInputName1" />
					</td>
				</tr>
				<tr>
					<td class="label"  style="color: red;" width="13%"></td>
					<td colspan="5">
							<s:if test="CountArea"><input type="checkbox" value="<s:property value='CountArea'/>" checked="checked" id="CountArea" name="CountArea"/></s:if>
									<s:else>
										<input type="checkbox" value="<s:property value='CountArea'/>" id="CountArea" name="CountArea"/>
							</s:else>
							忽略已选拣货区域查询条件
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
		<br/><br/>
	<table>
			<tr>
				<td class="label">
					每批单据数，不填为无限制
				</td>
				<td width="60">
					<input loxiaType="number" id="autoSize" value="50"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					每批最少单据数，不填为无限制
				</td>
				<td width="60">
					<input loxiaType="number" id="minAutoSize" value="5"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					生成配货批数量，不填为全部创建
				</td>
				<td width="60">
					<input loxiaType="number" id="plCount" value="20"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button id="autoConfirm" type="button" loxiaType="button" class="confirm"><s:text name="button.pl.create.auto"></s:text> </button>
				</td>
			</tr>
	</table>
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
		<button type="button" loxiaType="button" id="back" class="confirm"><s:text name="button.back"></s:text> </button>
	</div>
	<jsp:include page="/common/include/include-shop-query-multi-invoice.jsp"></jsp:include>
	<div id="showSkuDialog">
		<table id="tbl_sku_query_dialog"></table>
		<div id="tbl_sku_query_dialog_pager"></div>
		<br/>
		<button type="button" loxiaType="button" class="confirm" id="btnSkuSelectConfirm" >确认</button>
		<button type="button" loxiaType="button" id="btnSkuSelectClose" >取消</button>
	</div>
</div>
</body>
</html>