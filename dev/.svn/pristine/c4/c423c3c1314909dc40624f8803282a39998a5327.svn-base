<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel="stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
	</head>
	<body style= "text-align: center;" >
		<div class= "page-header" style= "margin-top: 10px; margin-left: 10px; height: 100px;" >
			<button class= "btn btn-xs btn-app btn-warning" onclick="this.disabled= true; openDiv('${root }/control/expressBalanceController/balanceSummaryList.do?express_code=${express_code }&express_name=${express_name }&balance_month=${balance_month }');" >
				<i class= "icon-undo bigger-200" ></i>返回
			</button>
		</div>
		<form id="serchForm">
			<input type="hidden" value="${con_id }" name="con_id"/>
			<input type="hidden" value="${express_code }" name="express_code"/>
			<input type="hidden" value="${express_name }" name="express_name"/>
			<input type="hidden" value="${balance_month }" name="balance_month"/>
		</form>
		<div class="div_margin" style="text-align:center; height: 5%;">
			<h3 class="control-label blue">
				${express_name }${balance_month }月结算明细报表
			</h3>
		</div>
		<div class="title_div" id="sc_title">
			<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr class="table_head_line">
			  			<td class="td_cs" style="width: 200px" title="店铺">店铺</td>
						<td class="td_cs" style="width: 200px" title="仓库">仓库</td>
						<td class="td_cs" style="width: 200px" title="订单号/指令号">订单号/指令号</td>
						<td class="td_cs" style="width: 200px" title="上位系统订单号">上位系统订单号</td>
						<td class="td_cs" style="width: 200px" title="订单类型">订单类型</td>
						<td class="td_cs" style="width: 200px" title="运单号">运单号</td>
						<td class="td_cs" style="width: 200px" title="运输方向（正向运输/逆向退货）">运输方向（正向运输/逆向退货）</td>
						<td class="td_cs" style="width: 200px" title="运输产品类型">运输产品类型</td>
						<td class="td_cs" style="width: 200px" title="订单生成时间">订单生成时间</td>
						<td class="td_cs" style="width: 200px" title="代收货款金额">代收货款金额</td>
						<td class="td_cs" style="width: 200px" title="订单金额(元)">订单金额(元)</td>
						<td class="td_cs" style="width: 200px" title="SKU编码">SKU编码</td>
						<td class="td_cs" style="width: 150px" title="实际重量(KG)">实际重量(KG)</td>
						<td class="td_cs" style="width: 150px" title="长(CM)">长(CM)</td>
						<td class="td_cs" style="width: 150px" title="宽(CM)">宽(CM)</td>
						<td class="td_cs" style="width: 150px" title="高(CM)">高(CM)</td>
						<td class="td_cs" style="width: 150px" title="体积(CM³)">体积(CM³)</td>
						<td class="td_cs" style="width: 200px" title="始发地">始发地</td>
						<td class="td_cs" style="width: 200px" title="目的省">目的省</td>
						<td class="td_cs" style="width: 200px" title="市">市</td>
						<td class="td_cs" style="width: 200px" title="区">区</td>
						<td class="td_cs" style="width: 100px" title="是否保价">是否保价</td>
						<td class="td_cs" style="width: 100px" title="是否COD">是否COD</td>
						<td class="td_cs" style="width: 200px" title="首重报价(元)">首重报价(元)</td>
						<td class="td_cs" style="width: 200px" title="续重报价(元)">续重报价(元)</td>
						<td class="td_cs" style="width: 200px" title="标准运费(元)">标准运费(元)</td>
						<td class="td_cs" style="width: 200px" title="运单折扣(%)">运单折扣(%)</td>
						<td class="td_cs" style="width: 200px" title="折后运费(元)">折后运费(元)</td>
						<td class="td_cs" style="width: 200px" title="保价费(元)">保价费(元)</td>
						<td class="td_cs" style="width: 200px" title="其他">其他</td>
						<td class="td_cs" style="width: 200px" title="合计费用(元)">合计费用(元)</td>
						<td class="td_cs" style="width: 200px" title="备注">备注</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_detail_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
				<tbody align="center" >
					<c:forEach items= "${pageView.records }" var= "records" >
						<tr>
							<td class="td_cs" style="width: 200px" title="${records.store_name }">${records.store_name }</td>
							<td class="td_cs" style="width: 200px" title="${records.warehouse }">${records.warehouse }</td>
							<td class="td_cs" style="width: 200px" title="${records.delivery_order }">${records.delivery_order }</td>
							<td class="td_cs" style="width: 200px" title="${records.epistatic_order }">${records.epistatic_order }</td>
							<td class="td_cs" style="width: 200px" title="${records.order_type }">${records.order_type }</td>
							<td class="td_cs" style="width: 200px" title="${records.express_number }">${records.express_number }</td>
							<td class="td_cs" style="width: 200px" title="${records.transport_direction }">${records.transport_direction }</td>
							<td class="td_cs" style="width: 200px" title="${records.itemtype_name }">${records.itemtype_name }</td>
							<td class="td_cs" style="width: 200px" title="${records.transport_time }">${records.transport_time }</td>
							<td class="td_cs" style="width: 200px" title="${records.collection_on_delivery }">${records.collection_on_delivery }</td>
							<td class="td_cs" style="width: 200px" title="${records.order_amount }">${records.order_amount }</td>
							<td class="td_cs" style="width: 200px" title="${records.sku_number }">${records.sku_number }</td>
							<td class="td_cs" style="width: 150px" title="${records.weight }">${records.weight }</td>
							<td class="td_cs" style="width: 150px" title="${records.length }">${records.length }</td>
							<td class="td_cs" style="width: 150px" title="${records.width }">${records.width }</td>
							<td class="td_cs" style="width: 150px" title="${records.higth }">${records.higth }</td>
							<td class="td_cs" style="width: 150px" title="${records.volumn }">${records.volumn }</td>
							<td class="td_cs" style="width: 200px" title="${records.delivery_address }">${records.delivery_address }</td>
							<td class="td_cs" style="width: 200px" title="${records.province }">${records.province }</td>
							<td class="td_cs" style="width: 200px" title="${records.city }">${records.city }</td>
							<td class="td_cs" style="width: 200px" title="${records.state }">${records.state }</td>
							<td class="td_cs" style="width: 100px" title="${records.insurance_flag }">${records.insurance_flag }</td>
							<td class="td_cs" style="width: 100px" title="${records.cod_flag }">${records.cod_flag }</td>
							<td class="td_cs" style="width: 200px" title="${records.first_weight_price }">${records.first_weight_price }</td>
							<td class="td_cs" style="width: 200px" title="${records.added_weight_price }">${records.added_weight_price }</td>
							<td class="td_cs" style="width: 200px" title="${records.standard_freight }">${records.standard_freight }</td>
							<td class="td_cs" style="width: 200px" title="${records.discount }">${records.discount }</td>
							<td class="td_cs" style="width: 200px" title="${records.afterdiscount_freight }">${records.afterdiscount_freight }</td>
							<td class="td_cs" style="width: 200px" title="${records.insurance_fee }">${records.insurance_fee }</td>
							<td class="td_cs" style="width: 200px"></td>
							<td class="td_cs" style="width: 200px" title="${records.total_fee }">${records.total_fee }</td>
							<td class="td_cs" style="width: 200px" title="${records.remark }">${records.remark }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
		<input id= "con_id" value= "${con_id }" type="hidden" />
		<input id= "express_code" value= "${express_code }" type="hidden" />
		<input id= "express_name" value= "${express_name }" type="hidden" />
		<input id= "balance_month" value= "${balance_month }" type="hidden" />
		<script type= "text/javascript" >
			function pageJump() {
				openDiv("${root }control/expressBalanceController/balanceDetailList.do?con_id=" 
					+ $("#con_id").val()
					+ "&express_code=" 
					+ $("#express_code").val()
					+ "&express_name=" 
					+ $("#express_name").val()
					+ "&balance_month=" 
					+ $("#balance_month").val()
					+ "&startRow="
					+ $("#startRow").val()
					+ "&endRow=" 
					+ $("#startRow").val()
					+ "&page="
					+ $("#pageIndex").val() 
					+ "&pageSize="
					+ $("#pageSize").val()
				);
			};
		</script>
	</body>
</html>
