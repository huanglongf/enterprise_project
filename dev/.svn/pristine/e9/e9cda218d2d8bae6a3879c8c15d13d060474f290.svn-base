<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="../../lmis/yuriy.jsp"%>
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
<%-- <script type= "text/javascript" src= "<%=basePath %>lmis/rawDataUpload/js/rawDataUpload_form.js"></script> --%>
<script type= "text/javascript" src= "<%=basePath %>js/ajaxfileupload.js"></script>
<script type= "text/javascript" src= "<%=basePath %>assets/js/ace-elements.min.js"></script>
<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
	<style type="text/css">
		.cover-bg{
			position: fixed;
		}
	</style>
<script type='text/javascript'>
$(function(){
	$('#uploadFile').ace_file_input({
		style:'well',
		btn_choose:'*请选择需要上传的原始数据EXCEL*',
		btn_change:null,
		no_icon:'icon-cloud-upload',
		droppable:true,
		thumbnail:'small'//large | fit
		, before_change:function(files, dropped) {
			if(files != null) {
				// 获取文件
				var fileName = files[0].name;
				// 获取文件名后缀
				var suffix = fileName.substr(fileName.lastIndexOf("."));
				// 判断后缀是否合法
				if(suffix != ".xls" && suffix != ".xlsx" && suffix != ".csv") {
					alert("请选择EXCEL文件！");
					return false;
				}
			}
			return true;
		}
		, before_remove:function() {
			if(!confirm("是否移除此文件？")){
				return false;
			}
			return true;
		}
		, preview_error:function(filename, error_code) {
		}
	}).on('change', function(){
	});
	
});
$(function(){
	var ageingId = $('#ageingId').val();
	openIdDiv("rawDataList", "/BT-LMIS//control/radar/ageingDetailUploadController/query.do?ageingId="+ageingId);
});


function upload(){
	if($("#rawDataType").val() == ""){
		alert("请选择上传原始数据类型！");
	} else if($("#uploadFile").val() == ""){
		alert("请选择上传文件！");
	} else {
		var rawDataType = $('#rawDataType').val();
		var ageingId = $('#ageingId').val();
		if(rawDataType == "Order"){
			if(!confirm("是否已上传订单明细数据？")){
				return false;
			}
		}
		loadingStyle();
		$.ajaxFileUpload({
			//用于文件上传的服务器端请求地址
			url: '/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailupload.do?ageingId='+ageingId,
			// 当要提交自定义参数时，这个参数要设置成post
			type: 'post',
			// 自定义参数。这个东西比较有用，当有数据是与上传的图片相关的时候，这个东西就要用到了。
			data: {rawDataType: rawDataType},
			// 是否需要安全协议，一般设置为false
			secureuri: false, 
			//文件上传域的ID
			fileElementId: 'uploadFile',
			//返回值类型 一般设置为json
			dataType: 'json',
			//服务器成功响应处理函数
			success: function (result, status){
				if(result.result_code=="FIE"){
					alert(result.result_content);
					openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailUpload.do?ageingId='+ageingId);
				} else if(result.result_code=="ULF"){
					alert("文件上传失败！原因：" + result.result_content);
					openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailUpload.do?ageingId='+ageingId);
				}else{
					alert("文件上传成功！");
					openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailUpload.do?ageingId='+ageingId);
	            }
				cancelLoadingStyle();
			},
			//服务器响应失败处理函数
			error: function (data, status, e){
				alert("系统错误请联系管理员！" + e);
				openDiv('/BT-LMIS/control/radar/ageingdetailcontroller/ageingDetailUpload.do?ageingId='+ageingId);
				cancelLoadingStyle();
			}
		})
	}
}
function back_ageing_detail(){
	debugger;
	var warehouseCode='${queryParam.warehouseCode}';
    var ageingId='${queryParam.ageingId}';
    var pCode='${queryParam.pCode}';
    var cCode='${queryParam.cCode}';
    var sCode='${queryParam.sCode}';
    var productTypeCode='${queryParam.productTypeCode}';
    var expressCode='${queryParam.expressCode}';
    openDiv('<%=basePath%>control/radar/ageingdetailcontroller/query.do?ageingId='+ageingId+"&warehouseCode="+warehouseCode+"&pCode="+pCode+"&cCode="+cCode+"&sCode="+sCode +
		"&productTypeCode="+productTypeCode+"&expressCode="+expressCode);
}
</script>

</head>
<body>
	<div class="page-header" align="left">
		<h1>原始数据上传</h1>
	</div>
	<div style="height: 250px;">
		<div id="fileDiv" class="widget-box div_margin" style="margin-left: auto; margin-right: auto; width: 30%;">
		<input id= "ageingId" type= "hidden" value= "${queryParam.ageingId }" />
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
		<div>
			<button class="btn" onclick="back_ageing_detail()">
				<i class="icon-undo bigger-110"></i>返回
			</button>
		</div>
	</div>
	<hr>
	<div id="rawDataList" class="form-group" style="width : 100%;">
		<%-- <%@ include file="/radar/ageingmaster/ageing_detail_backups.jsp" %> --%>
	</div>
</body>
</html>
