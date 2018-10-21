<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all"
	href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<script type="text/javascript"
	src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript"
	src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type= "text/javascript" src= "<%=basePath %>assets/js/date-time/bootstrap-datepicker.min.js" ></script>
<script type= "text/javascript" src= "<%=basePath %>assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js" ></script>
<script type= "text/javascript" src= "<%=basePath %>My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript"
	src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="<%=basePath %>error/sosp/js/sosp.js"></script>
<!-- BT-JS工具 -->
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
<!--Validform  -->
<link type="text/css" href="<%=basePath %>validator/css/style.css"
	rel="stylesheet" media="all" />
<link type="text/css" href="<%=basePath %>validator/css/demo.css"
	rel="stylesheet" />
<link type="text/css" href="<%=basePath %>css/table.css"
	rel="stylesheet" />
<link href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet">
<link href="<%=basePath %>/css/table.css" type="text/css"
	rel="stylesheet" />
</head>
<body>
	<div class="page-header">
		<h1>结算明细</h1>
	</div>
	<!-- sheet页面 -->
	<div style="width: 100%;">
		<div>
			<div class="tabbable">
				<ul id="ccf_sheet"
					style="hight: 100;text-align: center">
					<li id="carrierChargeZC" class="active">
						<a data-toggle="tab" href="#tabCarrierChargeZC" 
						 style="
						 width:14%;
						 	font-size:18px;
							float:left;
							text-align:center; 
							color:black;
							display:block; 
							padding:8px 40px; 
							text-decoration:none; 
							border-right:1px solid #000;
							" id="a1" onclick="changeColor(1)">
						支出运费</a></li>
					<li id="carrierChargeSR">
						<a data-toggle="tab" href="#tabCarrierChargeSR" 
						 style="
						 width:14%;
						 	font-size:18px;
							float:left;
							display:block; /* 将链接设为块级元素 */
							padding:8px 40px; /* 设置内边距 */
							text-decoration:none; /* 去掉下划线 */
							border-right:1px solid #000" id="a2" onclick="changeColor(2)"> 
							收入运费</a></li>
					<li id="storage" >
						<a data-toggle="tab" href="#tabCCF"  style="
						width:14%;
						 	font-size:18px;
							float:left;
							display:block; /* 将链接设为块级元素 */
							padding:8px 40px; /* 设置内边距 */
							text-decoration:none; /* 去掉下划线 */
							border-right:1px solid #000" id="a3" onclick="changeColor(3)">
							仓储费</a></li>
					<li id="operation">
						<a data-toggle="tab" href="#tabCZF"  style="
						width:14%;
						 	font-size:18px;
							float:left;
							display:block; /* 将链接设为块级元素 */
							padding:8px 40px; /* 设置内边距 */
							text-decoration:none; /* 去掉下划线 */
							border-right:1px solid #000" id="a4" onclick="changeColor(4)">
							操作费</a></li>
					<li id="consumable">
						<a data-toggle="tab" href="#tabHCF"  style="
						width:14%;
						 	font-size:18px;
							float:left;
							display:block; /* 将链接设为块级元素 */
							padding:8px 40px; /* 设置内边距 */
							text-decoration:none; /* 去掉下划线 */
							border-right:1px solid #000" id="a5" onclick="changeColor(5)">
							耗材费</a></li>
					<li id="packagePrice">
						<a data-toggle="tab" href="#tabDBF"  style="
						width:14%;
						 	font-size:18px;
							float:left;
							display:block; /* 将链接设为块级元素 */
							padding:8px 40px; /* 设置内边距 */
							text-decoration:none; /* 去掉下划线 */
							border-right:1px solid #000" id="a6" onclick="changeColor(6)">
							打包费</a></li>
					<li id="zzfwf">
						<a data-toggle="tab" href="#tabZZFWF"  style="
						 	font-size:18px;
							display:block; /* 将链接设为块级元素 */
							padding:8px 5px; /* 设置内边距 */
							text-decoration:none /* 去掉下划线 */
							" id="a7" onclick="changeColor(7)">
							增值服务费</a></li>
				</ul>
				<div id="ccf_sheet_div" class="tab-content" align="center">
					<div id="tabCarrierChargeZC" align="center"
						class="tab-pane fade in active">
						<!-- 支出运费 -->
						<%@ include file="/error/sosp/carrier_form_zc.jsp"%>
					</div>
					<div id="tabCarrierChargeSR" align="center" class="tab-pane fade">
						<!-- 收入运费 -->
						<%@ include file="/error/sosp/carrier_form_sr.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
