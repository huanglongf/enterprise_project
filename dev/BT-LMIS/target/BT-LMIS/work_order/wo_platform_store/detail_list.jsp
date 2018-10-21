<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	$(function(){
		$("#table_detail_content tbody tr").dblclick(function(){
			var id = $(this).children(":first").children(":first").val();
			var waybill = $(this).children().eq(2).text();
			$.ajax({
				type: "POST",
		        url: "/BT-LMIS/control/workOrderPlatformStoreController/orderDetail.do",
		        dataType: "text",
		        data: {id:id,waybill:waybill,sel_flag:2},
		        success : function(data){
		        	$("#packages_form").remove();
		        	$("body").append(data);
					$("#packages_form").modal({backdrop: "static", keyboard: false});
					// 隐藏遮罩
					$(".modal-backdrop").hide();
		        }
			}); 
		});
	});
</script>
<div id="add_list">
	<div style="overflow:auto; border:solid #F2F2F2 1px;">
		<table id="table_detail_content" class="table table-hover table-striped no-margin">
	   		<thead>
		  		<tr class="table_head_line">
		  			<td class="td_ch" ><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" ${flag == '1'?"style='display: none'":'' }/></td>
		  			<td class="td_cs" title="平台订单号">平台订单号</td>
		  			<td class="td_cs" title="运单号">运单号</td>
		  			<td class="td_cs" title="物流商">物流商</td>
		  			<td class="td_cs" title="收件人">收件人</td>
		  			<td class="td_cs" title="联系电话">联系电话</td>
		  		</tr>
			</thead>
			<tbody align="center">
		  		<c:forEach items="${records }" var="res">
		  			<tr style="cursor:pointer;">
		  				<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id }" ${flag == '1'?"checked='checked' style='display: none'":'' }/></td>
			  			<td class="td_cs" title="${res.platform_no }（双击进入）">${res.platform_no }</td>
			  			<td class="td_cs" title="${res.waybill }（双击进入）">${res.waybill }</td>
			  			<td class="td_cs" title="${res.logistics_provider }（双击进入）">${res.logistics_provider }</td>
			  			<td class="td_cs" title="${res.shiptoname }（双击进入）">${res.shiptoname }</td>
			  			<td class="td_cs" title="${res.phone }（双击进入）">${res.phone }</td>
			  		</tr>
		  		</c:forEach>
	  		</tbody>
		</table>
	</div>
</div>
<!-- 弹窗 -->
<div id="model_content">
	<jsp:include page="/work_order/wo_platform_store/package_list.jsp" flush="true" />
</div>