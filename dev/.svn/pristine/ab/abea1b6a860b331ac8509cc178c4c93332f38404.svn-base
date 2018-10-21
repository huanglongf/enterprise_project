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
			$(function() {
				synchronizeHeight();
				$(".pinal-left").resize(function(){
					synchronizeHeight();
				})
				var relatedWoNo = $("#relatedWoNo_label3").text();
				var arr=relatedWoNo.split(",");
				var str = "";
				if(arr.length>0){
					for(i=0;i<arr.length;i++){
						str = str+arr[i]+"\n";
					}
				}
				$("#relatedWoNo_label3").text(str);
			})
			function synchronizeHeight() {
				$("#timeline3").height($(".pinal-left").height() - 36);
				$(".tab-pane").height($("#timeline3").height());
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
		<div class="col-md-12">
			<input type="hidden" id="woId3" name="woId" value="${workOrderStore.id }" data-version="${workOrderStore.version }" />
			<div class="col-md-6 container pinal-left">
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单号&nbsp;:</label>
					<label class="col-md-6 no-padding no-margin">${workOrderStore.woNo }<span>${split_list_show?"":'【子单】'}</span></label>
				</div>	
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单状态&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${func:getDictLabel('wo_store_master_wo_status', workOrderStore.woStatus) }</label>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">处理部门&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.processDepartment == 0 ? "物流部" : "销售运营部" }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">工单类型&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.woTypeDisplay }</label>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">
						<c:if test="${groupType ==0 }">异常类型</c:if>
						<c:if test="${groupType ==1 }">工单子类型</c:if>
					&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.errorTypeDisplay }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">标题&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.title }</label>
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">问题所属店铺&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.problemStoreDisplay }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">平台订单号&nbsp;:</label>
					<label class="col-md-3 no-padding no-margin">${workOrderStore.platformNumber=='-99999'?"":workOrderStore.platformNumber }</label>
				</div>
				<div class="row form-group">
					<label class="col-md-3 no-padding-left no-padding-top no-margin blue" align="right">运单号&nbsp;:</label>
					<label class="col-md-9 no-padding no-margin" style="word-wrap: break-word; word-break: keep-all;">${workOrderStore.waybill=='-99999'?"":workOrderStore.waybill }</label>
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
					<label class="col-md-6 no-padding no-margin" id="relatedWoNo_label3" >${workOrderStore.relatedWoNo }</label>
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
					<li role="presentation" class="active"><a href="#first_tab3" role="tab" data-toggle="tab">处理记录</a></li>
					<li role="presentation"><a href="#third_tab3" role="tab" data-toggle="tab">订单列表</a></li>
					<li role="presentation" ${split_list_show ? "" : "style='display:none;'" }><a href="#second_tab3" role="tab" data-toggle="tab">拆分列表</a></li>
				</ul>
				<div class="tab-content no-padding">
					<div role="tabpanel" class="tab-pane active" id="first_tab3">
						<iframe id="timeline3" src="${root }control/workOrderPlatformStoreController/toTimeLine.do?woId=${workOrderStore.id }&readonly=2" style="border:0px;padding:0px;width:100%;overflow: visible;"></iframe>
					</div>
					<div role="tabpanel" class="tab-pane" id="third_tab3">
						<div id="third_tab_body3" style="border:0px;padding:0px;width:100%;overflow: visible;">
							<jsp:include page="/work_order/wo_platform_store/detail_list3.jsp" flush= "true" />
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="second_tab3">
						<div id="third_tab_body" style="border:0px;padding:0px;width:100%;overflow: visible;">
							<jsp:include page="/work_order/wo_platform_store/split_list3.jsp" flush= "true" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
