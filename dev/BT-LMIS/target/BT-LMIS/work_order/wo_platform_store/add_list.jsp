<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	$(function(){
		// 内容宽度，隐藏scroll
		hideYScroll("#sc_content", $("#sc_title").width());
		$(window).resize(function(){
			hideYScroll("#sc_content", $("#sc_title").width());
		});
		$("#sc_title").resize(function(){
			hideYScroll("#sc_content", $("#sc_title").width());
		})
		//
		$("#sc_content tbody tr").dblclick(function(){
			var id = $(this).children(":first").children(":first").val();
			var waybill = $(this).children().eq(2).text();
			var sel_flag = $("#sel_flag").val();
			$.ajax({
				type: "POST",
		        url: "/BT-LMIS/control/workOrderPlatformStoreController/orderDetail.do",
		        dataType: "text",
		        data: {id:id,waybill:waybill,sel_flag:sel_flag},
		        success : function(data){
		        	$("#model_content").html(data);
					$("#packages_form").modal();
					// 隐藏遮罩
					$(".modal-backdrop").hide();
		        }
			}); 
		});
	});
</script>
<div class="table-border">
	<div class="table-title no-margin-top" id="sc_title">
		<table class="table table-bordered no-margin table-fixed">
	   		<thead>
		  		<tr>
		  			<th class="text-center table-text col-sm" id="th_flag" >
						<input id="checkAllTable" type="checkbox" onclick="inverseCkb('ckbs')" ${flag == '1' ? "disabled='disabled' checked='checked'" : ''}/>
		  			</th>
		  			<th class="text-center table-text col-lg" title="平台订单号">平台订单号</th>
		  			<th class="text-center table-text col-lg" title="运单号">运单号</th>
		  			<th class="text-center table-text col-lg" title="物流商">物流商</th>
		  			<th class="text-center table-text col-lg" title="收件人">收件人</th>
		  			<th class="text-center table-text col-lg" title="联系电话">联系电话</th>
		  		</tr>
			</thead>
		</table>
	</div>
	<div class="table-content-parent">
		<div class="table-content table-content-free" style="height: 200px;" id="sc_content" onscroll="syntable($('#sc_title'),$('#sc_content'));">
			<table class="table table-striped table-bordered table-hover" style="table-layout: fixed;">
		  		<tbody>
			  		<c:forEach items="${records }" var="res">
			  			<tr class="pointer">
			  				<td class="text-center table-text col-sm" >
								<input id="ckbs" name="ckbs" type="checkbox" value="${res.id }" ${flag == '1' ? "disabled='disabled' checked='checked'" : ''}/>
			  				</td>
				  			<td class="text-center table-text col-lg" title="${res.platform_no }（双击进入）">${res.platform_no }</td>
				  			<td class="text-center table-text col-lg" title="${res.waybill }（双击进入）">${res.waybill }</td>
				  			<td class="text-center table-text col-lg" title="${res.logistics_provider }（双击进入）">${res.logistics_provider }</td>
				  			<td class="text-center table-text col-lg" title="${res.shiptoname }（双击进入）">${res.shiptoname }</td>
				  			<td class="text-center table-text col-lg" title="${res.phone }（双击进入）">${res.phone }</td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
	</div>
</div>
