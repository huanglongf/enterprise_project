<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=basePath %>/css/table.css"  />
		<link type="text/css" rel="stylesheet"  href="<%=basePath %>validator/css/demo.css" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" href="<%=basePath %>validator/css/style.css" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
	</head>

	<body>
		<div class="page-header" align="left">
			<h1>差异明细</h1>
		</div>
		
		<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
			<form id= "main_search" >
				<table class="table table-striped">
					<thead align="center">
						<tr>
						</tr>
					</thead>
					
					<tbody align="center">
				  		<c:forEach items="${pageView.`}" var="pageView">
							<tr>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>
	</body>
</html>
