<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type='text/javascript'>
		
		
		
		function pageJump() {
			var param='';
			 $.ajax({
					type: "POST",
		          	url:'${root}/control/orderPlatform/waybillMasterController/baozunupload_page.do?'+
		          			param+
		          			'&startRow='+
		          			$("#startRow").val()+
		          			'&endRow='+
		          			$("#startRow").val()+
		          			"&page="+
		          			$("#pageIndex").val()+
		          			"&pageSize="+
		          			$("#pageSize").val(),
		          	dataType: "text",
		          	data:'',
		   		cache:false,
		   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		          	success: function (data){
		             $("#page_view").html(data);
		          	}
			  	});  
		}
		
		

		
		
		
	</script>
	</head>
	<body>
			<div class="page-header"><h1 style='margin-left:20px'>导入运单错误明细</h1></div>	
			<table id="lg_table">
			  		<tr>
			  			<td  align="left">
							<button class="btn btn-xs btn-success " style= "height: 30px; width: 120px; " onclick="loadingCenterPanel('${root }/control/orderPlatform/expressinfoMasterInputlistController/uploadresult.do');">
								<i class="icon-hdd"></i>返回
							</button>
			  			</td>
			  		</tr>
				</table>
			<div style="margin-left: 20px;margin-bottom: 20px;">
				<form method="post" action="">
				 </form>
			</div>
		
		<div class= "tree_div"  id="page_view">
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"  id= "sc_content">
			<table id= "table_content" title="运单列表" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  			<thead align="center">
				<tr class='table_head_line'>
					<td class="td_cs" style="width: 100px">序号</td>
						<td class="td_cs" style="width: 100px">订单号</td>
						<td class="td_cs" style="width: 100px">发货人</td>
						<td class="td_cs" style="width: 100px">收货人</td>
						<td class="td_cs" style="width: 100px">错误信息</td> 
				</tr>
			</thead>
			<tbody id='tbody' align="center">
				<c:forEach items="${pageView.records}" var="records"
					varStatus='status'>
						<td class="td_cs" style="width: 100px" title="${status.count }">${status.count }</td>
						<td class="td_cs" style="width: 200px" title="${records.order_id }">
							${records.records.order_id}</td>
							<td class="td_cs" style="width: 200px"
								title="${records.from_contacts }">${records.from_contacts }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_contacts }">${records.to_contacts }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.place_error }">${records.place_error }</td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
		</div>
		
	</body>
</html>
<style>

.divclass{
border:5px solid #E0EEEE
} 

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

.m-input-select {
    display: inline-block;
    position: relative;
    -webkit-user-select: none;
    width: 160px;
    }
</style>
