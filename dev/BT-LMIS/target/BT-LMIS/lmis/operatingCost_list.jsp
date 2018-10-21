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
		<div class="page-header" style="margin-left : 20px;">
		<form id= "main_search">
			<table>
		  		<tr>
		  			<td width="10%">店铺名称:</td>
		  			<td width="20%"><input id="store_name" type="text" name="store_name" value="${tbOperationfeeDataPar.store_name }" /></td>
		  			<td width="10%">作业单号:</td>
		  			<td width="20%"><input id="job_orderno" name="job_orderno" type="text" value="${tbOperationfeeDataPar.job_orderno }" /></td>
		  		</tr>
		  		<tr>
		  			<td width="10%">货号:</td>
		  			<td width="20%"><input id="art_no" name="art_no" type="text" value="${tbOperationfeeDataPar.art_no }" /></td>
		  			<td width="10%">库存状态:</td>
		  			<td width="20%">
		  				<input id="inventory_status" name ="inventory_status" type="text" value="${tbOperationfeeDataPar.inventory_status }" />
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
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="exportOperating();">
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
			  			<td>订单类型</td>
			  			<td>店铺名称</td>
			  			<td>作业单号</td>
			  			<td>作业类型名称</td>
			  			<td>库位编码</td>
			  			<td>入库数量</td>
			  			<td>商品编码</td>
			  			<td>库存状态</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="pageView">
			  		<tr>
			  			<td>${pageView.create_time}></td>
			  			<td>${pageView.update_time}</td>
			  			<td>${pageView.order_type}</td>
			  			<td>${pageView.store_name}</td>
			  			<td>${pageView.job_orderno}</td>
			  			<td>${pageView.job_type}</td>
			  			<td>${pageView.storaggelocation_code}</td>
			  			<td>${pageView.in_num}</td>
			  			<td>${pageView.item_number}</td>
			  			<td>${pageView.inventory_status}</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">

	  	function exportOperating(){
		  	$("#btn_export").attr("disabled", true);
		  	if($("#store_name").val()!=''||$("#job_orderno").val()!=''||$("#art_no").val()!=''||$("#inventory_status").val()!=''){
		  		var url=root+'/control/rawDataExpressQueryController/operatingCostExport.do?store_name='+$("#store_name").val()+"&job_orderno="+$("#job_orderno").val()+"&art_no="+$("#art_no").val()+"&inventory_status="+$("#inventory_status").val();
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
	  		if($("#store_name").val()!=''||$("#job_orderno").val()!=''||$("#art_no").val()!=''||$("#inventory_status").val()!=''){
	  			openDivs('${root}control/rawDataExpressQueryController/operatingCostQuery.do?',data);
			}else{
				alert("请选择查询条件");
			}
		}
		function pageJump() {
			  var data=$("#main_search").serialize();
			  openDiv('${root}control/rawDataExpressQueryController/operatingCostQuery.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		}
	</script>
</html>
