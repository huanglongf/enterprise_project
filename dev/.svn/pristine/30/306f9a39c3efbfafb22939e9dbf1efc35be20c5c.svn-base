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
			<table>
		  		<tr>
		  			<td width="10%">运单号:</td>
		  			<td width="20%"><input id="waybill" type="text" name="waybill" value="${erCalMasterPar.waybill }" /></td>
		  			<td width="10%">店铺代码:</td>
		  			<td width="20%"><input id="warehouse_code" name="warehouse_code" type="text" value="${erCalMasterPar.warehouse_code }" /></td>
		  		</tr>
			</table>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;
			<button id="btn_export" class="btn btn-xs btn-inverse" onclick="radarExportJsp();">
				<i class="icon-trash"></i>导出
			</button
			>&nbsp;
		</div>
		<div>
			<table class="table table-striped table-hover">
		   		<thead>
			  		<tr>
			  			<td>运单号</td> 
			  			<td>物流公司</td>
			  			<td>店铺Code</td>
			  			<td>省Code</td>
			  			<td>市Code</td>
			  			<td>区/县Code</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${pageView.records}" var="pageView">
			  		<tr>
			  			<td>${pageView.waybill}</td>
			  			<td>${pageView.express_code}</td>
			  			<td>${pageView.warehouse_code}</td>
			  			<td>${pageView.p_code}</td>
			  			<td>${pageView.c_code}</td>
			  			<td>${pageView.s_code}</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
	</body>
	<script type="text/javascript">
	  function radarExportJsp(){
	  	$("#btn_export").attr("disabled", true);
	  	if($("#waybill").val()!=''||$("#warehouse_code").val()!=''){
	  		var url=root+'/control/rawDataExpressQueryController/radarExport.do?waybill='+$("#waybill").val()+"&warehouse_code="+$("#warehouse_code").val();
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
	  		if($("#waybill").val()!=''||$("#warehouse_code").val()!=''){
	  			openDiv("${root }control/rawDataExpressQueryController/radarQuery.do?waybill=" 
	  					+ $("#waybill").val()
	  					+ "&warehouse_code=" 
	  					+ $("#warehouse_code").val()
	  				);
		  	}else{
		  		alert("请选择查询条件");
		  	}
		}

		function pageJump() {
		      openDiv("${root }control/rawDataExpressQueryController/radarQuery.do?waybill=" 
						+ $("#waybill").val()
						+ "&warehouse_code=" 
						+ $("#warehouse_code").val()
						+ "&startRow=" 
						+ $("#startRow").val()
						+ "&endRow=" 
						+ $("#startRow").val()
						+ "&page=" 
						+ $("#pageIndex").val()
						+ "&pageSize=" 
						+ $("#pageSize").val()
			  );
		}
	</script>
</html>
