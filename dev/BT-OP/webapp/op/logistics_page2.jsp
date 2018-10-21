<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
<body>
	<div class="tree_div"></div>
	<div
		style="height: 300px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
		id="sc_content">
		<table id="table_content" class="table table-hover table-striped"
			style="table-layout: fixed;">
			<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td class="td_cs" style="width: 10px"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class="td_cs"  style="width: 50px">序号</td>
			  			<td class="td_cs" style="width: 140px">制单日期</td>
						<td class="td_cs" style="width: 180px">发货门店</td>
						<td class="td_cs" style="width: 180px">收货门店</td>
						<td class="td_cs" style="width: 140px">快递单号</td>
						<td class="td_cs" style="width: 100px">状态</td>
						<td class="td_cs" style="width: 140px">运单号</td>
						<td class="td_cs" style="width: 140px">客户单号</td>
						<td class="td_cs" style="width: 140px">总件数</td>
						<td class="td_cs" style="width: 140px">下单日期</td>
			  		</tr>  	
				</thead>
				<tbody  align="center">
			  		<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td class="td_cs" style="width: 10px" ><input id="ckb" name="ckb" type="checkbox" value="${records.id}"></td>
							<td class="td_cs" style="width: 50px;height: 36px" >${status.count }</td>
							<td class="td_cs" style="width: 140px;height: 36px" title="${records.order_time }">
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 180px;height: 36px"
								>${records.from_orgnization }</td>
							<td class="td_cs" style="width: 180px;height: 36px"
								>${records.to_orgnization }</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.waybill }</td>
							<td class="td_cs" style="width: 100px;height: 36px" >
									<c:if test="${records.status=='1'}">已录单</c:if>
		   							<c:if test="${records.status=='0'}">已作废</c:if>
		   							<c:if test="${records.status=='2'}">待揽收</c:if>
		   							<c:if test="${records.status=='4'}">已下单</c:if>
		   							<c:if test="${records.status=='5'}">已揽收</c:if>
		   							<c:if test="${records.status=='6'}">已发运</c:if>
		   							<c:if test="${records.status=='7'}">已签收</c:if>
		   							<c:if test="${records.status=='8'}">签收失败</c:if>
		   							<c:if test="${records.status=='9'}">已退回</c:if>
		   							<c:if test="${records.status=='10'}">已取消</c:if>
		   							<c:if test="${records.status=='11'}">下单失败</c:if>
							</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.order_id }</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.customer_number }</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.total_qty }</td>
							<td class="td_cs" style="width: 140px;height: 36px" title="${records.order_time }">
							<fmt:formatDate value="${records.order_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
		  			</c:forEach>
			</tbody>
		</table>

	</div>
	<!-- 分页添加 -->
	<div style="margin-right: 1%; margin-top: 20px;">${pageView.pageView }</div>
</body>
</html>
<script>
</script>
<style>
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.title_div td {
	font-size: 15px;
}

.m-input-select {
	display: inline-block;
	position: relative;
	-webkit-user-select: none;

}
</style>
