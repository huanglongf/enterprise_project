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
			<button class= "btn btn-xs btn-app btn-primary" onclick= "find();" >
				<i class= "icon-search bigger-230" ></i>查询
			</button>
		</div>
		<div class= "div_margin" style= "margin-top: 22px;" >
			<div class="title_div" id="sc_title" style="height: 85px;">		
				<table class="table table-striped" style="table-layout: fixed;">
			   		<thead>
				  		<tr class="table_head_line">
				  			<td class="td_cs" style="width:20%">结算汇总年月</td>
				  			<td class="td_cs" style="width:40%">结算汇总快递</td>
				  			<td class="td_cs" style="width:20%">结算汇总日期</td>
				  			<td class="td_cs" style="width:20%">结算汇总人</td>
				  		</tr>
				  		<tr>
				  			<td class="td_cs"><input id="cycle" name="cycle" type="text" value="${queryParam.cycle }" onblur="find();" /></td>
				  			<td class="td_cs"><input id="subject" name="subject" type="text" value="${queryParam.subject }" onblur="find();" /></td>
				  			<td class="td_cs"></td>
				  			<td class="td_cs"></td>
				  		</tr>
					</thead>
				</table>
			</div>
			<div class= "tree_div" style= "height: 85px; margin-top: -85px;" ></div>
			<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')">
				<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
			  		<tbody align="center" >
			  			<c:forEach items="${pageView.records}" var="list" >
					  		<tr>
					  			<td class="td_cs" style="width:20%">
					  				<a href= "javascript: void(0);" onclick= "openDiv('${root}/control/expressBalanceController/balanceSummaryList.do?express_code=${list.express }&express_name=${list.transport_name }&balance_month=${list.balance_month }')" >
					  					${list.balance_month}
					  				</a>
					  			</td>
					  			<td class="td_cs" style="width:40%">${list.transport_name}</td>
					  			<td class="td_cs" style="width:20%">${list.create_date}</td>
					  			<td class="td_cs" style="width:20%">${list.create_by}</td>
					  		</tr>
				  		</c:forEach>
			  		</tbody>
				</table>
			</div>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
	</body>
	<script type= "text/javascript" >
	  	/**
	   	* 查询
	   	*/
	  	function find(){
	  		openDiv("${root}control/expressBalanceController/summaryList.do?cycle=" 
					+ $("#cycle").val() 
					+ "&subject=" 
					+ $("#subject").val()
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
	  	function pageJump() {
			find();
			
		};
	</script>
</html>