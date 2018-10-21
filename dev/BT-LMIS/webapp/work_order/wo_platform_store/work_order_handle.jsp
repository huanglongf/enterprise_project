<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<title>工单处理</title>
		<meta charset="UTF-8">
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<%@ include file="/templet/common.jsp" %>
		<%@ taglib prefix="func" uri="/WEB-INF/tld/func.tld" %>
		
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>css/table.css" />
		<link type="text/css" rel="stylesheet" media="all" href="<%=basePath %>work_order/wo_platform_store/css/work_order_handle.css" />
		
		<script type="text/javascript" src="<%=basePath %>work_order/wo_management/js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>work_order/wo_platform_store/js/work_order_handle.js"></script>
		<script type="text/javascript">
			JQueryUploadHelper.SESSIONID = "<%=session.getId()%>";
			JQueryUploadHelper.init();
			$(function() {
				synchronizeHeight();
				$(".pinal-left").resize(function(){
					synchronizeHeight();
				})
				var relatedWoNo = $("#relatedWoNoDisplay").text();
				var arr=relatedWoNo.split(",");
				if(arr.length>1){
					$("#relatedWoNoDisplay").text(arr[0]+"等"+arr.length+"个工单")
				}
			})
			function synchronizeHeight() {
				$("#timeline").height($(".pinal-left").height() - 36);
				$(".tab-pane").height($("#timeline").height());
			}
		</script>
		<style>
			table.filetables {
				font-family: verdana, arial, sans-serif;
				font-size: 11px;
				color: #333333;
				border-width: 1px;
				border-color: #666666;
				border-collapse: collapse;
				width: 100%;
				height: 20px;
			}
			table.filetables th {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #87CEFA;
			}
			table.filetables th {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #EEB422;
			}
			table.filetables td {
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #666666;
				background-color: #ffffff;
			}
			.AutoNewline {
				Word-break: break-all;/*必须*/ 
			} 
		</style>
	</head>
	<body>
		<div class="page-header" align="left" ${readonly ? "style='display:none;'" : ""}>
			<h1>
				工单处理
				<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="goBack();">
					<i class="ace-icon fa fa-undo grey">
						返回
					</i>
				</button>
				<button ${gainFlag ? "" : "style='display:none;'" } type="button" class="btn btn-sm btn-white btn-primary btn-bold btn-round btn-width" onclick="empGetWorkOrder();">
					<i class="ace-icon fa fa-hand-lizard-o blue">
						获取
					</i>
				</button>
				<button   ${processFlag ? "" : "style='display:none;'" }   type="button" class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width" onclick="followUpRecordList();">
					<i class="ace-icon fa fa-map-pin yellow">
						跟进
					</i>
				</button>
				<button   ${processFlag ? "" : "style='display:none;'" }   type="button" class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width" onclick="$('#woProcess-model').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide();">
					<i class="ace-icon fa fa-cogs blue">
						处理
					</i>
				</button>
				<%-- <button ${processFlag ? "" : "style='display:none;'" } type="button" class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width" onclick="$('#woProcess-model').modal({backdrop: 'static', keyboard: false});$('.modal-backdrop').hide();">
					<i class="ace-icon fa fa-spinner fa-pulse fa-fw yellow"></i>
					
					<i class="ace-icon fa fa-cogs yellow">处理</i>
				</button> --%>
				 <button  type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="aKeyExport();downloadZip();">
					<i class="ace-icon fa fa-arrow-circle-o-down grey">
						导出
					</i>
				</button> 
				<button ${processFlag ? "" : "style='display:none;'" } type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" id="toWoAssociation" onclick="toWoAssociation();">
						<i class="ace-icon fa fa-exchange red">
							关联工单
						</i>
				</button>
			</h1>
		</div>
		<div class="col-md-12">
			<input type="hidden" id="woId" name="woId" value="${workOrderStore.id }" data-version="${workOrderStore.version }" />
			<input type="hidden" id="woNoMain" name="woNoMain" value="${workOrderStore.woNo }"/>
			<div class="col-md-6 container pinal-left">
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单号&nbsp;:</label>
					<label class="col-md-5 no-padding no-margin">${workOrderStore.woNo }<span>${split_list_show?"":'【子单】'}</span></label>
					<a class="col-md-2 no-padding-left no-padding-top no-margin red" onclick="loadUpdateItem()" ${split_list_show && showUpdate && processFlag  ? "" : "style='display:none;'" }>修改工单</a>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单状态&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${func:getDictLabel('wo_store_master_wo_status', workOrderStore.woStatus) }</label>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">处理部门&nbsp;:</label>
					<input id="processDepartment" type="hidden" value="${workOrderStore.processDepartment }"/>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.processDepartment == 0 ? "物流部" : "销售运营部" }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单类型&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.woTypeDisplay }</label>
					<input id="woType" type="hidden" value="${workOrderStore.woType }"/>
					<input id="woTypeDisplay" type="hidden" value="${workOrderStore.woTypeDisplay }"/>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">
						<%-- <c:if test="${groupType ==0 }">异常类型</c:if>
						<c:if test="${groupType ==1 }"> --%>工单子类型<%-- </c:if> --%>
					&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.errorTypeDisplay }</label>
					<input id="errorType" type="hidden" value="${workOrderStore.errorType }"/>
					<input id="errorTypeDisplay" type="hidden" value="${workOrderStore.errorTypeDisplay }"/>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">标题&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.title }</label>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">问题所属店铺&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.problemStoreDisplay }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">平台订单号&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.platformNumber=='0'||workOrderStore.platformNumber=='-99999'?"":workOrderStore.platformNumber }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">运单号&nbsp;:</label>
					<label class="col-md-9 no-padding no-margin" style="word-wrap: break-word; word-break: keep-all;">${workOrderStore.waybill=='0'||workOrderStore.waybill=='-99999'?"":workOrderStore.waybill }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">相关单据号（非销售单）&nbsp;:</label>
					<label class="col-md-9 no-padding no-margin">${workOrderStore.relatedNumber }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">期望处理时间&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${expectationProcessTimeStr }</label>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">操作系统&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.operationSystem }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单关联&nbsp;:</label>
					<label class="col-md-6 no-padding no-margin"><a id="relatedWoNoDisplay" onclick="toShowAssociationRelated();" >${workOrderStore.relatedWoNo }</a></label>
					<input type="hidden" id="relatedWoNo" value="${workOrderStore.relatedWoNo}">
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">问题描述&nbsp;:</label>
					<textarea class="col-md-9 no-padding no-margin" readonly="readonly" style="height:150px;text-align: left;">${workOrderStore.issueDescription }</textarea>
				</div>
				<div class="row">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">附件列表&nbsp;:</label>
					<table class="col-md-9 no-padding no-margin" style="display:block;height:150px;margin: 0px auto 0px auto;border:1px #D5D5D5 solid;overflow: auto;">
						<tbody style="display: inline-block;width:100%;">
						<tr style="display: inline-block;width:100%;">
							<td class="no-padding" style="display:inline-block;width:99%;max-width:99%;text-align: center;">
								<a onclick="OneKeyDown('${workOrderStore.accessory}');">一键下载</a>
							</td>
						</tr>
						<c:forEach items="${fns:split(workOrderStore.accessory,'_') }" var="accessory" varStatus="status">
							<c:set var="accessory_content" value="${fns:split(accessory,'#') }"></c:set>
							<c:if test="${not empty accessory_content[2] }">
								<c:set var="originName" value="${accessory_content[1] }${accessory_content[2] }"></c:set>
								<tr style="display: inline-block;width:100%;">
									<td class="no-padding" style="display:inline-block;width:99%;max-width:99%;text-align: center;">
										<a onclick="accessorySSDownload('${accessory_content[0]}','${originName}');">${originName}</a>
									</td>
								</tr>
							</c:if>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-6 pinal-right">
				<ul class="nav nav-tabs tab-color-blue" role="tablist">
					<li role="presentation" class="active"><a href="#first_tab" role="tab" data-toggle="tab">处理记录</a></li>
					<li role="presentation"><a href="#third_tab" role="tab" data-toggle="tab">订单列表</a></li>
					<li role="presentation" ${split_list_show ? "" : "style='display:none;'" }><a href="#second_tab" role="tab" data-toggle="tab">拆分列表</a></li>
				</ul>
				<div class="tab-content no-padding">
					<div role="tabpanel" class="tab-pane active" id="first_tab">
						<div id="first_tab_body" style="border:0px;padding:0px;width:100%;overflow: visible;">
							<iframe id="timeline" src="${root }control/workOrderPlatformStoreController/toTimeLine.do?woId=${workOrderStore.id }" style="border:0px;padding:0px;width:100%;overflow: visible;"></iframe>
							<%-- <jsp:include page="/work_order/wo_platform_store/timeline2.jsp" flush= "true" /> --%>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="third_tab">
						<div id="third_tab_body" style="border:0px;padding:0px;width:100%;overflow: visible;">
							<jsp:include page="/work_order/wo_platform_store/detail_list.jsp" flush= "true" />
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="second_tab">
						<div id="third_tab_body" style="border:0px;padding:0px;width:100%;overflow: visible;">
							<jsp:include page="/work_order/wo_platform_store/split_list.jsp" flush= "true" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 工单处理弹窗 -->
		<div id="woProcess-model" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
		    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick= ""><span aria-hidden="true">×</span></button>
		                <h4 class="modal-title">工单处理</h4>
		            </div>
		            <div class="modal-body container">
		            	<div class="row form-group">
		            		<div class="col-md-2" align="right">
		            			<label class="blue" for="processInfo">处理意见&nbsp;:</label>
	            			</div>
							<div class="col-xs-10">
								<textarea class="form-control" id="processInfo" name="processInfo" rows="7"></textarea>
							</div>
		            	</div>
		            	<div class="row form-group">
		            		<div class="col-md-2" align="right">
								<label class="blue" for="fileQueue">附件列表&nbsp;:</label>
							</div>
				      		<div class="col-xs-10">
						 		<div id="fileDiv" style="width:100%; height:150px; overflow:auto; border:1px solid #e5e5e5;">
							  		<div id="fileQueue" style="width: 100%;"></div>	
								  	<ims></ims>
								</div>
								<br>
								<input id="myFile" name="myFile" type="file" multiple="true" onselect=""/>
								<hr>
								<!-- <button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"  onclick="javascript:$('#myFile').uploadify('upload','*')">
									<i class="ace-icon fa grey">
										上传
									</i>
								</button>
								&nbsp;
					     		<button type="button" onclick= "javascript:$('#myFile').uploadify('cancel',$('.uploadifive-queue-item').first().data('file'))"  
					     			class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width">
					     			<i class="ace-icon fa grey">
										取消上传
									</i>
								</button> -->
								<input id="fileName_common" name="fileName_common" type="hidden" />
					     	</div>
		            	</div>
		            	<div class="row form-group" ${groupType == 1 ? "" : "style='display:none;'" }>
		            		<div class="col-md-2 no-padding-left search-title" align="right">
								<label class="blue" for="wo_type_display">工单类型确认&nbsp;:</label>
							</div>
							<div class="col-md-3 no-padding">
								<select id="woTypeRe" name="woTypeRe" data-edit-select="1" onchange="shiftWoType('#woTypeRe', {'errorTypeSelector': '#errorTypeRe', 'errorTypeStatus': '1', 'checkFlag': '0' });">
									<option value="">---请选择---</option>
									<c:forEach var="woType" items="${woTypeRe }">
										<option value="${woType.code}">${woType.name }</option>
									</c:forEach>
								</select>
							</div>
						
							<div class="col-md-2 no-padding-left search-title" align="right">
								<label class="blue" for="errorTypeRe">
									<%-- <c:if test="${groupType ==0 }">异常类型</c:if>
									<c:if test="${groupType ==1 }"> --%>工单子类型确认<%-- </c:if> --%>
								&nbsp;:</label>
							</div>
							<div class="col-md-3 no-padding">
								<select id="errorTypeRe" name="errorTypeRe" data-edit-select="1">
									<option value="">---请选择---</option>
									<c:forEach var="errorType" items="${errorType }">
										<option value="${errorType.id}">${errorType.type_name }</option>
									</c:forEach>
				                </select>
							</div>
		            	</div>
		            	<div class="row form-group" ${soReply ? "" : "style='display:none;'" }>
		            		<div class="col-md-2" align="right">
								<label class="blue" for="errorFlag">异常工单&nbsp;:</label>
							</div>
							<div class="col-md-10">
		                    	<input id="errorFlag" type="checkbox" class="ace ace-switch ace-switch-5"  ${workOrderStore.errorFlag == 1 ? "checked='checked' disabled='disabled'" : "" }/>
								<span class="lbl"></span>
		                    </div>
		            	</div>
		            </div>
		            <div class="modal-footer">
		            	<button data-dismiss="modal" type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" ${groupType==1?"onclick='loadTeam();'":"onclick='loadUsers();'"} >
							<i class="ace-icon fa fa-mail-reply grey">
								转发
							</i>
						</button>
						&nbsp;
						<button ${split_list_show ? "" : "style='display:none;'" } type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="loadAllTeam();">
							<i class="ace-icon fa fa-arrows grey">
								拆分
							</i>
						</button>
						&nbsp;
		            	<button ${split_list_show && groupType == 0 ? "" : "style='display:none;'" } type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="assist();">
							<i class="ace-icon fa fa-handshake-o grey">
								协助
							</i>
						</button>
						&nbsp;
		            	<button ${split_list_show ? "" : "style='display:none;'" } type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="reply();">
							<i class="ace-icon fa fa-paper-plane-o grey">
								回复
							</i>
						</button>
						&nbsp;
						<button  ${!split_list_show || ownerGroupType == 1 || groupType == 0  ? "" : "style='display:none;'" }  type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="finish();">
							<i class="ace-icon fa fa-power-off grey">
								完结
							</i>
						</button>
						&nbsp;
						<button  ${!split_list_show || ownerGroupType == 1 || groupType == 0  ? "" : "style='display:none;'" }  type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="cancle();">
							<i class="ace-icon fa fa-trash grey">
								取消
							</i>
						</button>
		            </div>
		        </div>
		    </div>
		</div>
		<!-- 转发弹窗 -->
		<div id="transmit-model" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
		    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button id="transmit_close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                <h4 class="modal-title">转发</h4>
		            </div>
		            <div class="modal-body container">
		            	<div class="row" >
		            		<div ${groupType!=1?"style='display: none;'":""}>
		            		<div class="col-md-2 search-title" >
								<label class="blue" for="SOgroup">销售运营组别列表&nbsp;:</label>
							</div>
		                    <div class="col-md-4">
		                    	<div class="col-md-11 no-padding">
			                   		<select id="SOgroup" name="SOgroup" style="width:100%;" onchange="loadSOUser();">
			                   			<option value="">没有可选的组别</option>
			                   		</select>
		                   		</div>
		                    </div>
		                    </div>
		            		<div class="col-md-2 search-title" align="center">
								<label class="blue" for="employee">人员列表&nbsp;:</label>
							</div>
		                    <div class="col-md-4">
		                    	<div class="col-md-11 no-padding">
			                   		<select id="employee" name="employee" style="width:100%;">
			                   			<option value="">没有可选的人员</option>
			                   		</select>
		                   		</div>
		                    </div>
		                </div>
		            </div>
		            <div class="modal-footer">
		            	<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="transmit();" >
							<i class="ace-icon fa fa-mail-reply grey">
								转发
							</i>
						</button>
		            </div>
		        </div>
		    </div>
		</div>
		<!-- 修改弹窗 -->
		<div id="update-model" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
		    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button id="update_close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                <h4 class="modal-title">修改</h4>
		            </div>
		            <div class="modal-body container">
		            	<div class="row form-group">
							<div class="col-md-2 no-padding-left search-title" align="right">
								<label class="blue" for="process_department">处理部门&nbsp;<span class="red">*</span>:</label>
							</div>
							<div class="col-md-3 no-padding">
								<select id="process_department" name="process_department" data-edit-select="1" onchange="shiftProcessDepartment('#process_department',{'woTypeSelector': '#wo_type_display', 'woTypeStatus': '1', 'checkFlag': '0' });">
									<option value="">---请选择---</option>
									<c:if test="${groupType ==0 }">
									<option value="0">物流中心</option>
									</c:if>
									<option value="1">销售运营部</option>
								</select>
							</div>
							<div class="col-md-2 no-padding-left search-title" align="right">
								<label class="blue" for="wo_type_display">工单类型&nbsp;<span class="red">*</span>:</label>
							</div>
							<div class="col-md-3 no-padding">
								<select id="wo_type_display" name="wo_type_display" data-edit-select="1" onchange="shiftWoType('#wo_type_display', {'errorTypeSelector': '#error_type_display', 'errorTypeStatus': '1', 'checkFlag': '0' });">
									<option value="">---请选择---</option>
									<c:forEach var="woType" items="${woType }">
										<option value="${woType.code}">${woType.name }</option>
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
										<option value="${errorType.id}">${errorType.type_name }</option>
									</c:forEach>
				                </select>
							</div>
						</div>
		            </div>
		            <div class="modal-footer">
		            	<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="updateWo();" >
								重新提交
						</button>
		            </div>
		        </div>
		    </div>
		</div>
		<!-- 拆分弹窗 -->
		<div id="split-model" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
		    <div class="modal-dialog modal-lg" style="width:800px;" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button id="split_close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                <h4 class="modal-title">拆分</h4>
		            </div>
		            <div class="modal-body container">
		            	<div class="row" >
		            		<div class="col-md-2 search-title" >
								<label class="blue" for="SOgroup_split">组别列表&nbsp;:</label>
							</div>
		                    <div class="col-md-4">
		                    	<div class="col-md-11 no-padding">
			                   		<select id="SOgroup_split" name="SOgroup_split" style="width:100%;" onchange="loadAllUser();">
			                   			<option value="">没有可选的组别</option>
			                   		</select>
		                   		</div>
		                    </div>
		            		<div class="col-md-2 search-title" align="center">
								<label class="blue" for="employee_split">人员列表&nbsp;:</label>
							</div>
		                    <div class="col-md-4">
		                    	<div class="col-md-11 no-padding">
			                   		<select id="employee_split" name="employee_split" style="width:100%;">
			                   			<option value="">没有可选的人员</option>
			                   		</select>
		                   		</div>
		                    </div>
		                </div>
		            </div>
		            <div class="modal-footer">
		            	<button type="button" class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" onclick="splitWO();" >
							<i class="ace-icon fa fa-arrows grey">
								拆分
							</i>
						</button>
		            </div>
		        </div>
		    </div>
		</div>
		<!--跟进列表  -->
		<div id= "followUpRecordList" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
		</div>
		<jsp:include page= "/work_order/wo_platform_store/wo_waybillGenerated3.jsp" flush= "true" />
		<jsp:include page= "/work_order/wo_platform_store/wo_waybillGenerated2.jsp" flush= "true" />
	
	</body>
</html>
