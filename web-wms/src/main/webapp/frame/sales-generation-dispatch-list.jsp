<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.genDph.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/sales-generation-dispatch-list.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>

</head>
<body contextpath="<%=request.getContextPath() %>">	
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
							<option value=""></option>
							<option value="1"><s:text name="label.warehouse.sta.isneedinvoice.true"></s:text> </option>
							<option value="0"><s:text name="label.warehouse.sta.isneedinvoice.false"></s:text></option>
						</select>
					</td>
					<td class="label">优先发货城市</td>
					<td>
						<select name="city" id="priorityCity" loxiaType="select">
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td class="label">商品编码</td>
					<td>
						<input name="sku.jmCode" loxiaType="input" trim="true"/>
					</td>
					<td class="label">扩展属性</td>
					<td>
						<input name="sku.keyProperties" loxiaType="input" trim="true"/>
					</td>
					<td class="label"  style="color: red;">是否特殊处理</td>
					<td>
						<select name="sta.isSpecialPackaging" id="selIsSpPg" loxiaType="select">
							<option value="false">否</option>
							<option value="true">是</option>
						</select>
					</td>
				<!--
					<td class="label">SKU编码</td>
					<td>
						<input name="sku.code" loxiaType="input" trim="true"/>
					</td>
				-->
				</tr>
				<tr>
					<td class="label">商品名称</td>
					<td>
						<input name="sku.name" loxiaType="input" trim="true"/>
					</td>
					<td class="label">商品条码</td>
					<td>
						<input name="sku.barCode" loxiaType="input" trim="true"/>
					</td>
					<td class="label" style="color: red;">
						<div isMqInvoice="true">使用流水开票</div>
					</td>
					<td>
						<div isMqInvoice="true"><input type="checkbox" id="isMqInvoice" /></div>
					</td>
				</tr>
				<tr>
					<td class="label">	<s:text name="label.warehouse.pl.sta.trantype"></s:text> </td>
					<td>
						<select name="sta.type" loxiaType="select">
							<option value="">请选择</option>
							<option value="OUTBOUND_SALES"><s:text name="label.wahrhouse.sta.type.salesoutbound"></s:text> </option>
							<option value="OUTBOUND_RETURN_REQUEST"><s:text name="label.wahrhouse.sta.type.changeoutbound"></s:text> </option>
							<option value="OUT_SALES_ORDER_OUTBOUND_SALES">外部销售出库</option>
						</select>
					</td>
					<td class="label" style="color: red;"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td>
						<select name="sta.staDeliveryInfo.lpCode" id="selLpcode" loxiaType="select">
							<option value=""></option>
						</select>
					</td>
					<td class="label" >行业</td>
					<td>
						<select name="sta.industryId" id="industryId" loxiaType="select">
							<option value=""></option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" >是否包含大件商品</td>
					<td>
						<input type="checkbox"  id="skuMaxLength"  name="sta.skuMaxLength"/>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label"  style="color: red;" width="13%"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="20%">
						<!-- <div style="float: left">
							<select id="shopId" name="shopId" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div> -->
						<div>
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>
					<td colspan="4">
						<div id="showShopList">
						</div>
						<input type="hidden" value="" id="postshopInput" />
					</td>
				</tr>
			</table>
			<span class="label" style="font-size: 15px;" >配货批核对模式附加条件</span>
			注: 商品种类数选择时必须配合单SKU，商品数量选择可以配合多SKU或其他任意条件包括商品种类数，多SKU查询为或者关系，单SKU无法与多SKU同时使用
			<table width="95%">
				<tr>
					<td class="label" width="13%">作业单商品<span style="color:red;">种类</span>数</td>
					<td width="20%">
						<select loxiaType="select" id="skuSelect" name="sta.skus" >
							<option value=""><s:text name="label.please.select"></s:text> </option>
							<option value="1">1种商品</option>
						</select>
					</td>
					<td class="label"  width="13%"><span style="color:red;">单</span>SKU编码</td>
					<td width="20%">
						<input name="sku.code" id="txtSkuCode" loxiaType="input" trim="true"/>
					</td>
					<td width="13%"></td>
					<td width="20%"></td>
				</tr>
				<tr>
					<td class="label" width="13%">作业单商品<span style="color:blue;">件</span>数</td>
					<td width="20%">
						<select loxiaType="select" id="skuQtySelect" name="sta.skuQty" >
							<option value="2"><s:text name="label.please.select"></s:text></option>
							<option value="1">1件商品</option>
						</select>
					</td>
					<td width="13%" class="label"><span style="color:blue;">多</span>SKU编码1</td>
					<td width="20%"><input name="skuCodeList[0]" loxiaType="input" trim="true"/></td>
					<td width="13%" class="label"><span style="color:blue;">多</span>SKU编码2</td>
					<td width="20%"><input name="skuCodeList[1]" loxiaType="input" trim="true"/></td>
				</tr>
				<tr>
					<td width="13%" class="label"><span style="color:blue;">多</span>SKU编码3</td>
					<td width="20%"><input name="skuCodeList[2]" loxiaType="input" trim="true"/></td>
					<td width="13%" class="label"><span style="color:blue;">多</span>SKU编码4</td>
					<td width="20%"><input name="skuCodeList[3]" loxiaType="input" trim="true"/></td>
					<td width="13%" class="label"><span style="color:blue;">多</span>SKU编码5</td>
					<td width="20%"><input name="skuCodeList[4]" loxiaType="input" trim="true"/></td>
				</tr>
			</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		<font class="label" style="color:red">　　默认查询近一个月的单据，否则请加上时间条件！</font>
		<br /><br />
		<table id="tbl-staList-query"></table>
		<div id="pager_query"></div>
		<br/>
		<button type="button" loxiaType="button" id="confirm" class="confirm"><s:text name="button.pl.create"></s:text> </button>
		<br/><br/>
	<table>
			<tr>
				<td class="label">
					每批单据数，不填为无限制
				</td>
				<td width="60">
					<input loxiaType="number" id="autoSize" value="100"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					生成配货批数量，不填为全部创建
				</td>
				<td width="60">
					<input loxiaType="number" id="plCount"/>
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
	<jsp:include page="/common/include/include-shop-query-multi.jsp"></jsp:include>
</div>
</body>
</html>
