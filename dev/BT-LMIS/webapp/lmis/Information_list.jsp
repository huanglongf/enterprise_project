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
		  			<td width="10%">条形码:</td>
		  			<td width="20%"><input id="barcode" type="text" name="barcode" value="${erExpressinfoDetailPar.barcode }" /></td>
		  			<td width="10%">运单号:</td>
		  			<td width="20%"><input id="waybill" name="waybill" type="text" value="${erExpressinfoDetailPar.waybill }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">订单时间:</td>
		  			<td width="20%">
		  			    <div class="input-prepend input-group" style="width: 240px;">
					      <span class="add-on input-group-addon">
						    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
					       <input type="text" readonly  style="width:100%" name="create_time" id="create_time" class="form-control" value="${erExpressinfoDetailPar.create_time}" />
					     </div>
		  			</td>
		  			<td width="10%">商品名称:</td>
		  			<td width="20%"><input id="item_name" type="text" name="item_name" value="${erExpressinfoDetailPar.item_name }" /></td>
		  		</tr>
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="exportInformation();">
				<i class="icon-trash"></i>导出
			</button
			>&nbsp;
		</div>
		<div>
			<table class="table table-striped table-hover">
		   		<thead>
			  		<tr>
			  			<td>创建时间</td>
			  			<td>更新时间</td>
			  			<td>SKU条码</td>
			  			<td>条形码</td>
			  			<td>商品名称</td>
			  			<td>运单号</td>
			  			
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="pageView">
			  		<tr>
			  			<td>${pageView.create_time}</td>
			  			<td>${pageView.update_time}</td>
			  			<td>${pageView.sku_number}</td>
			  			<td>${pageView.barcode}</td>
			  			<td>${pageView.item_name}</td>
			  			<td>${pageView.waybill}</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">
	  	
	  	function exportInformation(){
			  	$("#btn_export").attr("disabled", true);
			  	if($("#barcode").val()!=''||$("#waybill").val()!=''||$("#create_time").val()!=''||$("#item_name").val()!=''){
			  		var url=root+'/control/rawDataExpressQueryController/expressInformationExport.do?barcode='+$("#barcode").val()+"&waybill="+$("#waybill").val()+"&create_time="+$("#create_time").val()+"&item_name="+$("#item_name").val();
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

	  	function find() {
	  		var data=$("#main_search").serialize();
	  		if($("#barcode").val()!=''||$("#waybill").val()!=''||$("#create_time").val()!=''||$("#item_name").val()!=''){
	  			openDivs('${root}control/rawDataExpressQueryController/expressInformationQuery.do?',data);
		  	}else{
	  			alert("请选择查询条件");
		  	}
		}

		function pageJump() {
		     var data=$("#main_search").serialize();
			 openDiv('${root}control/rawDataExpressQueryController/expressInformationQuery.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		}
	</script>
</html>
