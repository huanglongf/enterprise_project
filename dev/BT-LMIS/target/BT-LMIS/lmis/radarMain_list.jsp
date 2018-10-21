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
		  			<td width="10%">店铺代码:</td>
		  			<td width="20%"><input id="store_code" type="text" name="store_code" value="${erExpressinfoAasterPar.store_code }" /></td>
		  			<td width="10%">揽件仓库代码（物理仓）:</td>
		  			<td width="20%"><input id="warehouse_code" name="warehouse_code" type="text" value="${erExpressinfoAasterPar.warehouse_code }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">订单时间:</td>
		  			<td width="20%">
		  			    <div class="input-prepend input-group" style="width: 240px;">
					      <span class="add-on input-group-addon">
						    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
					       <input type="text" readonly  style="width:100%" name="create_time" id="create_time" class="form-control" value="${erExpressinfoAasterPar.create_time}" />
					     </div>
		  			</td>
		  			<td width="10%">运单号:</td>
		  			<td width="20%"><input id="waybill" type="text" name="waybill" value="${erExpressinfoAasterPar.waybill }" /></td>
		  		</tr>
			</table>
			</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="exportMain();">
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
			  			<td>快递单号</td>
			  			<td>快递服务商代码</td>
			  			<td>平台订单号</td>
			  			<td>店铺代码</td>
			  			<td>揽件仓库代码（物理仓）</td>
			  			<td>收件人</td>
			  			<td>联系电话</td>
			  			<td>收件地址</td>
			  			<td>目的省代码</td>
			  			<td>目的市代码</td>
			  			<td>目的区代码</td>
			  			<td>目的街道代码</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="pageView">
			  		<tr>
			  			<td>${pageView.create_time}</td>
			  			<td>${pageView.update_time}</td>
			  			<td>${pageView.waybill}</td>
			  			<td>${pageView.express_code}</td>
			  			<td>${pageView.platform_no}</td>
			  			<td>${pageView.store_code}</td>
			  			<td>${pageView.warehouse_code}</td>
			  			<td>${pageView.shiptoname}</td>
			  			<td>${pageView.phone}</td>
			  			<td>${pageView.address}</td>
			  			<td>${pageView.provice_code}</td>
			  			<td>${pageView.city_code}</td>
			  			<td>${pageView.state_code}</td>
			  			<td>${pageView.street_code}</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">
	  	function exportMain(){
		  	$("#btn_export").attr("disabled", true);
		  	if($("#store_code").val()!=''||$("#warehouse_code").val()!=''||$("#waybill").val()!=''||$("#create_time").val()!=''){
		  		var url=root+'/control/rawDataExpressQueryController/radarMainExport.do?store_code='+$("#store_code").val()+"&warehouse_code="+$("#warehouse_code").val()+"&waybill="+$("#waybill").val()+"&create_time="+$("#create_time").val();
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
		  	if($("#store_code").val()!=''||$("#warehouse_code").val()!=''||$("#waybill").val()!=''||$("#create_time").val()!=''){
		  		openDivs('${root}control/rawDataExpressQueryController/radarMainQuery.do?',data);
			}else{
		  		alert("请选择查询条件");
			}
		}
		function pageJump() {
			  var data=$("#main_search").serialize();
			  openDiv('${root}control/rawDataExpressQueryController/radarMainQuery.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		}
	</script>
</html>
