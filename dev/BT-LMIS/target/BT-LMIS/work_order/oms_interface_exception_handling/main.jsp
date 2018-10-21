<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/templet/common.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content="overview &amp; stats" />
		<meta name= "viewport" content="width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type= "text/css" href="<%=basePath %>css/common.css" rel="stylesheet" />
		<link type= "text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link type= "text/css" href="<%=basePath %>work_order/oms_interface_exception_handling/css/main.css" rel="stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/validateForm.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>work_order/oms_interface_exception_handling/js/main.js" ></script>
	</head>
	<body>
<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-xs-12">
				<div class="widget-box">
					<div class="widget-header widget-header-small">
						<h5 class="widget-title lighter">查询栏</h5>
						<a class="pointer" title="初始化" onclick= "refreshCondition();"><i class="fa fa-refresh"></i></a>
					</div>
					<div class="widget-body">
						<div class="widget-main">
							<form id= "condition" class="container search_form">
								<div class="row form-group">
									<div class="col-md-1 no-padding text-center search-title" >
					                        <label class="control-label blue" for= "claim_flag" style= "white-space: nowrap;" >
			                         			接口索赔状态&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
				                    	 	<select id="claim_flag" name="claim_flag" data-edit-select="1">
												<option value="0">未处理</option>
												<option value="2">已处理</option>
												<option value="3">已反馈</option>
											</select> 
					                    </div>
				    				<div class="col-md-1 no-padding text-center search-title" >
				                        <label class="control-label blue" for= "systemCode" style= "white-space: nowrap;" >
		                         			系统对接编码&nbsp;: 
				                        </label>
				                    </div>
				                    <div class="col-md-3 no-padding" >
				                        <input id= "systemCode" name= "systemCode" type= "text" class="form-data search-data form-control" />
				                    </div>
				    				<div class="col-md-1 no-padding text-center search-title" >
				                        <label class="control-label blue" for= "claimCode" style= "white-space: nowrap;" >
		                         			索赔编码&nbsp;: 
				                        </label>
				                    </div>
				                    <div class="col-md-3 no-padding" >
				                        <input id= "claimCode" name= "claimCode" type= "text" class="form-data search-data form-control" />
				                    </div>
				                   
				                    </div>
				    			<div class="senior-search">
					    			<div class= "row form-group" >
						    			 <div class="col-md-1 no-padding text-center search-title" >
					                        <label class="control-label blue" for= "omsOrderCode" style= "white-space: nowrap;" >
			                         			OMS订单号&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <input id= "omsOrderCode" name= "omsOrderCode" type= "text" class="form-data search-data form-control" />
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class="control-label blue" for= "erpOrderCode" style= "white-space: nowrap;" >
			                         			平台订单号&nbsp;:
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                    	<input id= "erpOrderCode" name= "erpOrderCode" type= "text" class="form-data search-data form-control" />
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class="control-label blue" for= "rasCode" style= "white-space: nowrap;" >
			                         			换货申请编码&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                    	<input id= "rasCode" name= "rasCode" type= "text" class="form-data search-data form-control" />
					                    </div>
					                   
					    			</div>
					    			<div class= "row form-group" >
					    				 <div class="col-md-1 no-padding text-center search-title" >
					                        <label class="control-label blue" for= "transNumber" style= "white-space: nowrap;" >
			                         			物流单号&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                    	<input id= "transNumber" name= "transNumber" type= "text" class="form-data search-data form-control" />
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
			<p class= "alert alert-info no-margin-bottom" >
				<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick = "query();">
					<i class="ace-icon fa fa-search grey bigger-120">
						查询
					</i>
				</button>
				<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick = "update();">
					<i class="ace-icon fa fa-cog red bigger-120">
						索赔失败
					</i>
				</button>
			</p>
			<div id ="data">
			<jsp:include page= "/templet/table.jsp" flush= "true" />
			</div>
		</div>
		</div>
		</div>
		</div>
		<jsp:include page= "/work_order/oms_interface_exception_handling/process.jsp" flush= "true" />
	</body>
</html>