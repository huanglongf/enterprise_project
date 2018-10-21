<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 工单信息页面 -->
<script type="text/javascript">
	$(function(){
		cancelLoadingStyle();
		// 复制
		var clipboard=new Clipboard(".a-copy");
		
	});
	/* function OneKeyDown(accessory_list){
		if(!accessory_list){
			alert("无附件！");
			return;
		}
		$.ajax({
			url : root + 'control/workOrderManagementController/OneKeyDownload.do',
			type : 'post',
			data : {accessory_list:accessory_list,woId:'${wo_display.wo_no}'},
			dataType : 'json',
			success : function(resultData){
				if(resultData.code=='200'){
					alert("下载成功");
				}
			}
		}); 
	} */
	function OneKeyDown(accessory_list){
		if(!accessory_list){
			alert("无附件！");
			return;
		}
		var str = encodeURI(accessory_list);
		
		var str2 = str.replace(/#/g, '1a1b1c1d3e');
		
		window.location.href="/BT-LMIS/control/workOrderManagementController/OneKeyDownload.do?"
				+"woId="+'${wo_display.wo_no}'
				+"&accessory_list="+str2;
	}
	
	function cccp(){
		var b= $("#copyObject").text();
		copyToClipboard(b);
		
	}
	function copyToClipboard (text) {
	    /* if(text.indexOf('-') !== -1) {
	        let arr = text.split('-');
	        text = arr[0] + arr[1];
	    } */
	    var textArea = document.createElement("textarea");
	      textArea.style.position = 'fixed';
	      textArea.style.top = '0';
	      textArea.style.left = '0';
	      textArea.style.width = '2em';
	      textArea.style.height = '2em';
	      textArea.style.padding = '0';
	      textArea.style.border = 'none';
	      textArea.style.outline = 'none';
	      textArea.style.boxShadow = 'none';
	      textArea.style.background = 'transparent';
	      textArea.value = text;
	      document.body.appendChild(textArea);
	      textArea.select();

	      try {
	        var successful = document.execCommand('copy');
	        var msg = successful ? '成功复制到剪贴板' : '该浏览器不支持点击复制到剪贴板';
	       alert(msg);
	      } catch (err) {
	        alert('该浏览器不支持点击复制到剪贴板');
	      }

	      document.body.removeChild(textArea);
	}
</script>
<div class="row">
	<div class="col-md-12">
		<input id="wo_id" type="hidden" value="${wo_display.id }" />
		<input id="judgeType" type="hidden" value="${type }" />
		<div class="container" style="background:#F0F8FF;">
			<br>
			<div class="row">
	            <label class="col-xs-2 no-padding-right blue" style= "text-align:right;white-space:nowrap;">工单来源&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.wo_source }</label>
		        <label class="col-xs-2 no-padding-right blue" style= "text-align:right;white-space:nowrap;">创建时间&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.create_time }</label>
		        <label class="col-xs-2 no-padding-right blue" style= "text-align:right;white-space:nowrap;">创建者&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.create_by_display }</label>
		    </div>
		    <div class="row">
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">工单号&nbsp;:</label>
		        <label class="col-xs-2" >${wo_display.wo_no }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">工单分配状态&nbsp;:</label>
		        <label class="col-xs-2" >${wo_display.wo_alloc_status_display }</label>
		       	<label class="col-xs-2 no-padding-right blue" style="text-align:right; white-space:nowrap;">工单处理状态&nbsp;:</label>
		        <label class="col-xs-2" id="woProcessStatus_woDisplay">${wo_display.wo_process_status_display }</label>
		    </div>
		    <div class="row">
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">工单类型&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.wo_type_display }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">工单级别&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.wo_level_display }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">异常类型&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.exception_type }</label>
		    </div>
		    <div class="row">
		    	<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">物流服务商&nbsp;:</label>
		        <label class="col-xs-2" >${wo_display.carrier_display }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">快递单号&nbsp;:</label>
		        <label class="col-xs-2" style="cursor:pointer;" title="<c:if test="${wo_num>1 }">（已生成${wo_num }条工单）</c:if>">
	           		<a class="${wo_num> 1? 'red': '' }" onclick="ensureWorkOrderDetailSE(${wo_num }, '${wo_display.express_number }');">
	           			${wo_display.express_number }&nbsp;<c:if test="${wo_num>1 }">【${wo_num }】</c:if>
	           		</a>
	           	</label>
	           	<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">发货仓库&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.warehouse_display }</label>
		    </div>
		    <div class="row">
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">出库时间&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.transport_time }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">店铺&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.store_display }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">平台订单号&nbsp;:</label>
		       	<label class="col-xs-2">${wo_display.platform_number }</label>
		    </div>
		    <div class="row">
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">相关单据号&nbsp;:</label>
		       	<label class="col-xs-2">${wo_display.related_number }</label>
		       	<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">查询人&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.query_person }</label>
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right; white-space:nowrap;">分配者&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.allocated_by_display }</label>
		    </div>
		    <div class="row">
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">收件人&nbsp;:</label>
		       	<label class="col-xs-2">${wo_display.recipient }</label>
		       	<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">联系电话&nbsp;:</label>
		        <label class="col-xs-2">${wo_display.phone }</label>
		    </div>
			<div class="row">
				<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">错发商品数量&nbsp;:</label>
				<label class="col-xs-2">${wo_display.mistake_product_count }</label>
				<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">错发/漏发条形码&nbsp;:</label>
				<label class="col-xs-2">${wo_display.mistake_barcode }</label>
			</div>
		    <div class="row">
		    	<label class="col-xs-2 no-padding-right blue" style="text-align:right; white-space:nowrap;">详细地址&nbsp;:</label>
		        <label class="col-xs-6">${wo_display.address }</label>
		        
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right; white-space:nowrap;">&nbsp;</label>
		        	<span id="copyObject" style="display: none">
							【平台订单号：${wo_display.platform_number }】,<br>
							【运单号：${wo_display.express_number }】,<br>
							【物流服务商：${wo_display.carrier_display }】,
							【工单类型：${wo_display.wo_type_display }】,<br>
							【异常类型：${wo_display.exception_type }】,<br>
							【主要处理内容说明：${wo_display.process_content }】,<br>
							【收件人：${wo_display.recipient }】,<br>
							【联系电话：${wo_display.phone }】,<br>
		        	        【详细收货地址：${wo_display.address }】
		        	</span>
		        	<a class="red" onclick="cccp()">点击复制</a>
		        	<!-- <a id="cpClick" class="a-copy red" data-clipboard-action="copy" data-clipboard-target="#copyObject">点击复制</a> -->
		    </div>
			<div class="row">
				<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">补充说明&nbsp;:</label>
				<label class="col-xs-10">${wo_display.supplement_explain }</label>
			</div>
			<div class="row">
				<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">备注&nbsp;:</label>
				<label class="col-xs-10">${remarkMain}</label>
			</div>
		    <div class="row">
		    	<label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">主要处理内容说明&nbsp;:</label>
		        <label class="col-xs-10">${wo_display.process_content }</label>
		    </div>
		    <div class="row" ${wo_display.pause_reason == null||wo_display.pause_reason==""?"style='display: none'":"" } >
		        <label class="col-xs-2 no-padding-right blue" style="text-align:right;white-space:nowrap;">挂起原因&nbsp;:</label>
		        <label class="col-xs-10">${wo_display.pause_reason }</label>
		    </div>
		    <div class="row" align="right">
		    	<input type="button" onclick="OneKeyDown('${createdFile}')" value="工单附件下载"/>
		    	<%-- <a class="red" onclick="OneKeyDown('${createdFile}')">工单附件下载</a> --%>
		    </div>
		</div>
		<ul class="nav nav-pills" role="tablist" >
	    	<li role="presentation" ><a href="#event_monitor" data-target=".event_monitor" data-toggle="pill">日志明细</a></li>
		    <li role="presentation" class="active" ><a href="#operation" data-target=".operation" data-toggle="pill">详情操作</a></li>
		    <li role="presentation" ><a href="#package_detail" data-target=".package_detail" data-toggle="pill">包裹明细</a></li>
		    <li role="presentation" ><a href="#route_detail" data-target=".route_detail" data-toggle="pill">路由明细</a></li>
		    <li role="presentation" ><a href="#warning_detail" data-target=".warning_detail" data-toggle="pill">预警信息</a></li>
		</ul>
		<div class="tab-content no-padding">
		    <!-- 事件明细-->
		    <div class="tab-pane fade event_monitor">
		        <jsp:include page="/work_order/wo_management/wo_event_monitor.jsp" flush="true" />
		    </div>
		    <div class="tab-pane fade in active operation">
		    	<jsp:include page="/work_order/wo_management/wo_operation.jsp" flush="true" />
		    </div>
		    <div class="tab-pane fade package_detail">
		        <jsp:include page="/work_order/wo_management/wo_package_detail.jsp" flush="true" />
		    </div>
		    <div class="tab-pane fade route_detail">
		        <jsp:include page="/work_order/wo_management/wo_route_detail.jsp" flush="true" />
		    </div>
		    <div class="tab-pane fade warning_detail">
		        <jsp:include page="/work_order/wo_management/wo_warning_detail.jsp" flush="true" />
		    </div>
		</div>
	</div>
</div>
<jsp:include page="/work_order/wo_management/wo_operation_detail.jsp" flush="true" />