<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
function csuaa(){
    var b=document.getElementById("csuContent").scrollLeft;
    document.getElementById("csutitle").scrollLeft=b;
  }
function pageJump() {
	query_sr_unsettlement(1);
}
</script>
<div id="carrier_sr_unsettlement">
	<button class="btn btn-xs btn-yellow" onclick="query_sr_unsettlement(0)">
								查询
	</button>
	<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="exportSRYFExcel('unsett')">
								<i class="icon-hdd"></i>导出
	</button>
	<div class="title_div" id="csutitle">
		<table class="table table-striped" style="table-layout:fixed;">
	   		<thead>
			<tr>
				<td class="td_ch"><input type="checkbox" id="checkAll"
					onclick="inverseCkb('ckb')" />
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
				<td class="td_cs" title="收件人">收件人</td>
				<td class="td_cs" title="电话">电话</td>
				<td class="td_cs" title="是否保价">是否保价</td>
				<td class="td_cs" title="是否COD">是否COD</td>
			</tr>
			</thead>
		</table>
	</div>
	<div class="tree_div" ></div>
	<div style="height:400px; overflow:auto; overflow:scroll; border:solid #F2F2F2 1px;" id="csuContent" onscroll="csuaa()">
		<table id="table_detail_content" class="table table-hover table-striped" style="table-layout: fixed;">
	  		<tbody align="center">
			<c:forEach items="${pageView.records}" var="list">
				<tr>
					<td class="td_ch"><input id="ckb" name="ckb" type="checkbox"
						value="${list.id}"></td>
					<td class="td_cs" title="${list.costCenter }">${list.costCenter}</td>
					<td class="td_cs" title="${list.storeCode }">${list.storeCode}</td>
					<td class="td_cs" title="${list.storeName }">${list.storeName}</td>
					<td class="td_cs" title="${list.warehouse }">${list.warehouse}</td>
					<td class="td_cs" title="${list.transportCode }">${list.transportCode}</td>
					<td class="td_cs" title="${list.transportName }">${list.transportName}</td>
					<td class="td_cs" title="${list.deliveryOrder }">${list.deliveryOrder}</td>
					<td class="td_cs" title="${list.epistaticOrder }">${list.epistaticOrder}</td>
					<td class="td_cs" title="${list.orderType }">${list.orderType}</td>
					<td class="td_cs" title="${list.expressNumber }">${list.expressNumber}</td>
					<td class="td_cs" title="${list.transportDirection }">
						<c:if test="${list.transportDirection eq '0'}">正向</c:if>
						<c:if test="${list.transportDirection eq '1'}">反向</c:if>
					</td>
					<td class="td_cs" title="${list.itemtypeCode }">${list.itemtypeCode}</td>
					<td class="td_cs" title="${list.itemtypeName }">${list.itemtypeName}</td>
					<td class="td_cs" title="${list.transportTime }">
						<fmt:formatDate type="both" value="${list.transportTime}" />
					</td>
					<td class="td_cs" title="${list.collectionOnDelivery }">${list.collectionOnDelivery}</td>
					<td class="td_cs" title="${list.orderAmount }">${list.orderAmount}</td>
					<td class="td_cs" title="${list.skuNumber }">${list.skuNumber}</td>
					<td class="td_cs" title="${list.weight }">${list.weight}</td>
					<td class="td_cs" title="${list.length }">${list.length}</td>
					<td class="td_cs" title="${list.width }">${list.width}</td>
					<td class="td_cs" title="${list.higth }">${list.higth}</td>
					<td class="td_cs" title="${list.volumn }">${list.volumn}</td>
					<td class="td_cs" title="${list.deliveryAddress }">${list.deliveryAddress}</td>
					<td class="td_cs" title="${list.province }">${list.province}</td>
					<td class="td_cs" title="${list.city }">${list.city}</td>
					<td class="td_cs" title="${list.state }">${list.state}</td>
					<td class="td_cs" title="${list.street }">${list.street}</td>
					<td class="td_cs" title="${list.detailAddress }">${list.detailAddress}</td>
					<td class="td_cs" title="${list.shiptoname }">${list.shiptoname}</td>
					<td class="td_cs" title="${list.phone }">${list.phone}</td>
					<td class="td_cs" title="${list.insuranceFlag }">
						<c:if test="${list.insuranceFlag eq 'true'}">是</c:if>
						<c:if test="${list.insuranceFlag eq 'false'}">否</c:if>
					</td>
					<td class="td_cs" title="${list.codFlag }">
						<c:if test="${list.codFlag eq 'true'}">是</c:if>
						<c:if test="${list.codFlag eq 'false'}">否</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="margin-right:1%; margin-top:20px;">${pageView.pageView }</div>
<br/>
<br/>
<br/>
<br/>
</div>
