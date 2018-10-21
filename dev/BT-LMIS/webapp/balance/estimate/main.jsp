<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>LMIS</title>
		<%@ include file="/lmis/yuriy.jsp" %>
		
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css"  />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/loadingStyle.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/common.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/table.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>balance/estimate/css/main.css" />
		
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/clipboard/clipboard.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js" ></script>
		<script type="text/javascript" src="<%=basePath %>balance/estimate/js/main.js"></script>
		
	</head>
	<body>
		<div class="page-header no-margin-bottom" align="left">
			<h1>结算预估</h1>
		</div>
		<div class="lmis-panel">
			<div class="widget-box collapsed no-margin">
				<div class="widget-header header-color-blue">
					<h4>查询列表</h4>
			        <div class="widget-toolbar">
			            <a href="#" data-action="reload" onclick="refreshCondition();">
			                <i class="icon-refresh bigger-125"></i>
			            </a>
			            <a href="#" data-action="collapse">
			                <i class="icon-chevron-down bigger-125"></i>
			            </a>
			        </div>
			        <div class="widget-toolbar no-border">
			            <button class="btn btn-xs btn-white bigger" onclick="query();">
			                <i class="icon-search"></i>查询
			            </button>
			        </div>
				</div>
				 <div class="widget-body">
			    	<div class="widget-main" style="background:#F0F8FF;">
			    		<form id="condition" class="container">
			    			<div class= "row form-group" >
			    				<div class="col-md-1" style="text-align:right;">
			                        <label class="no-padding-right blue" for="batch_number" style="white-space:nowrap;">
	                         			预估批次&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3">
			                        <input id="batch_number" name="batch_number" type="text" class="form-control" />
			                    </div>
			                    <div class="col-md-1" style="text-align:right;">
			                    	<label class="no-padding-right blue" for="batch_status" style="white-space:nowrap;">
	                         			预估状态&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3">
			                    	<select id="batch_status" name="batch_status" data-edit-select="1">
			                    		<option value="">---请选择---</option>
			                    		<option value="WAI">等待中</option>
			                    		<option value="ING">进行中</option>
			                    		<option value="FIN">已完成</option>
			                    		<option value="CAN">已取消</option>
			                    		<option value="ERR">异常待处理</option>
			                    	</select>
			                    </div>
			                    <div class="col-md-1" style="text-align:right;">
			                    	<label class="no-padding-right blue" for="date_domain" style="white-space:nowrap;">
	                         			日期范围&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 form-group input-group">
			                    	<input id="date_domain" name="date_domain" class="form-control" type="text" readonly />
									<span class="input-group-addon">
										<i class="icon-calendar bigger-110"></i>
									</span>
			                    </div>
			    			</div>
			    			<div class="row form-group">
			    				<div class="col-md-1" style="text-align:right;">
			                    	<label class="no-padding-right blue" for="estimate_type" style="white-space:nowrap;">
	                         			预估类型&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3">
			                    	<select id="estimate_type" name="estimate_type" data-edit-select="1" onchange="shiftContractByType();">
			                    		<option value="">---请选择---</option>
			                    		<option value="0">支出</option>
			                    		<option value="1">收入</option>
			                    	</select>
			                    </div>
			                    <div class="col-md-1" style="text-align:right;">
			                    	<label class="no-padding-right blue" for="contract_in_estimate" style="white-space:nowrap;">
	                         			包含合同&nbsp;:
			                        </label>
			                    </div>
			                    <div class="col-md-3">
			                    	<select id="contract_in_estimate" name="contract_in_estimate" data-edit-select="1">
			                    		<option value="">---请选择---</option>
			                    	</select>
			                    </div>
			    			</div>
			    		</form>
			    	</div>
			    </div>
			</div>
			<p class="alert alert-info no-margin-bottom">
				<button class="btn btn-white" onclick="query();"><i class="icon-refresh"></i>刷新</button>
				<button class="btn btn-white" onclick="openAddForm();"><i class="icon-plus"></i>新增</button>
			</p>
			<jsp:include page="/balance/estimate/data.jsp" flush="true" />
		</div>
		<jsp:include page="/balance/estimate/addForm.jsp" flush="true" />
		<jsp:include page="/balance/estimate/batchNumberForm.jsp" flush="true" />
	</body>
</html>