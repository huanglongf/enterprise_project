<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript" src="<%=basePath%>daterangepicker/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		// 运费
		var freight= Number("0.00");
		// 运单数
		var order_num= 0;
		var val = "";
		var current_node= $("#freight_summary").next();
		while(true){
			if(current_node.attr("id") == undefined){
				// parseInt及parseFloat只会返回字符串中的数字部分
				// Number()，强转类型，字符串所有部分
				// 获取单项费用内容，带单位元，需去除，包头不包尾
				val= current_node.children().eq(3).text();
				// 累加总运费
				freight+= Number(val.substr(0, val.length - 1));
				// 累加运单数
				order_num+= Number(current_node.children().eq(1).text());
				current_node= current_node.next();
			} else if(current_node.attr("id") == "total_freight_discount"){
				// 获取总运费折扣内容，带单位元，需去除
				val= $("#total_freight_discount").children().eq(3).text();
				console.log(val=="");
				if(val == ""){
					$("#total_freight_discount").children().eq(3).text("0.00元");
				} else {
					// 扣去总运费折扣
					freight-= Number(val.substr(0, val.length - 1));
				}
				// 将记录添加进节点
				current_node= $("#freight_summary");
				// 填入运单总数
				current_node.children().eq(1).text(order_num);
				// 填入运费
				// 保留两位小数
				current_node.children().eq(3).text(freight.toFixed(2) + "元");
				break;
			} 
		}
		// cod
		var cod= Number("0.00");
		current_node = $("#cod_summary").next();
		while(true){
			if(current_node.attr("id") == undefined){
				// parseInt及parseFloat只会返回字符串中的数字部分
				// Number()，强转类型，字符串所有部分
				// 获取单项费用内容，带单位元，需去除
				val= current_node.children().eq(3).text();
				// 累加cod
				cod+= Number(val.substr(0, val.length - 1));
				current_node = current_node.next();
			} else if(current_node.attr("id") == "delegatedPickup_summary"){
				// 将记录添加进节点，填入cod，保留两位小数
				$("#cod_summary").children().eq(3).text(cod.toFixed(2) + "元");
				break;
			} 
		}
		// 委托取件费
		var delegatedPickup_summary= Number("0.00");
		// 特殊服务费
		val = $("#cod_summary").children().eq(3).text();
		cod = Number(val.substr(0, val.length - 1));
		val = $("#delegatedPickup_summary").children().eq(3).text();
		delegatedPickup_summary = Number(val.substr(0, val.length - 1));
		$("#specialService_summary").children().eq(3).text((cod + delegatedPickup_summary).toFixed(2) + "元");
		// 管理费
		var manFee= Number("0.00");
		current_node = $("#managementFee_summary").next();
		while(true){
			if(current_node.attr("id") == undefined){
				// parseInt及parseFloat只会返回字符串中的数字部分
				// Number()，强转类型，字符串所有部分
				// 获取单项费用内容，带单位元，需去除
				val = current_node.children().eq(3).text();
				// 累加管理费
				manFee+= Number(val.substr(0, val.length - 1));
				current_node = current_node.next();
			} else if(current_node.attr("id") == "endRow"){
				// 将记录添加进节点，填入管理费，保留两位小数
				$("#managementFee_summary").children().eq(3).text(manFee.toFixed(2) + "元");
				break;
			} 
		}
	});
	function jump_wl_detail(createDate,contractId,transportCode,clientid,storeid,ym){
		openDiv('${root}control/transPoolController/detail_list.do?createDate='+createDate+'&contractId='+contractId+'&transportCode='+transportCode+"&opId=2"+"&clientid="+clientid+"&storeid="+storeid+"&ym="+ym);
	}
</script>
</head>
<body style="text-align: center;">
	<div style="margin-left: 30px; display: inline;">
		<table border="4" style="margin-right: auto; margin-left: auto; width: 900px;">
			<tr style="text-align: center;">
				<td colspan="5"><h4>费 用 结 算 单</h4></td>
			</tr>
			<tr>
				<td colspan="2" width="50%">客户：${client.client_name }</td>
				<td colspan="2" width="40%">结算期间：[${ym }]</td>
				<td>
					<button class="btn btn-xs btn-yellow" onclick="ajaxPOI();">
						<i class="icon-hdd"></i>导出明细
					</button>
					<script type="text/javascript">
						function ajaxPOI() {
							alert("AJAX异步调用POI！");
						}
					</script>
				</td>
			</tr>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">项目</td>
				<td>数量</td>
				<td>单位</td>
				<td>费用</td>
				<td>备注</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%"><a href="javascript:;">操作费</a></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">固定费用结算</td>
				<td>${plist.gd_qty }</td>
				<td></td>
				<td>${plist.gd_fee }</td>
				<td></td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">按销售额百分百结算</td>
				<c:if test="${xse_fee!='0.00' }">
				<td>${plist.xse_qty }</td>
				<td>${plist.xse_qtyunit }</td>
				<td>${plist.xse_fee }</td>
				<td>${plist.xse_remark }</td>
				</c:if>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">按实际使用量结算</td>
				<td>${sjsy_qty }</td>
				<td>${sjsy_qtyunit }</td>
				<td>${sjsy_fee }</td>
				<td>${sjsy_remark }</td>
			</tr>
<!-- 				<tr style="text-align: center;"> -->
<!-- 					<td width="30%">B2C订单处理费</td> -->
<%-- 					<td>${OF.btc_qty }</td> --%>
<%-- 					<td>${OF.btc_qtyunit }</td> --%>
<%-- 					<td>${OF.btc_fee }</td> --%>
<%-- 					<td>${OF.btc_remark }</td> --%>
<!-- 				</tr> -->
<!-- 				<tr style="text-align: center;"> -->
<!-- 					<td width="30%">B2B订单处理费</td> -->
<%-- 					<td>${OF.btb_qty }</td> --%>
<%-- 					<td>${OF.btb_qtyunit }</td> --%>
<%-- 					<td>${OF.btb_fee }</td> --%>
<%-- 					<td>${OF.btb_remark }</td> --%>
<!-- 				</tr> -->
<!-- 				<tr style="text-align: center;"> -->
<!-- 					<td width="30%">退换货入库费</td> -->
<%-- 					<td>${OF.return_qty }</td> --%>
<%-- 					<td>${OF.return_qtyunit }</td> --%>
<%-- 					<td>${OF.return_fee }</td> --%>
<%-- 					<td>${OF.return_remark }</td> --%>
<!-- 				</tr> -->
<!-- 				<tr style="text-align: center;"> -->
<!-- 					<td width="30%">收货上架费</td> -->
<%-- 					<td>${OF.ib_qty }</td> --%>
<%-- 					<td>${OF.ib_qtyunit }</td> --%>
<%-- 					<td>${OF.ib_fee }</td> --%>
<%-- 					<td>${OF.ib_remark }</td> --%>
<!-- 				</tr> -->
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%"><a href="javascript:;">耗材费小计</a></td>
				<td>${qty}</td>
				<td></td>
				<td>${totalamount}</td>
				<td></td>
			</tr>
			<c:forEach items="${hcf_list}" var="hcf_list">
			<tr style="text-align: center;">
				<td width="30%">
					<c:if test="${hcf_list.sku_type==1}">主材</c:if>
					<c:if test="${hcf_list.sku_type==2}">辅材</c:if>
				</td>
				<td>${hcf_list.qty}</td>
				<td>${hcf_list.qty_unit}</td>
				<td>${hcf_list.total_amount}</td>
				<td></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%"><a href="javascript:;">仓储小计</a></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">固定费用结算</td>
				<td>${ccf_list.fixed_qty}</td>
				<td>${ccf_list.fixed_unit}</td>
				<td>${ccf_list.fixed_cost}</td>
				<td>${ccf_list.fixed_comment}</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">按面积结算</td>
				<td>${ccf_list.area_qty}</td>
				<td>${ccf_list.area_costunit}</td>
				<td>${ccf_list.area_cost}</td>
				<td>${ccf_list.area_comment}</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">按件数结算</td>
				<td>${ccf_list.piece_qty}</td>
				<td>${ccf_list.piece_unit}</td>
				<td>${ccf_list.piece_cost}</td>
				<td>${ccf_list.piece_comment}</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%">按托盘结算</td>
				<td>${ccf_list.tray_qty}</td>
				<td>${ccf_list.tray_qtyunit}</td>
				<td>${ccf_list.tray_cost}</td>
				<td>${ccf_list.tray_comment}</td>
			</tr>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%"><a href="javascript:;">增值费小计</a></td>
				<td>${zzfwf_qty}</td>
				<td></td>
				<td>${zzfwf_hz}</td>
				<td></td>
			</tr>
			<c:forEach items="${zzfwf_list}" var="zzfwf_list">
				<tr style="text-align: center;">
					<td width="30%">${zzfwf_list.addservice_name}</td>
					<td>${zzfwf_list.qty}</td>
					<td>${zzfwf_list.qty_unit}</td>
					<td>${zzfwf_list.amount}</td>
					<td></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%"><a href="javascript:;">打包费</a></td>
				<td>${dbf_qty}</td>
				<td>单</td>
				<td>${dbf_all}</td>
				<td></td>
			</tr>
			<tr style="text-align: center;">
				<td width="30%"><a href="javascript:;">保价费</a></td>
				<td>${bfj_je}</td>
				<td>元</td>
				<td>${bfj_fee}</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr id="freight_summary" style="text-align: center;">
				<td width="30%">运费-合计(折后运费+保价费-总运费折扣)</td>
				<td></td>
				<td>单</td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="bSUE" items="${bSUEs }">
				<tr style="text-align: center;">
					<td width="30%">
						<a 
							href= "javascript:;" 
							onclick= "openDiv('${root}/control/expressUsedBalanceController/balanceDetailList.do?con_id=${bSUE.con_id }&express_code=${bSUE.express }&express_name=${bSUE.transport_name }&product_type_code=${bSUE.product_type_code }&product_type_name=${bSUE.product_type_name }&balance_month=${ym }&clientid=${clientid }&client_name=${client.client_name }&storeid=${storeid }')" >
							<c:if test= "${bSUE.express == 'SF' }" >
								${bSUE.product_type_name }
							</c:if>
							<c:if test= "${bSUE.express != 'SF' }" >
								${bSUE.transport_name }
							</c:if>
						</a>
					</td>
					<td>${bSUE.order_num }</td>
					<td>单</td>
					<td>${bSUE.total_freight + bSUE.total_insurance }元</td>
					<td></td>
			   	</tr>
			</c:forEach>
			<c:forEach var="itemswl" items="${wl_pool}">
				<tr style="text-align: center;">
					<td width="30%"><a href="javascript:;" onclick="jump_wl_detail('${itemswl.create_date}', '${itemswl.con_id}', '${itemswl.transport_code}', '${clientid}', '${storeid }', '${ym }')">${itemswl.transport_name}</a></td>
					<td>${itemswl.bill_num }</td>
					<td>单</td>
					<td>${itemswl.total_price }元</td>
					<td></td>
				</tr>			  
			</c:forEach>
			<tr id="total_freight_discount" style="text-align: center;">
				<td width="30%">总运费折扣</td>
				<td></td>
				<td></td>
				<td><c:if test= "${empty cUS }">0.00元</c:if><c:if test= "${not empty cUS }" ><c:forEach var="cUS" items="${cUS }" ><c:if test= "${cUS.fee_type == 0 }" >${cUS.fee }元</c:if></c:forEach></c:if></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr id="specialService_summary" style="text-align: center;" >
				<td width="30%">特殊服务费(COD+委托取件费)-合计</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr id="cod_summary" style="text-align: center;" >
				<td width="30%">COD项-合计</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="bSUE" items="${bSUEs }">
				<c:if test="${bSUE.total_cod != '0.00' }" >
					<tr style="text-align: center;">
						<td width="30%">
							<c:if test= "${bSUE.express == 'SF' }" >
								${bSUE.product_type_name }
							</c:if>
							<c:if test= "${bSUE.express != 'SF' }" >
								${bSUE.transport_name }
							</c:if>
						</td>
						<td></td>
						<td></td>
						<td>${bSUE.total_cod }元</td>
						<td></td>
				   	</tr>
				</c:if>
			</c:forEach>
			<tr id="delegatedPickup_summary" style="text-align: center;" >
				<td width="30%">委托取件费-合计</td>
				<td></td>
				<td></td>
				<td>0.00元</td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5" style="background-color: gray;">&nbsp;</td>
			</tr>
			<tr id="managementFee_summary" style="text-align: center;" >
				<td width="30%">管理费-合计</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="cUS" items="${cUS }" >
				<c:if test="${cUS.fee_type == 1 }" >
					<tr style="text-align: center;<c:if test="${empty cUS.transport_name }">display: none;</c:if>" >
						<td width="30%" >${cUS.transport_name }</td>
						<td></td>
						<td></td>
						<td>${cUS.fee }元</td>
						<td></td>
					</tr>
				</c:if>
			</c:forEach>
			<tr id= "endRow" style= "display: none"></tr>
		</table>
	</div>
	<div style="margin-left: 30px; display: inline;"></div>
	<div style="margin-left: 30px; display: inline;"></div>
	<!-- Title -->
</body>
</html>