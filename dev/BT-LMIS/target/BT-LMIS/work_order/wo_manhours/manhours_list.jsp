<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" href="<%=basePath%>css/table.css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript"
	src="<%=basePath%>work_order/wo_manhours/js/manhours.js"></script>
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
								<form id="mh_form" name="mh_form" class="container search_form">
									<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="employee_number">工号&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<input class="form-data search-data form-control"
												id="employee_number" name="employee_number"
												style="width: 100%" type="text"
												value="${queryParam.employee_number }" />
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="employee_name">姓名&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<input class="form-data search-data form-control"
												id="employee_name" name="employee_name" style="width: 100%"
												type="text" value="${queryParam.employee_name }" />
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="date">日期区间&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-xs-12 row input-group no-padding">
												<input id="date" name="date" class="form-control data-range"
													type="text" readonly value="${queryParam.date }" /> <span
													class="input-group-addon"> <i
													class="icon-calendar bigger-110"></i>
												</span>
											</div>
										</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div
				style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
				<!-- <button class="btn btn-sm btn-white btn-default btn-bold btn-round"
					onclick="refresh();">
					<i class="fa fa-refresh bigger-120"> 重置 </i>
				</button> -->
				<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
					onclick="find();">
					<i class="ace-icon fa fa-search grey bigger-120"> 查询 </i>
				</button>
				<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
					onclick="openDiv('/BT-LMIS/control/manhoursController/toAddForm.do');">
					<i class="ace-icon fa fa-plus-circle blue bigger-120"> 新增 </i>
				</button>
				<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
					onclick="del();">
					<i class="ace-icon fa fa-trash-o red bigger-120"> 删除 </i>
				</button>
				<button class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width"
					onclick="batchShiftStatus('true');">
					<i class="ace-icon fa fa-unlock green bigger-120"> 启用 </i>
				</button>
				<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width"
					onclick="batchShiftStatus('false');">
					<i class="ace-icon fa fa-lock orange bigger-120"> 禁用 </i>
				</button>
			</div>
			<div class="title_div" id="sc_title">
				<table class="table table-striped" style="table-layout: fixed;">
					<thead>
						<tr class="table_head_line">
							<td class="td_ch"><input type="checkbox" id="checkAll"
								onclick="inverseCkb('ckb')" /></td>
							<td class="td_cs">日期</td>
							<td class="td_cs">工号</td>
							<td class="td_cs">姓名</td>
							<td class="td_cs">工时</td>
							<td class="td_cs">已分配工时</td>
							<td class="td_cs">可分配工时</td>
							<td class="td_cs">工时状态</td>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tree_div"></div>
			<div
				style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
				id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
				<table id="table_content" class="table table-hover table-striped"
					style="table-layout: fixed;">
					<tbody align="center">
						<c:forEach items="${pageView.records }" var="res">
							<tr>
								<td class="td_ch"><input id="ckb${res.id }" name="ckb"
									type="checkbox" value="${res.id }" data-status="${res.status }"></td>
								<td class="td_cs" title="${res.date }">${res.date }</td>
								<td class="td_cs" title="${res.employee_number }">${res.employee_number }</td>
								<td class="td_cs" title="${res.name }">${res.name }</td>
								<td class="td_cs" title="${res.manhours }">${res.manhours }</td>
								<td class="td_cs" title="${res.allocated }">${res.allocated }</td>
								<td class="td_cs" title="${res.rest }">${res.rest }</td>
								<td class="td_cs" title="${res.status }">${res.status }
								<%-- | <c:if
										test="${res.status == '启用'}">
										<button
											class="btn btn-sm btn-white btn-warning btn-bold btn-round"
											onclick="upStatus('${res.id}','false');">
											<i class="ace-icon fa fa-lock orange bigger-120"></i>禁用
										</button>
									</c:if> <c:if test="${res.status == '禁用'}">
										<button
											class="btn btn-sm btn-white btn-success btn-bold btn-round"
											onclick="upStatus('${res.id}','true');">
											<i class="ace-icon fa fa-unlock green bigger-120"></i>启用
										</button>
									</c:if> --%>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div style="margin-right: 1%; margin-top: 20px;">${pageView.pageView }</div>
		</div>
	</div>
	</div>
	</div>
	<!-- 工时编辑页面弹窗 -->
	<div id="manhours_form" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="formLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content" style="border: 3px solid #394557;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 id="formLabel" class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<input id="manhours_id" name="manhours_id" type="hidden" />
					<div class="form-group">
						<label class="blue">工号&nbsp;:</label> <input
							id="express_number_form" class="form-control" type="text"
							readonly="readonly" />
					</div>
					<div class="form-group">
						<label class="blue">姓名&nbsp;:</label> <input id="name_form"
							class="form-control" type="text" readonly="readonly" />
					</div>
					<div class="form-group">
						<label class="blue">日期&nbsp;:</label>
						<div class="input-group">
							<input id="date_form" class="form-control" type="text" readonly />
							<span class="input-group-addon"> <i
								class="icon-calendar bigger-110"></i>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="blue">维护工时（分钟/天）&nbsp;:</label> <input
							id="manhours" name="manhours" class="form-control" type="text" />
					</div>
					<div class="form-group">
						<label class="blue">已分配工时（分钟/天）&nbsp;:</label> <input
							id="allocated" name="allocated" class="form-control" type="text"
							readonly />
					</div>
				</div>
				<div class="modal-footer">
					<button id="btn_back" type="button" class="btn btn-default"
						data-dismiss="modal">
						<i class="icon-undo" aria-hidden="true"></i>返回
					</button>
					<button id="btn_submit" type="button" class="btn btn-primary"
						onclick="update();">
						<i class="icon-save" aria-hidden="true"></i>保存
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
