<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/lmis/yuriy.jsp"%>
<link type="text/css" href="<%=basePath %>css/table.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet" />
<style type="text/css">
label {
	margin-right: 15px;
	font-size: 14px;
}
</style>
</head>
<body>
	<input id="lastPage" name="lastPage" style='display: none'
		value='${queryParam.maxResult}'>
	<input id="master_id" name="master_id" style='display: none'
		value='${queryParam.master_id}'>
	<input id="lastTotalCount" name="lastTotalCount" style='display: none'
		value='${totalSize}'>
	
	<div class= "title_div" id= "sc_title1" style='height:80px'>
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   			<thead align="center">
						<tr class= "table_head_line"  >
						<td >
						<input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/>
							<td class="td_cs"  >序号</td>
							<!-- <td class="td_cs"  >核销周期</td> -->
							<td class="td_cs"  >月结账号</td>
							<td class="td_cs"  >发货时间</td>
							<td class="td_cs"  >运单号</td>
							<td class="td_cs"  >发货重量（kg）</td>
							<td class="td_cs"  >体积(长*宽*高）</td>
							<td class="td_cs"  >始发地（省）</td>
							<td class="td_cs"  >始发地（市）</td>
							<td class="td_cs"  >始发地（区）</td>
							<td class="td_cs"  >目的地(省)</td>
							<td class="td_cs"  >目的地(市)</td>
							<td class="td_cs"  >目的地(区)</td>
							<td class="td_cs"  >计费重量（kg）</td>
							<td class="td_cs"  >供应商名称</td>
							<td class="td_cs"  >产品类型</td>
							<td class="td_cs"  >保值</td>
							<td class="td_cs"  >运费</td>
							<td class="td_cs"  >其他增值服务费</td>
							<td class="td_cs"  >合计费用</td>
							<td class="td_cs"  >发货仓</td>
							<td class="td_cs"  >所属店铺</td>
							<td class="td_cs"  >成本中心代码</td>
							<td class="td_cs"  >前置单据号</td>
							<td class="td_cs"  >平台订单号</td>
							<td class="td_cs"  >耗材sku编码</td>
							<td class="td_cs"  >长（mm）</td>
							<td class="td_cs"  >宽（mm)</td>
							<td class="td_cs"  >高(mm)</td>
							<td class="td_cs"  >体积(mm3)</td>
							<td class="td_cs"  >发货时间</td>
							<td class="td_cs"  >始发地（省）</td>
							<td class="td_cs"  >始发地（市）</td>
							<td class="td_cs"  >目的地（省）</td>
							<td class="td_cs"  >目的地（市）</td>
							<td class="td_cs"  >发货重量</td>
							<td class="td_cs"  >物流商名称</td>
							<td class="td_cs"  >产品类型</td>
							<td class="td_cs"  >保价货值</td>
							<td class="td_cs"  >体积计费重量（mm3）</td>
							<td class="td_cs"  >计费重量（kg)</td>
							<td class="td_cs"  >首重</td>
							<td class="td_cs"  >续重</td>
							<td class="td_cs"  >折扣</td>
							<td class="td_cs"  >标准运费</td>
							<td class="td_cs"  >折扣运费</td>
							<td class="td_cs"  >保价费</td>
							<td class="td_cs"  >附加费&服务费</td>
							<td class="td_cs"  >最终费用</td>
							<td class="td_cs"  >是否核销</td>
							<td class="td_cs"  >未核销原因备注</td>
							<td class="td_cs"  >备注</td>
						</tr>
						<tr>
						<td style='width: 50px'>
							<td class="td_ch"  ></td>
							<!-- <td class="td_cs"  >
							<input onkeyup="showMsg2(this.name)" name="billingCycle" /></td> -->
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="month_account" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="transport_time" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="waybill" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="transport_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="transport_volumn" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="origin_province" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="origin_city" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="origin_state" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="dest_province" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="dest_city" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="dest_state" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="charged_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="express_code" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="producttype_code" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="insurance" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="freight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="other_value_added_service_charges" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="total_charge" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="transport_warehouse" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="store" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="cost_center" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="epistatic_order" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="platform_no" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="sku_number" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="length" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="width" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="height" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="volumn" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="transport_time1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="origin_province1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="origin_city1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="dest_province1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="dest_city1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="transport_weight1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="express_code1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="producttype_code1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="insurance1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="volumn_charged_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="charged_weight1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="firstWeight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="addedWeight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="discount" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="standard_freight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="afterdiscount_freight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="insurance_fee1" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="additional_fee" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="last_fee" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="is_verification" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="reason" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="remarks" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="total_discount" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="physical_accounting" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="jp_num" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="volumn_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="volumn_account_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="jf_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="first_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="first_weight_price" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="added_weight" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="added_weight_price" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="account_id" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="master_id" />
						<td class="td_cs"  ><input onkeyup="showMsg2(this.name)" name="close_account" />
						</tr>
					</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;overflow-x:show;" >
		  		<tbody align= "center">
			  			<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td class="td_ch" ><input id="ckb" name="ckb" type="checkbox" value="${records.id}"></td>
							<td class="td_cs"  title="${status.count }">${status.count }</td>
						<!-- 	<td class="td_cs"  title="${records.billingCycle }">${records.billingCycle }</td> -->
							<td class="td_cs" title="${records.transport_time }">
							<fmt:formatDate value="${records.transport_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs"  title="${records.waybill }">${records.waybill }</td>
							<td class="td_cs"  title="${records.transport_weight }">${records.transport_weight }</td>
							<td class="td_cs"  title="${records.transport_volumn }">${records.transport_volumn }</td>
							<td class="td_cs"  title="${records.origin_province }">${records.origin_province }</td>
							<td class="td_cs"  title="${records.origin_city }">${records.origin_city }</td>
							<td class="td_cs"  title="${records.origin_state }">${records.origin_state }</td>
							<td class="td_cs"  title="${records.dest_province }">${records.dest_province }</td>
							<td class="td_cs"  title="${records.dest_city }">${records.dest_city }</td>
							<td class="td_cs"  title="${records.dest_state }">${records.dest_state }</td>
							<td class="td_cs"  title="${records.charged_weight }">${records.charged_weight }</td>
							<td class="td_cs"   title="${records.express_code }">${records.express_code }</td>
							<td class="td_cs"   title="${records.producttype_code }">${records.producttype_code }</td>
							<td class="td_cs"   title="${records.insurance }">${records.insurance }</td>
							<td class="td_cs"   title="${records.freight }">${records.freight }</td>
							<td class="td_cs"   title="${records.insurance_fee }">${records.insurance_fee }</td>
							<td class="td_cs"   title="${records.other_value_added_service_charges }">${records.other_value_added_service_charges }</td>
							<td class="td_cs"   title="${records.total_charge }">${records.total_charge }</td>
							<td class="td_cs"   title="${records.transport_warehouse }">${records.transport_warehouse }</td>
							<td class="td_cs"   title="${records.store }">${records.store }</td>
							<td class="td_cs"   title="${records.cost_center }">${records.cost_center }</td>
							<td class="td_cs"   title="${records.epistatic_order }">${records.epistatic_order }</td>
							<td class="td_cs"   title="${records.platform_no }">${records.platform_no }</td>
							<td class="td_cs"   title="${records.sku_number }">${records.sku_number }</td>
							<td class="td_cs"   title="${records.length }">${records.length }</td>
							<td class="td_cs"   title="${records.width }">${records.width }</td>
							<td class="td_cs"   title="${records.height }">${records.height }</td>
							<td class="td_cs"   title="${records.volumn }">${records.volumn }</td>
							<td class="td_cs"   title="${records.transport_time1 }">
							<fmt:formatDate value="${records.transport_time1}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs"   title="${records.origin_province1 }">${records.origin_province1 }</td>
							<td class="td_cs"   title="${records.origin_city1 }">${records.origin_city1 }</td>
							<td class="td_cs"   title="${records.dest_province1 }">${records.dest_province1 }</td>
							<td class="td_cs"   title="${records.dest_city1 }">${records.dest_city1 }</td>
							<td class="td_cs"   title="${records.transport_weight1 }">${records.transport_weight1 }</td>
							<td class="td_cs"   title="${records.express_code1 }">${records.express_code1 }</td>
							<td class="td_cs"   title="${records.producttype_code1 }">${records.producttype_code1 }</td>
							<td class="td_cs"  title="${records.insurance1 }">${records.insurance1 }</td>
							<td class="td_cs"  title="${records.volumn_charged_weight }">${records.volumn_charged_weight }</td>
							<td class="td_cs"  title="${records.charged_weight1 }">${records.charged_weight1 }</td>
							<td class="td_cs"  title="${records.firstWeight }">${records.firstWeight }</td>
							<td class="td_cs"  title="${records.addedWeight }">${records.addedWeight }</td>
							<td class="td_cs"  title="${records.discount }">${records.discount }</td>
							<td class="td_cs"  title="${records.standard_freight }">${records.standard_freight }</td>
							<td class="td_cs"  title="${records.afterdiscount_freight }">${records.afterdiscount_freight }</td>
							<td class="td_cs"  title="${records.insurance_fee1 }">${records.insurance_fee1 }</td>
							<td class="td_cs"  title="${records.additional_fee }">${records.additional_fee }</td>
							<td class="td_cs"  title="${records.last_fee }">${records.last_fee }</td>
							<td class="td_cs" title="${records.is_verification }">
							<c:if test="${records.is_verification=='1'}">已核销</c:if>
		   					<c:if test="${records.is_verification=='0'}">未核销</c:if>
							</td>
							<td class="td_cs"  title="${records.reason}">${records.reason}</td>
							<td class="td_cs"  title="${records.remarks }">${records.remarks }</td>
						</tr>
		  			</c:forEach>
		  		</tbody>
			</table>
		</div>
	
	
	<!-- 分页添加 -->
	<div style="margin-right: 5%; margin-top: 20px; margin-bottom: 10%;">${pageView.pageView}</div>
	<!-- 分页添加 -->
</body>
<script type="text/javascript">
	  
	$("#table_content tbody tr").dblclick(function(){
		var  tr=$(this);
		
		openDiv('${root}control/lmis/expressbillMasterController/tablist.do?id='+tr.find('input[name="ckb"]').val());
		
	});
	
	
	
	
	
	function pageJump() {
		 var param='';
		 loadingStyle();
		 param=$('#main_search').serialize();
		// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	 	$.ajax({
				type: "POST",
	           	url:'${root}/control/lmis/diffBilldeatilsController/pageQuery.do?master_id='+$("#master_id").val()+'&is_verification=0'+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
	           	dataType: "text",
	           	data:'',
	    		cache:false,
	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	           	success: function (data){
	              $("#diff_billdeatils_page").html(data);
	              cancelLoadingStyle();
	           	}
		  	});  
		  	
		  	//$.post('${root}/control/expressReturnStorageController/query.do?storeCode=tst&warehouseCode=erreer',function(){});
	    }
	</script>
</html>
<style>
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.table_head_line td {
	font-weight: bold;
	font-size: 16px
}

.modal-header {
	height: 50px;
}
</style>
