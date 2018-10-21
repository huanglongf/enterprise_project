<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="func" uri="/WEB-INF/tld/func.tld" %>
<div>
<!-- 
	<a style='display: block;width:100%;padding:0px;padding-top:8px;padding-bottom:8px;text-align: center;color: black;background-color: #EEB422;' onclick="toShowAssociationSplit();">查看子单详情</a>
	 -->
	<div style="overflow:auto; border:solid #F2F2F2 1px;">
		<table id="table_split_content3" class="table table-hover table-striped no-margin">
	   		<thead>
		  		<tr class="table_head_line">
		  			<td class="td_cs" title="工单号">工单号</td>
		  			<td class="td_cs" title="拆分人">拆分人</td>
		  			<td class="td_cs" title="拆分到">拆分到</td>
		  			<td class="td_cs" title="是否完结">是否完结</td>
		  		</tr>
			</thead>
			<tbody align="center">
		  		<c:forEach items="${split_list }" var="res">
		  			<tr style="cursor:pointer;">
			  			<td class="td_cs" title="${res.wo_no }（双击进入）">${res.wo_no }</td>
			  			<td class="td_cs" title="${res.create_by_display }（双击进入）">${res.create_by_display }</td>
			  			<td class="td_cs" title="${res.logistics_provider }（双击进入）">${res.split_to }</td>
			  			<td class="td_cs" title="${res.wo_status }（双击进入）">${func:getDictLabel('wo_store_master_wo_status', res.wo_status) }</td>
			  		</tr>
		  		</c:forEach>
	  		</tbody>
		</table>
	</div>
</div>