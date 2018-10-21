<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp"%>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
	</head>
	<body>
		<div class= "page-header" style= "margin-left: 10px; height: 100px" >
			<button class= "btn btn-xs btn-app btn-warning" onclick= "this.disabled= true; openDiv('${root}/control/expressBalanceController/summaryList.do');" >
				<i class= "icon-undo bigger-200" ></i>返回
			</button>
			<button id= "btn_export" class= "btn btn-xs btn-app btn-pink" onclick= "exportReport();" >
				<i class= "icon-arrow-down bigger-200" ></i>报表下载
			</button>
			<button id= "btn_error" class= "btn btn-xs btn-app btn-purple" onclick= "exportErrorWaybill();" >
				<i class= "icon-arrow-down bigger-200" ></i>异常运单
			</button>
			<button class= "btn btn-xs btn-app btn-primary no-radius" onclick= "this.disabled= true;openDiv('${root }/control/expressBalanceController/balanceDetailList.do?con_id=${con_id }&express_code=${express_code }&express_name=${express_name }&balance_month=${balance_month }')" >
				<i class="icon-edit bigger-230" ></i>结算明细
			</button>
			<script type= "text/javascript" >
				function exportErrorWaybill() {
					$("#btn_error").attr("disabled", true);
					$.ajax({
						type: "POST",
						url: root+ "/control/expressBalanceController/exportErrorWaybill.do",  
						data: $("#serchForm").serialize(),
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
				function exportReport() {
					$("#btn_export").attr("disabled", true);
					$.ajax({
						type: "POST",
						url: root+ "/control/expressBalanceController/exportReport.do",  
						data: $("#serchForm").serialize(),
						dataType: "",  
						success: function(jsonStr) {
							window.open(root+ jsonStr);
							$("#btn_export").attr("disabled", false);
							
						}
						
					});
					
				}
			</script>
		</div>
		<form id= "serchForm" >
			<input type= "hidden" value= "${con_id }" name= "con_id" />
			<input type= "hidden" value= "${express_code }" name= "express_code" />
			<input type= "hidden" value= "${express_name }" name= "express_name" />
			<input type= "hidden" value= "${balance_month }" name= "balance_month" />
		</form>
		<div class= "div_margin" style= "text-align: center; height: 5%;" >
			<h3 class= "control-label blue" >
				${express_name }${balance_month }月结算汇总报表
			</h3>
		</div>
		<div class= "title_div" id= "sc_title">		
			<table class= "table table-striped" style= "table-layout: fixed;" >
		   		<thead>
		   			<tr class= "table_head_line" >
		   				<c:forEach items= "${expressSummary.title }" var= "title" >
		   					<td class= "td_cs" style= "width: 200px" title= "${title.value }">${title.value }</td>
		   				</c:forEach>
					</tr> 	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div id= "sc_content" style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_detail_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
				<tbody align= "center" >
					<c:forEach items= "${expressSummary.results }" var= "row" >
						<tr>
							<c:forEach items="${row }" var= "col">
								<td class="td_cs" style="width: 200px" title="${col.value }">${col.value }</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>