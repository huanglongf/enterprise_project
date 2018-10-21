<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
	</head>
	<body style="text-align: center;">
		<div class="page-header" style="margin-top: 10px; margin-left: 10px; height: 100px;">
			<button class="btn btn-xs btn-app btn-warning" 
				onclick="this.disabled= true; openDiv('${root}/control/summaryController/summaryList.do?ym=${balance_month }&clientid=${clientid }&storeid=${storeid }');" >
				<i class="icon-undo bigger-200"></i>返回
			</button>
			<button id="btn_export" class="btn btn-xs btn-app btn-pink" onclick="exportUsedDetailExcel();">
				<i class="icon-arrow-down bigger-200"></i>导出
			</button>
			<script type="text/javascript">
				function exportUsedDetailExcel(){
					$("#btn_export").attr("disabled", true);
					$.ajax({
						type: "POST",
						url: root+ "/control/expressUsedBalanceController/exportUsedDetailExcel.do",  
						data: $("#serchForm").serialize(),
						dataType: "",  
						success: function(jsonStr) {
							window.open(root+ jsonStr);
							$("#btn_export").attr("disabled", false);
							
						}
						
					});
					
				}
			</script>
		</div>
		<form id= "serchForm" >
			<input type="hidden" value= "${con_id }" name= "con_id" />
			<input type="hidden" value= "${express_code }" name= "express_code" />
			<input type="hidden" value= "${express_name }" name= "express_name" />
			<input type="hidden" value= "${product_type_code }" name= "product_type_code" />
			<input type="hidden" value= "${product_type_name }" name= "product_type_name" />
			<input type="hidden" value= "${balance_month }" name= "balance_month"/>
			<input type="hidden" value= "${client_name }" name= "client_name" />
		</form>
		<div class= "div_margin" style= "text-align: center; height: 5%;" >
			<h3 class= "control-label blue">
				${client_name }使用<c:if test="${express_name == '顺丰快递' }">${product_type_name }</c:if><c:if test="${express_name != '顺丰快递' }">${express_name }</c:if>${balance_month }月结算明细报表
			</h3>
		</div>
		<div class= "title_div" id= "sc_title" >		
			<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr class= "table_head_line" >
			  			<td class="td_cs" style= "width: 200px" title= "所属仓库" >所属仓库</td>
			  			<td class="td_cs" style= "width: 200px" title= "所属店铺" >所属店铺</td>
						<td class="td_cs" style= "width: 200px" title= "订单号" >订单号</td>
						<td class="td_cs" style= "width: 200px" title= "备注" >备注</td>
						<td class="td_cs" style= "width: 200px" title= "商品编码" >商品编码</td>
						<td class="td_cs" style= "width: 150px" title= "长(CM)" >长(CM)</td>
						<td class="td_cs" style= "width: 150px" title= "宽(CM)" >宽(CM)</td>
						<td class="td_cs" style= "width: 150px" title= "高(CM)" >高(CM)</td>
						<td class="td_cs" style= "width: 200px" title= "体积(CM³)" >体积(CM³)</td>
						<td class="td_cs" style= "width: 200px" title= "发货时间" >发货时间</td>
						<td class="td_cs" style= "width: 200px" title= "计费省" >计费省</td>
						<td class="td_cs" style= "width: 200px" title= "计费市" >计费市</td>
						<td class="td_cs" style= "width: 200px" title= "计费区" >计费区</td>
						<td class="td_cs" style= "width: 200px" title= "发货重量(KG)" >发货重量(KG)</td>
						<td class="td_cs" style= "width: 200px" title= "物流商名称" >物流商名称</td>
						<td class="td_cs" style= "width: 200px" title= "产品类型" >产品类型</td>
						<td class="td_cs" style= "width: 100px" title= "是否COD" >是否COD</td>
						<td class="td_cs" style= "width: 200px" title= "订单金额(元)" >订单金额(元)</td>
						<td class="td_cs" style= "width: 200px" title= "运单号" >运单号</td>
						<td class="td_cs" style= "width: 200px" title= "类型" >类型</td>
						<td class="td_cs" style= "width: 200px" title= "计费重量(KG)" >计费重量(KG)</td>
						<td class="td_cs" style= "width: 200px" title= "保价费(元)" >保价费(元)</td>
						<td class="td_cs" style= "width: 200px" title= "首重报价(元)" >首重报价(元)</td>
						<td class="td_cs" style= "width: 200px" title= "续重报价(元)" >续重报价(元)</td>
						<td class="td_cs" style= "width: 200px" title= "折扣" >折扣</td>
						<td class="td_cs" style= "width: 200px" title= "COD手续费(元)" >COD手续费(元)</td>
						<td class="td_cs" style= "width: 200px" title= "运费(元)" >运费(元)</td>
						<td class="td_cs" style= "width: 200px" title= "最终费用(元)（不含COD）" >最终费用(元)（不含COD）</td>
			  		</tr>
				</thead>
			</table>
		</div>
		<div class="tree_div"></div>
		<div style="height : 400px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
			<table id="table_detail_content" class="table table-hover table-striped" style="table-layout: fixed;">
				<tbody align="center">
					<c:forEach items="${pageView.records}" var="records">
						<tr>
							<td class="td_cs" style= "width: 200px" title= "${records.warehouse }" >${records.warehouse }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.store_name }" >${records.store_name }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.epistatic_order }" >${records.epistatic_order }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.delivery_order }" >${records.delivery_order }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.sku_number }" >${records.sku_number }</td>
							<td class="td_cs" style= "width: 150px" title= "${records.length }" >${records.length }</td>
							<td class="td_cs" style= "width: 150px" title= "${records.width }" >${records.width }</td>
							<td class="td_cs" style= "width: 150px" title= "${records.higth }" >${records.higth }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.volumn }" >${records.volumn }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.transport_time }" >${records.transport_time }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.province }" >${records.province }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.city }" >${records.city }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.state }" >${records.state }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.weight }" >${records.weight }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.transport_name }" >${records.transport_name }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.itemtype_name }" >${records.itemtype_name }</td>
							<td class="td_cs" style= "width: 100px" title= "${records.cod_flag }" >${records.cod_flag }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.order_amount }" >${records.order_amount }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.express_number }" >${records.express_number }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.order_type }" >${records.order_type }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.charged_weight }" >${records.charged_weight }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.insurance_fee }" >${records.insurance_fee }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.first_weight_price }" >${records.first_weight_price }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.added_weight_price }" >${records.added_weight_price }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.discount }" >${records.discount }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.cod }" >${records.cod }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.afterdiscount_freight }" >${records.afterdiscount_freight }</td>
							<td class="td_cs" style= "width: 200px" title= "${records.total_fee }" >${records.total_fee }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
		<input id="con_id" value="${con_id }" type="hidden"/>
		<input id="express_code" value="${express_code }" type="hidden"/>
		<input id="express_name" value="${express_name }" type="hidden"/>
		<input id="product_type_code" value="${product_type_code }" type="hidden"/>
		<input id="product_type_name" value="${product_type_name }" type="hidden"/>
		<input id="balance_month" value="${balance_month }" type="hidden"/>
		<input id="clientid" value="${clientid }" type="hidden"/>
		<input id="client_name" value="${client_name }" type="hidden"/>
		<input id="storeid" value="${storeid }" type="hidden"/>
		<script type="text/javascript">
			function pageJump() {
				openDiv("${root}control/expressUsedBalanceController/balanceDetailList.do?con_id=" 
					+ $("#con_id").val()
					+ "&express_code=" 
					+ $("#express_code").val()
					+ "&express_name=" 
					+ $("#express_name").val()
					+ "&product_type_code=" 
					+ $("#product_type_code").val()
					+ "&product_type_name=" 
					+ $("#product_type_name").val()
					+ "&balance_month=" 
					+ $("#balance_month").val()
					+ "&clientid=" 
					+ $("#clientid").val()
					+ "&client_name=" 
					+ $("#client_name").val()
					+ "&storeid=" 
					+ $("#storeid").val()
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
