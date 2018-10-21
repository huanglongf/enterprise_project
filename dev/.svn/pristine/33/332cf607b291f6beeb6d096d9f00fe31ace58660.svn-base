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
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js"></script>
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
		<div class="page-header" style="margin-left : 20px;">
		<form id= "main_search">
			<table>
		  		<tr>
		  			<td width="10%">店铺名称:</td>
		  			<td width="20%"><input id="store_name" type="text" name="store_name" value="${tbInvitationUseanmountDataPar.store_name }" /></td>
		  			<td width="10%">PO单号:</td>
		  			<td width="20%"><input id="inbound_no" name="inbound_no" type="text" value="${tbInvitationUseanmountDataPar.inbound_no }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">宝尊编码:</td>
		  			<td width="20%"><input id="bz_number" type="text" name="bz_number" value="${tbInvitationUseanmountDataPar.bz_number }" /></td>
		  			<td width="10%">商品名称:</td>
		  			<td width="20%"><input id="item_name" name="item_name" type="text" value="${tbInvitationUseanmountDataPar.item_name }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">订单时间:</td>
		  			<td width="20%">
		  			    <div class="input-prepend input-group" style="width: 240px;">
					      <span class="add-on input-group-addon">
						    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
					       <input type="text" readonly  style="width:100%" name="create_time" id="create_time" class="form-control" value="${tbInvitationUseanmountDataPar.create_time}" />
					     </div>
		  			</td>
		  		</tr>
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="findParma();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="exportForm();">
				<i class="icon-trash"></i>导出
			</button
			>&nbsp;
		</div>
		<div>
			<table class="table table-striped table-hover">
		   		<thead>
			  		<tr>
			  			<td>创建时间</td>
			  			<td>创建人</td>
			  			<td>更新时间</td>
			  			<td>入库时间</td>
			  			<td>店铺名称</td>
			  			<td>供应商</td>
			  			<td>PO单号</td>
			  			<td>宝尊编码</td>
			  			<td>实际入库数量</td>
			  			<td>采购单价</td>
			  			<td>账期类型</td>
			  			<td>商品名称</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="list">
			  		<tr>
			  			<td>${list.create_time}</td>
			  			<td>${list.create_user}</td>
			  			<td>${list.update_time}</td>
			  			<td>${list.ib_time}</td>
			  			<td>${list.store_name}</td>
			  			<td>${list.vendor}</td>
			  			<td>${list.inbound_no}</td>
			  			<td>${list.bz_number}</td>
			  			<td>${list.inbound_qty}</td>
			  			<td>${list.purchase_price}</td>
			  			<td>${list.paymentdays_type}</td>
			  			<td>${list.item_name}</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>	
	</body>
	<script type="text/javascript">
	  	function exportForm(){
	  		$("#btn_export").attr("disabled", true);
	  		if($("#store_name").val()!=''||$("#inbound_no").val()!=''||$("#bz_number").val()!=''||$("#item_name").val()!=''||$("#create_time").val()!=''){
	  			var url=root+'/control/rawDataExpressQueryController/suppliesDetailExport.do?store_name='+$("#store_name").val()+"&inbound_no="+$("#inbound_no").val()+"&bz_number="+$("#bz_number").val()+"&item_name="+$("#item_name").val()+"&create_time="+$("#create_time").val();
				var htm_td="";
				   $.ajax({
						type : "POST",
						url: url,  
						data:"",
						dataType: "",  
						success : function(jsonStr) {
							window.open(root+jsonStr);
							$("#btn_export").attr("disabled", false);
						}
					});
		  	}else{
	  			alert("请选择查询条件");
		  	}
		}

	  	function findParma() {
	  		var data=$("#main_search").serialize();
		  	if($("#store_name").val()!=''||$("#inbound_no").val()!=''||$("#bz_number").val()!=''||$("#item_name").val()!=''||$("#create_time").val()!=''){
		  		openDivs('${root}control/rawDataExpressQueryController/suppliesDetailQuery.do?',data);
			}else{
				alert("请选择查询条件");
			}
		}
	  	function pageJump() {
			  var data=$("#main_search").serialize();
			  openDiv('${root}control/rawDataExpressQueryController/suppliesDetailQuery.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		}
	</script>
</html>
