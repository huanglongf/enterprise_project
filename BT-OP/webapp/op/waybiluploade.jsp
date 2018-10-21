<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		
		<%-- <link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/bootstrap.min.css" /> --%>
		<%-- <link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/ace.min.css" />  --%>
		<%-- <link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/jquery.shCircleLoader.css" /> --%>
		<%-- <link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/table.css" /> --%>
		<link rel= "stylesheet" type= "text/css" media= "all" href= "<%=basePath %>assets/css/loadingStyle.css" /> 
		
		<%-- <script type="text/javascript" src="<%=basePath %>/assets/js/jquery-2.0.3.min.js"></script> --%>
		<%-- <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/moment.js"></script> --%>
		<%-- <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script> --%>
		<%-- <script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script> --%>
		<script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script>
		<script type="text/javascript" src="<%=basePath %>/js/jquery.shCircleLoader.js"></script>  
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<%--  <script type="text/javascript" src="<%=basePath %>js/common_view.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/ace-elements.min.js"></script> 
		
		<script type= "text/javascript">

		
		$(function(){
			$('#uploadFile').ace_file_input({
				style:'well',
				btn_choose:'*请选择需要上传的EXCEL文件*',
				btn_change:null,
				no_icon:'icon-cloud-upload',
				droppable:true,
				thumbnail:'small'//large | fit
				//,icon_remove:null//set null, to hide remove/reset button
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
		
	
			function upload(){
			if($("#uploadFile").val() == ""){
				alert("请选择上传文件！");
			} else {
				var rawDataType = null;
				loadingStyle();
				$.ajaxFileUpload({
					//用于文件上传的服务器端请求地址
					url: '${root}control/orderPlatform/waybillMasterController/baozunupload.do',
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
							alert("未检测到文件内容！");
						} else if(result.result_code=="ULF"){
							alert("文件上传失败！原因：" + result.result_content);
						}else{
							alert("文件上传成功！成功导入" + result.success_records + "条原始数据！");
			            }
						cancelLoadingStyle();
						openDiv("${root}/control/orderPlatform/expressinfoMasterInputlistController/uploadresult.do");
					},
					//服务器响应失败处理函数
					error: function (data, status, e){
						alert("系统错误请联系管理员！" + e);
						cancelLoadingStyle();
						openDiv("${root}control/orderPlatform/waybillMasterController/initail.do");
					}
				})
			}
			}
		
		</script>
</head>
<body>

		<div style="height: 250px;">
		<div class="page-header"><h1 style='margin-left:20px'>批量导入</h1></div>	
		<div id="fileDiv" class="widget-box div_margin" style="margin-left: auto; margin-right: auto; width: 30%;">
			<div class="widget-body">
				<div class="widget-main">
					<input multiple="" type="file" id="uploadFile" name="uploadFile" />
					<button class="btn btn-xs btn-primary" onclick="upload();">
						<i class="icon-upload"></i>上传
					</button>
					<label style="float:right">
							<button class="btn btn-xs" onclick="loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/initail.do');">
								<i class="icon-upload"></i>返回
							</button>
					</label>
				</div>
			</div>
		</div>
	</div>
</body>
</html>