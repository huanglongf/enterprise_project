<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/bootstrap.min.css" />
<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/ace.min.css" />
<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" />
<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>css/table.css" />
<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>css/loadingStyle.css" />

<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js"></script>
<script type= "text/javascript" src= "<%=basePath %>js/common.js"></script>
<script type= "text/javascript" src= "<%=basePath %>js/common_view.js"></script>
<script type= "text/javascript" src= "<%=basePath %>lmis/rawDataUpload/js/rawDataUpload_form.js"></script>
<script type= "text/javascript" src= "<%=basePath %>js/ajaxfileupload.js"></script>
<script type= "text/javascript" src= "<%=basePath %>assets/js/ace-elements.min.js"></script>
<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
</head>
<body>
	<div class="page-header" align="left">
		<h1>原始数据上载</h1>
	</div>
	<div style="height: 250px;">
		<div class="div_margin" style="margin-left:auto; margin-right:auto; width:30%; text-align:center;">
			<label class="no-padding-right blue" for="rawDataType">
		 		---上传原始数据类型---
		 	</label>
		</div>
		<div class="div_margin" style="margin-left:auto; margin-right:auto; width:30%;">
		 	<select id="rawDataType" name="rawDataType" style="width: 100%;" data-edit-select="1">
				<option value="">---请选择---</option>
				<!-- <option value="opration">操作费原始数据</option>
				<option value="valueAddedService">增值服务费原始数据</option>
				<option value="detailPurchase">明细采购原始数据</option>
				<option value="supplies">耗材实际使用量</option>
				<option value="storage">仓储费原始数据</option>
				<option value="express">快递运单原始数据</option>
				<option value="logistics">物流运单原始数据</option> -->
				<option value="Order">运单</option>
				<option value="OrderDetail">运单明细</option>
				<option value="Storage">仓储费</option>
				<option value="Operator">操作费</option>
				<option value="Use">耗材实际使用量</option>
				<option value="Invitation">耗材采购量</option>
			</select>
		</div>
		<div id="fileDiv" class="widget-box div_margin" style="margin-left: auto; margin-right: auto; width: 30%;">
			<div class="widget-body">
				<div class="widget-main">
					<input multiple="" type="file" id="uploadFile" name="uploadFile" />
					<button class="btn btn-xs btn-primary" onclick="upload();">
						<i class="icon-upload"></i>上传
					</button>
					<label style="float:right">
						<input type="checkbox" checked="checked" disabled="disabled" class="ace" />
						<span class="lbl"> Allow only .xls/.xlsx/.csv</span>
					</label>
				</div>
			</div>
		</div>
	</div>
	<hr>
	<div id="rawDataList" class="form-group" style="width : 100%;">
		<%@ include file="/lmis/rawDataUpload/rawData_list.jsp" %>
	</div>
</body>
</html>
