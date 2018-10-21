<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%-- 		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet"> --%>
<%-- 		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" /> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script> --%>
<%-- 		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script> --%>
<%-- 		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />		 --%>
		
		
		
				<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />	
	   <script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
			$(document).ready(function() {
				$(".form-control").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
			$(document).ready(function() {
				$("#reservation2").daterangepicker(null, function(start, end, label) {
                	console.log(start.toISOString(), end.toISOString(), label);
               	});
 			});
		</script>		
	</head>
	
	<body>


<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table>

		  		<tr>
		  			<td width="10%">始发地:</td>
		  			<td width="20%"><input id="startPlace" name="startPlace" type="text"  value="${queryParam.startPlace}" /></td>
		  			<td width="10%">目的地:</td>
		  			<td width="20%"><input id="endPlace" name="endPlace" type="text"  value="${queryParam.endPlace}"/></td>
		  		</tr>	
		  		<tr>
		  			<td width="10%">订单日期:</td>
		  			<td width="20%">
					<div class="input-prepend input-group" style="width: 240px;">
					  <span class="add-on input-group-addon">
						<i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
					 <input type="text" readonly  style="width:100%" name="orederTime" id="orederTime" class="form-control" value="${queryParam.startOrederTime}" />
					</div>		
		  			</td>
		  			<td width="10%">订单号/指令号:</td>
		  			<td width="20%"><input id="orderNum" name="orderNum" type="text"  value="${queryParam.orderNum}"/></td>
		  		</tr>		
		  		
		  		<tr>
		  		<td width="10%">运输时间:</td>
		  			<td width="20%">
					<div class="input-prepend input-group" style="width: 240px;">
					  <span class="add-on input-group-addon">
						<i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
					 <input type="text" readonly  style="width:100%" name="transportTime" id="transportTime" class="form-control" value="${queryParam.startTransportTime}" />
					</div>				  			
		  			</td>
		  			<td width="10%">运单号:</td>
		  			<td width="20%"><input id="transportNum" name="transportNum" type="text"  value="${queryParam.transportNum}" /></td>
		  		</tr>		  			  		
			</table>
		</div>
    
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>&nbsp;
			<button class="btn btn-xs btn-pink" onclick="excel();">
				<i class="icon-search icon-on-right bigger-110"></i>导出
			</button>&nbsp;			
			<button class="btn btn-xs btn-inverse" onclick="clean();">
				<i class="icon-trash"></i>清除查询条件
			</button>&nbsp;				
            <button class="btn btn-xs btn-grey" type="button" onclick="jump();">
   					<i class="icon-undo" ></i>返回
       		</button>			
		</div>
				
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  		    <td class="td_cs">物流商</td>
			  		    <td class="td_cs">运输类别</td>
			  		    <td class="td_cs">运输方向</td>
			  		    <td class="td_cs">订单日期</td>
			  		    <td class="td_cs">运输时间</td>
			  		    <td class="td_cs">运单号</td>
			  		    <td class="td_cs">店铺/品牌</td>
			  		    <td class="td_cs">订单号/指令号</td>
			  		    <td class="td_cs">始发地</td>
			  		    <td class="td_cs">目的省</td>
			  		    <td class="td_cs">市区</td>
			  		    <td class="td_cs">送货地址</td>
			  		    <td class="td_cs">送货件数</td>
			  		    <td class="td_cs">箱数</td>
			  		    <td class="td_cs">实际重量（kg)</td>
			  		    <td class="td_cs">体积立方(M3）</td>
			  		    <td class="td_cs">计费重量(kg)</td>
			  		    <td class="td_cs">重货单价</td>
			  		    <td class="td_cs">体积单价</td>
			  		    <td class="td_cs">重货费用</td>
			  		    <td class="td_cs">体积费用</td>
			  		    <td class="td_cs">最低收费</td>
			  		    <td class="td_cs">运费</td>
			  		    <td class="td_cs">送货费</td>
			  		    <td class="td_cs">保价金额</td>
			  			<td class="td_cs">保价费</td>
			  			<td class="td_cs">装卸费</td>
			  			<td class="td_cs">提货费</td>
			  			<td class="td_cs">其它费用</td>
			  			<td class="td_cs">合计</td>
			  			<td class="td_cs">备注</td>
			  		</tr>
		  		</thead>
</table>
</div>
<div class="tree_div"></div>



<div class="content_div" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">	
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  		    <td class="td_cs" title="${list.transport_name}">${list.transport_name}</td>
			  			<td class="td_cs" title="${list.transport_type}">${list.transport_type}</td>
			  			<td class="td_cs" title="${list.transport_direction}">${list.transport_direction}</td>
			  			<td class="td_cs" title="${list.bookbus_time}">${list.bookbus_time}</td>
			  			<td class="td_cs" title="${list.transport_time}">${list.transport_time}</td>
			  			<td class="td_cs" title="${list.expressno}">${list.expressno}</td>
			  			<td class="td_cs" title="${list.store_name}">${list.store_name}</td>
			  			<td class="td_cs" title="${list.orderno}">${list.orderno}</td>
			  			<td class="td_cs" title="${list.originating_place}">${list.originating_place}</td>
			  			<td class="td_cs" title="${list.privince_name}">${list.privince_name}</td>
			  			<td class="td_cs" title="${list.city_name}">${list.city_name}</td>
			  			<td class="td_cs" title="${list.delivery_address}">${list.delivery_address}</td>
			  			<td class="td_cs" title="${list.delivery_number}">${list.delivery_number}</td>
			  			<td class="td_cs" title="${list.box_number}">${list.box_number}</td>
			  			<td class="td_cs" title="${list.real_weight}">${list.real_weight}</td>
			  			<td class="td_cs" title="${list.volumn_cubic}">${list.volumn_cubic}</td>
			  			<td class="td_cs" title="">-</td>
			  			<td class="td_cs" title="${list.realweight_price}">${list.realweight_price}</td>
			  			<td class="td_cs" title="${list.volumn_price}">${list.volumn_price}</td>		
			  			<td class="td_cs" title="${list.realweight_cost}">${list.realweight_cost}</td>
			  			<td class="td_cs" title="${list.volumnweight_cost}">${list.volumnweight_cost}</td>
			  			<td class="td_cs" title="${list.lower_price}">${list.lower_price}</td>
			  			<td class="td_cs" title="${list.trans_cost}">${list.trans_cost}</td>
			  		    <td class="td_cs" title="${list.delevery_charges}">${list.delevery_charges}</td>
			  		    <td class="td_cs" title="${list.insurance_goods_price}">${list.insurance_goods_price}</td>
			  			<td class="td_cs" title="${list.insurance_fee}">${list.insurance_fee}</td>
			  			<td class="td_cs" title="${list.loading_charges}">${list.loading_charges}</td>
			  			<td class="td_cs" title="${list.pickup_charges}">${list.pickup_charges}</td>
			  			<td class="td_cs" title="${list.other_charges}">${list.other_charges}</td>
			  			<td class="td_cs" title="${list.total_cost}">${list.total_cost}</td>
			  			<td class="td_cs" title="${list.remark}">${list.remark}</td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
			<form id="serarch_td">
			<input type="hidden" id="contractId" name="contractId" value="${queryParam.contractId}">
			<input type="hidden" id="cost_center" name="cost_center"  value="${queryParam.cost_center}">
			<input type="hidden" id="createDate"  name="createDate" value="${queryParam.createDate}">
			<input type="hidden" id="start_place" name="start_place" value="${queryParam.startPlace}">
			<input type="hidden" id="shop_name" name="shop_name"  value="${queryParam.storeName}">	
			<input type="hidden" id="transportCode" name="transportCode"  value="${queryParam.transportCode}">		
			<input type="hidden" id="opId" name="opId"  value="${queryParam.opId}">	
			<input type="hidden" id="ym" name="ym"  value="${queryParam.ym}">	
			<input type="hidden" id="storeid" name="storeid"  value="${queryParam.storeid}">	
			<input type="hidden" id="clientid" name="clientid"  value="${queryParam.clientid}">	
			</form>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<script type="text/javascript">
			function pageJump() {
			 	var data=$("#serarch_td").serialize();
		 		openDiv('${root}control/transPoolController/detail_list.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
			};
		</script>
	</body>
	<script type="text/javascript">
	  	/**
	   	* 查询
	   	*/
	  	function find(){
	  		var data=$("#serarch_td").serialize();
			openDiv('${root}control/transPoolController/detail_list.do?'+data
					                                                  +'&transportNum='+$("#transportNum").val()
					                                                  +'&orderNum='+$("#orderNum").val()
					                                                  +'&startPlace='+$("#startPlace").val()
                                                                      +'&endPlace='+$("#endPlace").val()
                                                                      +"&orederTime="+$("#orederTime").val().replace(" - ",'/')
                                                                      +'&transportTime='+$("#transportTime").val().replace(" - ",'/')
                                                                      +'&transportCode='+$("#transportCode").val()
					                                                  )
	  	}

	  	function jump(){
		  	if($("#opId").val()=='2'){
		  		openDiv('${root}/control/summaryController/summaryList.do?ym='+$("#ym").val()+'&clientid='+$("#clientid").val()+'&storeid='+$("#storeid").val());
			}else{
		  		var data=$("#serarch_td").serialize();
		  		openDiv('${root}/control/transPoolController/list.do?'+data);
			}
		}

		function excel(){
			
			var data=$("#serarch_td").serialize();
			var url=root+'/control/transPoolController/excel_pool_detail.do?'+data
            +'&transportNum='+$("#transportNum").val()
            +'&orderNum='+$("#orderNum").val()
            +'&startPlace='+$("#startPlace").val()
            +'&endPlace='+$("#endPlace").val()
            +"&orederTime="+$("#orederTime").val().replace(" - ",'/')
            +'&transportTime='+$("#transportTime").val().replace(" - ",'/');
			 $.ajax({
					type : "POST",
					url: url,  
					data:"",
					dataType: "",  
					success : function(jsonStr) {
						window.open(root+jsonStr);
					}
				});
			}
	  	function clean(){
	  		$("input[type*='text']").each(function(){
	  		     $(this).val('');
	  		});
	  	}		
	</script>
</html>
