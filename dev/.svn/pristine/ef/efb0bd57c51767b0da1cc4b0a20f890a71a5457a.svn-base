<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
function cssaa(){
    var b=document.getElementById("cssContent").scrollLeft;
    document.getElementById("csstitle").scrollLeft=b;
  }
function pageJump() {
	query_sr_settlemented(1);
}
</script>
<div id="carrier_sr_settlemented">
	<button class="btn btn-xs btn-yellow" onclick="query_sr_settlemented(0)">
								查询
	</button>
	<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="exportSRYFExcel('sett')">
								<i class="icon-hdd"></i>导出
	</button>
	
	<div class="title_div" id="csstitle">
		<table class="table table-striped" style="table-layout:fixed;">
	   		<thead>
			<tr>
				<td class="td_ch"><input type="checkbox" id="checkAll"
					onclick="inverseCkb('ckb')" />
				<td class="td_cs" title="合同名称">合同名称</td>
				<td class="td_cs" title="成本中心">成本中心</td>
				<td class="td_cs" title="店铺代码">店铺代码</td>
				<td class="td_cs" title="店铺名称">店铺名称</td>
				<td class="td_cs" title="仓库">仓库</td>
				<td class="td_cs" title="运输公司代码">运输公司代码</td>
				<td class="td_cs" title="快递名称">快递名称</td>
				<td class="td_cs" title="发货指令">发货指令</td>
				<td class="td_cs" title="上位系统订单号">上位系统订单号</td>
				<td class="td_cs" title="订单类型">订单类型</td>
				<td class="td_cs" title="运单号">运单号</td>
				<td class="td_cs" title="运输方向(0:正向；1：逆向)">运输方向(0:正向；1：逆向)</td>
				<td class="td_cs" title="产品类型代码">产品类型代码</td>
				<td class="td_cs" title="产品类型名称">产品类型名称</td>
				<td class="td_cs" title="运输时间">运输时间</td>
				<td class="td_cs" title="代收货款金额">代收货款金额</td>
				<td class="td_cs" title="订单金额">订单金额</td>
				<td class="td_cs" title="耗材SKU编码">耗材SKU编码</td>
				<td class="td_cs" title="实际重量">实际重量</td>
				<!-- <td class="td_cs" title="物理核算重量">物理核算重量</td> -->
				<td class="td_cs" title="长">长</td>
				<td class="td_cs" title="宽">宽</td>
				<td class="td_cs" title="高">高</td>
				<td class="td_cs" title="体积">体积</td>
				<td class="td_cs" title="始发地">始发地</td>
				<td class="td_cs" title="省">省</td>
				<td class="td_cs" title="市">市</td>
				<td class="td_cs" title="区">区</td>
				<td class="td_cs" title="街道">街道</td>
				<!-- <td class="td_cs" title="计抛基数">计抛基数</td> -->
				<!-- <td class="td_cs" title="体积重量">体积重量</td> -->
				<!-- <td class="td_cs" title="体积核算重量">体积核算重量</td> -->
				<!-- <td class="td_cs" title="计费重量（报价）">计费重量（报价）</td> -->
				<td class="td_cs" title="计费重量">计费重量</td>
				<!-- <td class="td_cs" title="首重价格">首重价格</td>
				<td class="td_cs" title="首重">首重</td>
				<td class="td_cs" title="续重价格">续重价格</td>
				<td class="td_cs" title="续重">续重</td> -->
				<td class="td_cs" title="标准运费">标准运费</td>
				<td class="td_cs" title="折扣率">折扣率</td>
				<td class="td_cs" title="折后运费">折后运费</td>
				<td class="td_cs" title="保价费">保价费</td>
				<td class="td_cs" title="是否保价">是否保价</td>
				<td class="td_cs" title="是否COD">是否COD</td>
			</tr>
			</thead>
		</table>
	</div>
	<div class="tree_div" ></div>
	<div style="height:400px; overflow:auto; overflow:scroll; border:solid #F2F2F2 1px;" id="cssContent" onscroll="cssaa()">
		<table id="table_detail_content" class="table table-hover table-striped" style="table-layout: fixed;">
	  		<tbody align="center">
			<c:forEach items="${pageView.records}" var="list">
				<tr>
					<td class="td_ch"><input id="ckb" name="ckb" type="checkbox"
						value="${list.id}"></td>
					<td class="td_cs" title="${list.contractName }">${list.contractName}</td>
					<td class="td_cs" title="${list.cost_center }">${list.cost_center}</td>
					<td class="td_cs" title="${list.store_code }">${list.store_code}</td>
					<td class="td_cs" title="${list.store_name }">${list.store_name}</td>
					<td class="td_cs" title="${list.warehouse }">${list.warehouse}</td>
					<td class="td_cs" title="${list.transport_code }">${list.transport_code}</td>
					<td class="td_cs" title="${list.transport_name }">${list.transport_name}</td>
					<td class="td_cs" title="${list.delivery_order }">${list.delivery_order}</td>
					<td class="td_cs" title="${list.epistatic_order }">${list.epistatic_order}</td>
					<td class="td_cs" title="${list.order_type }">${list.order_type}</td>
					<td class="td_cs" title="${list.express_number }">${list.express_number}</td>
					<td class="td_cs" title="${list.transport_direction }">
						<c:if test="${list.transport_direction eq '0'}">正向</c:if>
						<c:if test="${list.transport_direction eq '1'}">反向</c:if>
					</td>
					<td class="td_cs" title="${list.itemtype_code }">${list.itemtype_code}</td>
					<td class="td_cs" title="${list.itemtype_name }">${list.itemtype_name}</td>
					<td class="td_cs" title="${list.transport_time }">
						<fmt:formatDate type="both" value="${list.transport_time}" />
					</td>
					<td class="td_cs" title="${list.collection_on_delivery }">${list.collection_on_delivery}</td>
					<td class="td_cs" title="${list.order_amount }">${list.order_amount}</td>
					<td class="td_cs" title="${list.sku_number }">${list.sku_number}</td>
					<td class="td_cs" title="${list.weight }">${list.weight}</td>
					<%-- <td class="td_cs" title="${list.account_weight }">${list.account_weight}</td> --%>
					<td class="td_cs" title="${list.length }">${list.length}</td>
					<td class="td_cs" title="${list.width }">${list.width}</td>
					<td class="td_cs" title="${list.higth }">${list.higth}</td>
					<td class="td_cs" title="${list.volumn }">${list.volumn}</td>
					<td class="td_cs" title="${list.delivery_address }">${list.delivery_address}</td>
					<td class="td_cs" title="${list.province }">${list.province}</td>
					<td class="td_cs" title="${list.city }">${list.city}</td>
					<td class="td_cs" title="${list.state }">${list.state}</td>
					<td class="td_cs" title="${list.street }">${list.street}</td>
					<%-- <td class="td_cs" title="${list.jp_num }">${list.jp_num}</td> --%>
					<%-- <td class="td_cs" title="${list.volumn_weight }">${list.volumn_weight}</td> --%>
					<%-- <td class="td_cs" title="${list.volumn_account_weight }">${list.volumn_account_weight}</td> --%>
					<%-- <td class="td_cs" title="${list.jf_weight }">${list.jf_weight}</td> --%>
					<td class="td_cs" title="${list.charged_weight }">${list.charged_weight}</td>
					<%-- <td class="td_cs" title="${list.first_weight }">${list.first_weight}</td>
					<td class="td_cs" title="${list.first_weight_price }">${list.first_weight_price}</td> --%>
					<%-- <td class="td_cs" title="${list.added_weight }">${list.added_weight}</td>
					<td class="td_cs" title="${list.added_weight_price }">${list.added_weight_price}</td> --%>
					<td class="td_cs" title="${list.standard_freight }">${list.standard_freight}</td>
					<td class="td_cs" title="${list.discount }">${list.discount}</td>
					<td class="td_cs" title="${list.afterdiscount_freight }">${list.afterdiscount_freight}</td>
					<td class="td_cs" title="${list.insurance_fee }">${list.insurance_fee}</td>
					<td class="td_cs" title="${list.insurance_flag }">
						<c:if test="${list.insurance_flag eq '0'}">否</c:if>
						<c:if test="${list.insurance_flag eq '1'}">是</c:if>
					</td>
					<td class="td_cs" title="${list.cod_flag }">
						<c:if test="${list.cod_flag eq '0'}">否</c:if>
						<c:if test="${list.cod_flag eq '1'}">是</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="margin-right:1%; margin-top:20px;">${pageView.pageView }</div>
</div>
