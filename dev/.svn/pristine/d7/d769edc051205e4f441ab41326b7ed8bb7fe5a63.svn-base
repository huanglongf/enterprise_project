<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/templet/common.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>uploadify/uploadify.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel= "stylesheet" />
		<link type="text/css" href="<%=basePath %>work_order/wo_management/css/wo_management.css" rel= "stylesheet" />
		
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript" src="<%=basePath %>uploadify/JQueryUploadHelper.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/clipboard/clipboard.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/wo_management.js"></script>
	</head>
	<body>
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
			<input id="isCollapse" type="hidden" value="${queryParam.isCollapse }" />
				<div class="col-xs-12"><!-- ${queryParam.isCollapse?'collapsed':'' } -->
					<div class="widget-box ">
						<div class="widget-header widget-header-small">
							<h5 class="widget-title lighter">查询栏</h5>
							<a class="pointer" title="初始化" onclick="refreshCondition();"><i
								class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<input id="wo_level_temp" type="hidden" value="${queryParam.wo_level }" />
				    			<input id="exception_type_temp" type="hidden" value="${queryParam.exception_type }" />
								<form id="wk_form" name="wk_form" class="container search_form">
									<input id="uuid" name="uuid" type="hidden" value="${queryParam.uuid }" />
					            	<input id="sort_by" name="sort_by" type="hidden" />
					            	<input id="sort" name="sort" type="hidden" />
									<div class="row form-group">
					            		<div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "wo_no" style= "white-space: nowrap;" >
			                         				工单号&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <input id= "wo_no" name= "wo_no" type= "text" class= "form-control" value= "${queryParam.wo_no }" />
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "express_number" style= "white-space: nowrap;" >
				                            	快递单号&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <input id= "express_number" name= "express_number" type= "text" class= "form-control" value= "${queryParam.express_number }" />
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "platform_number" style= "white-space: nowrap;" >
				                            	平台订单号&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <input id= "platform_number" name= "platform_number" type= "text" class= "form-control" value= "${queryParam.platform_number }" />
					                    </div>
			                    	</div>
		                    	<div class="senior-search">
									<div class="row form-group">
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "wo_process_status" style= "white-space: nowrap;" >
			                            		工单处理状态&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <select id= "wo_process_status" name= "wo_process_status" data-edit-select= "1" >
					                            <option value= "" >---请选择---</option>
					                            <option value= "NEW" ${"NEW"==queryParam.wo_alloc_status?"selected='selected'":"" } >新建</option>
					                            <option value= "PRO" ${"PRO"==queryParam.wo_alloc_status?"selected='selected'":"" } >处理中</option>
					                            <option value= "PAU" ${"PAU"==queryParam.wo_alloc_status?"selected='selected'":"" } >挂起待确认</option>
					                            <option value= "CCD" ${"CCD"==queryParam.wo_alloc_status?"selected='selected'":"" } >已取消</option>
					                            <option value= "FIN" ${"FIN"==queryParam.wo_alloc_status?"selected='selected'":"" } >已处理</option>
					                        </select>
					                    </div>
					            		<div class="col-md-1 no-padding text-center search-title" >
				                        	<label class= "no-padding-right blue" style= "white-space: nowrap;" >
				                           	 	创建时间区间&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" style= "white-space: nowrap;" >
					                    	<input id= "createTime_from" name= "createTime_from" type= "text"
					                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					                    		value= "${queryParam.createTime_from }" />
					                    	<input id= "createTime_to" name= "createTime_to" type="text"
					                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					                    		value= "${queryParam.createTime_to }" />
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "wo_type" style= "white-space: nowrap;" >
			                            		工单类型&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <select id= "wo_type" name= "wo_type" data-edit-select= "1" onchange= "shiftType('wo_type', {'levelId': 'wo_level', 'exceptionId': 'exception_type', 'check_flag': '0' });" >
					                            <option value= "" >---请选择---</option>
					                            <c:forEach items= "${wo_type }" var= "wo_type" >
					                                <option value= "${wo_type.code }" ${wo_type.code==queryParam.wo_type?"selected='selected'":"" } >${wo_type.name }</option>
					                            </c:forEach>
					                        </select>
					                    </div>
				                     </div>
				                     <div class="row form-group">
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "wo_level" style= "white-space: nowrap;" >
			                            		工单级别&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <select id= "wo_level" name= "wo_level" data-edit-select= "1" >
					                            <option value= "" >---请选择---</option>
					                        </select>
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "exception_type" style= "white-space: nowrap;" >
				                            	异常类型&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <select id= "exception_type" name= "exception_type" data-edit-select= "1" >
					                            <option value= "" >---请选择---</option>
					                        </select>
					                    </div>
					                	<div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "carrier" style= "white-space: nowrap;" >
				                            	物流服务商&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <select id= "carrier" name= "carrier" data-edit-select= "1" >
					                            <option value= "" >---请选择---</option>
					                            <c:forEach items= "${carrier }" var= "carrier" >
					                                <option value= "${carrier.transport_code }" ${carrier.transport_code==queryParam.carrier?"selected='selected'":"" } >${carrier.transport_name }</option>
					                            </c:forEach>
					                        </select>
					                    </div>
					                </div>
					                <div class="row form-group">
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "create_by" style= "white-space: nowrap;" >
			                           			创建者&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <input id= "create_by" name= "create_by" type= "text" class= "form-control" value= "${queryParam.create_by }" />
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
					                        <label class= "no-padding-right blue" for= "warehouse" style= "white-space: nowrap;" >
			                            		仓库&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" >
					                        <select id= "warehouse" name= "warehouse" data-edit-select= "1" >
					                            <option value= "" >---请选择---</option>
					                            <c:forEach items= "${warehouse }" var= "warehouse" >
					                                <option value= "${warehouse.warehouse_code }" ${warehouse.warehouse_code==queryParam.warehouse?"selected='selected'":"" } >${warehouse.warehouse_name }</option>
					                            </c:forEach>
					                        </select>
					                    </div>
					                    <div class="col-md-1 no-padding text-center search-title" >
				                        	<label class= "no-padding-right blue" style= "white-space: nowrap;" >
				                           	 	出库时间区间&nbsp;: 
					                        </label>
					                    </div>
					                    <div class="col-md-3 no-padding" style= "white-space: nowrap;" >
					                    	<input id= "transportTime_from" name= "transportTime_from" type= "text"
					                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					                    		value= "${queryParam.transportTime_from }" />
					                    	<input id= "transportTime_to" name= "transportTime_to" type="text"
					                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					                    		value= "${queryParam.transportTime_to }" />
					                    </div>
			                		</div>
			                <div class= "row form-group" >
			                    <div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "store" style= "white-space: nowrap;" >
	                            		店铺&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
		                        	<select id= "store" name= "store" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <c:forEach items= "${store }" var= "store" >
			                                <option value= "${store.store_code }" ${store.store_code==queryParam.store?"selected='selected'":"" } >${store.store_name }</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			            		<div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "wo_alloc_status" style= "white-space: nowrap;" >
										工单分配状态&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <select id= "wo_alloc_status" name= "wo_alloc_status" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <option value= "WAA" ${"WAA"==queryParam.wo_alloc_status?"selected='selected'":"" } >待自动分配</option>
			                            <option value= "WMA" ${"WMA"==queryParam.wo_alloc_status?"selected='selected'":"" } >待手动分配</option>
			                            <option value= "ALD" ${"ALD"==queryParam.wo_alloc_status?"selected='selected'":"" } >已分配</option>
			                        </select>
			                    </div>
			                    <div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "related_number" style= "white-space: nowrap;" >
		                            	相关单据号&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <input id= "related_number" name= "related_number" type= "text" class= "form-control" value= "${queryParam.related_number }" />
			                    </div>
			                </div>
			                <div class= "row form-group" >
			                    <div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "allocated_by" style= "white-space: nowrap;" >
	                            		分配者&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <input id= "allocated_by" name= "allocated_by" type= "text" class= "form-control" value= "${queryParam.allocated_by }" />
			                    </div>
			                    <div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "processor" style= "white-space: nowrap;" >
	                            		处理者&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <input id= "processor" name= "processor" type= "text" class= "form-control" value= "${queryParam.processor }" />
			                    </div>
			               
			                	<div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "claim_status" style= "white-space: nowrap;" >
			                           	 索赔状态&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <select id= "claim_status" name= "claim_status" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <option value= "0" ${"0"==queryParam.claim_status?"selected='selected'":"" } >暂作理赔</option>
			                            <option value= "1" ${"1"==queryParam.claim_status?"selected='selected'":"" } >索赔成功</option>
			                            <option value= "2" ${"2"==queryParam.claim_status?"selected='selected'":"" } >索赔失败</option>
			                        </select>
			                    </div>
			                     </div>
			                <div class= "row form-group" >
			                    <div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "claim_number" style= "white-space: nowrap;" >
		                           		 索赔编码&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <input id= "claim_number" name= "claim_number" type= "text" class= "form-control" value= "${queryParam.claim_number }" />
			                    </div>
			                    <div class="col-md-1 no-padding text-center search-title" >
			                        <label class= "no-padding-right blue" for= "claim_type" style= "white-space: nowrap;" >
			                           	 索赔分类&nbsp;: 
			                        </label>
			                    </div>
			                    <div class="col-md-3 no-padding" >
			                        <select id= "claim_type" name= "claim_type" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <option value= "1" ${"1"==queryParam.claim_type?"selected='selected'":"" } >遗失</option>
			                            <option value= "2" ${"2"==queryParam.claim_type?"selected='selected'":"" } >破损</option>
			                            <option value= "3" ${"3"==queryParam.claim_type?"selected='selected'":"" } >错发</option>
			                            <option value= "4" ${"4"==queryParam.claim_type?"selected='selected'":"" } >补偿</option>
			                        </select>
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
		    <p class= "alert alert-info no-margin-bottom" >
		   		<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="tablesearch('','');">
					<i class="ace-icon fa fa-search grey bigger-120">
						查询
					</i>
				</button>
				<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
					onclick="toWOAddForm()">
					<i class="ace-icon fa fa-plus-circle blue bigger-120"> 新增 </i>
				</button>
				<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
					onclick="toAllocForm()">
					<i class="ace-icon fa fa-cog green bigger-120"> 分配 </i>
				</button>
				<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
					onclick= "operateStatus('${role }', '0', 'CANCEL_ALLOC');" >
					<i class="ace-icon fa fa-undo red bigger-120"> 取消分配 </i>
				</button>
				<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
					onclick= "operateStatus('${role }', '0', 'CANCEL');">
					<i class="ace-icon fa fa-circle orange bigger-120"> 取消</i>
				</button>
		    </p>
		    <div class="search-table">
			<jsp:include page= "/templet/table.jsp" flush= "true" />
<%-- 			<jsp:include page= "/work_order/wo_management/wo_management_list.jsp" flush= "true" /> --%>
			</div>
			</div>
			</div>
		</div>
	</div>
		</div>
		<jsp:include page= "/work_order/wo_management/wo_management_add.jsp" flush= "true" />
		<jsp:include page= "/work_order/wo_management/wo_management_alloc.jsp" flush= "true" />
		<jsp:include page= "/work_order/wo_management/wo_waybillGenerated.jsp" flush= "true" />
	</body>
</html>