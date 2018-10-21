<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
function aa(){
    var b=document.getElementById("ccc").scrollLeft;
    document.getElementById("hhh").scrollLeft=b;
  }
function pageJump() {
	query_zc_error(1);
}
</script>
<div id="carrier_zc_error">
	<button class="btn btn-xs btn-yellow" onclick="query_zc_error(0)">
								查询
	</button>
	<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="exportZCYFExcel('error')">
								<i class="icon-hdd"></i>导出
	</button>
	<div class="title_div" id="hhh">
		<table class="table table-striped" style="table-layout:fixed;">
	   		<thead>
			<tr>
				<td class="td_ch"><input type="checkbox" id="checkAll"
					onclick="inverseCkb('ckb')" />
				<td class="td_cs" title="处理结果信息">处理结果信息</td>
				<td class="td_cs" title="合同名称">合同名称</td>
				<td class="td_cs" title="成本中心">成本中心</td>
				<td class="td_cs" title="店铺编码">店铺编码</td>
				<td class="td_cs" title="店铺名称">店铺名称</td>
				<td class="td_cs" title="仓库编码">仓库编码</td>
				<td class="td_cs" title="仓库名称">仓库名称</td>
				<td class="td_cs" title="承运商代码">承运商代码</td>
				<td class="td_cs" title="承运商">承运商</td>
				<td class="td_cs" title="发货指令/平台订单号">发货指令/平台订单号</td>
				<td class="td_cs" title="上位系统订单号/前置单据号/相关单据号">上位系统订单号/前置单据号/相关单据号</td>
				<td class="td_cs" title="订单类型">订单类型</td>
				<td class="td_cs" title="运单号">运单号</td>
				<td class="td_cs" title="运输方向">运输方向</td>
				<td class="td_cs" title="产品类型代码">产品类型代码</td>
				<td class="td_cs" title="产品类型名称">产品类型名称</td>
				<td class="td_cs" title="订单生成时间">订单生成时间</td>
				<td class="td_cs" title="代收货款金额">代收货款金额</td>
				<td class="td_cs" title="订单金额">订单金额</td>
				<td class="td_cs" title="耗材SKU编码">耗材SKU编码</td>
				<td class="td_cs" title="实际重量">实际重量</td>
				<td class="td_cs" title="长">长</td>
				<td class="td_cs" title="宽">宽</td>
				<td class="td_cs" title="高">高</td>
				<td class="td_cs" title="体积">体积</td>
				<td class="td_cs" title="始发地">始发地</td>
				<td class="td_cs" title="省">省</td>
				<td class="td_cs" title="市">市</td>
				<td class="td_cs" title="区">区</td>
				<td class="td_cs" title="街道">街道</td>
				<td class="td_cs" title="详细地址">详细地址</td>
				<td class="td_cs" title="是否保价">是否保价</td>
				<td class="td_cs" title="是否COD">是否COD</td>
				<!-- <td class="td_cs">合同类型1-快递2-物流3-店铺4-客户</td>
				<td class="td_cs">费用类型 0-运费 1-仓储费 2-操作费 3-耗材费 4-打包费 5-增值服务费</td>
				<td class="td_cs">结算对象e.g.运单号</td>
				<td class="td_cs">异常日志</td>
				<td class="td_cs">备注</td>
				<td class="td_cs">备用字段1</td>
				<td class="td_cs">备用字段2</td>
				<td class="td_cs">异常状态 0-新建 1-处理中 -1-关闭</td>
				<td class="td_cs">创建时间</td> -->
			</tr>
			</thead>
		</table>
	</div>
	<div class="tree_div" ></div>
	<div style="height:400px; overflow:auto; overflow:scroll; border:solid #F2F2F2 1px;" id="ccc" onscroll="aa()">
		<table id="table_detail_content" class="table table-hover table-striped" style="table-layout: fixed;">
	  		<tbody align="center">
			<c:forEach items="${pageView.records}" var="list">
				<tr>
					<td class="td_ch"><input id="ckb" name="ckb" type="checkbox"
						value="${list.id}"></td>
					<td class="td_cs" title="${list.pro_result_info }">${list.pro_result_info}</td>
					<td class="td_cs" title="${list.contract_name }">
						${list.contract_name}
						<%-- <c:if test="${list.contract_type=='1'}">快递</c:if>
						<c:if test="${list.contract_type=='2'}">物流</c:if> --%>
					</td>
					<td class="td_cs" title="${list.cost_center }">${list.cost_center}</td>
					<td class="td_cs" title="${list.store_code }">${list.store_code}</td>
					<td class="td_cs" title="${list.store_name }">${list.store_name}</td>
					<td class="td_cs" title="${list.warehouse_code }">${list.warehouse_code}</td>
					<td class="td_cs" title="${list.warehouse }">${list.warehouse}</td>
					<td class="td_cs" title="${list.transport_code }">${list.transport_code}</td>
					<td class="td_cs" title="${list.transport_name }">${list.transport_name}</td>
					<td class="td_cs" title="${list.delivery_order }">${list.delivery_order}</td>
					<td class="td_cs" title="${list.epistatic_order }">${list.epistatic_order}</td>
					<td class="td_cs" title="${list.order_type }">${list.order_type}</td>
					<td class="td_cs" title="${list.express_number }">${list.express_number}</td>
					<td class="td_cs">
						<c:if test="${list.transport_direction eq '0'}">正向</c:if>
						<c:if test="${list.transport_direction eq '1'}">反向</c:if>
					</td>
					<td class="td_cs" title="${list.itemtype_code }">${list.itemtype_code}</td>
					<td class="td_cs" title="${list.itemtype_name }">${list.itemtype_name}</td>
					<td class="td_cs" title="${list.transport_time }">${list.transport_time}</td>
					<td class="td_cs" title="${list.collection_on_delivery }">${list.collection_on_delivery}</td>
					<td class="td_cs" title="${list.order_amount }">${list.order_amount}</td>
					<td class="td_cs" title="${list.sku_number }">${list.sku_number}</td>
					<td class="td_cs" title="${list.weight }">${list.weight}</td>
					<td class="td_cs" title="${list.length }">${list.length}</td>
					<td class="td_cs" title="${list.width }">${list.width}</td>
					<td class="td_cs" title="${list.higth }">${list.higth}</td>
					<td class="td_cs" title="${list.volumn }">${list.volumn}</td>
					<td class="td_cs" title="${list.delivery_addlists }">${list.delivery_addlists}</td>
					<td class="td_cs" title="${list.province }">${list.province}</td>
					<td class="td_cs" title="${list.city }">${list.city}</td>
					<td class="td_cs" title="${list.state }">${list.state}</td>
					<td class="td_cs" title="${list.street }">${list.street}</td>
					<td class="td_cs" title="${list.detail_address }">${list.detail_address}</td>
					<td class="td_cs" title="${list.insurance_flag }">${list.insurance_flag}</td>
					<td class="td_cs" title="${list.cod_flag }">${list.cod_flag}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
	<div style="margin-right:1%; margin-top:20px;">${pageView.pageView }</div>
</div>