<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
	</head>
	<body>
		<div class= "page-header" style= "margin-left: 10px; height: 100px" >
			<button class= "btn btn-xs btn-app btn-warning" onclick= "this.disabled= true; openDiv('${root }control/summaryController/list.do');" >
				<i class= "icon-undo bigger-200" ></i>返回
			</button>
			<button id= "btn_export" class= "btn btn-xs btn-app btn-pink" onclick= "exportSummaryExcel('${ym }');" >
				<i class= "icon-arrow-down bigger-200" ></i>汇总报表
			</button>
			<button id= "btn_error" class= "btn btn-xs btn-app btn-purple" onclick= "exportErrorWaybill('${ym }');" >
				<i class= "icon-arrow-down bigger-200" ></i>异常运单
			</button>
			<script type= "text/javascript" >
				function exportSummaryExcel(balance_month) {
					$("#btn_export").attr("disabled", true);
					$.ajax({
						type: "POST",
						url: root+ "/control/summaryController/export.do",
						data: {
							"year": balance_month.substring(0, 4), 
							"month": balance_month.substring(5)
							
						},
						dataType: "json",
						success: function(result) {
							if(result.result_code != "SUCCESS") {
								alert(result.result_content)
								
							} else {
								window.open(root+ result.result_content);
								
							}
							$("#btn_export").attr("disabled", false);
							
						}
						
					});
					
				}
				function exportErrorWaybill(balance_month) {
					$("#btn_error").attr("disabled", true);
					$.ajax({
						type: "POST",
						url: root+ "/control/expressUsedBalanceController/exportErrorWaybill.do",
						data: {"balance_month": balance_month },
						dataType: "json",
						success: function(result) {
							if(result.result_code != "SUCCESS") {
								alert(result.result_content)
								
							} else {
								window.open(root+ result.result_content);
								
							}
							$("#btn_error").attr("disabled", false);
							
						}
						
					});
					
				}
			</script>
		</div>
		<div class= "div_margin" style= "text-align: center; height: 5%;" >
			<h3 class= "control-label blue" >
				${ym }月结算报表     
			</h3>
		</div>
		<div class= "title_div no-margin-top" id= "sc_title" style= "height: 85px;" >
			<table class= "table table-striped" style= "table-layout: fixed;" >
				<thead>
			  		<tr class="table_head_line">
			  			<td class= "td_cs" >客户</td>
			  			<td class= "td_cs" >日志</td>
			  			<td class= "td_cs" >重新生成</td>
			  		</tr>
			  		<tr class="table_head_line">
			  			<td class= "td_cs" ><input id= "findClient" name= "findCLient" type= "text"  onchange= "checkClientName('${ym }');" ></td>
			  			<td class= "td_cs" ></td>
			  			<td class= "td_cs" ></td>
			  		</tr>
		  		</thead>
			</table>
		</div>
		<div class= "tree_div" style= "height: 85px; margin-top: -85px;" ></div>
		<div id= "sc_content" style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_detail_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center" >
		  			<c:forEach items= "${client }" var= "client" >
				  		<tr>
			  				<td class="td_cs" ><a href= "javascript:void();" onclick= "openClientSummary('${ym }','${client.id }');">${client.client_name }</a></td>
			  				<td class="td_cs" ><a href="javascript:void();" >查看</a></td>
			  				<td class="td_cs" ><a href="javascript:void();" onclick="alert('开发中……');" >重新生成</a></td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	  	function checkClientName(ym){
		    openDiv("${root}control/summaryController/lists.do?clientname="+ $('#findClient').val()+ "&ym="+ ym);
		    
	  	}
	  	function openClientSummary(ym,id){
		    openDiv("${root}control/summaryController/summaryList.do?clientid="+ id+ "&ym="+ ym);
		    
	  	}
	</script>
</html>
