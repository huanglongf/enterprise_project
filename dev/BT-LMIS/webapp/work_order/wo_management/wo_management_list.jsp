<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	$(function(){
		if($("#loadingCircle")) {
			// 解除旋转
			cancelLoadingStyle();
			
		}
		$("#table_detail_content tbody tr").dblclick(function(){toProcessForm("query", $(this));});
		
	});
</script>
<div id="wo_management_platform_list" class="form-group">
	<div class="title_div" id="sc_title">
		<table class="table table-striped" style="table-layout:fixed;">
	   		<thead>
		  		<tr class="table_head_line">
		  			<td class="td_ch" ><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" /></td>
		  			<td class="td_cs" title="工单号">工单号</td>
		  			<td class="td_cs" title="快递单号">快递单号</td>
		  			<td class="td_cs" title="平台订单号">平台订单号</td>
		  			<td class="td_cs" title="相关单据号">相关单据号</td>
		  			<td class="td_cs" title="发货仓库">发货仓库</td>
		  			<td class="td_cs" title="店铺">店铺</td>
		  			<td class="td_cs" title="物流服务商">物流服务商</td>
		  			<td class="td_cs" title="升级剩余时间（分钟）">
	  					<i class="fa fa-sort" aria-hidden="true" style="cursor:pointer;" onclick= "sort($(this), window.event, 'remaining_levelup_time');"></i>
		  				升级剩余时间（分钟）
		  			</td>
		  			<td class="td_cs" title="预警天数">预警天数</td>
		  			<td class="td_cs" title="工单类型">工单类型</td>
		  			<td class="td_cs" title="异常类型">异常类型</td>
		  			<td class="td_cs" title="处理者">处理者</td>
		  			<td class="td_cs" title="计划完成时间">计划完成时间</td>
		  			<td class="td_cs" title="工单处理状态">工单处理状态</td>
		  			<td class="td_cs" title="工单分配状态">工单分配状态</td>
		  			<td class="td_cs" title="工单级别">工单级别</td>
		  			<td class="td_cs" title="出库时间">出库时间</td>
		  			<td class="td_cs" title="查询人">查询人</td>
		  			<td class="td_cs" title="创建者">创建者</td>
		  			<td class="td_cs" title="创建时间">创建时间</td>
		  			<td class="td_cs" title="创建来源">创建来源</td>
		  			<td class="td_cs" title="分配者">分配者</td>
		  			<td class="td_cs" title="标准工时（分钟）">标准工时（分钟）</td>
		  			<td class="td_cs" title="实际工时（分钟）">实际工时（分钟）</td>
		  			<td class="td_cs" title="索赔状态">索赔状态</td>
		  			<td class="td_cs" title="索赔编码">索赔编码</td>
		  			<td class="td_cs" title="索赔分类">索赔分类</td>
		  			<td class="td_cs" title="申请索赔金额">申请索赔金额</td>
		  			<td class="td_cs" title="索赔失败原因">索赔失败原因</td>
		  		</tr>
			</thead>
		</table>
	</div>
	<div class="tree_div" ></div>
	<div style="height:400px; overflow:auto; overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
		<table id="table_detail_content" class="table table-hover table-striped" style="table-layout: fixed;">
	  		<tbody align="center">
		  		<c:forEach items="${pageView.records }" var="res">
		  			<tr style="cursor:pointer;">
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id }" /></td>
			  			<td class="td_cs" title="${res.wo_no }（双击进入）">${res.wo_no }</td>
			  			<td class="td_cs" title="${res.express_number }（双击进入）">${res.express_number }</td>
			  			<td class="td_cs" title="${res.platform_number }（双击进入）">${res.platform_number }</td>
			  			<td class="td_cs" title="${res.related_number }（双击进入）">${res.related_number }</td>
			  			<td class="td_cs" title="${res.warehouse_display }（双击进入）">${res.warehouse_display }</td>
			  			<td class="td_cs" title="${res.store_display }（双击进入）">${res.store_display }</td>
			  			<td class="td_cs" title="${res.carrier_display }（双击进入）">${res.carrier_display }</td>
			  			<td class="td_cs" title="${res.remaining_levelup_time }（双击进入）">${res.remaining_levelup_time }</td>
		  				<td class="td_cs" title="${res.warningDays }（双击进入）">${res.warningDays }</td>
			  			<td class="td_cs" title="${res.wo_type_display }（双击进入）">${res.wo_type_display }</td>
			  			<td class="td_cs" title="${res.exception_type }（双击进入）">${res.exception_type }</td>
			  			<td class="td_cs" title="${res.processor_display }（双击进入）">${res.processor_display }</td>
			  			<td class="td_cs" title="${res.estimated_time_of_completion }（双击进入）">${res.estimated_time_of_completion }</td>
			  			<td class="td_cs" title="${res.wo_process_status_display }（双击进入）">${res.wo_process_status_display }</td>
			  			<td class="td_cs" title="${res.wo_alloc_status_display }（双击进入）">${res.wo_alloc_status_display }</td>
			  			<td class="td_cs" title="${res.wo_level_display }（双击进入）">${res.wo_level_display }</td>
			  			<td class="td_cs" title="${res.transport_time }（双击进入）">${res.transport_time }</td>
			  			<td class="td_cs" title="${res.query_person }（双击进入）">${res.query_person }</td>
			  			<td class="td_cs" title="${res.create_by_display }（双击进入）">${res.create_by_display }</td>
			  			<td class="td_cs" title="${res.create_time }（双击进入）">${res.create_time }</td>
			  			<td class="td_cs" title="${res.wo_source }（双击进入）">${res.wo_source }</td>
			  			<td class="td_cs" title="${res.allocated_by_display }（双击进入）">${res.allocated_by_display }</td>
			  			<td class="td_cs" title="${res.standard_manhours }（双击进入）">${res.standard_manhours }</td>
			  			<td class="td_cs" title="${res.actual_manhours }（双击进入）">${res.actual_manhours }</td>
			  			<td class="td_cs" title="${res.claim_status }（双击进入）">${res.claim_status }</td>
			  			<td class="td_cs" title="${res.claim_number }（双击进入）">${res.claim_number }</td>
			  			<td class="td_cs" title="${res.claim_type }（双击进入）">${res.claim_type }</td>
			  			<td class="td_cs" title="${res.total_applied_claim_amount }（双击进入）">${res.total_applied_claim_amount }</td>
			  			<td class="td_cs" title="${res.failure_reason }（双击进入）">${res.failure_reason }</td>
			  		</tr>
		  		</c:forEach>
	  		</tbody>
		</table>
	</div>
	<div style="margin-right:1%; margin-top:20px;">${pageView.pageView }</div>
</div>