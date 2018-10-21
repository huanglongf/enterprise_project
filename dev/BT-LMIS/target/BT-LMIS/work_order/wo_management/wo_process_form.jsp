<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel= "stylesheet" />
		<link type="text/css" href="<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>work_order/wo_management/css/wo_management.css" rel= "stylesheet" />
		
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/clipboard/clipboard.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/wo_management_platform.js"></script>
		<style>
			.r{position:fixed; bottom:0;z-index:99;}
		</style>
		
	</head>
	<body>
		<div class= "page-header no-margin-bottom r" align= "left" >
	    	<button id="btn_undo" class="btn btn-white" onclick="back('${type }', '${uuid }');"><i class="icon-undo"></i>返回</button>
		    <button ${type == "process" && wo_display.wo_process_status_display == "处理中"?"":"style='display:none;'" } class="btn btn-white" onclick="$('#woPause_form').modal();"><i class="icon-undo"></i>挂起</button>
		    <button ${type == "process" && wo_display.wo_process_status_display == "挂起待确认"? "":"style='display:none;'" } class="btn btn-white" onclick="operateStatus('${role }', '', 'RECOVER');"><i class="icon-undo"></i>取消挂起</button>
		    <!-- <button ${type == "process" && wo_display.woProcessStatus != "已取消" && wo_display.woProcessStatus != "已处理"? "": "style= 'display: none;'" } class= "btn btn-white" onclick= "operateStatus('${role }', '', 'CANCEL_ALLOC');" ><i class= "icon-arrow-left" ></i>打回</button> -->	
		    <button ${type == "process" && wo_display.wo_process_status_display != "已取消" && wo_display.wo_process_status_display != "已处理"?"":"style='display:none;'" } class="btn btn-sm btn-danger" onclick="operateStatus('${role }', '', 'CANCEL');"><i class="icon-circle"></i>取消</button>
		</div>
		<jsp:include page="/work_order/wo_management/wo_process_info.jsp" flush="true" />
		<jsp:include page="/work_order/wo_management/wo_management_pause.jsp" flush="true" />
		<jsp:include page="/work_order/wo_management/wo_management_alter.jsp" flush="true" />
		<jsp:include page="/work_order/wo_management/wo_claim_detail_form_add.jsp" flush="true" />
		<jsp:include page="/work_order/wo_management/wo_waybillGenerated.jsp" flush="true" />
	</body>
</html>