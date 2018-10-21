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
<script type="text/javascript" src="<%=basePath%>js/selectFilter.js"></script>

<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript"
	src="<%=basePath%>work_order/wo_manhours/js/manhours.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#wo_type").next().next().next().find("li").click(function () {
			setTimeout(() => {
				load_error_type($("#wo_type").val());
			}, 50);
		});
	})
	function load_error_type(code) {
		if (code != '-1') {
			$(".error_type").hide();
		} else {
			$(".error_type").show();
		}
		$(".yuriy_hide").hide();
		$("#" + code).show();
	}
	function pageJump() {
		tablesearch('', '');
	}
	
	function tablesearch(column, sort) {
		$("#sort_column").val(column);
		$("#sort").val(sort);
		var stime = (new Date($('#s_time').val())).getTime();
		var etime = (new Date($('#e_time').val())).getTime();
		var exception_type = "";
		var wo_type = $('#wo_type').val();
		if (wo_type !== null || wo_type !== undefined || wo_type !== ''
				|| wo_type !== '-1') {
			exception_type = $("#sl" + wo_type).val();
		}
		$(".search-table").load("${root }/control/exportWKController2/exportCheckWK.do?pageName=table&tableName=wo_check_wk&startRow="
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
				+ "&create_by="
				+ $('#create_by').val()
				+ "&processor="
				+ $('#processor').val()
				+ "&store="
				+ $('#store').val()
				+ "&carrier="
				+ $('#carrier').val()
				+ "&wo_type=" + wo_type
				+ "&exception_type=" + exception_type
				+ "&express_number="
				+ $('#express_number').val()
				+ "&wo_no="
				+ $('#wo_no').val());
		
		};
		function tableAction(currentRow, tableFunctionConfig) {
		}
		
		function refresh() {
			openDiv("${root }/control/exportWKController2/exportCheckWK.do?pageName=check_wk&tableName=wo_check_wk")
			/* $("#create_time").val("");
			$("#express_number").val("");
			initializeSelect('create_by');
			initializeSelect('processor');
			initializeSelect('store');
			initializeSelect('sp_type');
			initializeSelect('carrier');
			$("#wo_no").val(""); */
		}
		
		function exportWK() {
			var exception_type = "";
			var wo_type = $('#wo_type').val();
			if (wo_type !== null || wo_type !== undefined || wo_type !== ''
					|| wo_type !== '-1') {
				exception_type = $("#sl" + wo_type).val();
			}
			window.location.href="${root }/control/exportWKController2/exportCheckWKExcel.do?tableName=wo_check_wk"
				+ "&wo_no="
				+ $('#wo_no').val()
				+ "&create_time="
				+ encodeURI($("#create_time").val())
				+ "&create_by="
				+ $('#create_by').val()
				+ "&processor="
				+ $('#processor').val()
				+ "&store="
				+ $('#store').val()
				+ "&carrier="
				+ $('#carrier').val()
				+ "&express_number="
				+ $('#express_number').val()
				+ "&wo_type=" + wo_type
				+ "&exception_type=" + exception_type
				+ "&sortColumn=" + $("#sort_column").val() + "&sort=" + $("#sort").val();
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
							<a class="pointer" title="初始化" onclick="refresh();"><i
								class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<form id="wk_form" name="wk_form" class="container search_form">
									<div class="row form-group">
										<%-- <div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="client_code">创建时间&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-xs-12 input-group no-padding">
												<input type="text" id="s_time"
												name="s_time" style="width:100%;height:34px;"
												class="Wdate"
												onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
												value="<c:if test="${queryParam.s_time!='NaN'}">${queryParam.s_time}</c:if>" />
												<span class="input-group-addon">
															<i class="icon-calendar bigger-110"></i>
														</span>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="client_code">结束时间&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-xs-12 input-group no-padding">
												<input type="text" id="e_time"
												name="e_time" style="width:100%;height:34px;"
												class="Wdate"
												onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
												value="<c:if test="${queryParam.e_time!='NaN'}">${queryParam.e_time}</c:if>" />
												<span class="input-group-addon">
															<i class="icon-calendar bigger-110"></i>
														</span>
											</div>
										</div> --%>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="client_code">创建日期&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-12 no-padding input-group">
												<input id="create_time" name="create_time" class="form-control data-range" type="text" readonly />
												<span class="input-group-addon">
													<i class="icon-calendar bigger-110"></i>
												</span>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue"
											for="employee_number">创建者&nbsp;:</label></div>
										<div class="col-md-3 no-padding"><select id="create_by" name="create_by"
											class='select' data-edit-select="1">
												<option value=-1>---请选择---</option>
												<c:forEach items="${work_emp_list}" var="work_emp_list">
													<option value="${work_emp_list.id}"
														<c:if test="${queryParam.create_by==work_emp_list.id}">selected="selected"</c:if>>${work_emp_list.name}</option>
												</c:forEach>
										</select></div>
										<div class="col-md-1 no-padding text-center search-title" ><label class="control-label blue"
													for="employee_name">工单号&nbsp;:</label></div>
												<div class= "col-md-3 no-padding"><input id="wo_no" name="wo_no" class="form-data search-data form-control"
													style="width: 100%" type="text"
													value="<c:if test="${queryParam.wo_no!='NaN' }">${queryParam.wo_no }</c:if>" /></div>
									</div>
									<div class="senior-search">
										<div class="row form-group">
											<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue"
												for="employee_number">处理者&nbsp;:</label></div>
											<div class="col-md-3 no-padding">
												<select id="processor" name="processor"
												class='select' data-edit-select="1">
													<option value=-1>---请选择---</option>
													<c:forEach items="${work_emp_list}" var="work_emp_list">
														<option value="${work_emp_list.id}"
															<c:if test="${queryParam.processor==work_emp_list.id}">selected="selected"</c:if>>${work_emp_list.name}</option>
													</c:forEach>
											</select></div>
											<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue"
												for="employee_name">店铺&nbsp;:</label></div>
											<div class="col-md-3 no-padding"><select id="store" name="store"
												class='select' data-edit-select="1">
													<option value=-1>---请选择---</option>
													<c:forEach items="${store_list}" var="store_list">
														<option value="${store_list.store_code}"
															<c:if test="${queryParam.store==store_list.store_code}">selected="selected"</c:if>>${store_list.store_name}</option>
													</c:forEach>
											</select></div>
											<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue"
												for="employee_name">快递公司&nbsp;:</label></div>
											<div class="col-md-3 no-padding"><select id="carrier" name="carrier"
												class='select' data-edit-select="1">
													<option value=-1>---请选择---</option>
													<c:forEach items="${carrier_list}" var="carrier_list">
														<option value="${carrier_list.transport_code}"
															<c:if test="${queryParam.carrier==carrier_list.transport_code}">selected="selected"</c:if>>${carrier_list.transport_name}</option>
													</c:forEach>
											</select></div>
											</div>
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue"
													for="employee_number">工单类型&nbsp;:</label></div>
												<div class="col-md-3 no-padding"><select id="wo_type" name="wo_type"
													class='select' data-edit-select="1">
														<option value=-1>---请选择---</option>
														<c:forEach items="${wktype_list}" var="wktype_list">
															<option value="${wktype_list.code}"
																<c:if test="${queryParam.wo_type==wktype_list.code}">selected="selected"</c:if>>${wktype_list.name}</option>
														</c:forEach>
												</select></div>
												<div class="col-md-1 no-padding text-center search-title"><label class="control-label blue"
													for="employee_name">异常类型&nbsp;:</label></div>
												<div class="col-md-3 no-padding">
													<div class="error_type">
														<select id="exception_type" name="exception_type"
															class='select' data-edit-select="1">
															<option value=-1>---请选择---</option>
															<c:forEach items="${warningtype}" var="warningtype">
																<option value="${warningtype.warningtype_code}"
																	<c:if test="${queryParam.exception_type==warningtype.warningtype_code}">selected="selected"</c:if>>${warningtype.warningtype_name}</option>
															</c:forEach>
														</select>
													</div> <c:forEach items="${lists}" var="lists">
														<div id="${lists.type_code}" class="yuriy_hide"
															style="display: none;">
															<select id="sl${lists.type_code}" data-edit-select="1"
																style="display: none;">
																<option value=-1>---请选择---</option>
																<c:forEach items="${lists.wkerror_list}" var="lss">
																	<option value="${lss.id}">${lss.type_name}</option>
																</c:forEach>
															</select>
														</div>
													</c:forEach>
												</div>
												<div class="col-md-1 no-padding text-center search-title" ><label class="control-label blue"
													for="employee_name">快递单号&nbsp;:</label></div>
												<div class="col-md-3 no-padding"><input id="express_number" class="form-data search-data form-control"
													name="express_number" style="width: 100%" type="text"
													value="<c:if test="${queryParam.express_number!='NaN' }">${queryParam.express_number }</c:if>" /></div>
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
						<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('', '');">
							<i class="ace-icon fa fa-search grey bigger-120">
								查询
							</i>
						</button>
						&nbsp;
						<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="exportWK();">
							<i class="ace-icon fa fa-download grey">
								导出
							</i>
						</button>
						&nbsp;
					</div>
					
					<div class="search-table">
						<jsp:include page="/templet/table2.jsp" flush="true" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
