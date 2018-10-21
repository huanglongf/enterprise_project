<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<body>
	<div class="tree_div"></div>
	<div
		style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
		id="sc_content">
		<table id="table_content" class="table table-hover table-striped"
			style="table-layout: fixed;">
			<thead align="center">
				<tr class='table_head_line'>
					<td class="td_cs" style="width: 100px">序号</td>
						<td class="td_cs" style="width: 100px">导入时间</td>
						<td class="td_cs" style="width: 100px">操作人</td>
						<td class="td_cs" style="width: 100px">订单号</td>
						<td class="td_cs" style="width: 100px">错误信息</td> 
				</tr>
			</thead>
			<tbody id='tbody' align="center">
				<c:forEach items="${pageView.records}" var="records"
					varStatus='status'>
						<td class="td_cs" style="width: 100px" title="${status.count }">${status.count }</td>
						<td class="td_cs" style="width: 200px" title="${records.create_time }">
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 200px"
								title="${records.create_user }">${records.create_user }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.create_user }">${records.create_user }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.remark }">${records.remark }</td>
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
