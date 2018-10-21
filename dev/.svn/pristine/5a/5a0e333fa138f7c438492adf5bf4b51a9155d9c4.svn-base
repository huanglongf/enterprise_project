<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" type="text/css" media="all"
	href="<%=basePath%>css/table.css" />
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
<script type="text/javascript">
	function pageJump() {
		tablesearch('', '');
	}

	function tablesearch(column, sort) {
		$("#sort_column").val(column);
		$("#sort").val(sort);
		$(".search-table")
				.load(
						"${root }/control/WhsTempDataController/toWarehouseSearch.do?pageName=table&tableName=tls_whs_temp_data&startRow="
								+ $("#startRow").val()
								+ "&endRow="
								+ $("#startRow").val()
								+ "&page="
								+ $("#pageIndex").val()
								+ "&pageSize="
								+ $("#pageSize").val()
								+ "&sortColumn="
								+ column
								+ "&sort="
								+ sort
								+ "&cartonNo="
								+ $('#cartonNo').val()
								+ "&location="
								+ encodeURI($('#location').val())
								+ "&sku="
								+ encodeURI($('#sku').val())
								+ "&status="
								+ encodeURI($('#status').val())
								+ "&batchId="
								+ encodeURI($('#batchId').val())
								+ "&createTimeStart="
								+ encodeURI($('#createTimeStart').val())
								+ "&createTimeEnd="
								+ encodeURI($('#createTimeEnd').val())
								+ "&createByName=" + $('#createByName').val());

	};
	function tableAction(currentRow, tableFunctionConfig) {
	}

	function refresh() {
		$("#createByName").val("");
		$("#createTimeEnd").val("");
		$("#createTimeStart").val("");
		$("#batchId").val("");
		$("#status").val("");
		$("#sku").val("");
		$("#location").val("");
		$("#cartonNo").val("");
		/* 	initializeSelect('create_by');*/
	}

	function del() {
		if ($("input[name='ckb']:checked").length != 1) {
			alert("请选择一条数据!");
			return;
		}
		if (!confirm("确定删除吗?")) {
			return;
		}
		var priv_ids=$("input[name='ckb']:checked").val();;
		$.ajax({
			type : "POST",
			url : root + "/control/WhsTempDataController/deleteByBatchId.do?",
			dataType : "json",
			data : "batchId=" + $.trim(priv_ids),
			success : function(data) {
				alert(data.msg);
					tablesearch('', '');
			}
		});
	}
	
	function exportTemplat() {
		window.location.href="${root}/control/WhsTempDataController/downloadTemplete.do"
	}
	
	
	
	
	function exportWhsTempData(){
		
		
		window.location.href="${root}/control/WhsTempDataController/exportWhsTempData.do?pageName=table&tableName=tls_whs_temp_data&startRow="
			+ $("#startRow").val()
			+ "&endRow="
			+ $("#startRow").val()
			+ "&sortColumn="
			+ $("#sort_column").val()
			+ "&sort="
			+ $("#sort").val()
			+ "&cartonNo="
			+ $('#cartonNo').val()
			+ "&location="
			+ encodeURI($('#location').val())
			+ "&sku="
			+ encodeURI($('#sku').val())
			+ "&status="
			+ encodeURI($('#status').val())
			+ "&batchId="
			+ encodeURI($('#batchId').val())
			+ "&createTimeStart="
			+ encodeURI($('#createTimeStart').val())
			+ "&createTimeEnd="
			+ encodeURI($('#createTimeEnd').val())
			+ "&createByName=" + $('#createByName').val();
	}
	
	$(function () {
	    $("#upload").click(function () {
	        ajaxFileUpload();
	    });
	    
    	$('input[id=file1]').change(function() { 
	        $('#photoCover').val($(this).val());
        });
	    
	    $("#deleteBatch").click(function () {
	    	deleteBatch();
	    });
	    
	})
	function ajaxFileUpload() {
	   loadingStyle();
		$.ajaxFileUpload({
			url: '${root}/control/WhsTempDataController/importWhsTempData.do', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
			fileElementId: 'file1', //文件上传域的ID
			dataType: 'json', //返回值类型 一般设置为json
			/* data:{'group':$('#gId').val()}, */
				//服务器成功响应处理函数
				success: function (data, status){
				if(data.code=='200'){
					alert('操作成功');
					cancelLoadingStyle();
					tablesearch('','');
					$("#import_form").modal('hide');
				}else{
						alert(data.msg);
						cancelLoadingStyle();
						if(data.code=='502'){
							window.location.href='${root}/control/WhsTempDataController/exportErrorExcel.do?path='+data.path;
						}
					}
				},
			error: function (data, status, e){
					//服务器响应失败处理函数
					alert('异常');
					cancelLoadingStyle();
					 $("#import_form").modal('hide');
	           	}
			})
	        return false;
		}
	
	
	function deleteBatch() {
			var batchCode = $.trim($("#batchCode").val());
			if(batchCode==''){
				alert("请填写批次号！");
				return;
			}
			loadingStyle();
			$.ajax({
				type : "POST",
				url : root + "/control/WhsTempDataController/deleteByBatchId.do?",
				dataType : "json",
				data : "batchId=" + batchCode,
				success : function(data) {
					alert(data.msg);
					cancelLoadingStyle();
					tablesearch('', '');
					$("#delete_form").modal('hide');
				}
			});
			
		   
		}
	
	function delButton() {
		
		$("#batchCode").val("");
		
		$('#delete_form').modal({backdrop: 'static', keyboard: false});
		$('.modal-backdrop').hide();
	}
	
	
</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-box">
						<div class="widget-header widget-header-small">
							<h5 class="widget-title lighter">查询栏</h5>
							<a class="pointer" title="初始化" onclick="refresh();"> <i
								class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<form id="wtd_form" name="wtd_form"
									class="container search_form">
									<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="cartonNo">箱号&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<input id="cartonNo"
												class="form-data search-data form-control" name="cartonNo"
												style="width: 100%" type="text"
												/>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="location">库位&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<input id="location"
												class="form-data search-data form-control" name="location"
												style="width: 100%" type="text"
												 />
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="sku">SKU&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<input id="sku" class="form-data search-data form-control"
												name="sku" style="width: 100%" type="text"
												 />
										</div>
									</div>
									<div class="senior-search">
										<div class="row form-group">
											<div class="col-md-1 no-padding text-center search-title">
												<label class="control-label blue" for="status">状态&nbsp;:</label>
											</div>
											<div class="col-md-3 no-padding">
												<input id="status"
													class="form-data search-data form-control" name="status"
													style="width: 100%" type="text"
													 />
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="control-label blue" for="batchId">导入批次号&nbsp;:</label>
											</div>
											<div class="col-md-3 no-padding">
												<input id="batchId"
													class="form-data search-data form-control" name="batchId"
													style="width: 100%" type="text"
													/>
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="createTimeStart">导入时间起&nbsp;:</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-xs-12 input-group no-padding">
													<input type="text" id="createTimeStart"
														name="createTimeStart" style="width: 100%; height: 34px;"
														class="Wdate"
														onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
														/>
													<span class="input-group-addon"> <i
														class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
										</div>
										<div class="row form-group">
											<div class="col-md-1 no-padding text-center search-title">
												<label class="blue" for="createTimeEnd">导入时间止&nbsp;:</label>
											</div>
											<div class="col-md-3 no-padding">
												<div class="col-xs-12 input-group no-padding">
													<input type="text" id="createTimeEnd" name="createTimeEnd"
														style="width: 100%; height: 34px;" class="Wdate"
														onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
														/>
													<span class="input-group-addon"> <i
														class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
											<div class="col-md-1 no-padding text-center search-title">
												<label class="control-label blue" for="createByName">创建人&nbsp;:</label>
											</div>
											<div class="col-md-3 no-padding">
												<input id="createByName"
													class="form-data search-data form-control"
													name="createByName" style="width: 100%" type="text"
													 />
											</div>
										</div>
									</div>
									<div class="row text-center">
										<a class="senior-search-shift pointer" title="高级查询"><i
											class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div
						style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							onclick="tablesearch('', '');">
							<i class="ace-icon fa fa-search grey bigger-120"> 查询 </i>
						</button>
						&nbsp;
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							onclick="$('#import_form').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide();">
							<i class="ace-icon fa fa-download grey"> 导入 </i>
						</button>
						&nbsp;
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							onclick="exportWhsTempData();">
							<i class="ace-icon fa fa-download grey"> 导出 </i>
						</button>
						&nbsp;
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style="width: 140px;"
							onclick="delButton();">
							<i class="ace-icon fa fa-trash-o red bigger-120"> 删除导入批次数据 </i>
						</button>
					</div>
					<div class="search-table">
						<jsp:include page="/templet/table.jsp" flush="true" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 列表配置弹窗 -->
	<div id="import_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
	    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
	                <h4 class="modal-title">仓库暂存数据导入</h4>
	            </div>
	            <div class="modal-body container">
					<table>
						<tr align="center">
							<input id="file1" type="file" name='file' style='display:none' />  
					    	<div style='text-align: center;'>  
						    	<input id="photoCover" class="input-large" type="text" style="height:30px;display: none;" >  
						   		<a class="btn" onclick="$('input[id=file1]').click();">选择文件</a> 
						   		<a class="btn" onclick="exportTemplat();">下载模板</a> 
							</div>  		
						</tr>
					</table>
	            </div>
	            <div class="modal-footer">
	                <button id='upload' type="button" class="btn btn-primary" onclick=";" >
	                    <i class="icon-save" aria-hidden="true"></i>确认
	                </button>
	                <button type="button" class="btn btn-default" onclick="$('#import_form').modal('hide');" >
	                    <i class="icon-undo" aria-hidden="true"></i>关闭
	                </button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- 删除批次弹窗 -->
	<div id="delete_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" style="width:800px;" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
	                <h4 class="modal-title">删除导入批次数据</h4>
	            </div>
	            <div class="modal-body container" align="center">
					<table>
						<tr align="center">
							<td align="center">
								<label class="blue" >
									<span class="red">*</span>
									导入批次号:
								</label>
							</td>
							<td align="center">
								<input class="form-control" type="text" id="batchCode" name="batchCode" placeholder="请输入(必填)" >
							</td>
						</tr>
						<tr align="center">
							<td align="center">
							</td>
							<td align="center">
								<span class="red">说明：此功能为全批次数据删除，请慎重</span>
							</td>
						</tr>
					</table>
	            </div>
	            <div class="modal-footer" >
	                <button id='deleteBatch' type="button" class="btn btn-primary" onclick=";" >
	                    <i class="icon-save" aria-hidden="true"></i>确认
	                </button>
	                <button type="button" class="btn btn-default" onclick="$('#delete_form').modal('hide');" >
	                    <i class="icon-undo" aria-hidden="true"></i>关闭
	                </button>
	            </div>
	        </div>
	    </div>
	</div>
	
</body>
</html>
