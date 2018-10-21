<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/templet/common.jsp"%>
		<title>工单新增</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<script type="text/javascript" src="<%=basePath%>/work_order/wo_platform_store/js/main.js"></script>
		<script>
			JQueryUploadHelper.SESSIONID = "<%=session.getId()%>";
			$(document).ready(JQueryUploadHelper.init);
			$(function(){
				// NGINX配置地址赋值
				$.ajax({
					type: "POST",
			      	url: "/BT-LMIS/control/commonController/nginxURL.do",
			        dataType: "json",
			        success: function (result) {
			        	FileUpload= result.FileUpload;
			        	FileDown= result.FileDown;
			        }
				});
				getFileList();
			});
			$("#ms").multipleSelect({
	            filter: true
	        });
		    $(function() {
		        $('#ms').change(function() {
		            console.log($(this).val());
		        }).multipleSelect({
		            width: '100%'
		        });
		    });
		</script>
	</head>
	<body>
		<div class="page-header" align="left">
			<h1>
				工单${empty workOrderStore.id ? "新增" : "编辑" }
				<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="backDress();">
					<i class="ace-icon fa fa-undo grey bigger-120">
						返回
					</i>
				</button>
				<button type="button" class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="saveTemporary(1);">
					<i class="ace-icon fa fa-save green bigger-120">
						暂存
					</i>
				</button>
				<button type="button" id="saveTemporary" class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width" onclick="saveTemporary(0);">
					<i class="ace-icon fa fa-paper-plane-o blue bigger-120">
						提交
					</i>
				</button>
				<button ${empty workOrderStore.id ? "style='display: none;'" : "" } type="button" class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width" onclick="delTempWorkOrder('${workOrderStore.id }');">
					<i class="ace-icon fa fa-trash red bigger-120">
						删除
					</i>
				</button>
			</h1>
		</div>
		<input id="fileName_common" name="fileName_common" value="${workOrderStore.accessory}" type="hidden" />
		<input id="sel_flag" name="sel_flag" value="${sel_flag}" type="hidden" />
		<input id="orderFlag" name="orderFlag" value="0" type="hidden" />
		<input id="error_type_tem" name="error_type_tem" value="${workOrderStore.errorTypeDisplay}" type="hidden" />
		<input id="wo_id_form" name="wo_id_form" value="${workOrderStore.id}" type="hidden" />
		<input id="version" name="version" value="${workOrderStore.version}" type="hidden" />
		<div class="row">
			<div class="col-md-12">
				<div class="container">
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="process_department">处理部门&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-3 no-padding">
							<select id="process_department" name="process_department" data-edit-select="1" onchange="shiftProcessDepartment('#process_department',{'woTypeSelector': '#wo_type_display', 'woTypeStatus': '1', 'checkFlag': '0' });">
								<option value="">---请选择---</option>
								<c:if test="${groupType ==0 }">
									<option value="0" ${0 ==  workOrderStore.processDepartment ? "selected = 'selected'" : "" }>物流中心</option>
								</c:if>
								<option value="1" ${1 ==  workOrderStore.processDepartment ? "selected = 'selected'" : "" }>销售运营部</option>
							</select>
						</div>
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="wo_type_display">工单类型&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-3 no-padding">
							<select id="wo_type_display" name="wo_type_display" data-edit-select="1" onchange="shiftWoType('#wo_type_display', {'errorTypeSelector': '#error_type_display', 'errorTypeStatus': '1', 'checkFlag': '0' });">
								<option value="">---请选择---</option>
								<c:forEach var="woType" items="${woType }">
									<option value="${woType.code}" ${woType.code ==  workOrderStore.woType ? "selected = 'selected'" : "" }>${woType.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="error_type_display">
								<%-- <c:if test="${groupType ==0 }">异常类型</c:if>
								<c:if test="${groupType ==1 }"> --%>工单子类型<%-- </c:if> --%>
								&nbsp;:</label>
						</div>
						<div class="col-md-3 no-padding">
							<select id="error_type_display" name="error_type_display" data-edit-select="1">
								<option value="">---请选择---</option>
								<c:forEach var="errorType" items="${errorType }">
									<option value="${errorType.id}" ${errorType.id ==  workOrderStore.errorType ? "selected = 'selected'" : "" }>${errorType.type_name }</option>
								</c:forEach>
			                </select>
						</div>
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="title">标题&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-3 no-padding input-group">
							<input class="form-control" id="title" name="title" type="text" value="${workOrderStore.title}">
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="problem_store_display">问题所属店铺&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-3 no-padding">
							<select id="problem_store_display" name="problem_store_display" data-edit-select="1">
								<option value="">---请选择---</option>
									<c:forEach var="store" items="${storeList}">
										<c:choose>
											<c:when test="${!empty workOrderStore.problemStore}">
												<option value="${store.store_code}" ${store.store_code ==  workOrderStore.problemStore ? "selected = 'selected'" : "" }>${store.store_name}</option>
											</c:when>
											<c:otherwise>
												<option value="${store.store_code}" ${store.store_code ==  storeCode ? "selected = 'selected'" : "" }>${store.store_name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
			                </select>
						</div>
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="operation_system">操作系统&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-3 no-padding">
							<select id="operation_system" name="operation_system" data-edit-select="1">
								<option value="">---请选择---</option>
								<option value="OMS" ${"OMS" ==  workOrderStore.operationSystem ? "selected = 'selected'" : "" }>OMS</option>
								<option value="PACS" ${"PACS" ==  workOrderStore.operationSystem ? "selected = 'selected'" : "" }>PACS</option>
								<option value="WMS" ${"WMS" ==  workOrderStore.operationSystem ? "selected = 'selected'" : "" }>WMS</option>
								<option value="BI" ${"BI" ==  workOrderStore.operationSystem ? "selected = 'selected'" : "" }>BI</option>
								<option value="码栈" ${"码栈" ==  workOrderStore.operationSystem ? "selected = 'selected'" : "" }>码栈</option>
								<option value="非系统问题" ${"非系统问题" ==  workOrderStore.operationSystem ? "selected = 'selected'" : "" }>非系统问题</option>
			                </select>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="related_number">相关单据号(非销售单)&nbsp;:</label>
						</div>
						<div class="col-md-3 no-padding input-group">
							<input class="form-control" id="related_number" name="related_number" type="text" value="${workOrderStore.relatedNumber}">
						</div>
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="expectation_process_time">期望处理时间&nbsp;:</label>
						</div>
						<div class="col-md-3 no-padding">
							<div class="col-md-11 no-padding">
								<input id="expectation_process_time" name="expectation_process_time" style="width:110%;height:34px;" 
								value="${expectationProcessTimeStr}" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
							</div>
						</div>
						<%-- <div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="operation_system">操作系统&nbsp;:</label>
						</div>
						<div class="col-md-3 no-padding" align="center">
								<input type="checkbox" name="operation_system1" value=",OMS" checked="checked" ${"OMS" ==  workOrderStore.operationSystem ? "checked='checked'" : "" } onclick="setChecked(1)"/><a class="blue" onclick="setChecked(1)">&nbsp;OMS&nbsp;&nbsp;</a>
								<input type="checkbox" name="operation_system2" value=",PACS" ${"PACS" ==  workOrderStore.operationSystem ? "checked='checked'" : "" } onclick="setChecked(2)"/><a class="blue" onclick="setChecked(2)">&nbsp;PACS&nbsp;&nbsp;</a>
								<input type="checkbox" name="operation_system3" value=",WMS" ${"WMS" ==  workOrderStore.operationSystem ? "checked='checked'" : "" } onclick="setChecked(3)"/><a class="blue" onclick="setChecked(3)">&nbsp;WMS&nbsp;&nbsp;</a>
						</div> --%>
					</div>
					
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="related_number">所属组别&nbsp;<span class="red">*</span>:</label>
						</div>
						
						<div class="col-md-3 no-padding input-group">
							<select id="create_group" name="create_group" data-edit-select="1">
								<c:if test="${groupListSize>1 }"> <!--这里判断拿到长度并进行判断--> 
									<option value="">---请选择---</option>
									<c:forEach var="groupList" items="${groupList }">
										<option value="${groupList.groupId}" ${groupList.groupId ==  workOrderStore.createByGroup ? "selected = 'selected'" : "" }>${groupList.groupNmae }</option>
									</c:forEach>
								</c:if> 
								<c:if test="${groupListSize<=1 }"> <!--这里判断拿到长度并进行判断--> 
									<c:forEach var="groupList" items="${groupList }">
										<option value="${groupList.groupId}" ${groupList.groupId ==  workOrderStore.createByGroup ? "selected = 'selected'" : "" }>${groupList.groupNmae }</option>
									</c:forEach>
								</c:if>
			                </select>
						</div>
						
						<%-- <div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="related_number">关联工单&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-3 no-padding input-group">
							<select id="ms" multiple="multiple" style="width: 100%">
									<c:forEach var="workOrder" items="${workOrderList}">
										<option value="${workOrder.wo_no}" ${workOrder.id ==  workorderid ? "selected = 'selected'" : "" } ondblclick="test()">${workOrder.wo_no}</option>
									</c:forEach>
					        </select>
						</div> --%>
						
						<div class="col-md-2 no-padding-left search-title"  align="right">
							<a  onclick="toWoAssociationMain();" id="toWoAssociation">
								<i class="ace-icon fa fa-exchange yellow">
									<span style="text-decoration: underline; color: red">
										关联工单
									</span>
								</i>
							</a>
						</div>
						<div class="col-md-3 no-padding input-group">
							<input class="form-control" name="woNoLOG" id="woNoLOG2" type="text" value="${workOrderStore.relatedWoNo}" readonly="readonly"/>
						</div> 
						
						<!-- <div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue"></label>
						</div>
						<div class="col-md-3 no-padding input-group">
				        	<button  onclick="toWoAssociation();">
								<i class="ace-icon fa fa-search yellow bigger-120">
									关联工单
								</i>
							</button>
						</div> -->
						
					</div>
					
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="remark">问题描述&nbsp;<span class="red">*</span>:</label>
						</div>
						<div class="col-md-8 no-padding">
							<textarea id="remark" class="form-control" style="height: 120px; resize: none;">${workOrderStore.issueDescription }</textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="platform_id">平台单号/快递单号&nbsp;:</label>
						</div>
						<div class="col-md-3 no-padding input-group">
							<input class="form-control" id="platform_id" name="platform_id" type="text" onkeydown="searchWaybill(event);">
					        <span class="input-group-btn">
					        	<button id="search_btn" ${flag == '1' && records != '[]' ? "style= 'display: none'" : '' } type="button" class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width" onclick="searchWaybill(null);">
									<i class="ace-icon fa fa-search yellow bigger-120">
										搜索
									</i>
								</button>
								<button id="cancelBan_btn" ${flag == '0' || (flag == '1' && records == '[]') ? "style= 'display: none'" : '' } type="button" class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width" onclick="cancelBan();">
									<i class="ace-icon fa fa-gg yellow bigger-120">
										取消绑定
									</i>
								</button>
					        </span>
						</div>
					</div>
					<div class="row form-group">
						<div class="col-md-2"></div>
						<div id="add_list" class="col-md-8 no-padding" style="overflow-y: hidden;">
							<jsp:include page="/work_order/wo_platform_store/add_list.jsp" flush="true" />
						</div>
					</div>
					
					<div class="row form-group">
						<div class="col-md-2 no-padding-left search-title" align="right">
							<label class="blue" for="fileList">附件列表&nbsp;: </label>
						</div>
						<div class="col-md-8 no-padding">
							<div id="fileDiv" style="width: 100%; height: 150px; overflow: auto; border: 1px solid #e5e5e5;">
								<div id="fileQueue" style="width: 100%;"></div>
								<ims></ims>
							</div>
							<input id="myFile" name="myFile" type="file" multiple="true" onselect="" />
							<hr>
							<!-- <button onclick="javascript:$('#myFile').uploadify('cancel',$('.uploadifive-queue-item').first().data('file'))" class="button" style="float: right">取消上传</button>
							<button onclick="javascript:$('#myFile').uploadify('upload','*')" class="button" style="float: right;">上传</button> -->
							<style>
								.button{
									display:block;
									width: 120px;
									height: 34px;	
									border-width:1px;
									background-color: #e5e5e5;
									color: black;
									border: 1px solid white;
									-moz-border-radius: 15px;      /* Gecko browsers */
									-webkit-border-radius: 15px;   /* Webkit browsers */
									border-radius:15px;            /* W3C syntax */
									line-height: 34px;
									text-decoration: none;
								}
								#fileDiv a:hover{
							  		text-decoration:none;
								}
							</style>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="platformId_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content" style="border: 3px solid #394557;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close" >
							<span aria-hidden="true" onclick="clearText();">×</span>
						</button>
						<h4 id="formLabel" class="modal-title">平台订单号</h4>
					</div>
					<div id="modal-body" class="modal-body">
						<input id="warehouse_name" name="warehouse_name" type="hidden" />
						
						<div class="form-group">
							<label class="blue">运单号&nbsp;:</label>
							<input id="waybill_add" name="manhours" class="form-control" type="text" />
						</div>
						<div class="form-group">
							<label class="blue">平台订单号&nbsp;:</label> 
							<input id="platform_id_add" name="allocated" class="form-control" type="text" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" onclick= "saveTemporary(2);">
							<i class="icon-save" aria-hidden="true"></i>确定提交
						</button>
					</div>
				</div>
			</div>
		</div>
		<div id="model_content">
			<jsp:include page="/work_order/wo_platform_store/package_list.jsp" flush="true" />
		</div>
		<jsp:include page= "/work_order/wo_platform_store/wo_waybillGenerated.jsp" flush= "true" />
	</body>
</html>