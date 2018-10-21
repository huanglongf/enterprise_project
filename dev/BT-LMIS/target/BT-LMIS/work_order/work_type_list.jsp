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
	src="<%=basePath%>/work_order/work_type.js"></script>
<script type="text/javascript" src="<%=basePath%>js/selectFilter.js"></script>
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
								<form id="wt_form" name="wt_form" class="container search_form">
									<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="client_code">工单类型&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<select id="typeKid_search" name="typeId" data-edit-select="1">
												<option value="">请选择</option>
												<c:forEach var="list" items="${allType}">
													<option value="${list.wk_type}"
														<c:if test="${queryParam.typeId==list.wk_type}">selected</c:if>>${list.wk_type}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="control-label blue" for="client_name">启用状态&nbsp;:</label>
										</div>
										<div class="col-md-3 no-padding">
											<select id="status_search" name="status" data-edit-select="1">
												<option value="">全部</option>
												<option value="1"
													<c:if test="${queryParam.status=='1'}">selected='selected'</c:if>>启用</option>
												<option value="0"
													<c:if test="${queryParam.status=='0'}">selected='selected'</c:if>>禁用</option>
											</select>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div
						style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;">
						<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
							onclick="tablesearch('','')">
							<i class="ace-icon fa fa-search grey bigger-120"> 查询 </i>
						</button>
						<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
							onclick="toAdd('')">
							<i class="ace-icon fa fa-plus-circle blue bigger-120"> 新增 </i>
						</button>
						<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
							onclick="del();">
							<i class="ace-icon fa fa-trash-o red bigger-120"> 删除 </i>
						</button>
						<button class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width"
							onclick="updateStatus('1','1');">
							<i class="ace-icon fa fa-unlock green bigger-120"> 启用 </i>
						</button>
						<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width"
							onclick="updateStatus('1','0');">
							<i class="ace-icon fa fa-lock orange bigger-120"> 禁用 </i>
						</button>
					</div>
					<div class="search-table">
						<jsp:include page="/work_order/work_type_table.jsp" flush= "true" /> 
						<%-- <jsp:include page="/templet/table.jsp" flush= "true" /> --%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function back() {
		openDiv('${root}control/transPoolController/listMain.do');
	}
	function pageJump() {
		tablesearch('','');
	}
	function toAdd() {
		openDiv('/BT-LMIS/control/OrderTypeController/toForm.do');
	}
	function refresh() {
		openDiv(root+'control/OrderTypeController2/typelist.do?pageName=work_type_list&tableName=wk_type');
	}
	function tablesearch(column, sort) {
		$(".search-table").load("/BT-LMIS/control/OrderTypeController2/typelist2.do?pageName=table&tableName=wk_type&startRow="
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
			+ "&"
			+ $(".search_form").serialize()
		);
	}
</script>
</html>
