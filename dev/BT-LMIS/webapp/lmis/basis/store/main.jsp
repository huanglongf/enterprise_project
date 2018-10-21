<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>LMIS</title>
		<%@ include file="/lmis/yuriy.jsp" %>
		
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>font-awesome-4.7.0/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css"  />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/loadingStyle.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/common.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
		
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js" ></script>
		<script type="text/javascript" src="<%=basePath %>lmis/basis/store/js/main.js" ></script>
		
	</head>
	<body>
		<div class="page-header no-margin-bottom" align="left">
			<h1>店铺管理</h1>
		</div>
		<div class="lmis-panel">
			<jsp:include page="/lmis/basis/store/condition.jsp" flush="true" />
			<p class="alert alert-info no-margin-bottom">
				<button class="btn btn-white" onclick="dataView();"><i class="icon-refresh"></i>刷新</button>
				<button class="btn btn-white" onclick="toForm(null);"><i class="icon-plus"></i>新增</button>
				<button class="btn btn-white" onclick="del();"><i class="fa fa-times"></i>删除</button>
				<button class="btn btn-white" onclick="download();"><i class="fa fa-times"></i>导出</button>
			</p>
			<jsp:include page="/lmis/basis/store/data.jsp" flush="true" />
		</div>
		<jsp:include page="/lmis/basis/store/form.jsp" flush="true" />
	</body>
</html>