<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
	</head>
	<body>
		<div class="page-header" style="margin-top : 10px; margin-left : 10px; height : 100px">
			<button class="btn btn-xs btn-app btn-primary" onclick="find();">
				<i class="icon-search bigger-230"></i>查询
			</button>
		</div>
		<div style="height: 400px; width: 100%; overflow: auto; overflow:scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
			<table class="table table-striped table-hover">
		   		<thead>
			  		<tr class="table_head_line">
			  		    <td nowrap="nowrap"><h5>对账周期</h5></td>
			  		    <td nowrap="nowrap"><h5>店铺</h5></td>
			  		    <td nowrap="nowrap"><h5>单量</h5></td>
			  		    <td nowrap="nowrap"><h5>运费</h5></td>
			  		    <td nowrap="nowrap"><h5>中转费</h5></td>
			  		    <td nowrap="nowrap"><h5>派送费</h5></td>
			  		    <td nowrap="nowrap"><h5>其他派送费</h5></td>
			  		    <td nowrap="nowrap"><h5>上楼费</h5></td>
			  		    <td nowrap="nowrap"><h5>提货费</h5></td>

			  			<td nowrap="nowrap"><h5>合计</h5></td>
			  		</tr>

		  		</thead>
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
		  			
			  		<tr align="center">
			  			<td nowrap="nowrap"><a href="javascript:void(0);" onclick="jump_detail('${list.contract_id}','${list.create_date_day}','${list.id}','${list.cost_center}','${list.start_place}','${list.shop_name}')">${list.create_date}</a></td>
			  			 <td nowrap="nowrap">${list.cycle_month}</td>
			  			<td nowrap="nowrap">${list.store_name}</td>
			  			<td nowrap="nowrap">${list.bill_num}</td>
			  			<td nowrap="nowrap">${list.trans_total_cost}</td>
			  			<td nowrap="nowrap">${list.transfer_total_cost}</td>
			  			<td nowrap="nowrap">${list.delivery_total_cost}</td>
			  			<td nowrap="nowrap">${list.other_deliever_cost}</td>
			  			<td nowrap="nowrap">${list.upstairs_total_cost}</td>
			  			<td nowrap="nowrap">${list.take_total_cost}</td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<script type="text/javascript">
			function pageJump() {
			 	var data=$("#serarch_td").serialize();	
		 		openDiv('${root}control/transPoolController/list.do?data='+data+'startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
			};
		</script>
	</body>
	<script type="text/javascript">
	  	/**
	   	* 查询
	   	*/
	  	function find(){
			openDiv('${root}control/transPoolController/list.do?createDate='+$("#createDate").val()+"&storeName="+$("#storeName").val());
	  	}

	  	function jump_detail(contractId,createDate,id,cost_center,start_place,shop_name){
	  		openDiv('${root}control/transPoolController/detail_list.do?createDate='+createDate+'&contractId='+contractId+'&cost_center='+cost_center+"&start_place="+start_place+"&shop_name="+shop_name);
		}	  	
	</script>
</html>
