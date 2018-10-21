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
<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script>
<script type="text/javascript">
	


	//下载装箱模板
	function downloadPackTemplete() {
		window.location.href="${root}/control/CompareDataController/downloadPackTemplete.do"
	}
	//下载采集模板
	function downloadCollectTemplete() {
		window.location.href="${root}/control/CompareDataController/downloadCollectTemplete.do"
	}
	
	//校验装箱数据
	function ajaxPackFileUpload() {
		   loadingStyle();
			$.ajaxFileUpload({
				url: '${root}/control/CompareDataController/importPackData.do', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				data : {taskCode:$("#taskCode").val()},
				dataType: 'json', //返回值类型 一般设置为json
				/* data:{'group':$('#gId').val()}, */
					//服务器成功响应处理函数
					success: function (data, status){
					if(data.code=='200'){
						$("#file1Path").val(data.path);
						alert('操作成功');
						cancelLoadingStyle();
					}else{
							alert(data.msg);
							cancelLoadingStyle();
							if(data.code=='502'){
								window.location.href='${root}/control/CompareDataController/exportErrorPack.do?path='+data.path;
							}
						}
					},
				error: function (data, status, e){
						//服务器响应失败处理函数
						alert('异常');
						cancelLoadingStyle();
		           	}
				})
		        return false;
		}
	//校验采集数据
	function ajaxCollectFileUpload() {
		   loadingStyle();
			$.ajaxFileUpload({
				url: '${root}/control/CompareDataController/importCollectData.do', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file2', //文件上传域的ID
				data : {taskCode:$("#taskCode").val()},
				dataType: 'json', //返回值类型 一般设置为json
				/* data:{'group':$('#gId').val()}, */
					//服务器成功响应处理函数
					success: function (data, status){
					if(data.code=='200'){
						$("#file2Path").val(data.path);
						alert('操作成功');
						cancelLoadingStyle();
					}else{
							alert(data.msg);
							cancelLoadingStyle();
							if(data.code=='502'){
								window.location.href='${root}/control/CompareDataController/exportErrorCollect.do?path='+data.path;
							}
						}
					},
				error: function (data, status, e){
						//服务器响应失败处理函数
						alert('异常');
						cancelLoadingStyle();
		           	}
				})
		        return false;
		}
	
	function createTaskCode() {
		loadingStyle();
		$("#file1Path").val("");
		$("#file2Path").val("");
		$.ajax({
			type : "POST",
			url : root + "/control/CompareDataController/createTaskCode.do?",
			dataType : "json",
			success : function(data) {
				$("#taskCode").val(data.taskCode);
				$("#taskDate").val(data.date);
				cancelLoadingStyle();
			},
			error: function (data, status, e){
				//服务器响应失败处理函数
				alert('异常');
				cancelLoadingStyle();
	       	}
		});
	}
	
	function reImport(taskCode,taskTime) {
		
		$('#import_form').modal({backdrop: 'static', keyboard: false});
		$('.modal-backdrop').hide();
		
		$("#file1Path").val("");
		$("#file2Path").val("");
		$("#taskCode").val(taskCode);
		$("#taskDate").val(taskTime);
	}


	function compared(taskCode) {
		loadingStyle();
		$.ajax({
			type : "POST",
			url : root + "/control/CompareDataController/compared.do?",
			dataType : "json",
			data : {taskCode:taskCode},
			success : function(data) {
				cancelLoadingStyle();
				alert(data.msg);
                tablesearch('','');
			},
			error: function (data, status, e){
				//服务器响应失败处理函数
				alert('异常');
				cancelLoadingStyle();
           	}
		});

	}
    function  deleteTask(id,taskCode){
        $.ajax({
            type : "POST",
            url : '${root}/control/CompareDataController/deleteCompareTaskById.do?',
            dataType : "json",
			data:{'id':id,'taskCode':taskCode},
            success : function(data) {
               alert(data.msg);
                tablesearch('','');
            }
        });
	}
	function  refreshPage() {
        $(".search-table")
            .load("${root }/control/CompareDataController/toCompareDataPage.do?tableName=tls_whs_task&pageName=deletePage&startRow="
				+ $("#startRow").val()
        + "&endRow="
        + $("#startRow").val()
        + "&page="
        + $("#pageIndex").val()
        + "&pageSize="
        + $("#pageSize").val())
    }
    function tablesearch(column, sort) {
        $("#sort_column").val(column);
        $("#sort").val(sort);
        $(".search-table")
            .load("${root }/control/CompareDataController/toCompareDataPage.do?tableName=tls_whs_task&pageName=deletePage&startRow="
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
                + sort)


    };
	//下载对比数据
	function exportCollectPackData(data) {
		loadingStyle();
		window.location.href="${root}/control/CompareDataController/exportCollectPackData.do?taskCode="+data;
		cancelLoadingStyle();
	}
	//点击确认创建任务数据
	function creatCompareTask(){
        loadingStyle();
		var path1 = $("#file1Path").val();
		var path2 = $("#file2Path").val();
		var taskCode = $("#taskCode").val();
		var taskDate = $("#taskDate").val();

		if(path1==null||path1==""){
			alert("装箱数据未上传或未校验");
            cancelLoadingStyle();
			return;
		}
		if(path2==null||path2==""){
			alert("采集数据未上传或未校验");
            cancelLoadingStyle();
			return;
		} 
		 $.ajax({
	            type : "POST",
	            url : '${root}/control/CompareDataController/creatCompareTask.do',
	            dataType : "json",
				data:{'file1Path':path1,
					  'file2Path':path2,
					  'taskCode':taskCode,
					  'taskDate':taskDate
					},
	            success : function(data) {
                    cancelLoadingStyle();
	               alert(data.msg);
	           	 $("#file1Path").val('');
	    		 $("#file2Path").val('');
	               $("#import_form").modal('hide'); //隐藏弹窗
                    tablesearch('','');//刷新界面
	            }
	        });
	}

</script>
</head>
<body>
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
						<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style="width: 140px;"
						onclick="$('#import_form').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide();createTaskCode();">
							<i class="icon-plus grey">新建对比任务 </i>
						</button>
					</div>
					<div class="search-table">
						<jsp:include page="/lmis/tools/table.jsp" flush="true" />
					</div>
				</div>
			</div>
		</div>
		
		<!-- 导入弹窗 -->
	<div id="import_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
	
		<input id="file1Path" type="hidden" name='file1Path' />
		<input id="file2Path" type="hidden" name='file2Path' />
	
	    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
	                <h4 class="modal-title">新建对比任务</h4>
	            </div>
	            <div class="modal-body container">
					<table align="center">
						<tr align="center">
							<td align="center">
								<label class="blue" >
									任务序列号:
								</label>
							</td>
							<td align="center">
								<input class="form-control" type="text"  id="taskCode" name="taskCode" disabled="disabled" >
							</td>
							
							<td align="center">
								<label class="blue">
									任务时间:
								</label>
							</td>
							<td align="center">
								<input class="form-control" type="text" id="taskDate" name="taskDate" disabled="disabled" >
							</td>
							
						</tr>
						<tr align="center">
							<td align="center">
								<label class="blue" >
									装箱数据:
								</label>
							</td>
							<td align="center">
									<input id="file1" type="file" name='file' style='display:none' />  
							    	<input id="photoCover" class="input-large" type="text" style="height:30px;display: none;" >  
							   		<a class="btn" onclick="$('input[id=file1]').click();">选择文件</a> 
							</td>
							<td align="center">
								<a class="btn" onclick="downloadPackTemplete();">下载模板</a> 
							</td>
							<td align="center">
								<a class="btn" onclick="ajaxPackFileUpload();">校验</a> 
							</td>
						</tr>
						
						<tr align="center">
							<td align="center">
								<label class="blue" >
									采集数据:
								</label>
							</td>
							<td align="center">
									<input id="file2" type="file" name='file' style='display:none' />  
							    	<input id="photoCover2" class="input-large" type="text" style="height:30px;display: none;" >  
							   		<a class="btn" onclick="$('input[id=file2]').click();">选择文件</a> 
							</td>
							<td align="center">
								<a class="btn" onclick="downloadCollectTemplete();">下载模板</a> 
							</td>
							<td align="center">
								<a class="btn" onclick="ajaxCollectFileUpload();">校验</a> 
							</td>
						</tr>
						
					</table>
	            </div>
	            <div class="modal-footer">
	                <button id='upload' type="button" class="btn btn-primary" onclick="creatCompareTask()" >
	                    <i class="icon-save" aria-hidden="true"></i>确认
	                </button>
	                <button type="button" class="btn btn-default" onclick="$('#import_form').modal('hide');" >
	                    <i class="icon-undo" aria-hidden="true"></i>关闭
	                </button>
	            </div>
	        </div>
	    </div>
	</div>
		
	</div>
</body>
</html>
