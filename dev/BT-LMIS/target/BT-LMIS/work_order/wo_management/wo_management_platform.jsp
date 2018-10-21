<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>My97DatePicker/skin/WdatePicker.css" rel= "stylesheet" />
		<link type="text/css" href="<%=basePath %>uploadify/uploadify.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel= "stylesheet" />
		<link type="text/css" href="<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>work_order/wo_management/css/wo_management.css" rel= "stylesheet" />
		
		<script type="text/javascript" src="<%=basePath %>jquery/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript" src="<%=basePath %>uploadify/JQueryUploadHelper.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/clipboard/clipboard.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/wo_management_platform.js"></script>
	</head>
	<body>
		<div class="fixedDiv">
			<p class="alert alert-info no-padding no-margin" align="center">
				<button style="width: 15%" class="no-margin btn btn-app ${queryParam.wo_process_status == '' || queryParam.wo_process_status == null? 'btn-primary': 'btn-light' } btn-lg radius-20" onclick="shiftStatus('', $(this));addStyle();">
					<i class="icon-laptop bigger-230"></i>汇总
					<span id="news_" class="badge badge-pink badge-left" style="display:none;">+0</span>
					<span id="current_" class="label label-primary arrowed-in">0</span>
				</button>
				<input id="triggerMode_" type="hidden" />
				<button style= "width: 15%" class= "no-margin btn btn-app ${queryParam.wo_process_status == 'NEW'? 'btn-primary': 'btn-light' } btn-lg radius-20" onclick= "shiftStatus('NEW', $(this));addStyle();" >
					<i class= "icon-adjust bigger-230" ></i>新建
					<span id= "news_NEW" class="badge badge-pink badge-left" style= "display: none;" >+0</span>
					<span id= "current_NEW" class="label label-primary arrowed-in">0</span>
				</button>
				<input id="triggerMode_NEW" type="hidden" />
				<button style= "width: 15%" class= "no-margin btn btn-app ${queryParam.wo_process_status == 'PRO'? 'btn-primary': 'btn-light' } btn-lg radius-20" onclick= "shiftStatus('PRO', $(this));removeStyle();" >
					<i class= "icon-exchange bigger-230" ></i>处理中
					<span id= "news_PRO" class="badge badge-pink badge-left" style= "display: none;" >+0</span>
					<span id= "current_PRO" class="label label-primary arrowed-in">0</span>
				</button>
				<input id="triggerMode_PRO" type="hidden" />
				<button style= "width: 15%" class= "no-margin btn btn-app ${queryParam.wo_process_status == 'PAU'? 'btn-primary': 'btn-light' } btn-lg radius-20" onclick= "shiftStatus('PAU', $(this));addStyle();" >
					<i class= "icon-pause bigger-230" ></i>挂起待确认
					<span id= "news_PAU" class="badge badge-pink badge-left" style= "display: none;" >+0</span>
					<span id= "current_PAU" class="label label-primary arrowed-in">0</span>
				</button>
				<input id="triggerMode_PAU" type="hidden" />
				<button style= "width: 15%" class= "no-margin btn btn-app ${queryParam.wo_process_status == 'CCD'? 'btn-primary': 'btn-light' } btn-lg radius-20" onclick= "shiftStatus('CCD', $(this));addStyle();" >
					<i class= "icon-circle bigger-230" ></i>已取消
					<span id= "news_CCD" class="badge badge-pink badge-left" style= "display: none;" >+0</span>
					<span id= "current_CCD" class="label label-primary arrowed-in">0</span>
				</button>
				<input id="triggerMode_CCD" type="hidden" />
				<button style= "width: 15%" class= "no-margin btn btn-app ${queryParam.wo_process_status == 'FIN'? 'btn-primary': 'btn-light' } btn-lg radius-20" onclick= "shiftStatus('FIN', $(this));addStyle();" >
					<i class= " icon-check bigger-230" ></i>已处理
					<span id= "news_FIN" class="badge badge-pink badge-left" style= "display: none;" >+0</span>
					<span id= "current_FIN" class="label label-primary arrowed-in">0</span>
				</button>
				<input id="triggerMode_FIN" type="hidden" />
			</p>
			<div class= "widget-box ${queryParam.isCollapse?'collapsed':'' } no-margin" >
				<div class= "widget-header header-color-blue" >
					<h4>查询列表</h4>
					<div class= "widget-toolbar" >
						<a href= "#" data-action= "reload" onclick= "refreshCondition();" >
							<i class= "icon-refresh bigger-125" ></i>
						</a>
						<a href= "#" data-action= "collapse" >
							<i class= "icon-chevron-down bigger-125" ></i>
						</a>
					</div>
					<div class="widget-toolbar no-border">
						<button class="btn btn-xs btn-white bigger" onclick = "find();" >
							<i class="icon-search"></i>查询
						</button>
					</div>
				</div>
				<div class= "widget-body" >
					<div class= "widget-main" style= "background: #F0F8FF;" >
						<input id="wo_level_temp" type="hidden" value="${queryParam.wo_level }" />
			    		<input id="exception_type_temp" type="hidden" value="${queryParam.exception_type }" />
						<form class= "container-fluid" >
							<input id="uuid" name="uuid" type="hidden" value="${queryParam.uuid }" />
							<input id="sort_by" name="sort_by" type="hidden" />
			            	<input id="sort" name="sort" type="hidden" />
							<input id= "wo_process_status" name= "wo_process_status" type= "hidden" value= "${queryParam.wo_process_status }" />
							<div class= "row" style= "margin-bottom: 15px;" >
								<div class= "col-md-1" style= "text-align: right;" >
		                        	<label class= "no-padding-right blue" style= "white-space: nowrap;" >
		                           	 	创建时间区间&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" style= "white-space: nowrap;" >
			                    	<input id= "createTime_from" name= "createTime_from" type= "text"
			                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
			                    		value= "${queryParam.createTime_from }" />
			                    	<input id= "createTime_to" name= "createTime_to" type="text"
			                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
			                    		value= "${queryParam.createTime_to }" />
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "create_by" style= "white-space: nowrap;" >
	                           			创建者&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<input id= "create_by" name= "create_by" type= "text" class= "form-control" value= "${queryParam.create_by }" />
			                    </div>
							</div>
							<div class= "row form-group" >
								<div class= "col-md-1" style= "text-align: right;" >
									<label class= "no-padding-right blue" for= "wo_no" style= "white-space: nowrap;" >
	                     					工单号&nbsp;: 
			                        </label>
								</div>
								<div class= "col-md-2" >
		                        	<input id= "wo_no" name= "wo_no" type= "text" class= "form-control" value= "${queryParam.wo_no }" />
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "wo_type" style= "white-space: nowrap;" >
		                           		工单类型&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<select id= "wo_type" name= "wo_type" data-edit-select= "1" onchange= "shiftType('wo_type', {'levelId': 'wo_level', 'exceptionId': 'exception_type', 'check_flag': '0' });" >
			                            <option value= "" >---请选择---</option>
			                            <c:forEach items= "${wo_type }" var= "wo_type" >
			                                <option value= "${wo_type.code }" ${wo_type.code==queryParam.wo_type?"selected='selected'":"" } >${wo_type.name }</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "wo_level" style= "white-space: nowrap;" >
	                            		工单级别&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<select id= "wo_level" name= "wo_level" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                        </select>
			                    </div>
			                    <div class= "col-xs-1" style= "text-align: right;" >
			                        <label class= "no-padding-right blue" for= "exception_type" style= "white-space: nowrap;" >
		                            	异常类型&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-xs-2" >
			                        <select id= "exception_type" name= "exception_type" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                        </select>
			                    </div>
							</div>
							<div class= "row" style= "margin-bottom: 15px;" >
								<div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "carrier" style= "white-space: nowrap;" >
		                            	物流服务商&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<select id= "carrier" name= "carrier" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <c:forEach items= "${carrier }" var= "carrier" >
			                                <option value= "${carrier.transport_code }" ${carrier.transport_code==queryParam.carrier?"selected='selected'":"" } >${carrier.transport_name }</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
						 			<label class= "no-padding-right blue" for= "express_number" style= "white-space: nowrap;" >
		                            	快递单号&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<input id= "express_number" name= "express_number" type= "text" class= "form-control" value= "${queryParam.express_number }" />
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "warehouse" style= "white-space: nowrap;" >
	                            		仓库&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<select id= "warehouse" name= "warehouse" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <c:forEach items= "${warehouse }" var= "warehouse" >
			                                <option value= "${warehouse.warehouse_code }" ${warehouse.warehouse_code==queryParam.warehouse?"selected='selected'":"" } >${warehouse.warehouse_name }</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
		                        	<label class= "no-padding-right blue" style= "white-space: nowrap;" >
		                           	 	出库时间区间&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" style= "white-space: nowrap;" >
			                    	<input id= "transportTime_from" name= "transportTime_from" type= "text"
			                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
			                    		value= "${queryParam.transportTime_from }" />
			                    	<input id= "transportTime_to" name= "transportTime_to" type="text"
			                    		style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
			                    		value= "${queryParam.transportTime_to }" />
			                    </div>
							</div>
							<div class= "row form-group" >
						 		<div class= "col-md-1" style= "text-align: right;" >
									<label class= "no-padding-right blue" for= "store" style= "white-space: nowrap;" >
	                            		店铺&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<select id= "store" name= "store" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <c:forEach items= "${store }" var= "store" >
			                                <option value= "${store.store_code }" ${store.store_code==queryParam.store?"selected='selected'":"" } >${store.store_name }</option>
			                            </c:forEach>
			                        </select>
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "platform_number" style= "white-space: nowrap;" >
		                            	平台订单号&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<input id= "platform_number" name= "platform_number" type= "text" class= "form-control" value= "${queryParam.platform_number }" />
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "related_number" style= "white-space: nowrap;" >
		                            	相关单据号&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<input id= "related_number" name= "related_number" type= "text" class= "form-control" value= "${queryParam.related_number }" />
			                    </div>
							</div>
							<div class= "row form-group" >
								<div class= "col-md-1" style= "text-align: right;" >
			                    	<label class= "no-padding-right blue" for= "allocated_by" style= "white-space: nowrap;" >
	                            		分配者&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                    	<input id= "allocated_by" name= "allocated_by" type= "text" class= "form-control" value= "${queryParam.allocated_by }" />
			                    </div>
							</div>
							<div class= "row form-group" >
								<div class= "col-md-1" style= "text-align: right;" >
			                        <label class= "no-padding-right blue" for= "claim_status" style= "white-space: nowrap;" >
			                           	 索赔状态&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                        <select id= "claim_status" name= "claim_status" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <option value= "0" ${"0"==queryParam.claim_status?"selected='selected'":"" } >暂作理赔</option>
			                            <option value= "1" ${"1"==queryParam.claim_status?"selected='selected'":"" } >索赔成功</option>
			                            <option value= "2" ${"2"==queryParam.claim_status?"selected='selected'":"" } >索赔失败</option>
			                        </select>
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                        <label class= "no-padding-right blue" for= "claim_number" style= "white-space: nowrap;" >
		                           		 索赔编码&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                        <input id= "claim_number" name= "claim_number" type= "text" class= "form-control" value= "${queryParam.claim_number }" />
			                    </div>
			                    <div class= "col-md-1" style= "text-align: right;" >
			                        <label class= "no-padding-right blue" for= "claim_type" style= "white-space: nowrap;" >
			                           	 索赔分类&nbsp;: 
			                        </label>
			                    </div>
			                    <div class= "col-md-2" >
			                        <select id= "claim_type" name= "claim_type" data-edit-select= "1" >
			                            <option value= "" >---请选择---</option>
			                            <option value= "1" ${"1"==queryParam.claim_type?"selected='selected'":"" } >遗失</option>
			                            <option value= "2" ${"2"==queryParam.claim_type?"selected='selected'":"" } >破损</option>
			                            <option value= "3" ${"3"==queryParam.claim_type?"selected='selected'":"" } >错发</option>
			                            <option value= "4" ${"4"==queryParam.claim_type?"selected='selected'":"" } >补偿</option>
			                        </select>
			                    </div>
	  						</div>
						</form>
					</div>
				</div>
			</div>
			<p class= "alert alert-info no-margin-bottom" >
				<button class= "btn btn-white" onclick= "find();" ><i class= "icon-refresh" ></i>刷新</button>
				<button class= "btn btn-white" onclick= "toWOAddForm();" ><i class= "icon-plus" ></i>新增</button>
				<button class= "btn btn-white" onclick= "download();" ><i class= "icon-download" ></i>导出</button>
				<button onclick= "$('#woPause_form').modal();" class= "btn btn-white" ${queryParam.wo_process_status == "PRO"? "": "style= 'display: none'" } ><i class= "icon-bolt" ></i>挂起</button>
				<button onclick= "operateStatus('${role }', '1', 'RECOVER');" class= "btn btn-white" ${queryParam.wo_process_status == "PAU"? "": "style= 'display: none'" } ><i class= "icon-arrow-right" ></i>取消挂起</button>
				<button onclick= "operateStatus('${role }', '1', 'CANCEL');" class= "btn btn-sm btn-danger" ${queryParam.wo_process_status == "CCD" || queryParam.wo_process_status == "FIN"? "style= 'display: none'": "" } ><i class= "icon-circle" ></i>取消</button>
				<button id="zfbutton" class= "btn btn-white"  style= '${queryParam.wo_process_status == "PRO"? "": "display: none" }'  onclick= "toBatchZf();"><i class= "icon-download" ></i>批量转发</button>
			</p>
			<jsp:include page= "/work_order/wo_management/wo_management_platform_list.jsp" flush= "true" />
		</div>
		<jsp:include page= "/work_order/wo_management/wo_management_add.jsp" flush= "true" />
		<jsp:include page= "/work_order/wo_management/wo_management_pause.jsp" flush= "true" />
		<jsp:include page= "/work_order/wo_management/wo_waybillGenerated.jsp" flush= "true" />



		<!-- 列表配置弹窗 -->
		<div id="import_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" style="width:800px;" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
						<h4 class="modal-title">批量转发</h4>
					</div>
					<div class= "col-xs-12 row form-group" >
						<hr>
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue" for= "groups" style= "white-space: nowrap;" >
								<h4><strong>转发工单</strong></h4>
							</label>
						</div>
					</div>
					<div class= "col-xs-12 row form-group" >
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue" for= "groups" style= "white-space: nowrap;" >
								组别&nbsp;:
							</label>
						</div>
						<div class= "col-xs-2" >
							<select id= "groups" name= "groups" data-edit-select= "1" checkType= "{}" onchange= "checkValue($(this));isQa();" >
								<option value= "" >---请选择---</option>
								<c:forEach items= "${allgroups }" var= "groups" >
									<option value= "${groups.id }"  style="${groups.is_qa }">
											${groups.group_name }
									</option>
								</c:forEach>
							</select>
						</div>
						<div class= "alterCheckInfo col-xs-2" validation= "0" >
						</div>
						<div class= "col-xs-2" style= "text-align: right;" >
							<label class= "no-padding-right blue" for= "account" style= "white-space: nowrap;" >
								员工&nbsp;:
							</label>
						</div>
						<div class= "col-xs-2" >
							<select id= "account" name= "account"   data-edit-select= "1" checkType= "{'NOTNULL': '不能为空'}" onchange= "checkValue($(this));" >
								<option value= "" >---请选择---</option>
							</select>
						</div>
						<div class= "alterCheckInfo col-xs-2" validation= "0" >
						</div>
					</div>
					<div class="modal-footer" style="text-align: center">
						<button id='upload' type="button" class="btn btn-primary" onclick="saveZf()" >
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
<script>
    var checked =[];
    function removeStyle() {
        $("#zfbutton").removeAttr("style");

    }
    function addStyle() {
        $("#zfbutton").attr("style","display: none");
    }
	function toBatchZf() {
        $("input:checkbox[name='ckb']:checked").each(function() {
       // 遍历name=test的多选框
                checked.push($(this).val());
        });
        if(checked.length==0){
            alert("请选择至少一个已处理类型的工单");
            return;
        }
        $('#import_form').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide()
    }

    //异动工单
    function saveZf() {
        if(!confirm("是否转发工单？")) {
            return;

        }
        loadingStyle();
        var account = $("#account").val();
		var group = $("#groups").val();
		if(group==null||group==""){
		    alert("请选择组");
		    return;
		}
        var isQa = $('#groups').find("option:selected").attr("style");
		var event = "zfworkorder";
		if(isQa!="1"){
            if(account==null||account==""){
                alert("请选择员工");
                return;
            }
		}else{
            account =group;//转发到Qa组
            event = "zfworkorderqazu";
		}
            $.ajax({
                type: "POST",
                url: "/BT-LMIS/control/workOrderManagementController/zfWorkOrder.do",
                dataType: "json",
                data: {
                    "event": event,
                    "wo_ids": checked.toString(),
                    "account": account
                },
                success: function(result) {
                    alert(result.result_content);
                    if(result.result_code == "SUCCESS") {
                        // 隐藏当前弹窗
                        $("#import_form").modal("hide");
                    }
                    // 解除旋转
                    cancelLoadingStyle();
                    find();//刷新列表

                },
                error: function() {
                    // 解除旋转
                    cancelLoadingStyle();

                }

            });
    }
    function isQa() {
      var isQa = $('#groups').find("option:selected").attr("style");
      if(isQa=="1"){
          $("#account").next().attr("disabled", "disabled");
	  }else{
          $("#account").next().attr("disabled", false);
	  }
    }
</script>
</html>