<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	$(function(){
		// 内容宽度，隐藏scroll
		hideYScroll("#sc_content2", $("#sc_title2").width());
		$(window).resize(function(){
			hideYScroll("#sc_content2", $("#sc_title2").width());
		});
		$("#sc_title2").resize(function(){
			hideYScroll("#sc_content2", $("#sc_title2").width());
		})
	})
</script>

<div id="packages_form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="formLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg" style="width:900px;" role="document">
		<div class="modal-content" style="border: 3px solid #394557;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">订单明细</h4>
			</div>
			<div id="modal-body" class="modal-body">
				<input id="warehouse_name" name="warehouse_name" type="hidden" />
				<div class="row form-group">
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">店铺&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.store_name}</label>
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">仓库&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.warehouse_name}</label>
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">物流商&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.transport_name}</label>
				</div>
				<div class="row form-group">
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">平台订单号&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.platform_no}</label>
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">快递单号&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.waybill}</label>
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">收件人&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.shiptoname}</label>
				</div>
				<div class="row form-group">
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">联系电话&nbsp;:</label>
					<label class="col-md-2" align="left" style="white-space: nowrap;">${detail.phone}</label>
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">详细地址&nbsp;:</label>
					<label class="col-md-6" align="left" style="word-wrap: break-word;">${detail.address}</label>
				</div>
				<div class="row form-group">
					<label class="col-md-2 blue" align="right" style="white-space: nowrap;">包裹明细&nbsp;:</label>
					<div class="col-md-10">
						<div class="table-border">
							<div class="table-title no-margin-top" id="sc_title2">
								<table class="table table-bordered no-margin table-fixed">
							   		<thead>
								  		<tr>
								  			<th class="text-center table-text col-lg" title="SKU条码">SKU条码</th>
								  			<th class="text-center table-text col-lg" title="条形码">条形码</th>
								  			<th class="text-center table-text col-lg" title="商品名称">商品名称</th>
								  			<th class="text-center table-text col-lg" title="扩展属性">扩展属性</th>
								  			<th class="text-center table-text col-lg" title="商品数量">商品数量</th>
								  		</tr>
								  	</thead>
								</table>
							</div>
							<div class="table-content-parent">
								<div class="table-content table-content-free" style="height: 200px;" id="sc_content2" onscroll="syntable($('#sc_title2'),$('#sc_content2'));">
									<table class="table table-striped table-bordered table-hover" style="table-layout: fixed;">
		  								<tbody>
		  									<c:forEach items="${packages}" var="res">
									  			<tr class="pointer">
										  			<td class="text-center table-text col-lg" title="${res.sku_number }">${res.sku_number }</td>
										  			<td class="text-center table-text col-lg" title="${res.barcode }">${res.barcode }</td>
										  			<td class="text-center table-text col-lg" title="${res.item_name }">${res.item_name }</td>
										  			<td class="text-center table-text col-lg" title="${res.extend_pro }">${res.extend_pro }</td>
										  			<td class="text-center table-text col-lg" title="${res.qty }">${res.qty }</td>
										  		</tr>
									  		</c:forEach>
		  								</tbody>
		  							</table>
								</div>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>