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
						"${root }/control/FailureReasonController/toFailurePage.do?pageName=table&tableName=failure_reason&startRow="
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
								+ "&reason="
								+ $('#fail_reason').val()
								);

	};
	function tableAction(currentRow, tableFunctionConfig) {
	}

	function refresh() {
		$("#fail_reason").val("");
	}

	function del() {
		if ($("input[name='ckb']:checked").length != 1) {
			alert("请选择一条数据!");
			return;
		}
		if (!confirm("确定删除吗?")) {
			return;
		}
		var priv_ids=$("input[name='ckb']:checked").val();
		$.ajax({
			type : "POST",
			url : root + "/control/FailureReasonController/deleteById.do?",
			dataType : "json",
			data : "id=" + $.trim(priv_ids),
			success : function(data) {
				alert(data.msg);
					tablesearch('', '');
			}
		});
	}

	function add() {

        $('#import_form').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide()
	}
	function saveFailureReason() {
       var reason = $.trim($("#reason").val());
       if(reason==null||reason==""){
           alert("请填写失败原因");
           return;
	   }
	   if(reason.length>255){
           alert("长度过长");
           return;
	   }
        $.ajax({
            type : "POST",
            url : root + "/control/FailureReasonController/addFailReason.do?",
            dataType : "json",
            data : "reason=" + reason,
            success : function(data) {
                alert(data.msg);
                tablesearch('', '');
                $('#import_form').modal('hide');
                $("#reason").val("");
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
											<label class="control-label blue" for="fail_reason">失败原因&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<input id="fail_reason"
												class="form-data search-data form-control" name="fail_reason"
												style="width: 100%" type="text"
												/>
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
						<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('','');">
					<i class="ace-icon fa fa-search grey bigger-120">
						查询
					</i>
				     </button>
						<button
							class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							onclick="add();">
							<i class="ace-icon fa fa-plus-circle blue bigger-120"> 新增 </i>
						</button>
						<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width" onclick="del();">
							<i class="ace-icon fa fa-trash-o red bigger-120">
								删除
							</i>
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
	    <div class="modal-dialog modal-lg" style="width:400px;" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
	                <h4 class="modal-title">新增索赔失败原因</h4>
	            </div>
	            <div class="modal-body container">
								<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="reason">失败原因&nbsp;:</label>
										</div>
										<div>
											<input id="reason"
												class="form-data search-data form-control" name="reason"
												style="width: 100%" type="text"
												/>
										</div>
									</div>
	            </div>
	            <div class="modal-footer" style="text-align: center">
	                <button id='upload' type="button" class="btn btn-primary" onclick="saveFailureReason()" >
	                    <i class="icon-save" aria-hidden="true"></i>保存
	                </button>
	                <button type="button" class="btn btn-default" onclick="$('#import_form').modal('hide');" >
	                    <i class="icon-undo" aria-hidden="true"></i>关闭
	                </button>
	            </div>
	        </div>
	    </div>
	</div>
	
	
	
</body>
</html>
