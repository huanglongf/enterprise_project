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
		<div style="height: 400px; width: 100%; overflow: auto; overflow:scroll; border: solid #F2F2F2 1px;">
			<table class="table table-striped table-hover">
		   		<thead>
			  		<tr class="table_head_line">
			  			<td nowrap="nowrap" align="center">结算周期</td>
			  		    <td nowrap="nowrap" align="center">承运商</td>
			  		    <td nowrap="nowrap" align="center">合同名称</td>			  		    
			  		</tr>

		  		</thead>
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
		  			
			  		<tr align="center">
			  		   <td nowrap="nowrap"><a href="javascript:void(0);" onclick="jump_detail('${list.contract_id}','${list.create_date}','${list.transportCode}')">${list.settle_cyle}</a></td>
			  			 <td nowrap="nowrap">${list.transport_name}</td>
			  			<td nowrap="nowrap">${list.contract_name}</td>
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
			openDiv('${root}control/transPoolController/list.do?createDate='+$("#createDate").val()+"&storeName="+$("#storeName").val()+"&transportCode="+transportCode);
	  	}

	  	function findMb(){
			openDiv('${root}control/transPoolController/list_gt.do?createDate='+$("#createDate").val()+"&storeName="+$("#storeName").val());
	  	}
	  	
	  	function jump_detail(contractId,createDate,transportCode){
	  		openDiv('${root}control/transPoolController/list.do?createDate='+createDate+'&contractId='+contractId+"&transportCode="+transportCode);
		}	  	
	</script>
</html>
