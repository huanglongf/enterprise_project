<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ include file="/templet/common.jsp" %>
<script>
	function pageJump() {
		tablesearch('', '');
	}

	function tablesearch(column, sort) {
		$("#sort_column").val(column);
		$("#sort").val(sort);
		var tableName = $("#tableName").val();
		var group_name = "";
		var emp_name = "";
		var createTime = $("#create_time").val();
		if(createTime==''){
			alert("时间不能为空！");
			return;
		}
		
		if(tableName=="wo_group_report"){
			group_name = $("#group_name").val();
		}else{
			emp_name = $("#group_name").val();
		}
		$(".search-table").load("/BT-LMIS/control/woPersonalReportController/queryWPR.do?pageName=table&tableName="
								+tableName+"&startRow="
								+$("#startRow").val()
								+"&endRow="
								+$("#startRow").val()
								+"&page="
								+$("#pageIndex").val()
								+"&pageSize="
								+$("#pageSize").val()
								+"&sortColumn="
								+column
								+"&sort="+sort
								+ "&create_time="
								+ encodeURI($("#create_time").val())
								+ "&group_name="
								+ group_name
								+ "&emp_name="
								+ emp_name);
	}

	function tableAction(currentRow, tableFunctionConfig) {
	}

	function refresh() {
		$("#create_time").val("");
		$("#group_name").val("");
	}
	
	function exportReport() {
		var tableName = $("#tableName").val();
		var group_name = "";
		var emp_name = "";
		var createTime = $("#create_time").val();
		if(createTime==''){
			alert("时间不能为空！");
			return;
		}
		if(tableName=="wo_group_report"){
			group_name = $("#group_name").val();
		}else{
			emp_name = $("#group_name").val();
		}
		window.location.href="/BT-LMIS/control/woPersonalReportController/exportReport.do?pageName=table&tableName="
			+tableName+ "&sortColumn=" + $("#sort_column").val() + "&sort=" + $("#sort").val()
			+ "&create_time="
			+ encodeURI($("#create_time").val())
			+ "&group_name="
			+ group_name
			+ "&emp_name="
			+ emp_name;
	}
</script>
<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-xs-12">
				<div class="search-toolbar">
					<div class="widget-box">
						<div class="widget-header widget-header-small">
							<h5 class="widget-title lighter">查询栏</h5>
							<a class="pointer" title="初始化" onclick="refresh();"><i
								class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<form class="container search_form" id="form">
									<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue">查询类别：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<select id="tableName" name="tableName" data-edit-select="1">
													<option value="wo_personal_report">个人</option>
													<option value="wo_group_report">组别</option>
					                        	</select>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="create_time">创建时间：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding input-group">
												<input id="create_time" name="create_time" class="form-control data-range" type="text" value="${paraMap.create_time}" readonly />
												<span class="input-group-addon">
													<i class="icon-calendar bigger-110"></i>
												</span>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-2">个人/组别名称:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<input class="form-data search-data form-control"
													id="group_name" name="group_name" style="width: 100%"
													type="text" />
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="dataTables_wrapper form-inline">
						<div class="row">
							<div class="col-md-12">
								<div class="dataTables_length">
									<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('', '');">
										<i class="ace-icon fa fa-search grey"> 
											查询 
										</i>
									</button>
									<button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="exportReport();">
										<i class="ace-icon fa fa-download green">
											导出
										</i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="search-table">
					<jsp:include page="/templet/table2.jsp" flush="true" />
				</div>
			</div>
		</div>
	</div>
</div>
