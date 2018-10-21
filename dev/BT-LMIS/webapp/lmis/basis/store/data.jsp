<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/lmis/yuriy.jsp" %>
<script type="text/javascript">
	$(function(){$("#table_content tbody tr").dblclick(function(){toForm($(this));});});
</script>
<div id="data" class="form-group">
	<div class="title_div no-margin-top" id="sc_title">		
		<table class="table table-striped" style="table-layout: fixed;">
	   		<thead>
		  		<tr class="table_head_line">
		  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" /></td>
		  			<td class="td_cs">成本中心</td>
		  			<td class="td_cs">店铺编码</td>
		  			<td class="td_cs">店铺名称</td>
		  			<td class="td_cs">所属客户</td>
		  			<td class="td_cs">店铺类型</td>
		  			<td class="td_cs">结算方式</td>
		  			<td class="td_cs">联系人</td>
		  			<td class="td_cs">联系电话</td>
		  			<td class="td_cs">联系地址 </td>
		  			<td class="td_cs">开票公司 </td>
		  			<td class="td_cs">发票类型 </td>
		  			<td class="td_cs">发票信息 </td>
		  			<td class="td_cs">备注</td>
		  			<td class="td_cs">雷达监控</td>
		  			<td class="td_cs">有效性</td>
					<td class="td_cs">操作</td>
		  		</tr>
			</thead>
		</table>
	</div>
	<div class="tree_div"></div>
	<div style="height:400px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
		<table id="table_content" class="table table-hover table-striped" style="table-layout: fixed;">
			<tbody align="center">
				<c:forEach items="${pageView.records }" var="res">
					<tr style="cursor:pointer;">
						<td class="td_ch" ><input id="ckb" name="ckb" type="checkbox" value="${res.id }" ></td>
						<td class="td_cs" title= "${res.cost_center }（双击编辑）">${res.cost_center }</td>
						<td class="td_cs" title= "${res.store_code }（双击编辑）">${res.store_code }</td>
						<td class="td_cs" title= "${res.store_name }（双击编辑）">${res.store_name }</td>
						<td class="td_cs" title= "${res.client_name }（双击编辑）">${res.client_name }</td>
						<td class="td_cs" title= "${res.store_type }（双击编辑）">${res.store_type }</td>
						<td class="td_cs" title= "${res.settlement_method }（双击编辑）">${res.settlement_method }</td>
						<td class="td_cs" title= "${res.contact }（双击编辑）">${res.contact }</td>
						<td class="td_cs" title= "${res.phone }（双击编辑）">${res.phone }</td>
						<td class="td_cs" title= "${res.address }（双击编辑）">${res.address }</td>
						<td class="td_cs" title= "${res.company }（双击编辑）">${res.company }</td>
						<td class="td_cs" title= "${res.invoice_type }（双击编辑）">${res.invoice_type }</td>
						<td class="td_cs" title= "${res.invoice_info }（双击编辑）">${res.invoice_info }</td>
						<td class="td_cs" title= "${res.remark }（双击编辑）">${res.remark }</td>
						<td class="td_cs" title= "${res.wo_flag }（双击编辑）">${res.wo_flag }</td>
						<td class="td_cs" title= "${res.validity }（双击编辑）">${res.validity }</td>
						<td class="td_cs">
							<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}control/storeController/storeSetTransport.do?storeCode=${res.store_code}&id=${res.id}');">
								<i class="icon-refresh"></i>快递保价
							</button>
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 分页添加 -->
	<div style= "margin-right: 30px; margin-top: 20px;" >${pageView.pageView }</div>
</div>
