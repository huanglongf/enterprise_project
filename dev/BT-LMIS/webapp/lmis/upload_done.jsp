<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" href="<%=basePath %>validator/css/style.css"
	type="text/css" media="all" />
<link href="<%=basePath %>validator/css/demo.css" type="text/css"
	rel="stylesheet" />
<link href="<%=basePath %>/css/table.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>	
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>	
<script type="text/javascript" src="<%=basePath %>lmis/address_param/address_param.js"></script>	
<script type="text/javascript"
	src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">

</script>
<style type="text/css">
 .text-style{
 font-size: 17px;
 color: blue;
 }
 .iput_style{
 width: 25%;
 height: 28px;
 }
</style>		
	</head>
	<body>
	    <c:if test="${empty msg}">
		  			<a href='<%=basePath %>/upload/log.xls' target="_Blank">数据格式有误 ！请下载日志文件修改完成后再上传。</a>
		  		</c:if>
	 </body>
</html>

<script type="text/javascript">
</script>
