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
		style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"
		id="sc_content">
		<table id="table_content" class="table table-hover table-striped"
			style="table-layout: fixed;">
			<thead align="center">
				<tr class='table_head_line'>
					<td style="width: 13px"><input type="checkbox" id="checkAll"
						onclick="inverseCkb('ckb')" /></td>
					<td class="td_cs" style="width: 50px">序号</td>
						<td class="td_cs" style="width: 200px">导入批次号</td>
						<td class="td_cs" style="width: 100px">转换成功条数</td>
						<td class="td_cs" style="width: 100px">转换失败条数</td>
						<td class="td_cs" style="width: 100px">导入总条数</td> 
						<td class="td_cs" style="width: 100px">导入日期</td>
						<td class="td_cs" style="width: 100px">当前转换状态</td>
				</tr>
			</thead>
			<tbody id='tbody' align="center">
				<c:forEach items="${pageView.records}" var="records"
					varStatus='status'>
					<tr ondblclick='toUpdate("${records.id}")'>
						<td><input id="ckb" name="ckb" type="checkbox"
							value="${records.id}"></td>
						<td class="td_cs" style="width: 50px" title="${status.count }">${status.count }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.bat_id }">${records.bat_id }</td>
							<td class="td_cs" style="width: 100px"
								title="${records.total_num }">${records.success_num }</td>
							<td class="td_cs" style="width: 100px"
								title="${records.total_num }">${records.fail_num }</td>
							<td class="td_cs" style="width: 100px"
								title="${records.total_num }">${records.total_num }</td>
							<td class="td_cs" style="width: 100px" title="${records.create_time }">
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 100px"title="${records.total_num }">
									<c:if test="${records.flag=='2'}">数据有误，未转换</c:if>
		   							<c:if test="${records.flag=='0'}">未转换</c:if>
		   							<c:if test="${records.flag=='4'}">已转换</c:if>
		   							<c:if test="${records.flag=='3'}">正在转</c:if>
							</td>
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
