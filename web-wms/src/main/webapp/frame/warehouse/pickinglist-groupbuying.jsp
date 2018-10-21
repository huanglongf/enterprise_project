<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/multiple-select.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-groupbuying.js' includeParams='none' encode='false'/>"></script>
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
					<td width="20%"><input loxiaType="date" name="fromDate" showtime="true" id="fromDate"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="20%"><input loxiaType="date" name="toDate" showtime="true" id="toDate"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.sta"></s:text> </td>
					<td width="20%"><input name="sta.code" loxiaType="input" trim="true" id="sta_code"/></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
					<td><input name="sta.refSlipCode" loxiaType="input" trim="true" id="sta_refslipCode"/></td>
					<td class="label" >商品大小(<font style="color: red;">默认小件</font>)</td>
					<td id="skuConfigtd">
					</td>
					<td class="label" style="color: red;"><s:text name="label.warehouse.pl.sta.delivery"></s:text> </td>
					<td>
						<select name="sta.staDeliveryInfo.lpCode" id="selLpcode" loxiaType="select">
							<option value="">--请选择物流商--</option>
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
				    <td class="label"  style="color: red;" width="13%">是否预售订单</td>
					<td width="20%">
						<div>
							<select name="sta.isPreSale" id="isPreSale" loxiaType="select">
							<option value="" selected="selected">非预售订单</option>
							<option value="1">预售订单</option>
						</select>
						</div>
					</td>
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
					<td class="label"  style="color: red;" width="13%"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="20%">
						<select  class="special-flexselect" id="shopLikeQuery" name="shopLikeQuery" data-placeholder="*请输入店铺名称">
						</select>
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						<div id="showShopList">
						</div>
						<input type="hidden" value="" id="postshopInput" />
						<input type="hidden" value="" id="postshopInputName" />
					</td>
					
					<td class="label">优先发货城市</td>
					<td>
						<select name="city" id="priorityCity" width="200px" multiple="multiple"></select>
					</td>
					
					<td class="label">优先发货省份</td>
					<td  width="20%"> 
						<select name="priority" id="priorityId" width="200px" multiple="multiple">
						</select>
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
					
					<td colspan="2">
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
				<tr>
					<td class="label"  style="color: red;" width="13%">仓库区域</td>
					<td width="20%">
						<div>
							<button type="button" loxiaType="button" class="confirm" id="btnSearchWhArea" >查询区域</button>
						</div>
						<div id="whAeraList1">
						</div>
						<input type="hidden" value="" id="whAreaInput1" />
						<input type="hidden" value="" id="whAreaInputName1" />
					</td>
				</tr>
				<tr>
				<td class="label"  style="color: red;" width="13%"></td>
					<td colspan="5">
							<s:if test="whCountArea"><input type="checkbox" value="<s:property value='whCountArea'/>" checked="checked" id="whCountArea" name="whCountArea"/></s:if>
									<s:else>
										<input type="checkbox" value="<s:property value='whCountArea'/>" id="whCountArea" name="whCountArea"/>
							</s:else>
							忽略已选仓库区域查询条件
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:if test="whCountAreas"><input type="checkbox" value="<s:property value='whCountAreas'/>" checked="checked" id="whCountAreas" name="whCountAreas"/></s:if>
									<s:else>
										<input type="checkbox" value="<s:property value='whCountAreas'/>" id="whCountAreas" name="whCountAreas"/>
							</s:else>
							混合仓库区域
					</td>
				</tr>
				<tr>
				   <td  class="label" width="13%">规则编码</td>
				   <td  width="20%"><input loxiaType="input" name="ruleCode" id="ruleCode"/></td>
				   <td  class="label" width="13%">规则名称</td>
				   <td  width="20%"><input loxiaType="input" name="ruleName" id="ruleName"/></td>
				   <td><button type="button" id="addRuleName">保存</button></td>
				</tr>
				<tr>
				   <td class="label" width="13%">规则名称</td>
				   <td >
				      <select name="ruleName2" id="ruleName2" loxiaType="select" onchange="change();">
						</select>
				   </td>
				   <td><button type="button" id="deleteRuleName" >删除</button></td>
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
		<button type="button" loxiaType="button" id="confirm" class="confirm"><s:text name="button.pl.create"></s:text> </button>
		<br/><br/>
	<table>
	    <tr>
				<td class="label">
					每批单据数，不填为无限制
				</td>
				<td width="60">
					<input loxiaType="number" id="autoSize" value="10"/>
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
</div>
</body>
</html>