<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse" /></title>
<script type="text/javascript"
	src="<s:url value='/scripts/frame/report/wh-order-status-count.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

<script type="text/javascript"
	src="<s:url value='/scripts/Chart.min.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

<style>
.showborder {
	border: thin;
}

table.def {
	font-family: verdana, arial, sans-serif;
	font-size: 18px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 14px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 1px;
	padding: 14px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<table>
		<tr>
			<td>统计时间</td>
			<td><input loxiaType="date" style="width: 150px"
				name="startDate" id="startDate" showTime="true" /></td>
			<td>-</td>
			<td><input loxiaType="date" style="width: 150px" name="endDate"
				id="endDate" showTime="true" /></td>
			<td>
				<button id="reflush" type="button" loxiaType="button"
					class="confirm">刷新</button>
			</td>
		</tr>
	</table>
	<br />
	<table>
		<tr>
			<td width="600px" valign="top"><div id="tabs">
					<ul>
						<li><a href="#tabs_1">订单状态汇总</a></li>
						<li><a href="#tabs_2">快递状态汇总</a></li>
					</ul>
					<div id="tabs_1">
						<table id="tbl_order" class="gridtable">
							<thead>
								<tr>
									<th width="50px"></th>
									<th>合计</th>
									<th>新建</th>
									<th>占用</th>
									<th>建批</th>
									<th>核对</th>
									<th>出库</th>
									<th>完成</th>
									<th>取消</th>
								</tr>
							</thead>
							<tbody id="tbl_order_tby">
							</tbody>
						</table>
					</div>
					<div id="tabs_2">
						<table id="tbl_trans" class="gridtable">
							<thead>
								<tr>
									<th width="50px"></th>
									<th>合计</th>
									<th>新建</th>
									<th>占用</th>
									<th>建批</th>
									<th>核对</th>
									<th>出库</th>
									<th>完成</th>
									<th>取消</th>
								</tr>
							</thead>
							<tbody id="tbl_trans_tby">
							</tbody>
						</table>
					</div>
				</div></td>
			<td width="400px" valign="top"><div id="canvas-holder"></div></td>
		</tr>
	</table>

</body>
</html>