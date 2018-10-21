<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist.js"' includeParams='none' encode='false'/>"></script>
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
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label" width="13%">平台订单相关时间</td>
					<td width="20%"><input loxiaType="date" name="orderCreateTime" showtime="true"/></td>
					<td class="label" width="13%"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td width="20%"><input loxiaType="date" name="toOrderCreateTime" showtime="true"/></td>
					<td ></td>
					<td></td>
				</tr>
				
				<tr>
					<td class="label"  style="color: red;" width="13%"><s:text name="label.warehouse.sta.shop"></s:text> </td>
					<td width="20%">
						<select  class="special-flexselect" id="shopLikeQuery" name="shopLikeQuery" data-placeholder="*请输入店铺名称">
						</select>
						<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
					</td>
					<td colspan="4">
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
							是否合并拣货区域
					</td>
				</tr>
			</table>
	</form>
	<span class="label" style="font-size: 15px;" >配货规则</span>
	<table id="filterTable1">
			<tr>
				<td colspan="4">
					<div id="div-ruleList">
						<table id="tbl-ruleList"></table>
						<div id="pager_query_rule"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label" style="font-size: 20px;text-align:center">当前选择规则：</td>
				<td class="label" style="font-size: 20px;text-align:center;color:red"><span id="currentRule">当前选中规则名称</span></td>
				<td class="label" style="font-size: 20px;text-align:center">可创单据数：</td>
				<td class="label" style="font-size: 20px;text-align:center;color:red"><span id="canCount">可创单据数</span></td>
			</tr>
		</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
		<br/><br/> 
	<table>
			<tr>
				<td class="label">
					每批单据数，不填为无限制
				</td>
				<td width="60">
					<input loxiaType="number" id="autoSize" value="20"/>
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
				<td class="label">
					每单商品总数，不填为全部创建
				</td>
				<td width="60">
					<input loxiaType="number" id="sumCount" value="50"/>
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
<div id="skuConfigtd" class="hidden"></div>
<div id="staLineInfo" class="hidden">
	<jsp:include page="/common/include/include-shop-query-multi.jsp"></jsp:include>
	<div id="pickingListRuleDialog">
		<input id="selTransopc" hidden/>
		<table id="tbl_pickingList_rule_dialog"></table>
		<br/>
	</div>
</div>
	<table>
		<tr>
			<td class="label"  class="label" style="color: red;">ps:平台订单相关时间:非COD订单为付款时间、COD订单为创建时间。现有配货规则只支持单件、多件、团购混合创建</td>
		</tr>
	</table>
</body>
</html>