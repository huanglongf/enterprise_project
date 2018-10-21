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
		  			<td width="20%"><input id="store_name" type="text" name="store_name" value="${tbInvitationRealuseanmountDataPar.store_name }" /></td>
		  			<td width="10%">耗材编码:</td>
		  			<td width="20%"><input id="sku_code" name="sku_code" type="text" value="${tbInvitationRealuseanmountDataPar.sku_code }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">成本中心:</td>
		  			<td width="20%"><input id="cost_center" type="text" name="cost_center" value="${tbInvitationRealuseanmountDataPar.cost_center }" /></td>
		  			<td width="10%">耗材名称:</td>
		  			<td width="20%"><input id="sku_name" name="sku_name" type="text" value="${tbInvitationRealuseanmountDataPar.sku_name }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">订单时间:</td>
		  			<td width="20%">
		  			    <div class="input-prepend input-group" style="width: 240px;">
					      <span class="add-on input-group-addon">
						    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
					       <input type="text" readonly  style="width:100%" name="create_time" id="create_time" class="form-control" value="${tbInvitationRealuseanmountDataPar.create_time}" />
					     </div>
		  			</td>
		  		</tr>
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="exportSupplies();">
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
			  			<td>店铺代码</td>
			  			<td>店铺名称</td>
			  			<td>成本中心</td>
			  			<td>耗材编码</td>
			  			<td>耗材名称</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="pageView">
			  		<tr>
			  			<td>${pageView.create_time}</td>
			  			<td>${pageView.update_time}</td>
			  			<td>${pageView.store_code}</td>
			  			<td>${pageView.store_name}</td>
			  			<td>${pageView.cost_center}</td>
			  			<td>${pageView.sku_code}</td>
			  			<td>${pageView.sku_name}</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">
	  	function exportSupplies(){
		  	$("#btn_export").attr("disabled", true);
		  	if($("#store_name").val()!=''||$("#sku_code").val()!=''||$("#cost_center").val()!=''||$("#sku_name").val()!=''||$("#create_time").val()!=''){
		  		var url=root+'/control/rawDataExpressQueryController/suppliesAmountExport.do?store_name='+$("#store_name").val()+"&sku_code="+$("#sku_code").val()+"&cost_center="+$("#cost_center").val()+"&sku_name="+$("#sku_name").val()+"&create_time="+$("#create_time").val();
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
	  		if($("#store_name").val()!=''||$("#sku_code").val()!=''||$("#cost_center").val()!=''||$("#sku_name").val()!=''||$("#create_time").val()!=''){
	  			openDivs('${root}control/rawDataExpressQueryController/suppliesAmountQuery.do?',data);
			}else{
				alert("请选择查询条件");
		    }
		}
	  	function pageJump() {
			  var data=$("#main_search").serialize();
			  openDiv('${root}control/rawDataExpressQueryController/suppliesAmountQuery.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		}
	</script>
</html>
