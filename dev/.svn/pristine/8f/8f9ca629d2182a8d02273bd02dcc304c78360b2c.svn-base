<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%-- <link href="<%=basePath %>plugin/daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>plugin/daterangepicker/daterangepicker-bs3.css" /> --%>
		<%-- <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script> --%>
		<%-- <script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script> --%>
		<%-- <script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script>
		<script type="text/javascript" src="<%=basePath %>/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script> --%>
		<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>/js/selectFilter.js"></script>
		<%-- <script type= "text/javascript" src= "<%=basePath %>/plugin/My97DatePicker/WdatePicker.js" ></script> --%>
		<script type='text/javascript'>
		
		
		</script>
</head>
<body>
		<div class="page-header"><h1 style='margin-left:20px'>运单路由信息</h1></div>	
		
		<div class= "tree_div"  id="page_view">
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"  id= "sc_content">
			<table id= "table_content" title="运单列表" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  			<thead  align="center">
			  		<tr class='table_head_line'>
						<td class="td_cs" style="width: 100px">运单状态</td>
						<td class="td_cs" style="width: 200px">下单日期</td>
			  		</tr> 
			  		<tr>
				  		<td class="td_cs" style="width: 100px" title="${records.status }">
							<c:if test="${records.status=='1'}">已录单</c:if>
   							<c:if test="${records.status=='0'}">已作废</c:if>
   							<c:if test="${records.status=='2'}">已下单</c:if>
						</td>
						<td class="td_cs" style="width: 200px"
							title="${records.order_time }">${records.order_time }</td>
			  		</tr> 	
				</thead>
				<tbody  align="center">
			  		<c:forEach items= "${logistics.records }" var= "records" >
							
							<td class="td_cs" style="width: 200px"
								title="${records.route_remark }">${records.route_remark }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.route_time }">${records.route_time }</td>
		  			</c:forEach>
		  		</tbody>
			</table>
		</div>
</body>
</html>