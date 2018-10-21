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
		$(".search-table").load("/BT-LMIS/control/woWaybillMasterController/queryWoWaybillMaster.do?pageName=table&tableName=wo_waybill_master"
								+"&startRow="
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
								+"&express_number="+$("#express_number").val()
								+"&itemtype_name="+$("#itemtype_name").val()
								+"&store_name="+$("#store_name").val()
								+"&warehouse="+$("#warehouse").val()
								+"&transport_name="+$("#transport_name").val()
								+"&epistatic_order="+$("#epistatic_order").val()
								+"&delivery_order="+$("#delivery_order").val()
								+"&cod_flag="+$("#cod_flag").val()
								);
	}

	function tableAction(currentRow, tableFunctionConfig) {
	}

	function refresh() {
		$("#create_time").val("");
		$("#express_number").val("");
		initializeSelect('store_name');
		initializeSelect('warehouse');
		initializeSelect('transport_name');
		initializeSelect('cod_flag');
		$("#itemtype_name").val("");
		$("#epistatic_order").val("");
		$("#delivery_order").val("");
	}
	
	/* function exportReport() {
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
	} */
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
											<label class="blue">运单号：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<input class="form-control" id="express_number" name="express_number" type="text">
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="store_name">店铺：</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<select id="store_name" name="store_name" data-edit-select="1">
													<option value="">---请选择---</option>
													<c:forEach var="store" items="${stores }">
														<option value="${store}">${store }</option>
													</c:forEach>
					                        	</select>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-2">仓库:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
												<select id="warehouse" name="warehouse" data-edit-select="1">
													<option value="">---请选择---</option>
													<c:forEach var="warehouse" items="${warehouses }">
														<option value="${warehouse}">${warehouse }</option>
													</c:forEach>
					                        	</select>
											</div>
										</div>
									</div>
									
									<div class="senior-search"> 
										<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="transport_name">快递公司：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="transport_name" name="transport_name" data-edit-select="1">
															<option value="">---请选择---</option>
															<c:forEach var="transport" items="${transports }">
																<option value="${transport}">${transport }</option>
															</c:forEach>
												 		</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="itemtype_name">产品类型：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="itemtype_name" name="itemtype_name" type="text">
													</div>
													<!-- <div class="col-md-11 no-padding">
														<select id="itemtype_name" name="itemtype_name" data-edit-select="1">
							                        	</select>
													</div> -->
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="epistatic_order">前置单据号：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="epistatic_order" name="epistatic_order" type="text">
													</div>
												</div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="current_processor_display">出库时间：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input id="create_time" name="create_time" class="form-control data-range" type="text" readonly />
														<span class="input-group-addon">
															<i class="icon-calendar bigger-110"></i>
														</span>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="delivery_order">平台订单号：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="delivery_order" name="delivery_order" type="text">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="cod_flag">是否COD：</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="cod_flag" name="cod_flag" data-edit-select="1">
															<option value="">---请选择---</option>
															<option value="1">否</option>
															<option value="0">是</option>
							                        	</select>
													</div>
												</div>
											</div>
											
									</div>
									<div class="row text-center">
										<a class="senior-search-shift pointer" title="高级查询"><i class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a>
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
									<!-- <button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="exportReport();">
										<i class="ace-icon fa fa-download green">
											导出
										</i>
									</button> -->
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
